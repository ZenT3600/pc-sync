package iis.tommasosalvini.pcrestorer.mirror;

import iis.tommasosalvini.pcrestorer.logging.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;

public class MirrorFileFilter implements FileFilter {

    private final boolean findHiddenFiles;

    public MirrorFileFilter(boolean findHiddenFiles) {
        this.findHiddenFiles = findHiddenFiles;
    }

    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            String filename = pathname.getName();

            if (filename.equals("$RECYCLE.BIN")) {
                Log.debug("Ignoring recycle bin...");
                return false;
            }

            if (filename.equals("System Volume Information")) {
                try {
                    // if this succeeds, that means it's not actually a system directory
                    Files.getOwner(pathname.toPath());
                    return true;
                }
                catch (IOException e) {
                    Log.debug("Ignoring system directory...");
                    return false;
                }
            }
        }

        return findHiddenFiles || (!pathname.isHidden());
    }

}
