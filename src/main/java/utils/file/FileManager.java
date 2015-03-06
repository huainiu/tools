package utils.file;

import com.beust.jcommander.internal.Lists;

import java.io.*;
import java.util.List;

/**
 * Created by jzhang on 20/02/2015.
 */
public class FileManager {
    static public List<String> readFile(String file) {
        BufferedReader br = null;
        List<String> list = Lists.newArrayList();
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    static public void writeFile(String file, List<String> text) {

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            text.forEach(line -> {
                try {
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
