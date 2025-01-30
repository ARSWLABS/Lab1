/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 * Esta clase es la encargada de crear los hilos y de imprimir los números entre
 * un rango de números.
 * @version 1.0
 * @author Juan Cancelado y Diego Chicuazuque
 */
public class CountThreadsMain {

  /**
   * Este método es el encargado de iniciar la ejecución del programa.
   * @param args Argumentos de línea de comandos (no utilizados en este programa).
   */
  public static void main(String[] args) {
    CountThread thread1 = new CountThread(0, 99);
    CountThread thread2 = new CountThread(100, 199);
    CountThread thread3 = new CountThread(200, 299);

    System.out.println("Usando start():");
    thread1.start();
    thread2.start();
    thread3.start();

    try {
      thread1.join();
      thread2.join();
      thread3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("\nUsando run():");
    thread1.run();
    thread2.run();
    thread3.run();
  }
}
