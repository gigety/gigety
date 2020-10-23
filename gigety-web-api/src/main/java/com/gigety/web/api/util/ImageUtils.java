package com.gigety.web.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtils {

	private final static int CONNECTION_TIMEOUT = 5000;
	private final static int READ_TIMEOUT = 5000;

	/**
	 * Converts a URL to byte array
	 * 
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static byte[] copyURLToByteArray(final String urlStr) throws IOException {

		final URL url = new URL(urlStr);
		final URLConnection connection = url.openConnection();
		connection.setConnectTimeout(CONNECTION_TIMEOUT);
		connection.setReadTimeout(READ_TIMEOUT);
		try (InputStream input = connection.getInputStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			final byte[] buffer = new byte[8192];
			for (int count; (count = input.read(buffer)) > 0;) {
				output.write(buffer, 0, count);
			}
			return output.toByteArray();
		}
	}
}
