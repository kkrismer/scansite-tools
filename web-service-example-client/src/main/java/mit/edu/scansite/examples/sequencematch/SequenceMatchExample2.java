package mit.edu.scansite.examples.sequencematch;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../sequencematch/sequencematchregex=A+VCA/dsshortname=swissprot/organismclass=Mammals/speciesrestriction=human/numberofphosphorylations=0/molweightfrom=2000/molweightto=9000/isoelectricpointfrom=2.2/isoelectricpointto=6
public class SequenceMatchExample2 {
    private static final String param1 = "/sequencematchregex=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/organismclass=";
    private static final String param4 = "/speciesrestriction=";
    private static final String param5 = "/numberofphosphorylations=";
    private static final String param6 = "/molweightfrom=";
    private static final String param7 = "/molweightto=";
    private static final String param8 = "/isoelectricpointfrom=";
    private static final String param9 = "/isoelectricpointto=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "SequenceMatchExample2Output.txt";

    public static void main(String[] args) {
        String sequencematchregex = "A+VCA";
        String dsshortname = "swissprot";
        String organismclass = "Mammals";
        String speciesrestriction = "human";
        String numberofphosphorylations = "0";
        String molweightfrom = "2000";
        String molweightto = "9000";
        String isoelectricpointfrom = "2.2";
        String isoelectricpointto = "6";

        String urlString = baseURL + service
                + param1 + sequencematchregex
                + param2 + dsshortname
                + param3 + organismclass
                + param4 + speciesrestriction
                + param5 + numberofphosphorylations
                + param6 + molweightfrom
                + param7 + molweightto
                + param8 + isoelectricpointfrom
                + param9 + isoelectricpointto;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
    }
}
