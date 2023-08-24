package iis.tommasosalvini.pcrestorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;

public class IOManager {

    public static boolean areContentEquals(File sourceFile, File targetFile) {
        if (sourceFile.isFile() && (targetFile.isFile())) {
            return (sourceFile.lastModified() == targetFile.lastModified()) && (sourceFile.length() == targetFile.length());
        }
        throw new IllegalArgumentException("Unsupported " + sourceFile + " and " + targetFile);
    }

    public static boolean areTimestampEquals(File sourceFile, File targetFile) throws IOException {
        int compare1 = compareBasicAttributes(sourceFile, targetFile, "basic:creationTime");
        int compare2 = compareBasicAttributes(sourceFile, targetFile, "basic:lastModifiedTime");
        return (compare1 == 0) && (compare2 == 0);
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

    public static void delete(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("Unable to delete " + file);
        }
    }

    public static void updateTimestamps(File sourceFile, File targetFile) throws IOException {
        copyBasicAttribute(sourceFile, targetFile, "basic:creationTime");
        copyBasicAttribute(sourceFile, targetFile, "basic:lastModifiedTime");
        copyBasicAttribute(sourceFile, targetFile, "basic:lastAccessTime");
    }

    private static void copyBasicAttribute(File sourceFile, File targetFile, String attribute) throws IOException {
        Object value = Files.getAttribute(sourceFile.toPath(), attribute, LinkOption.NOFOLLOW_LINKS);
        Files.setAttribute(targetFile.toPath(), attribute, value, LinkOption.NOFOLLOW_LINKS);
    }

    private static int compareBasicAttributes(File sourceFile, File targetFile, String attribute) throws IOException {
        FileTime sourceFileTime = (FileTime)Files.getAttribute(sourceFile.toPath(), attribute, LinkOption.NOFOLLOW_LINKS);
        FileTime targetFileTime = (FileTime)Files.getAttribute(targetFile.toPath(), attribute, LinkOption.NOFOLLOW_LINKS);
        return sourceFileTime.compareTo(targetFileTime);
    }

}
