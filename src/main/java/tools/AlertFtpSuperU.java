package tools;

import com.beust.jcommander.internal.Lists;
import utils.file.FileManager;
import utils.ftp.FtpClient;
import utils.javamail.MailManager;

import java.io.File;
import java.util.List;

/**
 * Created by jzhang on 03/03/2015.
 */
public class AlertFtpSuperU {

    public static void main(String[] args) {

        //editer le contenu de mail
        StringBuffer text = new StringBuffer().append("Bonjour Nicole,\n")
                .append("Nous avons les nouveaux fichiers suivants sur le serveur FTP de SuperU:\n\n");

        //liste de distination
        List<String> toEmail = Lists.newArrayList();
        toEmail.add("Production-Pige@retailexplorer.fr");

        String pathFile = new File("").getAbsolutePath()+"/list";
        List<String> old = FileManager.readFile(pathFile);
        List<String> news = Lists.newArrayList();
        FtpClient ftp = new FtpClient("su.elpev.com","su_national","n3zmge");
        List<String> list = ftp.getFileList(".");
        ftp.close();
        FileManager.writeFile(pathFile.concat(""), list);
        list.stream().forEach(file -> {
            if (!old.contains(file)) {
                news.add(file+"\n");
            }
        });
        news.forEach(text::append);
        text.append("\n\nCordialement,\nJie Zhang");

        MailManager mail = new MailManager("mail.retailexplorer.fr","jzhang","Ret1234$");

        if (news.size()>0)
            mail.envoyerMailSMTP("info@retailexplorer.fr", toEmail, "Nouveau fichier sur le ftp SuperU", text);
        else
            mail.envoyerMailSMTP("info@retailexplorer.fr", toEmail, "Nouveau fichier non trouvé sur le ftp SuperU", new StringBuffer(""));;

    }
}
