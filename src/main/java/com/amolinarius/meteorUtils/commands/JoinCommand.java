package com.amolinarius.meteorUtils.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.StringArgumentType;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;
import meteordevelopment.meteorclient.systems.accounts.types.CrackedAccount;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static meteordevelopment.meteorclient.MeteorClient.mc;

public class JoinCommand extends Command {
    public JoinCommand() {
        super("join", "Joins with specified nickname (Cracked servers only)");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("playerName", StringArgumentType.word()).executes(context -> {
            String playerName = context.getArgument("playerName", String.class);
            CrackedAccount account = new CrackedAccount(playerName);
            account.login();
            if (mc.player != null) {
                ServerInfo serverInfo = mc.player.networkHandler.getServerInfo();
                mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("Rejoin the server to apply new name.")));
                if (serverInfo != null) {
                    ConnectScreen.connect(
                        new MultiplayerScreen(new TitleScreen()),
                        MinecraftClient.getInstance(),
                        ServerAddress.parse(serverInfo.address),
                        serverInfo,
                        false
                    );
                }
                else {
                    error("Unable to find user's server. Try relogging manually.");
                }
            }
            else {
                error("Unable to disconnect user. Try relogging manually");
            }

            return SINGLE_SUCCESS;
        }));
    }
}
