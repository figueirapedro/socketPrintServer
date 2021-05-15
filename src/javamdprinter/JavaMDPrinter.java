/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamdprinter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Escola
 */
public class JavaMDPrinter {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
        Server sv = new Server();
        PrintManager.getInstance().on();
        sv.start();
        Scanner scan = new Scanner(System.in);
        try {
            boolean loop = true;
            do {
                System.out.println("Type 'END' to shutdown");
                if (scan.next().equals("END"))
                    loop = false;
            } while (loop);
        } finally {
            scan.close();
            sv.close();
            PrintManager.getInstance().off();
        } 
    }
}
