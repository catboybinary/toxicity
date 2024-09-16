package meow.binary;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import meow.binary.commands.ToxicityCommand;

public final class Toxicity {
    public static final String MOD_ID = "toxicity";

    public static void init() {
        // Write common init code here.
        ToxicityConfig.HANDLER.load();
        CommandRegistrationEvent.EVENT.register(ToxicityCommand::register);
    }
}
