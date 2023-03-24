package iis.tommasosalvini.pcrestorer.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Example.config
 * ---
 * BANNED_PATHS = Path1
 * BANNED_PATHS = Path2
 * BANNED_PATHS = Path3
 * SELECTED_DRIVES = Drive1
 * SELECTED_DRIVES = Drive2
 * ---
 */

public class Configuration {

	private ArrayList<File> BANNED_PATHS = new ArrayList<File>();
	private ArrayList<File> SELECTED_DRIVES = new ArrayList<File>();
	
	public Configuration(File confFile) throws FileNotFoundException, InvalidKeyException {
		try (Scanner fileScan = new Scanner(confFile)) {
			do {
				String line = fileScan.nextLine();
				String[] keyValue = line.split(" = ");
				if (keyValue[0] == "BANNED_PATHS") BANNED_PATHS.add(new File(keyValue[1]));
				else if (keyValue[0] == "SELECTED_DRIVES") SELECTED_DRIVES.add(new File(keyValue[1]));
				else throw new InvalidKeyException("Invalid configuration");
			} while (fileScan.hasNext());
		}
	}
	
	public Configuration(String confPath) throws FileNotFoundException, InvalidKeyException {
		this(new File(confPath));
	}

	public ArrayList<File> getBannedPaths() {
		return BANNED_PATHS;
	}

	public ArrayList<File> getSelectedDrives() {
		return SELECTED_DRIVES;
	}
	
}
