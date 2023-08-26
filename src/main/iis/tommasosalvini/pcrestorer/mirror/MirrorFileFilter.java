package main.iis.tommasosalvini.pcrestorer.mirror;

import main.iis.tommasosalvini.pcrestorer.logging.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MirrorFileFilter implements FileFilter {

    public MirrorFileFilter() {}

    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            String filename = pathname.getName();

            if (filename.equals("config.pc-sync.txt")) {
                Log.debug("Ignoring own config...");
                return false;
            }

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

        return true;
    }

}
