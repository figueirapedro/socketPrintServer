/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamdprinter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Escola
 */
public class PrintManager {
        ConcurrentLinkedQueue<String> prints;
    

    /* Implementação do Singleton */
    private static PrintManager instance;

    private PrintManager() {
        prints = new ConcurrentLinkedQueue<>();
        //filaMensagens = new LinkedList<>();
    }

    public static PrintManager getInstance() {
        if (instance == null) {
            instance = new PrintManager();
        }
        return instance;
    }

    /* Fim da implementação do Singleton */
    List<ThreadPrintManager> threads;

    public void addPrint(String msg) {
        prints.add(msg);
    }

    String pollPrint() {
         return prints.poll();
    }

    public void on() {
        if (threads == null) {
            threads = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ThreadPrintManager thread = new ThreadPrintManager((i + 1));
                thread.setName("Thread Print " + (i + 1));
                threads.add(thread);
            }
        }
    }
    
    public void startThread(){
        for (Thread thr : threads){
            if(!thr.isAlive()){
                thr.start();
                break;
            }
        }
    }

    public void off() {
        if (threads != null) {
            for (ThreadPrintManager thread : threads) {
                thread.setStatus(false);
                try {
                    thread.join(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrintManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            }
        }
    }    
}
