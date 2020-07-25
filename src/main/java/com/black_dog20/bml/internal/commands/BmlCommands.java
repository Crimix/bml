package com.black_dog20.bml.internal.commands;

import com.black_dog20.bml.Bml;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bml.MOD_ID)
public class BmlCommands {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("bml");
        new CommandConfigGui().register(builder);
        dispatcher.register(builder);
    }
}
