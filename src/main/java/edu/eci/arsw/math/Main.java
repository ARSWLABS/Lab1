/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author hcadavid
 */
public class Main {

  public static void main(String a[]) {
    //System.out.println(bytesToHex(PiDigits.getDigits(0, 10)));
    //System.out.println(bytesToHex(PiDigits.getDigits(1, 100)));
    //System.out.println(bytesToHex(PiDigits.getDigits(1, 1000000)));

    int availableCores = Runtime.getRuntime().availableProcessors();

    // Establecemos el número de dígitos de Pi que queremos calcular (un millón de dígitos hexadecimales)
    int digitsToCalculate = 1000000;

    // Experimentamos con diferentes configuraciones de hilos
    runExperiment("Un solo hilo", 1, digitsToCalculate);
    runExperiment(
      "Con " + availableCores + " hilos (núcleos)",
      availableCores,
      digitsToCalculate
    );
    runExperiment(
      "Con " + (availableCores * 2) + " hilos",
      availableCores * 2,
      digitsToCalculate
    );
    runExperiment("Con 200 hilos", 200, digitsToCalculate);
    runExperiment("Con 500 hilos", 500, digitsToCalculate);
  }

  private static void runExperiment(
    String experimentName,
    int numThreads,
    int digitsToCalculate
  ) {
    System.out.println("\nExperimentando: " + experimentName);

    // Medimos el tiempo antes de comenzar el experimento
    long startTime = System.nanoTime();

    // Ejecutamos el cálculo de Pi usando múltiples hilos
    calculatePiWithThreads(numThreads, digitsToCalculate);

    // Medimos el tiempo después de ejecutar el experimento
    long endTime = System.nanoTime();

    // Calculamos el tiempo de ejecución en milisegundos
    long duration = (endTime - startTime) / 1000000;
    System.out.println(
      "Tiempo de ejecución con " + numThreads + " hilos: " + duration + " ms"
    );
  }

  private static void calculatePiWithThreads(
    int numThreads,
    int digitsToCalculate
  ) {
    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
    int range = digitsToCalculate / numThreads;
    @SuppressWarnings("unchecked")
    Future<byte[]>[] futures = (Future<byte[]>[]) new Future<?>[numThreads];

    // Dividimos el trabajo entre los hilos
    for (int i = 0; i < numThreads; i++) {
      final int start = i * range;
      final int end = (i == numThreads - 1)
        ? digitsToCalculate
        : (i + 1) * range;
      futures[i] = executorService.submit(() -> PiDigits.getDigits(start, end));
    }

    // Esperamos a que todos los hilos terminen y juntamos los resultados
    byte[] result = new byte[digitsToCalculate];
    try {
      for (int i = 0; i < numThreads; i++) {
        byte[] partialResult = futures[i].get();
        System.arraycopy(
          partialResult,
          0,
          result,
          i * range,
          partialResult.length
        );
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    // Cerramos el servicio del ejecutor
    executorService.shutdown();

    // Convertimos el resultado a hexadecimal y lo imprimimos (si es necesario)
    System.out.println(bytesToHex(result).substring(0, 100) + "..."); // Solo imprimimos los primeros 100 caracteres
  }

  private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < hexChars.length; i++) {
      sb.append(hexChars[i]);
    }
    return sb.toString();
  }
}
