/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamdprinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Escola
 */
public class ThreadPrintManager extends Thread {
    
    private boolean status;
    private int port;
    
    public void setPort(int p){
        this.port = port;
    }
    
    public int getPort(){
        return this.port;
    }

    public ThreadPrintManager(int numThread) {
        this.port = 11340 + numThread;       
    }

    @Override
    public void run() {
        System.out.println(getPort());
        setStatus(true);
        while (status) {
            try {
                String msg = PrintManager.getInstance().pollPrint();
                if (msg != null) {
                    sendToPrinter(msg);
                }
                Thread.sleep(1);
            } catch (InterruptedException | IOException | ClassNotFoundException ex) {
                Logger.getLogger(ThreadPrintManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setStatus(boolean value) {
        status = value;
    }

    private void sendToPrinter(String msg) throws InterruptedException, IOException, ClassNotFoundException {    
        InetAddress host = InetAddress.getLocalHost();
        SocketAddress address = new InetSocketAddress(host, port);
        try (Socket socket = new Socket()) {
            socket.connect(address);
            try (OutputStream out = socket.getOutputStream()) {
                try (Writer writer = new OutputStreamWriter(out)) {
                    writer.write(msg);
                    System.out.println("Message sent");
                }
            }
            System.out.println("Connection END");
            socket.close();
        }
        Thread.sleep(1000);
    }    
}
