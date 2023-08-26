package main.iis.tommasosalvini.pcrestorer.registry;

import main.iis.tommasosalvini.pcrestorer.logging.Log;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;
import javax.naming.NoPermissionException;

public class Registry {
	
	private static String execCmd(String cmd) throws IOException, NoPermissionException {
		Process p = Runtime.getRuntime().exec(cmd);
	    Scanner sOut = new java.util.Scanner(p.getInputStream()).useDelimiter("\\A");
	    Scanner sErr = new java.util.Scanner(p.getErrorStream()).useDelimiter("\\A");
	    if (!sErr.hasNext()) return sOut.hasNext() ? sOut.next() : "";
	    else {
			Log.error("Invalid permission to modify registry");
	    	throw new NoPermissionException("Invalid permission to modify registry");
	    }
	}
	
	public static void importRegistryDump(File dump) throws InvalidParameterException, NoPermissionException, IOException {
		if (!dump.exists()) {
			Log.error("The registry dump " + dump.getName() + " provided does not exist");
			throw new InvalidParameterException("The registry dump provided does not exist");
		}
		System.out.println(execCmd("reg import " + dump.getAbsolutePath()));
	}
	
}