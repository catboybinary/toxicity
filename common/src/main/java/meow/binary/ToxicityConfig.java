package meow.binary;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class ToxicityConfig {
    public static ConfigClassHandler<ToxicityConfig> HANDLER = ConfigClassHandler.createBuilder(ToxicityConfig.class)
            .id(new ResourceLocation(Toxicity.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(IPlatform.getConfigPath().resolve("toxicity.json5"))
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    @AutoGen(category = "client")
    @MasterTickBox("rainColor")
    public boolean enableCustomRain = true;

    @SerialEntry
    @AutoGen(category = "client")
    @ColorField
    public Color rainColor = new Color(0x649448);

    @SerialEntry(comment = "Damage to the player from the acid rain.")
    @AutoGen(category = "main")
    @IntSlider(min = 0, max = 20, step = 1)
    public int flatDamage = 1;

    @SerialEntry(comment = "Damage to the armor pieces from the acid rain.")
    @AutoGen(category = "main")
    @IntSlider(min = 0, max = 20, step = 1)
    public int flatArmorDamage = 1;

    @SerialEntry
    @AutoGen(category = "main")
    @IntSlider(min = 1, max = 100, step = 1)
    public int damageIntervalInTicks = 20;
}
