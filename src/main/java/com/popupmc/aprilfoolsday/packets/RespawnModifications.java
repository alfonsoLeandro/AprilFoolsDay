package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;

public class RespawnModifications extends PacketAdapter {
    public RespawnModifications(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.RESPAWN);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer())) return;

        PacketContainer packet = event.getPacket();
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // Set world type to the nether
        if(settings.isSingleDimensionEnabled()) packet.getDimensions().write(0, settings.getDimension().getId());

        // Flat World
        if(settings.isFakeWorldTypeEnabled()) packet.getWorldTypeModifier().write(0, settings.getFakeWorldType());
    }
}
