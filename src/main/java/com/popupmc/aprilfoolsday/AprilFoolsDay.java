package com.popupmc.aprilfoolsday;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;
import com.popupmc.aprilfoolsday.events.OnPlayerJoinEvent;
import com.popupmc.aprilfoolsday.packets.*;
import com.popupmc.aprilfoolsday.settings.Settings;
import com.popupmc.aprilfoolsday.utils.YamlFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AprilFoolsDay extends JavaPlugin {

    private YamlFile configYaml = null;
    private Settings settings;

    /**
     * Sends a message to the console.
     * @param msg The message to be sent.
     */
    private void send(String msg){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&6AprilFools&f]" +" "+msg));
    }

    /**
     * Plugin enable logic.
     */
    @Override
    public void onEnable() {
        send("&fStatus: &aEnabled");
        send("&6AprilFoolsDay &fby junebug12851 and lelesape");
        //TODO: Discord invitation link
        send("&fJoin PopUpMC discord server here: &c");
        reloadConfig();
        this.settings = new Settings(this);
        registerEvents();
        registerCommands();
        registerPacketListeners();
    }


    /**
     * Plugin disable logic.
     */
    @Override
    public void onDisable() {
        send("&fStatus: &cDisabled&f. Thank you for using this plugin.");
        send("&6AprilFoolsDay &fby junebug12851 and lelesape");
        //TODO: Discord invitation link
        send("&fJoin PopUpMC discord server here: &c");
    }




    /**
     * Registers event listeners.
     */
    private void registerEvents(){
        // Setup event listeners
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoinEvent(this), this);
    }

    /**
     * Tries to register plugin commands.
     */
    private void registerCommands(){
        PluginCommand toggleJoke = getCommand("toggle-joke");
        PluginCommand spawnHerobrine = getCommand("afd-spawn");

        if(toggleJoke == null || spawnHerobrine == null){
            send("&cERROR: Commands not registered properly, please check your plugin.yml inside ApriFools' jar is intact.");
            send("&cDisabling AprilFoolsDay...");
            this.setEnabled(false);
            return;
        }

        // Allow user to toggle joke
        toggleJoke.setExecutor(new OnToggleJokeCommand());

        // Setup Command Code
        // Debug command to spawn fake herobrine player
        // doesnt work though, trying to work with people to figure out why
        //spawnHerobrine.setExecutor(new OnFakePlayerCommand());

    }


    /**
     * Registers ProtocolLib's packet listeners.
     */
    private void registerPacketListeners(){
        // Grab Protocol Manager
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Make sure the client sees all entities as creepers
        manager.addPacketListener(new SingleEntity(this));

        // Remove all entity sounds
        // For some reason this packet doesnt ever seem to be called
        manager.addPacketListener(new NoEntitySound(this));

        // Remove all named sounds and replace with a grass step sound
        manager.addPacketListener(new SingleNamedSound(this));

        // Make world nether on login with other cosmetic changes and enable instant respawn
        manager.addPacketListener(new LoginModifications(this));

        // Make world nether on login with other cosmetic changes and enable instant respawn
        manager.addPacketListener(new RespawnModifications(this));

        // Ensure rain remains disabled and quick respawn enabled
        manager.addPacketListener(new GameStateModifications(this));

        // Corrupt Rotation & Y data in Move & Rotate packets of all but the player
        manager.addPacketListener(new CorruptPositionAndRotation(this));

        // Corrupt Rotation data in Rotate packets of all but the player
        manager.addPacketListener(new CorruptRotation(this));

        // Corrupt Y data in Rotate packets of all but the player
        manager.addPacketListener(new CorruptPosition(this));

        // All paintings are creeper paintings
        manager.addPacketListener(new CreeperPaintings(this));

        // All animations are off-hand arm swings
        manager.addPacketListener(new SingleAnimation(this));

        // Block breaking progress is reversed
        // This doesnt seem to be called
        manager.addPacketListener(new ReverseBlockBreak(this));

        // Muck around with block data sent to clients
        // No Human on earth can figure this bull@!##! out
        // manager.addPacketListener(new CartoonyBlocks(this));
    }

    /**
     * Loads/Reloads the config file.
     */
    public void reloadConfig() {
        this.configYaml = new YamlFile(this, "config.yml");
    }

    /**
     * Gets the YamlFile object containing the FileConfiguration config object.
     * @return A YamlFile object containing the config file.
     */
    public YamlFile getConfigYaml(){
        return this.configYaml;
    }

    public Settings getSettings(){
        return this.settings;
    }


}
