package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;

import java.util.Random;

public class SingleEntity extends PacketAdapter {

    private final Random random;

    public SingleEntity(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        random = new Random();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // Check if event is already cancelled so as to not un-cancel it
        if(event.isCancelled()) return;

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer().getName())) return;

        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        if(settings.isSingleEntityEnabled()) event.getPacket().getIntegers().write(1, settings.getEntityId());

        // 10% chance of having the entity not spawn at the client
        if(settings.isEntityChanceEnabled()) {
            event.setCancelled(random.nextInt(100) <= settings.getEntityChance());
        }
    }
}
