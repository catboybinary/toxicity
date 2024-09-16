package meow.binary.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import meow.binary.ToxicityConfig;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ToxicityCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("toxicity").requires(s -> s.hasPermission(2))
                .then(Commands.literal("reload")
                        .executes(context -> {
                            ToxicityConfig.HANDLER.load();
                            return Command.SINGLE_SUCCESS;
                        })));
    }
}
