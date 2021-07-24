package com.black_dog20.bml.internal.commands;

import com.black_dog20.bml.internal.network.PacketHandler;
import com.black_dog20.bml.internal.network.messages.MessageOpenOverlayConfigGui;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class CommandConfigGui {

    public void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("overlayConfig")
                .executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        PacketHandler.sendTo(new MessageOpenOverlayConfigGui(), player);
        return Command.SINGLE_SUCCESS;
    }
}
