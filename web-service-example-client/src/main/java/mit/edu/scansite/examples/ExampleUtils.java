package mit.edu.scansite.examples;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

public class ExampleUtils {
//    public static String baseURL = "http://localhost:8080/";
    public static String baseURL = "https://scansite4.mit.edu/webservice/";
    String baseUrl = "";

    public static void writeToFile(String content, String fileName) {
        List<String> asList = new ArrayList<>();
        asList.add(content);
        Path file = Paths.get(fileName);
        try {
            Files.write(file, asList, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> runRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            List<String> result = new ArrayList<>();
            while ((output = br.readLine()) != null) {
                result.add(output);
            }

            conn.disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * At this point formatting the output to a "pretty print" XML format is considered
     * as a "nice to have" feature and might be implemented in future
     */
    public static String formatToXML(String content, ApplicationChoice applicationChoice) {
        switch (applicationChoice) {
            case PROTEINSCAN:
                return formatProteinScanResult(content);
            case PROTEIN_SEQUENCE_SCAN:
                return formatProteinScanResult(content);
            case DATABASE_SEARCH:
                return formatDatabaseSearchResult(content);
            case SEQUENCE_MATCH:
                return formatSequenceMatchResult(content);
            case ORTHOLOG_SCAN:
                return formatOtrhologScanResult(content);
            case LOCALIZATION_SCAN:
                return formatLocalizationScanResult(content);
        }
        return null;
    }

    private static String formatProteinScanResult(String content) {
        throw new NotImplementedException();
    }

    private static String formatDatabaseSearchResult(String content) {
        throw new NotImplementedException();
    }

    private static String formatSequenceMatchResult(String content) {
        throw new NotImplementedException();
    }

    private static String formatOtrhologScanResult(String content) {
        throw new NotImplementedException();
    }

    private static String formatLocalizationScanResult(String content) { throw new NotImplementedException(); }

}
