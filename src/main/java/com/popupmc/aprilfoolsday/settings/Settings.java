package com.popupmc.aprilfoolsday.settings;

import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.utils.*;
import org.bukkit.Sound;
import org.bukkit.WorldType;
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
    private boolean opBypass;
    private boolean corruptPositionDisabled;
    private boolean corruptRotationDisabled;
    private boolean singlePaintingDisabled;
    private boolean quickRespawnEnabled;
    private boolean singleDimensionEnabled;
    private boolean fakeWorldTypeEnabled;
    private boolean noEntitySoundDisabled;
    private boolean reverseBlockBreakDisabled;
    private boolean singleAnimationDisabled;
    private boolean singleEntityEnabled;
    private boolean entityChanceEnabled;
    private boolean singleSoundDisabled;
    private byte corruptRotationPitch;
    private byte corruptRotationYaw;
    private int corruptPositionYChance;
    private int corruptPositionYAmount;
    private int statusMessageDelayTicks;
    private int entityId;
    private int entityChance;
    private String resourcePackUrl;
    private String resourcePackSha1;
    private PaintingIDWrapper paintingID;
    private DimensionWrapper dimension;
    private WorldType fakeWorldType;
    private AnimationWrapper animation;
    private Sound sound;


    public Settings(AprilFoolsDay plugin){
        this.plugin = plugin;
        loadFields();
    }

    public void loadFields(){
        FileConfiguration config = plugin.getConfigYaml().getAccess();

        this.toggleCommandEnabled = config.getBoolean("toggle command.enabled");
        this.kickOnToggle = config.getBoolean("toggle command.kick on toggle");
        this.statusMessageEnabled = config.getBoolean("status message.enabled");
        this.jokeDefaultStatus = config.getBoolean("joke status.default status");
        this.opBypass = config.getBoolean("joke status.op bypass");
        this.corruptPositionDisabled = !config.getBoolean("corrupt position.enabled");
        this.corruptRotationDisabled = !config.getBoolean("corrupt rotation.enabled");
        this.singlePaintingDisabled = !config.getBoolean("single paintings.enabled");
        this.quickRespawnEnabled = config.getBoolean("fake quick-respawn");
        this.singleDimensionEnabled = config.getBoolean("single dimension-type.enabled");
        this.fakeWorldTypeEnabled = config.getBoolean("fake world-type.enabled");
        this.noEntitySoundDisabled = !config.getBoolean("no entity-sounds");
        this.reverseBlockBreakDisabled = !config.getBoolean("reverse block-break");
        this.singleAnimationDisabled = !config.getBoolean("play wrong animation.enabled");
        this.singleEntityEnabled = config.getBoolean("single-entity.enabled");
        this.entityChanceEnabled = config.getBoolean("missing entities.enabled");
        this.singleSoundDisabled = !config.getBoolean("single sound.enabled");

        this.corruptRotationPitch = (byte)config.getInt("corrupt rotation.head-angle");
        this.corruptRotationYaw = (byte)config.getInt("corrupt rotation.body-rotation");

        this.corruptPositionYChance = config.getInt("corrupt position.chance");
        this.corruptPositionYAmount = config.getInt("corrupt position.amount")*4096;
        String delayString = config.getString("status message.delay");
        this.statusMessageDelayTicks = delayString == null ? 0 : TimeUnit.getTicks(Integer.parseInt(delayString.substring(delayString.length())), delayString.charAt(delayString.length()-1));
        this.entityId = config.getInt("single-entity.entity");
        this.entityChance = config.getInt("single-entity.entity");

        this.resourcePackUrl = config.getString("resource-pack.url");
        this.resourcePackSha1 = config.getString("resource-pack.sha1");


        this.paintingID = PaintingIDWrapper.getByName(config.getString("single paintings.painting"));

        this.dimension = DimensionWrapper.getByName(config.getString("single dimension-type.type"));

        this.fakeWorldType = config.getString("fake world-type.type") == null || WorldType.getByName(config.getString("fake world-type.type")) == null ? WorldType.NORMAL : WorldType.getByName(config.getString("fake world-type.type"));

        this.animation = AnimationWrapper.getByName(config.getString("play wrong animations.animation"));

        this.sound = Sound.valueOf(config.getString("single sound.sound"));
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

    public boolean jokeDefaultStatus(){
        return this.jokeDefaultStatus;
    }

    public boolean isOpBypass(){
        return this.opBypass;
    }

    public boolean isCorruptPositionDisabled(){
        return this.corruptPositionDisabled;
    }

    public boolean isCorruptRotationDisabled(){
        return this.corruptRotationDisabled;
    }

    public boolean isSinglePaintingDisabled(){
        return this.singlePaintingDisabled;
    }

    public boolean isQuickRespawnEnabled(){
        return this.quickRespawnEnabled;
    }

    public boolean isSingleDimensionEnabled(){
        return this.singleDimensionEnabled;
    }

    public boolean isFakeWorldTypeEnabled(){
        return this.fakeWorldTypeEnabled;
    }

    public boolean isNoEntitySoundDisabled(){
        return this.noEntitySoundDisabled;
    }

    public boolean isReverseBlockBreakDisabled(){
        return this.reverseBlockBreakDisabled;
    }

    public boolean isSingleAnimationDisabled(){
        return this.singleAnimationDisabled;
    }

    public boolean isSingleEntityEnabled(){
        return this.singleEntityEnabled;
    }

    public boolean isEntityChanceEnabled(){
        return this.entityChanceEnabled;
    }

    public boolean isSingleSoundDisabled(){
        return this.singleSoundDisabled;
    }


    public byte getCorruptRotationPitch(){
        return this.corruptRotationPitch;
    }

    public byte getCorruptRotationYaw(){
        return this.corruptRotationYaw;
    }


    public int getCorruptPositionYChance(){
        return this.corruptPositionYChance;
    }

    public int getCorruptPositionYAmount(){
        return this.corruptPositionYAmount;
    }

    public int getStatusMessageDelayTicks(){
        return this.statusMessageDelayTicks;
    }

    public int getEntityId(){
        return this.entityId;
    }

    public int getEntityChance(){
        return this.entityChance;
    }


    public String getResourcePackUrl(){
        return this.resourcePackUrl;
    }

    public String getResourcePackSha1(){
        return this.resourcePackSha1;
    }


    public PaintingIDWrapper getPaintingID(){
        return this.paintingID;
    }


    public DimensionWrapper getDimension(){
        return this.dimension;
    }


    public WorldType getFakeWorldType(){
        return this.fakeWorldType;
    }


    public AnimationWrapper getAnimation(){
        return this.animation;
    }


    public Sound getSound(){
        return this.sound;
    }
}
