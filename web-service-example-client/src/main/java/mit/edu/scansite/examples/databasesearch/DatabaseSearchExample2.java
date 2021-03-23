package mit.edu.scansite.examples.databasesearch;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../databasesearch/motifshortname=Shc_SH2/dsshortname=swissprot/organismclass=Mammals/numberofphosphorylations=0
public class DatabaseSearchExample2 {
    private static final String param1 = "/motifshortname=";
    private static final String param2 = "/dsshortname=";
    private static final String param3 = "/organismclass=";
    private static final String param4 = "/numberofphosphorylations=";

    private static final String service = "databasesearch";
    private static final String outputFileName = "DatabaseSearchExample2Output.txt";

    public static void main(String[] args) {
        String motifshortname = "Shc_SH2";
        String dsshortname = "swissprot";
        String organismclass = "Mammals";
        String numberofphosphorylations = "0";

        String urlString = baseURL + service
                + param1 + motifshortname
                + param2 + dsshortname
                + param3 + organismclass
                + param4 + numberofphosphorylations;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);

    }
}
