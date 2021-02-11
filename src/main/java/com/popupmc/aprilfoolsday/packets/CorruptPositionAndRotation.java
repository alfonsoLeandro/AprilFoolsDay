package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;
import org.bukkit.entity.Player;

import java.util.Random;

public class CorruptPositionAndRotation extends PacketAdapter {
    public CorruptPositionAndRotation(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();

        // If disabled for this player do nothing, stop here
        if(!OnToggleJokeCommand.getStatus(player))
            return;

        // Exclude the player by comparing entity ids
        if(packet.getIntegers().read(0) == player.getEntityId())
            return;

        // 10% chance of slightly corrupting y
        if(random.nextInt(100) <= CorruptPosition.yChance) {

            // Get Current Y Delta
            short curY = packet.getShorts().read(1);

            // Add or subtract noise
            if(random.nextBoolean())
                packet.getShorts().write(1, (short) (curY + CorruptPosition.yNoise));
            else
                packet.getShorts().write(1, (short) (curY - CorruptPosition.yNoise));
        }

        // Generate Random Yaw & Pitch

        // Yaw is between -90 & 90
        byte yaw = (byte)(random.nextInt(90 * 2) - 90);

        // I dont understand pitch, suppose to be -360 - 360 but is a byte which only goes up to 127
        // I'm just doing a generic signed byte
        byte pitch = (byte)(random.nextInt(127 * 2) - 127);

        packet.getBytes().write(0, yaw);
        packet.getBytes().write(1, pitch);
    }

    Random random = new Random();
}
