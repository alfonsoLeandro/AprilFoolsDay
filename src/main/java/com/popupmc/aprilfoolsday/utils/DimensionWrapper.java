package com.popupmc.aprilfoolsday.utils;

public enum DimensionWrapper {
    NETHER(-1),
    NORMAL(0),
    END(1);

    final private int id;

    DimensionWrapper(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static DimensionWrapper getByName(String name){
        if(name == null) return NORMAL;
        try{
            return valueOf(name);
        }catch (IllegalArgumentException e){
            return NORMAL;
        }
    }
}
