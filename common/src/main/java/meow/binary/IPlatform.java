package meow.binary;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public interface IPlatform {

    @ExpectPlatform
    static Path getConfigPath() {
        return null;
    }

}