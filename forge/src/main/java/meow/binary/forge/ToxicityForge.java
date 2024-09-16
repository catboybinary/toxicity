package meow.binary.forge;

import meow.binary.Toxicity;
import meow.binary.ToxicityConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(Toxicity.MOD_ID)
public final class ToxicityForge {
    public ToxicityForge() {
        // Run our common setup.
        Toxicity.init();
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> ToxicityConfig.HANDLER.generateGui().generateScreen(parent)
                )
        );
    }
}
