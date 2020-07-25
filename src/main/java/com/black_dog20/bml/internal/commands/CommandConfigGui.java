package com.black_dog20.bml.internal.commands;

import com.black_dog20.bml.internal.network.PacketHandler;
import com.black_dog20.bml.internal.network.messages.MessageOpenOverlayConfigGui;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CommandConfigGui {

    public void register(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(Commands.literal("overlayConfig")
                .executes(this::execute));
    }

    private int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        PacketHandler.sendTo(new MessageOpenOverlayConfigGui(), player);
        return Command.SINGLE_SUCCESS;
    }
}
