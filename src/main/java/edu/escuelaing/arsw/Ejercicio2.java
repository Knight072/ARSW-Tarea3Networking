package edu.escuelaing.arsw;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Ejercicio2 {
    public void escribirArchivo(){
        Scanner url = new Scanner (System.in);
        System.out.println("Ingrese la URL: ");
        String nombre = url.next();
        try {
            URL myURL = new URL(nombre);
            try(FileWriter fw = new FileWriter("myfile.html", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(myURL.openStream()))) {
                    String inputLine = null;
                    while ((inputLine = reader.readLine()) != null) {
                        out.println(inputLine);
                    }
                    reader.close();
                    bw.close();
                } catch (IOException x) {
                        System.err.println(x);
                }
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
