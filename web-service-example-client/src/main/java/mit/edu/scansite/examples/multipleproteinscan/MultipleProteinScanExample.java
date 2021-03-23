package mit.edu.scansite.examples.multipleproteinscan;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.ArrayList;
import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

public class MultipleProteinScanExample {
    private static final String param1 = "/identifier=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/motifclass=";
    private static final String param4 = "/stringency=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "MultipleProteinScanExampleOutput.txt";

    public static void main(String[] args) {
        List<String> identifiers = new ArrayList<>();
        identifiers.add("vav_human");
        identifiers.add("brca2_human");

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
}
