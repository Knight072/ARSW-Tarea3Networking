package edu.escuelaing.arsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Ejercicio4_5_1ServidorWeb {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor listo para recibir solicitudes en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("Error al abrir el socket del servidor: " + e.getMessage());
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                // Esperar y aceptar una conexión entrante
                clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

                // Crear un lector de solicitud HTTP desde el cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String requestLine = in.readLine(); // Leer la línea de solicitud HTTP

                if (requestLine == null || requestLine.isEmpty()) {
                    System.err.println("Línea de solicitud HTTP no válida o vacía");
                    continue; // Saltar a la siguiente iteración para aceptar otra conexión
                }

                System.out.println("Solicitud recibida: " + requestLine);

                // Extraer la ruta del archivo solicitado desde la solicitud HTTP GET
                String[] tokens = requestLine.split("\\s+");
                if (tokens.length < 2 || !tokens[0].equals("GET")) {
                    System.err.println("Solicitud HTTP no válida");
                    continue;
                }
                String filePath = tokens[1].substring(1); // El segundo token es la ruta del archivo

                // Servir el archivo solicitado
                FileInputStream fis = null;
                boolean fileExists = true;
                try {
                    fis = new FileInputStream(filePath);
                } catch (FileNotFoundException e) {
                    fileExists = false;
                }

                // Construir la respuesta HTTP
                OutputStream out = clientSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(out);

                if (fileExists) {
                    pw.println("HTTP/1.1 200 OK");
                    pw.println("Content-Type: " + contentType(filePath));
                    pw.println("Content-Length: " + new File(filePath).length());
                    pw.println();
                    pw.flush();

                    // Escribir el contenido del archivo en la respuesta
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.flush(); // Asegurar que todos los datos se envíen al cliente
                    fis.close();
                } else {
                    // Si el archivo no existe, devolver un error 404
                    pw.println("HTTP/1.1 404 Not Found");
                    pw.println("Content-Type: text/html");
                    pw.println();
                    pw.println("<h1>404 Not Found</h1>");
                    pw.flush();
                }

                // Cerrar streams y socket del cliente
                pw.close();
                in.close();
                clientSocket.close();

            } catch (IOException e) {
                System.err.println("Error al procesar la solicitud: " + e.getMessage());
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        System.err.println("Error al cerrar el socket del cliente: " + ex.getMessage());
                    }
                }
            }
        }
    }

    // Método para determinar el tipo de contenido del archivo basado en la extensión
    private static String contentType(String filePath) {
        if (filePath.endsWith(".html") || filePath.endsWith(".htm")) {
            return "text/html";
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filePath.endsWith(".png")) {
            return "image/png";
        } else if (filePath.endsWith(".txt")) {
            return "text/plain";
        } else {
            return "application/octet-stream";
        }
    }
}


