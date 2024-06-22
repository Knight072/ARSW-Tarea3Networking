package edu.escuelaing.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Ejercicio4_3_2Servidor {
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
        String inputLine, outputLine, operation = "cos";
        Map<String, Function<Double, Double>> operations = new HashMap<>();
        operations.put("sin", Math::sin);
        operations.put("cos", Math::cos);
        operations.put("tan", Math::tan);

        while ((inputLine = in.readLine()) != null) {
            outputLine = "";
            if (inputLine.startsWith("fun:")) {
                operation = inputLine.substring(4); // extrae la operación después de "fun:"
            } else if (operations.containsKey(operation)) {
                try {
                    double input = Double.parseDouble(inputLine);
                    double result = operations.get(operation).apply(input);
                    outputLine = "Respuesta de " + operation + ": " + result;
                } catch (NumberFormatException e) {
                    outputLine = "Entrada no válida: " + inputLine;
                }
            }
            System.out.println(outputLine);
            out.println(outputLine);
            if (inputLine.equals("Bye.")) {
                break;
            }
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
