package de.hsrw.dimitriosbarkas.ute.utils;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * This class provides some helper methods.
 */
@Log4j2
public final class Utilities {

    /**
     * Default constructor is private.
     */
    private Utilities() {
    }

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
     *
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
     *
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

    public static void addMutators(File file, List<String> mutatorsList) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node configurationNode = document.getElementsByTagName("configuration").item(0);

            //create mutators element
            Element mutators = document.createElement("mutators");
            //add each mutator
            for (String givenMutator : mutatorsList) {
                Element mutator = document.createElement("mutator");
                mutator.appendChild(document.createTextNode(givenMutator));
                mutators.appendChild(mutator);
            }

            configurationNode.appendChild(mutators);



            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            log.error(e);
        }
    }
}