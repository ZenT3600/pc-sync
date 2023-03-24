package iis.tommasosalvini.pcrestorer.utils;

import iis.tommasosalvini.pcrestorer.logging.Log;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.AccessDeniedException;

public class FileUtils {
	
	public static boolean compare(File f1, File f2) throws NoSuchAlgorithmException, IOException {
		byte[] f1bytes;
		byte[] f2bytes;
		try {
			f1bytes = Files.readAllBytes(f1.toPath());
			f2bytes = Files.readAllBytes(f2.toPath());
		} catch (AccessDeniedException e) {
			Log.warning("Could not access either file " + f1.getAbsolutePath() + " or " + f2.getAbsolutePath());
			return true;		// TODO: Cleaner solution
		}

		byte[] f1hash = MessageDigest.getInstance("MD5").digest(f1bytes);
		byte[] f2hash = MessageDigest.getInstance("MD5").digest(f2bytes);
		
		return new BigInteger(1, f1hash).toString(16).equals(new BigInteger(1, f2hash).toString(16));
	}
	
}
