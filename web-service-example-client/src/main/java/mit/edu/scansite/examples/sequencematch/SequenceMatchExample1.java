package mit.edu.scansite.examples.sequencematch;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../sequencematch/sequencematchregex=A+VCA/dsshortname=swissprot/organismclass=Mammals/speciesrestriction=human/numberofphosphorylations=0/keywordrestriction=cell
public class SequenceMatchExample1 {
    private static final String param1 = "/sequencematchregex=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/organismclass=";
    private static final String param4 = "/speciesrestriction=";
    private static final String param5 = "/numberofphosphorylations=";
    private static final String param6 = "/keywordrestriction=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "SequenceMatchExample1Output.txt";

    public static void main(String[] args) {
        String sequencematchregex = "A+VCA";
        String dsshortname = "swissprot";
        String organismclass = "Mammals";
        String speciesrestriction = "human";
        String numberofphosphorylations = "0";
        String keywordrestriction = "cell";

        String urlString = baseURL + service
                + param1 + sequencematchregex
                + param2 + dsshortname
                + param3 + organismclass
                + param4 + speciesrestriction
                + param5 + numberofphosphorylations
                + param6 + keywordrestriction;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);
    }
}
