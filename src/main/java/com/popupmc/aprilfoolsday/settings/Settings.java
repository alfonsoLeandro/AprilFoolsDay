package com.popupmc.aprilfoolsday.settings;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class used for loading in the config settings.
 * Needed because some config fields may need some checks before using them.
 *
 * @author lelesape.
 */
public class Settings {

    private final AprilFoolsDay plugin;
    private final FileConfiguration config;
    //Fields here



    public Settings(AprilFoolsDay plugin){
        this.plugin = plugin;
        this.config = plugin.getConfigYaml().getAccess();
        loadFields();
    }

    public void loadFields(){
        //TODO
    }



}
