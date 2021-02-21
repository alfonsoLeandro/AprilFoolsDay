package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;

public class LoginModifications extends PacketAdapter {
    public LoginModifications(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.LOGIN);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer())) return;

        PacketContainer packet = event.getPacket();
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // Set world type to the nether
        if(settings.isSingleDimensionEnabled()) packet.getDimensions().write(0, settings.getDimension().getId());

        // Disable Respawn Screen
        if(settings.isQuickRespawnEnabled()) packet.getBooleans().write(2, false);

        // Flat World
        if(settings.isFakeWorldTypeEnabled()) packet.getWorldTypeModifier().write(0, settings.getFakeWorldType());
    }
}
