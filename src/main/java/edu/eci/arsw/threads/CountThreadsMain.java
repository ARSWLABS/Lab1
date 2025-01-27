/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    public static void main(String[] args) {
        CountThread thread1 = new CountThread(0, 99);
        CountThread thread2 = new CountThread(100, 199);
        CountThread thread3 = new CountThread(200, 299);

        // Usando start()
        System.out.println("Usando start():");
        thread1.start();
        thread2.start();
        thread3.start();

        // Esperar que los hilos terminen (opcional)
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Usando run()
        System.out.println("\nUsando run():");
        thread1.run();
        thread2.run();
        thread3.run();
    }
}