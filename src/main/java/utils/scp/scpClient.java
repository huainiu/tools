package utils.scp;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.Properties;

/**
 * Created by jzhang on 16/03/2015.
 */
public class ScpClient {

    protected final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(getClass());


    private Session session;
    public ScpClient(String login, String passwd) {
        try {
            JSch client = new JSch();
            session = client.getSession("prod",login,22);
            session.setPassword(passwd);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking","no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        session.disconnect();
    }

    public void scpUpload(String Path,File f, String newName){
        try {
            Channel channel = session.openChannel("exec");
            String command = "scp -p -t " + newName;
            ((ChannelExec)channel).setCommand("cd " + Path + " && "+command);
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            channel.connect();

            if (checkAck(in)!=0) System.exit(0);
            long filesize = f.length();
            command = "C0644 " + filesize + " " + f.getName();
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }

            // send a content of lfile
            FileInputStream fis = new FileInputStream(f);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0)
                    break;
                out.write(buf, 0, len); // out.flush();
            }
            fis.close();

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }
            out.close();
            in.close();
            channel.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean mkdir(String path) {
        boolean isNewCata = false;
        try {
            ChannelSftp channel = (ChannelSftp)session.openChannel("sftp");
            channel.connect();
            for (String folder:path.replaceFirst(channel.pwd() + "/", "").split("/")) {
                try {
                    channel.cd(folder);
                } catch (SftpException e) {
                    try {
                        channel.mkdir(folder);
                        channel.cd(folder);
                        isNewCata = true;
                    } catch (SftpException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            channel.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            return isNewCata;
        }
    }

    private int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0)
            return b;
        if (b == -1)
            return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    public static void main(String[] args) {
/*        ScpClient client = new ScpClient("94.23.63.229","FJKKnaVy68T5vtReSykF");
        client.scpUpload("/home/prod/projects/pige-crawler-catalogue2",new File("C:\\Users\\jzhang\\Desktop\\squid.conf"),"newname");
        client.close();
*/
//        ScpClient client = new ScpClient("94.23.63.229","FJKKnaVy68T5vtReSykF");
//        System.out.println(client.mkdir("/home/prod/projects/pige-crawler-catalogue2/web/uploads/data/ens_test"));
//        client.close();
    }
}
