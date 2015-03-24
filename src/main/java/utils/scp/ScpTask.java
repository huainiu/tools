package utils.scp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jzhang on 24/03/2015.
 */
public class ScpTask implements Runnable{
    private Session session;
    private String Path;
    private String url;
    private String newName;

    protected final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(getClass());

    @Override
    public void run() {
        Task(session,Path,url,newName);
    }

    public ScpTask(Session session,String Path, String url, String newName) {
        this.session = session;
        this.Path = Path;
        this.url = url;
        this.newName = newName;
    }

    private void Task(Session session,String Path, String url, String newName){
        try {
            log.info("start download :"+url);

            Channel channel = session.openChannel("exec");
            String command = "scp -p -t " + newName;
            ((ChannelExec)channel).setCommand("cd " + Path + " && "+command);
            InputStream in = channel.getInputStream();
            OutputStream out = channel.getOutputStream();
            channel.connect();

            if (checkAck(in)!=0) System.exit(0);
            HttpResponse response = new DefaultHttpClient().execute(new HttpGet(url));

            long filesize = response.getEntity().getContentLength();
            command = "C0644 " + filesize + " " + newName;
            command += "\n";
            out.write(command.getBytes());
            out.flush();

            // send a content of lfile
            BufferedInputStream fis = new BufferedInputStream(response.getEntity().getContent());
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0)
                    break;
                out.write(buf, 0, len);
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


}
