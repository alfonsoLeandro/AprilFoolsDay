package com.popupmc.aprilfoolsday.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

public class OnFakePlayerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        Player p = (Player) sender;

        PacketContainer playerInfoPacket = manager.createPacket(PacketType.Play.Server.PLAYER_INFO);

        // New Player
        playerInfoPacket.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        // 1 Entry
        // playerInfoPacket.getIntegers().write(1, 1);

        // Create Sample Player
        OfflinePlayer samplePlayer = Bukkit.getOfflinePlayer(UUID.fromString("f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2"));
        PlayerInfoData samplePlayerData = new PlayerInfoData(
                WrappedGameProfile.fromOfflinePlayer(samplePlayer),
                1500,
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromJson("{\"text\":\"Steve\"}"));

        // Add to list to send to player
        ArrayList<PlayerInfoData> playerInfoData = new ArrayList<>();
        playerInfoData.add(samplePlayerData);

        // Add list to packet
        playerInfoPacket.getPlayerInfoDataLists().write(0, playerInfoData);

        // Send packet to player
        try {
            manager.sendServerPacket(p, playerInfoPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        // Prepare to create herobrine
//        PacketContainer herobrinePlayerSpawnPacket = manager.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
//
//        // Entity ID 9999
//        herobrinePlayerSpawnPacket.getIntegers().write(0, 9999);
//
//        // Write Herobrine UUID we just sent to the user
//        herobrinePlayerSpawnPacket.getUUIDs().write(0, UUID.fromString("f84c6a79-0a4e-45e0-879b-cd49ebd4c4e2"));
//
//        // Make within 5 blocks of player
//        Location location = p.getLocation();
//
//        herobrinePlayerSpawnPacket.getDoubles().write(0, location.getX() + 5d);
//        herobrinePlayerSpawnPacket.getDoubles().write(1, location.getY());
//        herobrinePlayerSpawnPacket.getDoubles().write(2, location.getZ() + 5d);
//
//        // Send packet to player
//        try {
//            manager.sendServerPacket(p, herobrinePlayerSpawnPacket);
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        return true;
    }
}
