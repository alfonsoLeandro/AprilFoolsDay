package com.popupmc.aprilfoolsday.utils;

public enum TimeUnit {

    /**
     * Ticks, The simplest Time unit for this class.
     */
    TICKS(1),
    /**
     * Seconds, conformed by 20 ticks.
     */
    SECONDS(TICKS.multiplier*20);



    /**
     * The value that a value needs to be multiplied to translate to ticks.
     */
    private final int multiplier;


    /**
     * Represents a conventional time unit.
     *
     * @param multiplier The value that a value needs to be multiplied to transform to ticks.
     */
    TimeUnit(int multiplier) {
        this.multiplier = multiplier;
    }


    /**
     * Gets the value that a value needs to be multiplied to transform to ticks.
     *
     * @return The value that a value needs to be multiplied to transform to ticks.
     */
    public int getMultiplier() {
        return this.multiplier;
    }


    /**
     * Gets a timeUnit by its alias.
     * @param alias The alias the timeunit is known by.
     * @return The timeUnit
     */
    public static TimeUnit getByAlias(char alias){
        switch (alias){
            case 't':
            case 'T':
                return TICKS;
            default:
                return SECONDS;
        }
    }


    public static int getTicks(int amount, char unit){
        return getTicks(amount, getByAlias(unit));
    }

    public static int getTicks(int amount, TimeUnit unit){
        return amount*unit.getMultiplier();
    }
}
