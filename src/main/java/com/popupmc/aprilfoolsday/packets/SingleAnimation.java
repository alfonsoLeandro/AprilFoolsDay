package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;
import org.bukkit.entity.Player;

public class SingleAnimation extends PacketAdapter {
    public SingleAnimation(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.ANIMATION);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(player)
        || settings.isSingleAnimationDisabled()) return;

        PacketContainer packet = event.getPacket();

        // Exclude the player by comparing entity ids
        if(packet.getIntegers().read(0) == player.getEntityId())
            return;

        // All animations are off-hand arm swing
        packet.getIntegers().write(1, settings.getAnimation().getId());
    }
}
