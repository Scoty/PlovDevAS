package com.proxiad.plovdev.utils.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.proxiad.plovdev.utils.HttpManager;

import android.os.AsyncTask;
import android.util.Log;

public class ReadJsonTask extends AsyncTask<String, Void, String> {
	private static final String LOG_TAG = "ReadJsonTask";

	// TODO display progress bar
	@Override
	protected String doInBackground(String... params) {

		InputStream inputStream = null;
		String result = null;

		try {
			HttpGet getJson = new HttpGet(params[0]);
			HttpResponse response = HttpManager.execute(getJson);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error connecting", e);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e) {
				Log.e(LOG_TAG, "Could not close stream", e);
			}
		}
		return result;
	}

}