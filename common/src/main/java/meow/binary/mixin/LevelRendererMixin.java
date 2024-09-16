package meow.binary.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import meow.binary.Toxicity;
import meow.binary.ToxicityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow @Final private static ResourceLocation RAIN_LOCATION;

    @Inject(method = "renderSnowAndRain", at = @At(value = "HEAD"))
    public void changeRainColor(LightTexture lightTexture, float f, double d, double e, double g, CallbackInfo ci) {
        if (!ToxicityConfig.HANDLER.instance().enableCustomRain) return;
        RenderSystem.setShaderColor(1f,1f,1f,1f);
    }

    @Inject(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/Tesselator;end()V", shift = At.Shift.BEFORE))
    public void changeColor(LightTexture lightTexture, float f, double d, double e, double g, CallbackInfo ci) {
        if (!ToxicityConfig.HANDLER.instance().enableCustomRain) return;

        Color color = ToxicityConfig.HANDLER.instance().rainColor;

        if (RenderSystem.getShaderTexture(0) == Minecraft.getInstance().getTextureManager().getTexture(RAIN_LOCATION).getId()) {
            RenderSystem.setShaderTexture(0, new ResourceLocation(Toxicity.MOD_ID, "textures/environment/rain.png"));
            RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
        }
    }

    @Inject(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/Tesselator;end()V", shift = At.Shift.AFTER))
    public void changeColor2(LightTexture lightTexture, float f, double d, double e, double g, CallbackInfo ci) {
        if (!ToxicityConfig.HANDLER.instance().enableCustomRain) return;
        RenderSystem.setShaderColor(1f,1f,1f,1f);
    }

    @Inject(method = "renderSnowAndRain", at = @At(value = "TAIL"))
    public void revert(LightTexture lightTexture, float f, double d, double e, double g, CallbackInfo ci) {
        if (!ToxicityConfig.HANDLER.instance().enableCustomRain) return;
        RenderSystem.setShaderColor(1f,1f,1f,1f);
    }
}
