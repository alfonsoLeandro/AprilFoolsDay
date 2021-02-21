package com.popupmc.aprilfoolsday.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.utils.Reloadable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

public class MainCommand implements CommandExecutor, Reloadable {

    private final AprilFoolsDay plugin;
    //Translatable messages from messages.yml
    private String prefix;
    private String noPerm;
    private String reloaded;
    private String unknown;


    public MainCommand(AprilFoolsDay plugin){
        this.plugin = plugin;
        loadMessages();
    }

    /**
     * Loads every messages from messages.yml to this command class.
     */
    private void loadMessages(){
        FileConfiguration messages = plugin.getMessagesYaml().getAccess();

        prefix = plugin.getConfigYaml().getAccess().getString("prefix");
        noPerm = messages.getString("no permission");
        reloaded = messages.getString("reloaded");
        unknown = messages.getString("unknown");
    }

    private void send(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+msg));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0 || args[0].equalsIgnoreCase("help")){
            send(sender, "&6List of commands:");
            send(sender, "/"+label+" help");
            send(sender, "/"+label+" version");
            send(sender, "/"+label+" reload");
            send(sender, "/"+label+" spawn");
            send(sender, "/toggle-joke <player>");


        }else if(args[0].equalsIgnoreCase("version")){
            if(!sender.hasPermission("aprilFools.version")){
                send(sender, noPerm);
                return true;
            }
            send(sender, "&fVersion: &a"+plugin.getDescription().getVersion());


        }else if(args[0].equalsIgnoreCase("reload")){
            if(!sender.hasPermission("aprilFools.reload")){
                send(sender, noPerm);
                return true;
            }
            plugin.reload();
            send(sender, reloaded);

        }else if(args[0].equalsIgnoreCase("spawn")){
            //TODO
            ProtocolManager manager = ProtocolLibrary.getProtocolManager();

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
                manager.sendServerPacket((Player) sender, playerInfoPacket);
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

        }else{
            send(sender, unknown.replace("%command%", label));
        }


        return true;
    }

    @Override
    public void reload() {
        this.loadMessages();
    }
}
