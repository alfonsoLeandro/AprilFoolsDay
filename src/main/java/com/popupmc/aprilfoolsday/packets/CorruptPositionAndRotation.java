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

public class CorruptPositionAndRotation extends PacketAdapter {

    private final Random random;

    public CorruptPositionAndRotation(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);
        random = new Random();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(player)
        || settings.isCorruptPositionDisabled()
        || settings.isCorruptRotationDisabled()) return;


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

        // Generate Random Yaw & Pitch

        // Yaw is between -90 & 90
        byte yaw = (byte)(random.nextInt(90 * 2) - 90);

        // I dont understand pitch, suppose to be -360 - 360 but is a byte which only goes up to 127
        // I'm just doing a generic signed byte
        byte pitch = (byte)(random.nextInt(127 * 2) - 127);

        packet.getBytes().write(0, yaw);
        packet.getBytes().write(1, pitch);
    }
}
