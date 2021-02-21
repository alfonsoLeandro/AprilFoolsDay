package com.popupmc.aprilfoolsday.utils;

public enum PaintingIDWrapper {
    KEBAB(0),
    AZTEC(1),
    ALBAN(2),
    AZTEC2(3),
    BOMB(4),
    PLANT(5),
    WASTELAND(6),
    POOL(7),
    COURBET(8),
    SEA(9),
    SUNSET(10),
    CREEBET(11),
    WANDERER(12),
    GRAHAM(13),
    MATCH(14),
    BUST(15),
    STAGE(16),
    VOID(17),
    ROSES(18),
    WITHER(19),
    FIGHTERS(20),
    POINTER(21),
    PIGSCENE(22),
    BURNING_SKULL(23),
    SKELETON(24),
    DONKEY_KONG(25);

    private final int id;

    PaintingIDWrapper(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static PaintingIDWrapper getByName(String name){
        if(name == null) return CREEBET;
        try {
            return valueOf(name);
        }catch (IllegalArgumentException e){
            return CREEBET;
        }
    }
}
