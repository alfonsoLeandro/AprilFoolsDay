package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;
import org.bukkit.entity.Player;

import java.util.Random;

public class CorruptPosition extends PacketAdapter {

    private final Random random;

    public CorruptPosition(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.REL_ENTITY_MOVE);
        random = new Random();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer().getName())
        || settings.isCorruptPositionDisabled()) return;

        PacketContainer packet = event.getPacket();

        // Exclude the player by comparing entity ids
        if(packet.getIntegers().read(0) == player.getEntityId())
            return;

        // 10% chance of slightly corrupting y
        if(random.nextInt(100) <= settings.getCorruptPositionYChance()) {

            // Get Current Y Delta
            short curY = packet.getShorts().read(1);

            // Add or subtract noise
            if(random.nextBoolean())
                packet.getShorts().write(1, (short) (curY + settings.getCorruptPositionYAmount()));
            else
                packet.getShorts().write(1, (short) (curY - settings.getCorruptPositionYAmount()));
        }
    }
}
