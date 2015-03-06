package utils.ftp;

import com.beust.jcommander.internal.Lists;
import utils.file.FileManager;
import utils.javamail.MailManager;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jzhang on 20/02/2015.
 */
public class FtpClient {
    private FTPClient client;

    public FtpClient(String host, String login, String passwd){
        try {
            client = new FTPClient();
            client.connect(host);
            client.login(login, passwd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFileList(String path) {
        List<String> list = Lists.newArrayList();
        try {
            FTPFile[] ftpFilesNiv1 = client.listFiles(path);
            for (FTPFile ftpFile : ftpFilesNiv1) {
                // Check if FTPFile is a regular file
                if (ftpFile.getType() == FTPFile.DIRECTORY_TYPE) {
                    list.addAll(getFileList(path + "/" + ftpFile.getName()));
                } else if (ftpFile.getType() == FTPFile.FILE_TYPE) {
                    list.add(path+"/"+ftpFile.getName());
//                    System.out.println(path + "/" + ftpFile.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

}
