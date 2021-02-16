package com.popupmc.aprilfoolsday.events;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
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
        if(resourcePackUrl != null && !resourcePackUrl.equalsIgnoreCase("")){
            String sha1 = settings.getResourcePackSha1();
            if(sha1 != null && !sha1.equalsIgnoreCase("")){
                player.setResourcePack(resourcePackUrl, sha1);
            }else{
                player.setResourcePack(resourcePackUrl);
            }
        }

        if(settings.isStatusMessageEnabled()){
            new BukkitRunnable(){

                @Override
                public void run(){
                    //TODO
                    send(player, plugin.getMessagesYaml().getAccess().getString(""));
                }

            }.runTaskLater(plugin, settings.getStatusMessageDelayTicks());
        }
    }

}
