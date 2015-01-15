package com.proxiad.plovdev.adapters.database.structure;

public enum DbPlovDev {
    TABLE_SPEAKERS("speakers"), TABLE_LECTURES("lectures"), TABLE_PARTNERS("partners");

    private String strValue;

    private DbPlovDev(String strValue) {
        this.strValue = strValue;
    }

    @Override
    public String toString() {
        return strValue;
    }

    //database info
    public static final String DATABASE_NAME = "plovDev.db";
    public static final int DATABASE_VERSION = 1;
}
