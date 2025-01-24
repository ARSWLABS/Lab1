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
public class CountThread {

  private int start, end;

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
  public void run() {
    for (int i = start; i <= end; i++) {
      System.out.println(i);
    }
  }
}
