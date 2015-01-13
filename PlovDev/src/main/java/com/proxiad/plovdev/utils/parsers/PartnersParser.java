package com.proxiad.plovdev.utils.parsers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.beans.PartnerBean;
import com.proxiad.plovdev.utils.ImageUtils;
import com.proxiad.plovdev.utils.ImportUtils;
import com.proxiad.plovdev.utils.tasks.ReadJsonTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PartnersParser extends JsonToListParser {

    private static final String LOG_TAG = "PartnersParser";
    private Context context;
    private DatabaseAdapter dbAdapter;
    private List<PartnerBean> partners;

    public PartnersParser(Context context, DatabaseAdapter dbAdapter) {
        super(context, dbAdapter);
        this.context = context;
        this.dbAdapter = dbAdapter;
    }

    @Override
    public List getList() {

        if (dbAdapter.getPartnersCount() <= 0) {
            parseJson();
        }

        parseDataFromDb();
        //TODO this can return null
        return partners;
    }

    @Override
    public boolean parseDataFromDb() {
        Cursor partnersCursor = dbAdapter.getAllPartners();
        if (partnersCursor.moveToFirst()) {
            partners = new ArrayList<PartnerBean>();
            do {
                partners.add(new PartnerBean(partnersCursor.getString(1), partnersCursor.getString(2)));
            } while (partnersCursor.moveToNext());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean parseJson() {
        // parse partners json
        String partnersJsonString = null;
        try {
            partnersJsonString = new ReadJsonTask().execute(DataParser.URL_PARTNERS).get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Task interrupted!", e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Execution failed!", e);
        }
        JSONArray partnersJsonArray;

        if (partnersJsonString != null) {
            try {
                partnersJsonArray = new JSONArray(partnersJsonString);
                dbAdapter.deleteAllPartners();

                for (int i = 0; i < partnersJsonArray.length(); i++) {
                    JSONObject partnersJson = partnersJsonArray.getJSONObject(i);

                    String title = getStringFromJson(partnersJson, "title");
                    String pageUrl = getStringFromJson(partnersJson, "url");
                    String logoLink = getStringFromJson(partnersJson, "logo");

                    Drawable partnerDrawable = ImageUtils.getCachedDrawable(title);

                    if (partnerDrawable == null) {
                        Bitmap bitmap = ImageUtils.load(DataParser.URL_WEB_PAGE + logoLink).bitmap;
                        if (bitmap != null) {
                            ImportUtils.addSpeakerPortraitToCache(title, bitmap);
                            partnerDrawable = ImageUtils.getCachedDrawable(title);
                        } else {
                            partnerDrawable = ImageUtils.getDefaultDrawable(context);
                        }
                    }
                    dbAdapter.insertPartner(title, pageUrl, logoLink);
                }
                return true;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception!", e);
            }
        }
        return false;
    }


}
