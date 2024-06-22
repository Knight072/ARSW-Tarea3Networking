package edu.escuelaing.arsw;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.*;

public class Ejercicio5_2Cliente {
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf;

    public Ejercicio5_2Cliente() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("127.0.0.1");
            socket.setSoTimeout(5000); // Timeout de 5 segundos para recibir la respuesta
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(Ejercicio5_2Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String sendRequest() {
        String received = "No response";
        try {
            buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            received = new String(packet.getData(), 0, packet.getLength());
        } catch (SocketTimeoutException ex) {
            System.out.println("No response from server. Keeping the last known time.");
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio5_2Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return received;
    }

    public static void main(String[] args) {
        Ejercicio5_2Cliente client = new Ejercicio5_2Cliente();
        String lastTime = "No time received yet";

        while (true) {
            String currentTime = client.sendRequest();
            if (!currentTime.equals("No response")) {
                lastTime = currentTime;
            }
            System.out.println("Current Time: " + lastTime);

            try {
                Thread.sleep(5000); // Espera de 5 segundos antes de la próxima solicitud
            } catch (InterruptedException ex) {
                Logger.getLogger(Ejercicio5_2Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

