package edu.mit.scansite.webservice.proteinscan;

import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.*;
import static edu.mit.scansite.webservice.proteinscan.ProteinScanUtils.REFERENCE_VERTEBRATA;

/**
 * Created by Thomas on 3/9/2017.
 *
 */
public class ProteinIdentifierScanServiceTest {

    private String motifShortName = null;


    @Test
    public void proteinIdentifierScanServiceTest() {
        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinIdentifierScanService.doProteinScan(proteinIdentifier, dsShortName,
                        motifShortName, motifClass, stringencyStr, REFERENCE_VERTEBRATA);
        int predictedSites = 28;
        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equalsIgnoreCase(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length > 0);
        assert (result.getPredictedSite().length == predictedSites);
    }


    @Test
    public void setProteinIdentifierScanServiceMotifsEmptyTest() {
        motifShortName = "";

        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinIdentifierScanService.doProteinScan(proteinIdentifier, dsShortName,
                        motifShortName, motifClass, stringencyStr, REFERENCE_VERTEBRATA);

        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equalsIgnoreCase(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length ==0);
    }

    @Test
    public void proteinIdentifierScanServiceMotifRelatedTest() {
        motifShortName = "1433_m1";

        edu.mit.scansite.webservice.transferobjects.ProteinScanResult result =
                ProteinIdentifierScanService.doProteinScan(proteinIdentifier, dsShortName,
                        motifShortName, motifClass, stringencyStr, REFERENCE_VERTEBRATA);
        int predictedSites = 1;
        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equalsIgnoreCase(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length > 0);
        assert (result.getPredictedSite().length == predictedSites);
    }

}
