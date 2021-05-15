/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamdprinter;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Escola
 */
public class Server extends Thread {
    
    private static ServerSocket sv;
    private static int port = 11340;
    private boolean loop;
    private List<ClientManager> clients = Collections.synchronizedList(new ArrayList<ClientManager>());


    @Override
    public void run() {
        try {
            loop = true;
            sv = new ServerSocket(port);
            while (loop) {
                System.out.println("Waiting for connection...");
                Socket socket = sv.accept();
                PrintManager.getInstance().startThread();
                handleClient(socket);
                Thread.sleep(10);
            }
            if (!sv.isClosed()) {
                sv.close();
            }
        } catch (IOException | InterruptedException ex) {
            if (ex.getMessage().equals("socket closed")) {
                System.out.println("Conection Closed...");
            } else {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleClient(Socket socket) {
        ClientManager manager = new ClientManager();
        manager.setClientSocket(socket);
        clients.add(manager);
        Thread threadSocket = new Thread(manager);
        threadSocket.start();
    }

    public void close() throws IOException {
        for(ClientManager cl : clients){
            cl.close();
        }
        loop = false;
        sv.close();
    } 
}
