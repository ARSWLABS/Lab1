package edu.eci.arsw.math;

public class Main {

    public static void main(String[] args) {
        System.out.println(bytesToHex(PiDigits.getDigits(0, 10, 1)));
        System.out.println(bytesToHex(PiDigits.getDigits(1, 20, 2)));
        System.out.println(bytesToHex(PiDigits.getDigits(2, 30, 3)));

    }

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];  // Primer dígito hex
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];  // Segundo dígito hex
        }
        return new String(hexChars); 
    }
}
