package com.popupmc.aprilfoolsday.utils;

public enum AnimationWrapper {
    SWING_MAIN_ARM(0),
    TAKE_DAMAGE(1),
    LEAVE_BED(2),
    SWING_OFFHAND(3),
    CRITICAL_EFFECT(4),
    MAGIC_CRITICAL_EFFECT(5);


    private final int id;

    AnimationWrapper(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }


    public static AnimationWrapper getByName(String name){
        if(name == null) return SWING_MAIN_ARM;
        name = name.replace(" ", "_");
        try{
            return valueOf(name);
        }catch (IllegalArgumentException ex){
            return SWING_MAIN_ARM;
        }
    }
}
