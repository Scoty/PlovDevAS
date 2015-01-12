package com.proxiad.plovdev.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;

public class ImportUtils {
	private static final String CACHE_DIRECTORY = "plovdev/images";

	private ImportUtils() {
	}

	public static File getCacheDirectory() {
		return IOUtils.getExternalFile(CACHE_DIRECTORY);
	}

	public static boolean addSpeakerPortraitToCache(String speakerId, Bitmap bitmap) {
		File cacheDirectory;
		try {
			cacheDirectory = ensureCache();
		} catch (IOException e) {
			return false;
		}

		File file = new File(cacheDirectory, speakerId);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (FileNotFoundException e) {
			return false;
		} finally {
			IOUtils.closeStream(out);
		}

		return true;
	}

	private static File ensureCache() throws IOException {
		File cacheDirectory = getCacheDirectory();
		if (!cacheDirectory.exists()) {
			cacheDirectory.mkdirs();
			new File(cacheDirectory, ".nomedia").createNewFile();
		}
		return cacheDirectory;
	}
}
