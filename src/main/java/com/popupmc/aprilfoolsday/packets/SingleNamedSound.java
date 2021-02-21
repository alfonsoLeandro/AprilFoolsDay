package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.settings.Settings;
import org.bukkit.Sound;

public class SingleNamedSound extends PacketAdapter {
    public SingleNamedSound(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.NAMED_SOUND_EFFECT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Settings settings = ((AprilFoolsDay)plugin).getSettings();

        // If disabled for this player do nothing, stop here
        if(!ToggleJokeCommand.getJokeStatus(event.getPlayer())
        || settings.isSingleSoundDisabled()) return;

        event.getPacket().getSoundEffects().write(0, settings.getSound());
    }
}
