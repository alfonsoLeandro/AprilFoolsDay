package com.popupmc.aprilfoolsday.events;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoin implements Listener {

    private final AprilFoolsDay plugin;

    public PlayerJoin(AprilFoolsDay plugin){
        this.plugin = plugin;
    }

    private void send(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigYaml().getAccess().getString("prefix")+" "+msg));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Settings settings = plugin.getSettings();
        Player player = event.getPlayer();

        //Resource pack
        String resourcePackUrl = settings.getResourcePackUrl();
        if(isNotNullOrEmpty(resourcePackUrl)){
            String sha1 = settings.getResourcePackSha1();
            if(isNotNullOrEmpty(sha1)){
                player.setResourcePack(resourcePackUrl, sha1);
            }else{
                player.setResourcePack(resourcePackUrl);
            }
        }

        //Get the status
        boolean status;
        if(ToggleJokeCommand.statusContainsPlayer(player.getName())){
            status = ToggleJokeCommand.getJokeStatus(player.getName());
        }else{
            if((player.isOp() && settings.isOpBypass()) || player.hasPermission("aprilFools.default.bypass")){
                status = false;
            }else {
                status = settings.jokeDefaultStatus();
                ToggleJokeCommand.putPlayerStatus(player.getName(), status);
            }
        }


        //Status message
        if(settings.isStatusMessageEnabled()){
            new BukkitRunnable(){
                @Override
                public void run(){
                    send(player, plugin.getMessagesYaml().getAccess().getString(status ? "status enabled" : "status disabled"));
                }

            }.runTaskLater(plugin, settings.getStatusMessageDelayTicks());

        }
    }


    private boolean isNotNullOrEmpty(String string){
        return string != null && !string.equalsIgnoreCase("");
    }

}
