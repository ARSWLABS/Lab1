/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author Juan Cancelado y Diego Chicuazuque
 */
package edu.eci.arsw.threads;

/**
 *
 * @author Juan Cancelado y Diego Chicuazuque
 */
public class CountThread extends Thread {

  private int start;
  private int end;

  /**
   * This method is the constructor of the class
   * @param start
   * @param end
   */

  public CountThread(int start, int end) {
    this.start = start;
    this.end = end;
  }

  /**
   * This method is the run method of the class
   * In this case it prints the numbers between as start and end
   */
  @Override
  public void run() {
    for (int i = start; i <= end; i++) {
      System.out.println(i);
    }
  }
}
