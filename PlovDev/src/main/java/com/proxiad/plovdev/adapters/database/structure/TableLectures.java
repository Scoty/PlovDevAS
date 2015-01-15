package com.proxiad.plovdev.adapters.database.structure;

public enum TableLectures {
    KEY_ID_PK("_id", 0), KEY_NAME("name", 1), KEY_START_TIME_STR("start_time_str", 2), KEY_START_TIME("start_time", 3), KEY_DESC("desc", 4), KEY_SPEAKER_USID("speaker_usid", 5);

    private String strValue;
    private int indexValue;

    private TableLectures(String strValue, int indexValue) {
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

    public static final String CREATE_TABLE_LECTURES = "CREATE TABLE " +
            DbPlovDev.TABLE_LECTURES + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_START_TIME_STR + " TEXT NOT NULL, " +
            KEY_START_TIME + " DATETIME, " +
            KEY_DESC + " TEXT NOT NULL, " +
            KEY_SPEAKER_USID + " TEXT NOT NULL);";
}
