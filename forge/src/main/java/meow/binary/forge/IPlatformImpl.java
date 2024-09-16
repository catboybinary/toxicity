package meow.binary.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class IPlatformImpl {
    public static Path getConfigPath() {
        return FMLPaths.CONFIGDIR.get();
    }
}
