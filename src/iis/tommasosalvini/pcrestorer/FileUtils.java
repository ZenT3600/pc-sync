package iis.tommasosalvini.pcrestorer;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {
	
	public static boolean compare(File f1, File f2) throws NoSuchAlgorithmException, IOException {
		byte[] f1bytes = Files.readAllBytes(f1.toPath());
		byte[] f2bytes = Files.readAllBytes(f2.toPath());
		
		byte[] f1hash = MessageDigest.getInstance("MD5").digest(f1bytes);
		byte[] f2hash = MessageDigest.getInstance("MD5").digest(f2bytes);
		
		return new BigInteger(1, f1hash).toString(16).equals(new BigInteger(1, f2hash).toString(16));
	}
	
}
