package edu.mit.scansite.webservice.proteinscan;

import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.*;
import static edu.mit.scansite.webservice.proteinscan.ProteinScanUtils.REFERENCE_VERTEBRATA;

/**
 * Created by Thomas on 3/9/2017.
 *
 */
public class ProteinSequenceScanServiceTest {


    private String motifInputShortNames;

    @Test
    public void proteinSequenceScanServiceNoMotifsTest() {
        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinSequenceScanService.doProteinScan(proteinIdentifier, sequence,
                        null, motifClass, stringencyStr, REFERENCE_VERTEBRATA);
        int predictedSites = 28;
        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equals(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length > 0);
        assert (result.getPredictedSite().length == predictedSites);
    }


    @Test
    public void proteinSequenceScanServiceMotifsEmptyTest() {
        motifInputShortNames = "";

        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinSequenceScanService.doProteinScan(proteinIdentifier, sequence,
                        motifInputShortNames, motifClass, stringencyStr, REFERENCE_VERTEBRATA);

        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equals(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length == 0);
    }


    @Test
    public void proteinSequenceScanServiceMotifsTest() {
        motifInputShortNames = "1433_m1";

        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinSequenceScanService.doProteinScan(proteinIdentifier, sequence,
                        motifInputShortNames, motifClass, stringencyStr, REFERENCE_VERTEBRATA);
        int predictedSites = 1;
        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equals(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length > 0);
        assert (result.getPredictedSite().length == predictedSites);
    }
}
