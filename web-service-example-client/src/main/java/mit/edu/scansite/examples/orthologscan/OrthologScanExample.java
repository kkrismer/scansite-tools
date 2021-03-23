package mit.edu.scansite.examples.orthologscan;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../scanorthologs/identifier=BRCA2_HUMAN/dsshortname=swissprot/orthologydsshortname=swissprotorthology/alignmentradius=40/stringency=High/motifgroup=Acid_ST_kin/siteposition=306
public class OrthologScanExample {
    private static final String param1 = "/identifier=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/orthologydsshortname=";
    private static final String param4 = "/alignmentradius=";
    private static final String param5 = "/stringency=";
    private static final String param6 = "/motifgroup=";
    private static final String param7 = "/siteposition=";

    private static final String service = "scanorthologs";
    private static final String outputFileName = "OrthologScanExampleOutput.txt";

    public static void main(String[] args) {
        String identifier = "BRCA2_HUMAN";
        String dsshortname = "swissprot";
        String orthologydsshortname = "swissprotorthology";
        String alignmentradius = "40";
        String stringency = "High";
        String motifgroup = "Acid_ST_kin";
        String siteposition = "306";

        String urlString = baseURL + service
                + param1 + identifier
                + param2 + dsshortname
                + param3 + orthologydsshortname
                + param4 + alignmentradius
                + param5 + stringency
                + param6 + motifgroup
                + param7 + siteposition;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);
    }
}
