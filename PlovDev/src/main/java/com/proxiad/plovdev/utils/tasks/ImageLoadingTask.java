package com.proxiad.plovdev.utils.tasks;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.proxiad.plovdev.utils.HttpManager;
import com.proxiad.plovdev.utils.IOUtils;
import com.proxiad.plovdev.utils.ImageUtils;
import com.proxiad.plovdev.utils.ImageUtils.ExpiringBitmap;

public class ImageLoadingTask extends AsyncTask<String, Void, ExpiringBitmap> {
	
	private static final String LOG_TAG = "ImageLoadingTask";

	// TODO display progress bar
	@Override
	protected ExpiringBitmap doInBackground(String... params) {
		ExpiringBitmap expiring = new ExpiringBitmap();
		String url = params[0];
		final HttpGet get = new HttpGet(url);
		HttpEntity entity = null;
		try {
			final HttpResponse response = HttpManager.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ImageUtils.setLastModified(expiring, response);
				entity = response.getEntity();
				InputStream in = null;
				OutputStream out = null;

				try {
					in = entity.getContent();

					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream, IOUtils.IO_BUFFER_SIZE);
					IOUtils.copy(in, out);
					out.flush();

					final byte[] data = dataStream.toByteArray();
					expiring.bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

				} catch (IOException e) {
					Log.e(LOG_TAG, "Could not load image from " + url, e);
				} finally {
					IOUtils.closeStream(in);
					IOUtils.closeStream(out);
				}
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, "Could not load image from " + url, e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					Log.e(LOG_TAG, "Could not load image from " + url, e);
				}
			}
		}

		return expiring;
	}

}
