/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamdprinter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Escola
 */
public class ClientManager implements Runnable {
    
    private Socket clientSocket;
    
    @Override
    public void run() {
        try {
            System.out.println(clientSocket.hashCode() + ": Connected");
            InputStream stream = clientSocket.getInputStream();
            try {        
                    boolean ativo = true;
                    while (ativo)
                    {
                        if (stream.available() != 0)
                        {
                            byte[] dados = new byte[stream.available()];
                            stream.read(dados);
                            PrintManager.getInstance().addPrint(new String(dados));
                            System.out.println("Message Sent!");
                        }
                        Thread.sleep(10);
                    }
                    System.out.println("END");
                } catch (InterruptedException ex) {
                        Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                if (stream != null) {
                    stream.close();
                }
                if (clientSocket.isConnected()) {
                    clientSocket.close();
                }
            }
        } catch (IOException ex) {
            if (ex.getMessage().equals("Socket closed")) {
                System.out.println(clientSocket.hashCode() + ": Connection Closed");
            } else {
                Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the clientSocket
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * @param clientSocket the clientSocket to set
     */
    
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void close() throws IOException {
        if (clientSocket.isConnected()) {
            clientSocket.close();
        }
    }
}
