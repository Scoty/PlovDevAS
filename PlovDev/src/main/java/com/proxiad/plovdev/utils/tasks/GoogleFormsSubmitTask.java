package com.proxiad.plovdev.utils.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.proxiad.plovdev.utils.HttpManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class GoogleFormsSubmitTask extends AsyncTask<String, Void, Boolean> {

    private static final String LOG_TAG = "GoogleFormsSubmitTask";
    private static final String FORM_URL = "https://docs.google.com/forms/d/1Ee330GpkMHX_0dKWmJb6ZPdm4FBhhqJSqBbgysEtq6M/viewform";

    // TODO display progress bar
    @Override
    protected Boolean doInBackground(String... params) {

        String lectureName = "entry.1566150510";
        int rating = 5;
        int o = 0;

        try {
            StringEntity lecturePlusRating = new StringEntity(lectureName + "=" + rating, "UTF-8");
            HttpPost submitToGoogleForms = new HttpPost(FORM_URL);
            HttpResponse response = HttpManager.execute(submitToGoogleForms, new StringEntity[]{lecturePlusRating});
            if (response != null) {
                return false;
            } else {
                Log.wtf(LOG_TAG, "The response was: " + EntityUtils.toString(response.getEntity()));
                return true;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error connecting", e);
        }
        return true;
    }

}
