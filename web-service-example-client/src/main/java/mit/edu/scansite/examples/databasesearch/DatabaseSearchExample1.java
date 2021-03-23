package mit.edu.scansite.examples.databasesearch;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../databasesearch/motifshortname=Shc_SH2/dsshortname=swissprot/organismclass=Mammals/speciesrestriction=rattus/numberofphosphorylations=1/molweightfrom=5000/molweightto=20000/isoelectricpointfrom=4/keywordrestriction=cell/sequencerestriction=PPP
public class DatabaseSearchExample1 {
    private static final String param1 = "/motifshortname=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/organismclass=";
    private static final String param4 = "/speciesrestriction=";
    private static final String param5 = "/numberofphosphorylations=";
    private static final String param6 = "/molweightfrom=";
    private static final String param7 = "/molweightto=";
    private static final String param8 = "/isoelectricpointfrom=";
    private static final String param9 = "/keywordrestriction=";
    private static final String param10 = "/sequencerestriction=";

    private static final String service = "databasesearch";
    private static final String outputFileName = "DatabaseSearchExample1Output.txt";

    public static void main(String[] args) {
        String motifshortname = "Shc_SH2";
        String dsshortname = "swissprot";
        String organismclass = "Mammals";
        String speciesrestriction = "rattus";
        String numberofphosphorylations = "1";
        String molweightfrom = "5000";
        String molweightto = "20000";
        String isoelectricpointfrom = "4";
        String keywordrestriction = "cell";
        String sequencerestriction = "PPP";

        String urlString = baseURL + service
                + param1 + motifshortname
                + param2 + dsshortname
                + param3 + organismclass
                + param4 + speciesrestriction
                + param5 + numberofphosphorylations
                + param6 + molweightfrom
                + param7 + molweightto
                + param8 + isoelectricpointfrom
                + param9 + keywordrestriction
                + param10+ sequencerestriction;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);

    }
}
