package com.test.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class StatisticUtil {
    /**
     * Computes the average age from a CSV input stream that contains the following structure.
     *
     *    Bob;Builder;15
     *    Alice;Builder;21
     *    Charles;Drawing;24
     *
     * Result in this case would be 20.
     *
     * @param is input stream
     * @return average age
     * @throws LineHasWrongSyntaxException if a syntax occurs in one line
     * @throws IOException if a general IO exception occurs
     */
    public double getAverageAge(InputStream is) throws LineHasWrongSyntaxException, IOException {
        long totalAge = 0;
        long count = 0;

        try(LineNumberReader br = new LineNumberReader(new InputStreamReader(is))) {
            String line;
            while(null != (line = br.readLine())) {
                String[] items = line.split(";");

                if(items.length != 3) {
                    throw new LineHasWrongSyntaxException(br.getLineNumber() - 1);
                }

                try {
                    totalAge += Long.valueOf(items[2]);
                } catch(NumberFormatException ex) {
                    throw new LineHasWrongSyntaxException(br.getLineNumber() - 1);
                }

                count++;
            }
        }
        return totalAge / count;
    }
}
