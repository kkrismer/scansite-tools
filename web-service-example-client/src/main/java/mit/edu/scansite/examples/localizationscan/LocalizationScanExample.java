package mit.edu.scansite.examples.localizationscan;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../predictlocation/localizationdsshortname=loctree/identifier=BRCA2_HUMAN/dsshortname=swissprot
public class LocalizationScanExample {
    private static final String param1 = "/localizationdsshortname=";
    private static final String param2 = "/identifier=";
    private static final String param3 = "/dsshortname=";

    private static final String service = "predictlocation";
    private static final String outputFileName = "LocalizationScanExampleOutput.txt";

    public static void main(String[] args) {
        String localizationdsshortname = "loctree";
        String identifier = "BRCA2_HUMAN";
        String dsshortname = "swissprot";

        String urlString = baseURL + service
                + param1 + localizationdsshortname
                + param2 + identifier
                + param3 + dsshortname;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);
    }
}
