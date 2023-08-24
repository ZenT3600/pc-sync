package iis.tommasosalvini.pcrestorer.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Example.config
 * ---
 * SELECTED_DRIVES = Drive1
 * SELECTED_DRIVES = Drive2
 * ---
 */

public class Configuration {
	private ArrayList<File> SELECTED_DRIVES = new ArrayList<File>();
	
	public Configuration(File confFile) throws FileNotFoundException, InvalidKeyException {
		try (Scanner fileScan = new Scanner(confFile)) {
			do {
				String line = fileScan.nextLine();
				String[] keyValue = line.split(" = ");
				switch (keyValue[0]) {
					case "SELECTED_DRIVES" -> SELECTED_DRIVES.add(new File(keyValue[1]));
					default -> throw new InvalidKeyException("Invalid configuration");
				}
			} while (fileScan.hasNext());
		}
	}

	public ArrayList<File> getSelectedDrives() {
		return SELECTED_DRIVES;
	}
	
}
