package com.popupmc.aprilfoolsday.settings;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.utils.Reloadable;
import com.popupmc.aprilfoolsday.utils.TimeUnit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class used for loading in the config settings.
 * Needed because some config fields may need some checks before using them.
 *
 * @author lelesape.
 */
public class Settings implements Reloadable {

    private final AprilFoolsDay plugin;
    //Fields here
    private boolean toggleCommandEnabled;
    private boolean kickOnToggle;
    private boolean statusMessageEnabled;
    private boolean jokeDefaultStatus;
    private int statusMessageDelayTicks;
    private String resourcePackUrl;
    private String resourcePackSha1;


    public Settings(AprilFoolsDay plugin){
        this.plugin = plugin;
        loadFields();
    }

    public void loadFields(){
        FileConfiguration config = plugin.getConfigYaml().getAccess();

        this.toggleCommandEnabled = config.getBoolean("toggle command.enabled");
        this.kickOnToggle = config.getBoolean("toggle command.kick on toggle");
        this.statusMessageEnabled = config.getBoolean("status message.enabled");
        this.jokeDefaultStatus = config.getBoolean("");

        String delayString = config.getString("status message.delay");
        this.statusMessageDelayTicks = delayString == null ? 0 : TimeUnit.getTicks(Integer.parseInt(delayString.substring(delayString.length())), delayString.charAt(delayString.length()-1));

        this.resourcePackUrl = config.getString("resource-pack.url");
        this.resourcePackSha1 = config.getString("resource-pack.sha1");
    }


    @Override
    public void reload() {
        this.loadFields();
    }


    //Fields

    public boolean isToggleCommandEnabled(){
        return this.toggleCommandEnabled;
    }

    public boolean isKickOnToggle(){
        return this.kickOnToggle;
    }

    public boolean isStatusMessageEnabled(){
        return this.statusMessageEnabled;
    }


    public int getStatusMessageDelayTicks(){
        return this.statusMessageDelayTicks;
    }


    public String getResourcePackUrl(){
        return this.resourcePackUrl;
    }

    public String getResourcePackSha1(){
        return this.resourcePackSha1;
    }
}
