package main.iis.tommasosalvini.pcrestorer;

import main.iis.tommasosalvini.pcrestorer.logging.Log;
import main.iis.tommasosalvini.pcrestorer.mirror.MirrorFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;

public class IOManager {

    public static boolean areContentEquals(File sourceFile, File targetFile) {
        if (sourceFile.isFile() && (targetFile.isFile())) {
            return sourceFile.length() == targetFile.length();
        }
        throw new IllegalArgumentException("Unsupported " + sourceFile + " and " + targetFile);
    }

    public static void delete(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("Unable to delete " + file);
        }
    }

    public static void copy(File sourceFile, File targetFile) throws IOException {
        targetFile.setWritable(true);
        Files.copy(
                sourceFile.toPath(),
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES,
                LinkOption.NOFOLLOW_LINKS
        );
        targetFile.setReadable(sourceFile.canRead());
        targetFile.setWritable(sourceFile.canWrite());
        targetFile.setExecutable(sourceFile.canExecute());
    }

    public static boolean areDirectoriesEqual(Path folder1, Path folder2, File folderCurr) throws IOException {
        File[] folderFiles = folderCurr.listFiles(new MirrorFileFilter());

        for (File folderFile : folderFiles) {
            File mirrorFile = Paths.get(folder2.toString(), folder1.relativize(folderFile.toPath()).toString()).toFile();
            if (folderFile.isDirectory()) {
                if (!areDirectoriesEqual(folder1, folder2, folderFile)) {
                    return false;
                }
            }
            else if (folderFile.isFile()) {
                if (!mirrorFile.exists() || (!IOManager.areContentEquals(folderFile, mirrorFile))) {
                    return false;
                }
            }
        }

        return true;
    }

}
