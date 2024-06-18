package edu.escuelaing.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ejercicio4Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine, operation;
        while ((inputLine = in.readLine()) != null) {
            outputLine = null;
            if(inputLine.startsWith("fun")) {
                if (inputLine.startsWith("fun:sin")){
                    operation = "sin";
                    System.out.println(Math.sin(Integer.parseInt(inputLine)));
                    outputLine = "Respuesta de sin: " + Math.sin(Integer.parseInt(inputLine));
                } else if (inputLine.startsWith("fun:cos")) {
                    System.out.println(Math.cos(Integer.parseInt(inputLine)));
                    outputLine = "Respuesta de cos: " + Math.cos(Integer.parseInt(inputLine));
                } else if (inputLine.startsWith("fun:tan")) {
                    System.out.println(Math.tan(Integer.parseInt(inputLine)));
                    outputLine = "Respuesta de tan: " + Math.tan(Integer.parseInt(inputLine));
                }
            }
            else{
                System.out.println(Math.cos(Integer.parseInt(inputLine)));
                outputLine = "Respuesta: " + Math.cos(Integer.parseInt(inputLine));
            }
            out.println(outputLine);
            if (outputLine.contains("Bye"))
                System.out.println("Bye");
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
