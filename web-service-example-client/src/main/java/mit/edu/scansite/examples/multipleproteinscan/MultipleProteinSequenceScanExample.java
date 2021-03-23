package mit.edu.scansite.examples.multipleproteinscan;

import mit.edu.scansite.examples.ExampleUtils;
import mit.edu.scansite.examples.ProteinSequenceScanExampleInput;

import java.util.ArrayList;
import java.util.List;

import static mit.edu.scansite.examples.ExampleUtils.baseURL;

/**
 * @author Thomas Bernwinkler
 * Last edited 4/4/2017
 * Provided by Yaffe Lab, Koch Institute, MIT
 */


public class MultipleProteinSequenceScanExample {
    private static final String param1 = "/identifier=";
    private static final String param2 = "/sequence=";
    private static final String param3 = "/motifclass=";
    private static final String param4 = "/stringency=";

    private static final String service = "proteinscan";
    private static final String outputFileName = "ProteinSequenceScanExample1Output.txt";

    public static void main(String[] args) {
        String identifier1 = "MY_PROTEIN";
        String sequence1 = "RDGVLLCQLLNNLLPHAINLREVNLRPQMSQFLCLKNIRTFLSTCCEKFGLKRSELFEA" +
                "FDLFDVQDFGKVIYTLSALSWTPIAQNRGIMPFPTEEESVGDEDIYSGLSDQIDDTVEEDEDLYDCVENE" +
                "EAEGDEIYEDLMRSEPVSMPPKMTEYDKRCCCLREIQQTE";

        String motifclass1 = "YEAST";
        String stringency1 = "High";

        String identifier2 = "VAV_HUMAN";
        String sequence2 = "MNVSYWAIWTRENASAKRKQFLCLKNIRTFLSTCCEKFGLKRSELFEAFDLFDVQDFGKVIYTLSALSWT" +
                "PIAQNRGIMPFPTEEESVGDEDIYSGLSDQIDDTVEEDEDLYDCVENEEAEGDEIYEDLMRSEPVSMPPK" +
                "MTEYDKRCCCLREIQQTEEKYTDTLGSIQQHFLKPLQRFLKPQDIEIIFINIEDLLRVHTHFLKEMKEAL" +
                "GTPGAPNLYQVFIKYKERFLVYGRYCSQVESASKHLDRVAAAREDVQMKLEECSQRANNGRFTARPADGA" +
                "YAASSQISPPSPGAGETHAGGDGARKLRLALDAMRDLAQCVNEVKRDNETLRQITNFQLSIENLDQSLAH" +
                "YGRPKIDGELKITSVERRSKMDRYAFLLDKALLICKRRGDSYDLKDFVNLHSFQVRDDSSGDRDNKKWSH" +
                "MFLLIEDQGAQGYELFFKTRELKKKWMEQFEMAISNIYPENATANGHDFQMFSFEETTSCKACQMLLRGT" +
                "FYQGYRCHRCRASAHKECLGRVPPCGRHGQDFPGTMKKDKLHRRAQDKKRNELGLPKMEVFQEYYGLPPP" +
                "PGAIGPFLRLNPGDIVELTKAEAEQNWWEGRNTSTNEIGWFPCNRVKPYVHGPPQDLSVHLWYAGPMERA" +
                "GAESILANRSDGTFLVRQRVKDAAEFAISIKYNVEVKHTVKIMTAEGLYRITEKKAFRGLTELVEFYQQN" +
                "SLKDCFKSLDTTLQFPFKEPEKRTISRPAVGSTKYFGTAKARYDFCARDRSELSLKEGDIIKILNKKGQQ" +
                "GWWRGEIYGRVGWFPANYVEEDYSEYC";

        String motifclass2 = "MAMMALIAN";
        String stringency2 = "High";

        ProteinSequenceScanExampleInput input1 = new ProteinSequenceScanExampleInput();
        ProteinSequenceScanExampleInput input2 = new ProteinSequenceScanExampleInput();

        input1.setIdentifier(identifier1);
        input1.setSequence(sequence1);
        input1.setMotifClass(motifclass1);
        input1.setStringency(stringency1);

        input2.setIdentifier(identifier2);
        input2.setSequence(sequence2);
        input2.setMotifClass(motifclass2);
        input2.setStringency(stringency2);

        List<ProteinSequenceScanExampleInput> inputList = new ArrayList<>();
        inputList.add(input1);
        inputList.add(input2);

        List<String> multipleSearchResults = new ArrayList<>();
        for (ProteinSequenceScanExampleInput input : inputList) {
            String identifier = input.getIdentifier();
            String sequence = input.getSequence();
            String motifclass = input.getMotifClass();
            String stringency = input.getStringency();

            String urlString = baseURL + service
                    + param1 + identifier
                    + param2 + sequence
                    + param3 + motifclass
                    + param4 + stringency;

            List<String> results = ExampleUtils.runRequest(urlString);
            String resultContent = "";
            for (String result : results) {
                resultContent += result + "\n";
            }
            multipleSearchResults.add(resultContent);
        }

        String multipleResultsContent = "";
        for (String result : multipleSearchResults) {
            multipleResultsContent += result + "\n\n\n\n";
        }

        ExampleUtils.writeToFile(multipleResultsContent, outputFileName);
        System.out.println(multipleResultsContent);
    }

}
