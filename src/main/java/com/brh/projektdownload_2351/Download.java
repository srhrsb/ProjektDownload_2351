package com.brh.projektdownload_2351;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class Download implements Runnable{
    private String link;
    private String target;
    private File outputFile;
    public Download(String link, String target ) {
        this.link = link;
        this.target = target;
    }

    public void run(){
        try{
            URL url = new URL(link);

            //öffnen der Verbindung
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            //Input Stream
            BufferedInputStream buffInputStream =
                    new BufferedInputStream( connection.getInputStream() );

            //File Objekt um Dateinamen des Links zu ermitteln
            File file =  new File(link);

            //Ausgabefile mit dem Target-Pfad und übernommenem Dateinamen
            outputFile = new File(target, file.getName());

            //Outputstream mit Ziel
            OutputStream outputStream = new FileOutputStream(outputFile);

            BufferedOutputStream buffOutputStream =
                    new BufferedOutputStream( outputStream , 1024);

            byte[] buffer = new byte[1024];
            int downloaded = 0;
            int readByte = 0;

            while( (readByte = buffInputStream.read(buffer, 0, 1024) ) >= 0 ){
                buffOutputStream.write(buffer, 0, readByte);
                downloaded += readByte;
                System.out.println("Runtergeladen ( "+this+")" +downloaded);
            }

            buffOutputStream.close();
            buffInputStream.close();
            System.out.println("Download erfolgreich");
        }
        catch( IOException e){
            throw new RuntimeException(e);
        }
    }
}