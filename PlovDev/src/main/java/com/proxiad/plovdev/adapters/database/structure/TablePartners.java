package com.proxiad.plovdev.adapters.database.structure;

public enum TablePartners {
    KEY_ID_PK("_id", 0), KEY_TITLE("title", 1), KEY_PAGE_URL("page_url", 2), KEY_LOGO("logo", 3);
    public static final String CREATE_TABLE_PARTNERS = "CREATE TABLE " +
            DbPlovDev.TABLE_PARTNERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TITLE + " TEXT NOT NULL, " +
            KEY_PAGE_URL + " TEXT NOT NULL, " +
            KEY_LOGO + " TEXT NOT NULL);";
    private String strValue;
    private int indexValue;

    private TablePartners(String strValue, int indexValue) {
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
}
