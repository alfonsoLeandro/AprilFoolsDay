package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;

public class SinglePaintings extends PacketAdapter {
    public SinglePaintings(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.SPAWN_ENTITY_PAINTING);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer().getName())
        || ((AprilFoolsDay)plugin).getSettings().isSinglePaintingDisabled()) return;

        // Change Painting ID to creeper
        event.getPacket().getIntegers().write(1, ((AprilFoolsDay)plugin).getSettings().getPaintingID().getId());
    }
}
