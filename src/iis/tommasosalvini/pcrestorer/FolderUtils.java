package iis.tommasosalvini.pcrestorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FolderUtils {
	
	public static boolean compare(File p1, File p2) throws NoSuchAlgorithmException, IOException {
		if (p1.isFile() || p2.isFile()){
			if (FileUtils.compare(p1, p2) == false) {
				return false;
			}
			return true;
		}
		
		Object[] p1contents = Files.list(p1.toPath()).toArray();
		Object[] p2contents = Files.list(p2.toPath()).toArray();
		String[] p1contentsName = new String[p1contents.length];
		for (int i = 0; i < p1contents.length; i++) {
			p1contentsName[i] = p1contents[i].toString();
		}
		String[] p2contentsName = new String[p2contents.length];
		for (int i = 0; i < p2contents.length; i++) {
			p2contentsName[i] = p2contents[i].toString();
		}
		
		if (!Arrays.equals(p1contentsName, p2contentsName)) return false;
		
		for (int i = 0; i < p1contents.length; i++) {
			if (FolderUtils.compare(new File(p1contents[i].toString()), new File(p2contents[i].toString())) == false) {
				return false;
			}
		}
		
		return true;
	}

}
