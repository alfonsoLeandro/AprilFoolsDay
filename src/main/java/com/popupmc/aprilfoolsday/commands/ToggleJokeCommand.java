package com.popupmc.aprilfoolsday.commands;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.settings.Settings;
import com.popupmc.aprilfoolsday.utils.Reloadable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ToggleJokeCommand implements CommandExecutor, Reloadable {

    private final static Map<String,Boolean> jokeStatus = new HashMap<>();
    private final AprilFoolsDay plugin;
    //Translatable messages
    private String prefix;
    private String noPerm;
    private String cannotToggle;
    private String enabled;
    private String disabled;
    private String notOnline;
    private String disabledForPlayer;
    private String enabledForPlayer;
    private String toggleCommandIsDisabled;
    private String reJoin;


    public ToggleJokeCommand(AprilFoolsDay plugin){
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages(){
        FileConfiguration messages = plugin.getMessagesYaml().getAccess();

        prefix = plugin.getConfigYaml().getAccess().getString("prefix");
        noPerm = messages.getString("no permission");
        cannotToggle = messages.getString("cannot toggle console");
        enabled = messages.getString("joke enabled");
        disabled = messages.getString("joke disabled");
        notOnline = messages.getString("not online");
        enabledForPlayer = messages.getString("enabled for player");
        disabledForPlayer = messages.getString("disabled for player");
        toggleCommandIsDisabled = messages.getString("toggle command disabled");
        reJoin = messages.getString("rejoin server");
    }

    private void send(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+msg));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!plugin.getSettings().isToggleCommandEnabled()){
            send(sender, toggleCommandIsDisabled);
            return true;
        }


        if(args.length > 0){
            if(!sender.hasPermission("aprilFools.toggle.others")){
                send(sender, noPerm);
                return true;
            }
            Player toToggle = Bukkit.getPlayer(args[0]);
            if(toToggle == null){
                send(sender, notOnline);
                return true;
            }
            toggle(sender, toToggle);
            send(sender, jokeStatus.get(args[0]) ? enabledForPlayer.replace("%player%", args[0]) : disabledForPlayer.replace("%player%", args[0]));


        }else{
            if(sender instanceof ConsoleCommandSender){
                send(sender, cannotToggle);
                return true;
            }
            if(!sender.hasPermission("aprilFools.toggle.self")){
                send(sender, noPerm);
                return true;
            }
            toggle(sender, (Player) sender);


        }

        return true;
    }

    private void toggle(CommandSender toggler, Player toToggle){
        Settings settings = plugin.getSettings();

        jokeStatus.put(toToggle.getName(), !jokeStatus.get(toToggle.getName()));
        if(settings.isKickOnToggle()) {
            toToggle.kickPlayer(jokeStatus.get(toToggle.getName()) ? enabled.replace("%toggler%", toggler.getName()) : disabled.replace("%toggler%", toggler.getName()));
        }else{
            send(toToggle, jokeStatus.get(toToggle.getName()) ? enabled.replace("%toggler%", toggler.getName()) : disabled.replace("%toggler%", toggler.getName()));
            send(toToggle, reJoin);
        }
    }


    @Override
    public void reload() {
        this.loadMessages();
    }

    /**
     * Gets the joke status of a given player.
     * @param player The player to check the joke status for.
     * @return True if the joke is enabled for the player.
     */
    public static boolean getJokeStatus(Player player){
        if(!jokeStatus.containsKey(player.getName())) {
            Settings settings = JavaPlugin.getPlugin(AprilFoolsDay.class).getSettings();
            boolean status;
            if((player.isOp() && settings.isOpBypass()) || player.hasPermission("aprilFools.default.bypass")) {
                status = false;
            } else {
                status = settings.jokeDefaultStatus();
            }
            putPlayerStatus(player.getName(), status);
        }
        return jokeStatus.get(player.getName());

    }

    /**
     * Checks whether the given player status has already been registered.
     * @param playerName The player to check the joke status for.
     * @return True if the player-boolean map contains the player status.
     */
    public static boolean statusContainsPlayer(String playerName){
        return jokeStatus.containsKey(playerName);
    }

    /**
     * Puts a player in the player-boolean status map.
     * @param playerName The player to put in the map.
     * @param value The value to be set.
     */
    public static void putPlayerStatus(String playerName, boolean value){
        jokeStatus.put(playerName, value);
    }
}
