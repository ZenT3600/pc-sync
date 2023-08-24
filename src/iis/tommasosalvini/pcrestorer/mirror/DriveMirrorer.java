package iis.tommasosalvini.pcrestorer.mirror;

import iis.tommasosalvini.pcrestorer.IOManager;
import iis.tommasosalvini.pcrestorer.config.Configuration;
import iis.tommasosalvini.pcrestorer.config.Constants;
import iis.tommasosalvini.pcrestorer.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DriveMirrorer {
    private final MirrorFileFilter fileFilter = new MirrorFileFilter(true);
    private Configuration configuration;
    private File target;
    private boolean stopRunning = false;

    public DriveMirrorer(File drive, Configuration configuration) {
        this.target = drive;
        this.configuration = configuration;
    }

    public void stop() {
        this.stopRunning = true;
    }

    public void run() {
        Log.info("Starting mirroring...");
        try {
            File source = Constants.SOURCE;
            Log.info("Mirroring target: " + target.getAbsolutePath());
            visitRecursively(target.toPath(), source.toPath(), target);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void visitRecursively(Path mirroringSource, Path mirroringTarget, File folder) throws IOException {
        File[] sourceFiles = folder.listFiles(fileFilter);

        if (sourceFiles != null) {
            for (File sourceFile : sourceFiles) {
                Path sourcePath = sourceFile.toPath();
                Path relativeSourcePath = mirroringSource.relativize(sourcePath);
                Path targetPath = Paths.get(mirroringTarget.toString(), relativeSourcePath.toString());
                File mirrorFile = targetPath.toFile();

                if (sourceFile.isDirectory()) {
                    if (!mirrorFile.exists()) {
                        Log.debug("Create directory: " + mirrorFile);
                        IOManager.copy(sourceFile, mirrorFile);
                        IOManager.updateTimestamps(sourceFile, mirrorFile);
                    }
                    visitRecursively(mirroringSource, mirroringTarget, sourceFile);
                    IOManager.updateTimestamps(sourceFile, mirrorFile);
                }
                if (sourceFile.isFile()) {
                    if (!mirrorFile.exists() || (!IOManager.areContentEquals(sourceFile, mirrorFile))) {
                        try {
                            if (!stopRunning) {
                                Log.debug("\"" + sourceFile + "\" -> \"" + mirrorFile + "\"");
                                IOManager.copy(sourceFile, mirrorFile);
                                IOManager.updateTimestamps(sourceFile, mirrorFile);
                            }
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
}
