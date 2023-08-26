package main.iis.tommasosalvini.pcrestorer;

import main.iis.tommasosalvini.pcrestorer.config.Configuration;
import main.iis.tommasosalvini.pcrestorer.config.Constants;
import main.iis.tommasosalvini.pcrestorer.logging.Log;
import main.iis.tommasosalvini.pcrestorer.mirror.DriveMirrorer;
import main.iis.tommasosalvini.pcrestorer.registry.Registry;

import javax.naming.NoPermissionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

public class Main {
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoPermissionException {
		Log.info("Begin execution...");
		Log.info("Mirror registry...");
		Registry.importRegistryDump(Paths.get(Constants.SOURCE.toString(), "dump.reg").toFile());
		Log.info("Mirror drives...");
		Configuration configuration = new Configuration(Paths.get(Constants.SOURCE.toString(), "config.pc-sync.txt").toFile());
		for (File d : configuration.getSelectedDrives()) {
			DriveMirrorer mirrorer = new DriveMirrorer(Constants.SOURCE, d, configuration);
			mirrorer.run();;
		}
		Log.info("Finished execution...");
	}

}
