package de.hsrw.dimitriosbarkas.ute.utils;

import java.io.*;

/**
 * This class provides some helper methods.
 */
public final class Utilities {

    /**
     * Default constructor is private.
     */
    private Utilities () {}

    /**
     * helper method for xml-mapping
     *
     * @param is InputStream
     * @return String from InputStream
     * @throws IOException if an I/O Error occurs
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    /**
     * Helper method to read from a file.
     * @param file the file to read from.
     * @return the content of the file as a string.
     */
    public static String readFromFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Helper method to write in a file
     * @param file the path to the file to write
     * @param data the data to write in the file.
     */
    public static void writeFile(File file, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
//            String str = "package com.test.app; \n\n";
//            byte[] strToBytes = str.getBytes();
//            fos.write(strToBytes);
            fos.write(data);
            //log.info(file.getAbsolutePath() + " saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
