package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;

public class NoEntitySound extends PacketAdapter {
    public NoEntitySound(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_SOUND);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer().getName())
        || ((AprilFoolsDay)plugin).getSettings().isNoEntitySoundDisabled()) return;

        event.setCancelled(true);
    }
}
