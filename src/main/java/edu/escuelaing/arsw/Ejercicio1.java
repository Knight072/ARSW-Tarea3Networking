package edu.escuelaing.arsw;
import java.net.*;
public class Ejercicio1 {

    public void returnProtocolURL(String url){
        try {
            URL myURL = new URL(url);
            System.out.println(myURL.getProtocol());
            System.out.println(myURL.getAuthority());
            System.out.println(myURL.getHost());
            System.out.println(myURL.getPort());
            System.out.println(myURL.getPath());
            System.out.println(myURL.getQuery());
            System.out.println(myURL.getFile());
            System.out.println(myURL.getRef());
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

