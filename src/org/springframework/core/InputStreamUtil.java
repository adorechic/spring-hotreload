package org.springframework.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtil {
	/**
	 * get Bytes from InputStream
	 * @param is
	 * @return
	 */
	public static final byte[] getBytes(InputStream is) {
		byte[] bytes = null;
		byte[] buff = new byte[8192];
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int n = 0;
			while((n = is.read(buff, 0, buff.length)) != -1) {
				os.write(buff, 0, n);
			}
			bytes = os.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(is != null) is.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return bytes;
	}
}
