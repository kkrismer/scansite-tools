package mit.edu.scansite.examples.proteinsequencescan;

import mit.edu.scansite.examples.ExampleUtils;

import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */

//../proteinscan/identifier=MY_PROTEIN/sequence=RDGVLLCQLLNNLLPHAINLREVNLRPQMSQFLCLKNIRTFLSTCCEKFGLKRSELFEAFDLFDVQDFGKVIYTLSALSWTPIAQNRGIMPFPTEEESVGDEDIYSGLSDQIDDTVEEDEDLYDCVENEEAEGDEIYEDLMRSEPVSMPPKMTEYDKRCCCLREIQQTE/motifclass=MAMMALIAN/motifshortnames=Lck_Kin/stringency=Low
public class ProteinSequenceScanExample3 {
    private static final String param1 = "/identifier=";
    private static final String param2 = "/sequence=";
    private static final String param3 = "/motifclass=";
    private static final String param4 = "/motifshortnames=";
    private static final String param5 = "/stringency=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "ProteinSequenceScanExample3Output.txt";

    public static void main(String[] args) {
        String identifier = "MY_PROTEIN";
        String sequence = "RDGVLLCQLLNNLLPHAINLREVNLRPQMSQFLCLKNIRTFLST" +
                "CCEKFGLKRSELFEAFDLFDVQDFGKVIYTLSALSWTPIAQNRGIMPFPTEEES" +
                "VGDEDIYSGLSDQIDDTVEEDEDLYDCVENEEAEGDEIYEDLMRSEPVSMPPKM" +
                "TEYDKRCCCLREIQQTE";

        String motifclass = "MAMMALIAN";
        String motifshortnames = "Lck_Kin";
        String stringency = "Low";

        String urlString = baseURL + service
                + param1 + identifier
                + param2 + sequence
                + param3 + motifclass
                + param4 + motifshortnames
                + param5 + stringency;

        List<String> results = ExampleUtils.runRequest(urlString);
        String resultContent = "";
        for (String result : results) {
            resultContent += result + "\n";
        }
        ExampleUtils.writeToFile(resultContent, outputFileName);
        System.out.println(resultContent);
    }
}
