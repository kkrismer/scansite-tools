package mit.edu.scansite.examples.multipleproteinscan;

import mit.edu.scansite.examples.ExampleUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

public class FileInputMultipleProteinScanExample {
    private static final String param1 = "/identifier=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/motifclass=";
    private static final String param4 = "/stringency=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "FileInputMultipleProteinScanExampleOutput.txt";

    public static void main(String[] args) {
        String fileName = "./src/main/resources/input.txt";
        List<String> identifiers = getProteinIdentifiersFromFile(fileName);

        String dsshortname = "swissprot";
        String motifclass = "YEAST";
        String stringency = "High";

        List<String> multipleScanResults = new ArrayList<>();

        for (String identifier : identifiers) {
            String urlString = baseURL + service + param1 + identifier
                    + param2 + dsshortname
                    + param3 + motifclass
                    + param4 + stringency;

            List<String> results = ExampleUtils.runRequest(urlString);
            String resultContent = "";
            for (String result : results) {
                resultContent += result + "\n";
            }
            multipleScanResults.add(resultContent);
        }

        String multipleResultsContent = "";
        for (String result : multipleScanResults) {
            multipleResultsContent += result + "\n\n\n\n";
        }

        ExampleUtils.writeToFile(multipleResultsContent, outputFileName);
        System.out.println(multipleResultsContent);
    }


    /** make sure to only use valid gene identifiers in your file and separate them by line, i.e.
      * VAV_HUMAN
      * BRCA2_HUMAN
      * BRAC_HUMAN
      * EGFR_HUMAN
      *
      * see also input.txt
      * File reading code source: http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
      */
    private static List<String> getProteinIdentifiersFromFile(String fileName) {
//        System.out.println(System.getProperty("user.dir")); // to print the default directory for files to read
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<String> ids = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                ids.add(line);
                line = br.readLine();
            }
            return ids;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
