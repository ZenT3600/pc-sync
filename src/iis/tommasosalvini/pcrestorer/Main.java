package iis.tommasosalvini.pcrestorer;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		System.out.println(FolderUtils.compare(new File("C:\\Users\\studente\\Documents\\Clone\\Disco_C\\Users\\studente\\Documents"), new File("C:\\Users\\studente\\Documents\\CloneTest")));
	}

}
