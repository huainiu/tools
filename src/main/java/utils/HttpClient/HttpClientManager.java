package utils.HttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.*;

/**
 * Created by jzhang on 17/03/2015.
 */
public class HttpClientManager {
    HttpClient client;

    public HttpClientManager(){
        client = new DefaultHttpClient();
    }

    public void download(String path,String url){
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity!=null) {
                BufferedInputStream bis = new BufferedInputStream(entity.getContent());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
                int inByte;
                while((inByte = bis.read()) != -1) bos.write(inByte);
                bis.close();
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HttpClientManager().download("C:\\Users\\jzhang\\Desktop\\1.jpg","http://nos-catalogues-promos.e-leclerc.com/Leclerc/operations/14228911005876/catalogs/1/pages/cover/standard.png");
        new HttpClientManager().download("C:\\Users\\jzhang\\Desktop\\2.jpg","http://nos-catalogues-promos.e-leclerc.com/Leclerc/operations/14224376005861/catalogs/1/pages/cover/standard.png");
    }


}
