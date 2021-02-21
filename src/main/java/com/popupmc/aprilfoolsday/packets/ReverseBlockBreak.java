package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;

public class ReverseBlockBreak extends PacketAdapter {
    public ReverseBlockBreak(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
    }

    @Override
    public void onPacketSending(PacketEvent event) {

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer())
        || ((AprilFoolsDay)plugin).getSettings().isReverseBlockBreakDisabled()) return;

        // Get Packet
        PacketContainer packet = event.getPacket();

        // Get Break Progress
        // It's between 0-9, numbers outside the range result in progress reset
        int progress = packet.getIntegers().read(1);

        // Stop here if numbers outside range
        if(progress < 0 || progress > 9)
            return;

        // Reverse progress
        progress = 9 - progress;

        // Write reversed progress
        packet.getIntegers().write(1, progress);
    }
}
