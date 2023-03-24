package iis.tommasosalvini.pcrestorer.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FolderUtils {

	public static ArrayList<File> flatten(File f1) throws IOException {
		ArrayList<File> flattened = new ArrayList<File>();
		Object[] contents;
		try {
			contents = Files.list(f1.toPath()).toArray();
		} catch (AccessDeniedException e) {
			return new ArrayList<File>();
		}
		for (Object content : contents) {
			if (((Path) content).toFile().isDirectory()) {
				ArrayList<File> subContents = FolderUtils.flatten(((Path) content).toFile());
				flattened.addAll(subContents);
				flattened.add(((Path) content).toFile());
			} else {
				if (!(((Path) content).toFile().getName().equals("desktop.ini"))) flattened.add(((Path) content).toFile());
			}
		}
		return flattened;
	}
	
	public static boolean compare(File f1, File f2) throws IOException, NoSuchAlgorithmException {
		ArrayList<File> c1 = FolderUtils.flatten(f1);
		ArrayList<File> c2 = FolderUtils.flatten(f2);
		ArrayList<String> c1names = new ArrayList<String>();
		ArrayList<String> c2names = new ArrayList<String>();
		for (File fc1 : c1) c1names.add(fc1.getName());
		for (File fc2 : c2) c2names.add(fc2.getName());

		if (c1.size() != c2.size()) return false;
		for (int i = 0; i < c1names.size(); i++) if (!(c1names.get(i).equals(c2names.get(i)))) return false;
		for (int i = 0; i < c1names.size(); i++) if (!(FileUtils.compare(c1.get(i), c2.get(i)))) return false;
		return true;
	}

}
