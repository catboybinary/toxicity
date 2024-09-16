package meow.binary.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class IPlatformImpl {
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
