package com.proxiad.plovdev.utils.parsers;

import android.content.Context;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class JsonToListParser {

    public JsonToListParser(Context context, DatabaseAdapter dbAdapter) {
    }

    public abstract List getList();

    public abstract boolean parseDataFromDb();

    public abstract boolean parseJson();

    public String getStringFromJson(JSONObject json, String name) throws JSONException {
        if (json.has(name)) {
            return json.getString(name);
        }
        return "";
    }
}
