package utils.DownloadAndUpload;

import utils.HttpClient.HttpClientManager;
import utils.scp.ScpClient;

import java.io.File;

/**
 * Created by jzhang on 23/03/2015.
 */
public class TaskThread extends Thread{
    HttpClientManager httpClient;
    ScpClient scpClient;
    public TaskThread(HttpClientManager httpClient,ScpClient scpClient) {
        this.httpClient = httpClient;
        this.scpClient = scpClient;

    }
    public void run() {
//        httpClient.download(pathLocal, url);
//        scpClient.scpUpload(pathBase + pathCata, new File(pathLocal), "page" + (++pageNumber) + ".jpg");

    }
}
