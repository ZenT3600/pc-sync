package iis.tommasosalvini.pcrestorer;

import iis.tommasosalvini.pcrestorer.config.Configuration;
import iis.tommasosalvini.pcrestorer.config.Constants;
import iis.tommasosalvini.pcrestorer.logging.Log;
import iis.tommasosalvini.pcrestorer.mirror.DriveMirrorer;
import iis.tommasosalvini.pcrestorer.registry.Registry;

import javax.naming.NoPermissionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

public class Main {
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoPermissionException {
		Log.info("Begin execution...");
		Log.info("Mirror registry...");
		Registry.importRegistryDump(Paths.get(Constants.SOURCE.toString(), "dump.reg").toFile());
		Log.info("Mirror drives...");
		Configuration configuration = new Configuration(Paths.get(Constants.SOURCE.toString(), "config.txt").toFile());
		for (File d : configuration.getSelectedDrives()) {
			DriveMirrorer mirrorer = new DriveMirrorer(d, configuration);
			mirrorer.run();;
		}
		Log.info("Finished execution...");
	}

}
