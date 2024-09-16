package meow.binary.mixin;

import meow.binary.ToxicityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityTickMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void doDamage(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Level level = entity.level();
        ToxicityConfig config = ToxicityConfig.HANDLER.instance();
        if (!level.isClientSide() && entity.tickCount % config.damageIntervalInTicks == 0 && level.isRaining()) {

            if (!isInRain(entity)) return;
            int armorPieces = 0;

            for (ItemStack i : entity.getArmorSlots()) {
                if (i.getItem() instanceof ArmorItem) armorPieces++;
            }

            float damage = config.flatDamage * (1f - armorPieces / 4f);


            if (damage > 0) {
                entity.invulnerableTime = 0;
                entity.hurt(level.damageSources().magic(), damage);
            }
            hurtLivingEntityArmor(entity, level.damageSources().magic(), config.flatArmorDamage);
        }
    }

    private boolean isInRain(LivingEntity entity) {
        BlockPos blockPos = entity.blockPosition();
        return entity.level().isRainingAt(blockPos) || entity.level().isRainingAt(BlockPos.containing(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
    }

    private void hurtLivingEntityArmor(LivingEntity ent, DamageSource arg, float f) {
        Iterator<ItemStack> iterator = ent.getArmorSlots().iterator();
        int i = 0;

        while (iterator.hasNext()) {
            ItemStack armor = iterator.next();

            if ((!arg.is(DamageTypeTags.IS_FIRE) || !armor.getItem().isFireResistant()) && armor.getItem() instanceof ArmorItem) {
                int finalI = i;
                armor.hurtAndBreak((int) f, ent, (argx) -> {
                    argx.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI));
                });
            }
            i++;
        }
    }
}
