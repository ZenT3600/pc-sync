package test;

import static org.junit.Assert.*;

import main.iis.tommasosalvini.pcrestorer.IOManager;
import main.iis.tommasosalvini.pcrestorer.config.Configuration;
import main.iis.tommasosalvini.pcrestorer.config.Constants;
import main.iis.tommasosalvini.pcrestorer.logging.Log;
import main.iis.tommasosalvini.pcrestorer.mirror.DriveMirrorer;
import main.iis.tommasosalvini.pcrestorer.registry.Registry;
import org.junit.*;

import javax.naming.NoPermissionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

public class JUnitTests {

    private static final String root = Paths.get(System.getProperty("user.dir"), "src", "test").toString();

    public static void writeToFile(Path filePath, String content) throws IOException {
        File file = filePath.toFile();
        file.createNewFile();
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(content);
        myWriter.close();
    }

    @AfterClass
    public static void cleanup() {
        File source = Paths.get(root, "source").toFile();
        File target = Paths.get(root, "target").toFile();
        source.delete();
        target.delete();
    }

    @BeforeClass
    public static void initDirStructure() throws IOException {
        File source = Paths.get(root, "source", "C:").toFile();
        File target = Paths.get(root, "target", "C:").toFile();
        source.mkdirs();
        writeToFile(Paths.get(root, "source", "config.pc-sync.txt"), "SELECTED_DRIVES = C:");

        String documents = "Documents";
        String desktop = "Desktop";
        String textFile = "readme.txt";
        String iniFile = "config.ini";
        String pngFile = "image.png";
        String jpgFile = "image.jpg";

        Paths.get(source.toString(), documents).toFile().mkdirs();
        writeToFile(Paths.get(source.toString(), documents, textFile), "hello world");
        writeToFile(Paths.get(source.toString(), documents, iniFile), "i am a config file");
        writeToFile(Paths.get(source.toString(), documents, jpgFile), "this is a picture");
        Paths.get(source.toString(), desktop).toFile().mkdirs();
        writeToFile(Paths.get(source.toString(), desktop, pngFile), "this is a picture");
        writeToFile(Paths.get(source.toString(), desktop, jpgFile), "this is a different picture");

        Paths.get(target.toString(), documents).toFile().mkdirs();
        writeToFile(Paths.get(target.toString(), documents, textFile), "hello world");
        writeToFile(Paths.get(target.toString(), documents, iniFile), "i am a config file 2; electric boogaloo");
        writeToFile(Paths.get(target.toString(), documents, pngFile), "THIS FILE SHOULD NOT BE HERE!");
        // writeToFile(Paths.get(target.toString(), documents, jpgFile), "this is a picture");
        Paths.get(target.toString(), desktop).toFile().mkdirs();
        writeToFile(Paths.get(target.toString(), desktop, pngFile), "this is a picture but in as different folder");
        writeToFile(Paths.get(target.toString(), desktop, jpgFile), "this is a different picture");
    }

    @Test
    public void test() throws IOException, InvalidKeyException {
        assertFalse(IOManager.areDirectoriesEqual(Paths.get(root, "source", "C:"), Paths.get(root, "target", "C:"), Paths.get(root, "source", "C:").toFile()));
        // Registry.importRegistryDump(Paths.get(Constants.SOURCE.toString(), "dump.reg").toFile());
        Configuration configuration = new Configuration(Paths.get(root, "source", "config.pc-sync.txt").toFile());
        for (File d : configuration.getSelectedDrives()) {
            DriveMirrorer mirrorer = new DriveMirrorer(Paths.get(root, "source", "C:").toFile(), Paths.get(root, "target", d.toString()).toFile(), configuration);
            mirrorer.run();
        }
        assertTrue(IOManager.areDirectoriesEqual(Paths.get(root, "source", "C:"), Paths.get(root, "target", "C:"), Paths.get(root, "source", "C:").toFile()));
    }
}
