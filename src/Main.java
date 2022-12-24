import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {

        openZip("E://Game/savegames/zip.zip", "E://Game/savegames/");

        ArrayList<GameProgress> list = new ArrayList<>();

        openProgress(list,"E://Game/savegames/save1.dat",
                                    "E://Game/savegames/save2.dat",
                                    "E://Game/savegames/save3.dat");
        System.out.println(list);


    }

    public static void openZip(String zip, String unpack) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(unpack + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<GameProgress> openProgress(ArrayList list, String... files) {
        GameProgress gameProgress ;
        for (String file : files) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                gameProgress = (GameProgress) ois.readObject();
                list.add(gameProgress);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
}