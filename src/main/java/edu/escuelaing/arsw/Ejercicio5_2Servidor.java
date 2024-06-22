package edu.escuelaing.arsw;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejercicio5_2Servidor {
    DatagramSocket socket;

    public Ejercicio5_2Servidor() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            Logger.getLogger(Ejercicio5_2Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];
        try {
            while(true){
                LocalDateTime date = LocalDateTime.now();
                if(date.now().getSecond() % 5 == 0){
                    System.out.println(date.now().getSecond());
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String dString = new Date().toString();
                    buf = dString.getBytes();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio5_2Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.close();
    }

    public static void main(String[] args) {
        Ejercicio5_2Servidor ds = new Ejercicio5_2Servidor();
        ds.startServer();
    }
}
