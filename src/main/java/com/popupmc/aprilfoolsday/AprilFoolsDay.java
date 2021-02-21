package com.popupmc.aprilfoolsday;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.popupmc.aprilfoolsday.commands.MainCommand;
import com.popupmc.aprilfoolsday.events.PlayerJoin;
import com.popupmc.aprilfoolsday.utils.Reloadable;
import com.popupmc.aprilfoolsday.commands.ToggleJokeCommand;
import com.popupmc.aprilfoolsday.packets.*;
import com.popupmc.aprilfoolsday.settings.Settings;
import com.popupmc.aprilfoolsday.utils.YamlFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class AprilFoolsDay extends JavaPlugin {

    private YamlFile configYaml;
    private YamlFile messagesYaml;
    private Settings settings;
    final private Set<Reloadable> reloadables = new HashSet<>();

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
        send("&fJoin PopUpMC discord server here: &chttps://discord.gg/ru3Hk9Vfny");
        this.settings = new Settings(this);
        reloadables.add(settings);
        reloadConfig();
        reloadMessages();
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
        send("&fJoin PopUpMC discord server here: &chttps://discord.gg/ru3Hk9Vfny");
    }




    /**
     * Registers event listeners.
     */
    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(this), this);
    }

    /**
     * Tries to register plugin commands.
     */
    private void registerCommands(){
        PluginCommand toggleJoke = getCommand("toggle-joke");
        PluginCommand mainCommand = getCommand("aprilFoolsDay");

        if(toggleJoke == null || mainCommand == null){
            send("&cERROR: Commands not registered properly, please check your plugin.yml inside ApriFools' jar is intact.");
            send("&cDisabling AprilFoolsDay...");
            this.setEnabled(false);
            return;
        }

        // Main command executor
        MainCommand mc = new MainCommand(this);
        mainCommand.setExecutor(mc);
        reloadables.add(mc);

        // Allow user to toggle joke
        ToggleJokeCommand tjk = new ToggleJokeCommand(this);
        toggleJoke.setExecutor(tjk);
        reloadables.add(tjk);


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
        manager.addPacketListener(new SinglePaintings(this));

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
     * Reloads the plugin.
     */
    public void reload(){
        this.reloadMessages();
        this.reloadConfig();
        for(Reloadable rl : reloadables) rl.reload();
    }

    /**
     * Loads/Reloads the config file.
     */
    public void reloadConfig() {
        this.configYaml = new YamlFile(this, "config.yml");
    }

    /**
     * Loads/Reloads the messages file.
     */
    public void reloadMessages() {
        this.messagesYaml = new YamlFile(this, "messages.yml");
    }

    /**
     * Gets the YamlFile object containing the FileConfiguration config object.
     * @return A YamlFile object containing the config file.
     */
    public YamlFile getConfigYaml(){
        return this.configYaml;
    }

    /**
     * Gets the YamlFile object containing the FileConfiguration messages object.
     * @return A YamlFile object containing the messages file.
     */
    public YamlFile getMessagesYaml(){
        return this.messagesYaml;
    }

    public Settings getSettings(){
        return this.settings;
    }


}
