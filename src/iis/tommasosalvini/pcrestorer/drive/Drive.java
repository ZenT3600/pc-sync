package iis.tommasosalvini.pcrestorer.drive;

import iis.tommasosalvini.pcrestorer.logging.Log;
import java.io.File;
import java.security.InvalidParameterException;

public class Drive {

    private File root;

    public Drive(File identifier) {
        if (!identifier.isDirectory()) {
            Log.error("Provided drive " + identifier.getName() + " is not a directory");
            throw new InvalidParameterException("Provided drive is not a directory");
        }
        /*
        if (identifier.getParent() != null) {
            Log.error("Provided drive " + identifier.getName() + " is not at the root of the filesystem");
            throw new InvalidParameterException("Provided drive is not at the root of the filesystem");
        }*/
        this.root = identifier;
    }

    public Drive(String identifierPath) {
        this(new File(identifierPath));
    }

    public File getRoot() {
        return root;
    }

}
