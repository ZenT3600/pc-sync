package main.iis.tommasosalvini.pcrestorer.mirror;

import main.iis.tommasosalvini.pcrestorer.IOManager;
import main.iis.tommasosalvini.pcrestorer.config.Configuration;
import main.iis.tommasosalvini.pcrestorer.config.Constants;
import main.iis.tommasosalvini.pcrestorer.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DriveMirrorer {
    private final MirrorFileFilter fileFilter = new MirrorFileFilter();
    private Configuration configuration;
    private File source;
    private File target;

    public DriveMirrorer(File source, File drive, Configuration configuration) {
        this.source = source.toPath().toAbsolutePath().toFile();
        this.target = drive;
        this.configuration = configuration;
    }

    public void run() {
        Log.info("Starting mirroring...");
        try {
            Log.info("Mirroring target: " + target.getAbsolutePath());
            Log.info("Mirroring source: " + source.getAbsolutePath());
            visitRecursively(source.toPath(), target.toPath(), target, true);
            visitRecursively(target.toPath(), source.toPath(), source, false);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void visitRecursively(Path mirroringSource, Path mirroringTarget, File folder, boolean delete) throws IOException {
        File[] folderFiles = folder.listFiles(fileFilter);

        for (File folderFile : folderFiles) {
            File mirrorFile = Paths.get(mirroringSource.toString(), mirroringTarget.relativize(folderFile.toPath()).toString()).toFile();
            if (folderFile.isDirectory()) {
                if (!mirrorFile.exists()) {
                    Log.debug("Create directory: " + mirrorFile);
                    IOManager.copy(mirrorFile, folderFile);
                }
                visitRecursively(mirroringSource, mirroringTarget, folderFile, delete);
            }
            else {
                if (folderFile.exists() && (!mirrorFile.exists())) {
                    if (delete) {
                        Log.debug("\"" + mirrorFile + "\" -> \"/dev/null\"");
                        IOManager.delete(folderFile);
                    } else {
                        Log.debug("\"" + folderFile + "\" -> \"" + mirrorFile + "\"");
                        IOManager.copy(folderFile, mirrorFile);
                    }
                }
                if ((mirrorFile.exists() && (!folderFile.exists())) || (mirrorFile.exists() && (!IOManager.areContentEquals(folderFile, mirrorFile)))) {
                    try {
                        if (folderFile.getName().equals("config.pc-sync.txt")) continue;    // Quick and dirty. Find better solution
                        Log.debug("\"" + mirrorFile + "\" -> \"" + folderFile + "\"");
                        IOManager.copy(mirrorFile, folderFile);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        Log.error("Error: " + exception.getMessage());
                    }
                }
            }
        }
    }
}
