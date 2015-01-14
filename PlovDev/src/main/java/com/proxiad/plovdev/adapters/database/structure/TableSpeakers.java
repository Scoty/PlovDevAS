package com.proxiad.plovdev.adapters.database.structure;

public enum TableSpeakers {
    KEY_ID_PK("_id", 0), KEY_USID("usid", 1), KEY_NAME("name", 2), KEY_IMG_URL("img_url", 3), KEY_PAGE_URL("page_url", 4), KEY_COMP_NAME("comp_name", 5), KEY_COMP_URL("comp_url", 6), KEY_BIO("bio", 7);

    private String strValue;
    private int indexValue;

    private TableSpeakers(String strValue, int indexValue) {
        this.strValue = strValue;
        this.indexValue = indexValue;
    }

    @Override
    public String toString() {
        return strValue;
    }

    public int getIndex() {
        return indexValue;
    }

    public String[] getColumns() {
        String[] toReturn = new String[values().length];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = values()[i].toString();
        }
        return toReturn;
    }


    public static final String CREATE_TABLE_SPEAKERS = "CREATE TABLE " +
            DbPlovDev.TABLE_SPEAKERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_USID + " TEXT NOT NULL, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_IMG_URL + " TEXT NOT NULL, " +
            KEY_PAGE_URL + " TEXT NOT NULL, " +
            KEY_COMP_NAME + " TEXT NOT NULL, " +
            KEY_COMP_URL + " TEXT NOT NULL, " +
            KEY_BIO + " TEXT NOT NULL);";
}
