package iis.tommasosalvini.pcrestorer.utils;

import iis.tommasosalvini.pcrestorer.drive.Drive;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DriveUtils {

    public static boolean compare(Drive d1, Drive d2) throws IOException, NoSuchAlgorithmException {
        String[] c1 = d1.getRoot().list();
        String[] c2 = d1.getRoot().list();
        ArrayList<String> c1names = new ArrayList<String>();
        ArrayList<String> c2names = new ArrayList<String>();
        for (String fc1 : c1) c1names.add(d1.getRoot().toString() + "/" + new File(fc1).getName());
        for (String fc2 : c2) c2names.add(d2.getRoot().toString() + "/" + new File(fc2).getName());

        if (c1.length != c2.length) return false;
        for (int i = 0; i < c1.length; i++) if (!(c1names.get(i).equals(c2names.get(i)))) return false;
        for (int i = 0; i < c2.length; i++) {
            if (!(FolderUtils.compare(new File(c1names.get(i)), new File(c2names.get(i))))) return false;
        }
        return true;
    }

}
