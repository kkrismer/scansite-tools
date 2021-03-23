package edu.mit.scansite.webservice.proteinscan;

import edu.mit.scansite.shared.DatabaseException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.HistogramStringency;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.webservice.transferobjects.ProteinScanResult;
import org.junit.Before;
import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.*;

/**
 * Created by Thomas Bernwinkler on 3/9/2017.
 *
 */
public class ProteinScanWebServiceTest {

    private LightWeightProtein protein;
    private DataSource dataSource;
    private String motifInputShortNames;

    @Before
    public void setup() throws DatabaseException {
        dataSource = ProteinScanUtils.getLocalizationDataSource("error message");
        protein = ProteinScanUtils.getLightWeightProtein(proteinIdentifier, dsShortName);
        motifInputShortNames = null;
    }


    @Test
    public void proteinScanWebServiceNoMotifTest() {
        ProteinScanResult result = ProteinScanWebService.doProteinScan(protein, ProteinScanUtils.
                REFERENCE_VERTEBRATA, dsShortName, dataSource, motifInputShortNames, motifClass, stringencyStr);
        int predictedSites = 28;
        assert (result != null);
        assert (result.getProteinName().equals(proteinIdentifier));
        assert (result.getProteinSequence().equals(sequence));
        assert (result.getPredictedSite() != null);
        assert (result.getPredictedSite().length > 0);
        assert (result.getPredictedSite().length == predictedSites);
    }


    @Test
    public void stringencyTestHigh() {
        String highStringency = "High";
        HistogramStringency s = ProteinScanWebService.getStringency(highStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_HIGH));
    }


    @Test
    public void stringencyTestHighUpperCase() {
        String highStringency = "HIGH";
        HistogramStringency s = ProteinScanWebService.getStringency(highStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_HIGH));
    }

    @Test
    public void stringencyTestHighLowerCase() {
        String highStringency = "high";
        HistogramStringency s = ProteinScanWebService.getStringency(highStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_HIGH));
    }


    @Test
    public void stringencyTestMedium() {
        String mediumStringency = "Medium";
        HistogramStringency s = ProteinScanWebService.getStringency(mediumStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_MEDIUM));
    }


    @Test
    public void stringencyTestLow() {
        String lowStringency = "Low";
        HistogramStringency s = ProteinScanWebService.getStringency(lowStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_LOW));
    }


    @Test
    public void stringencyTestMinimum() {
        String minStringency = "Minimum";
        HistogramStringency s = ProteinScanWebService.getStringency(minStringency);
        assert (s != null);
        assert (s.equals(HistogramStringency.STRINGENCY_MIN));
    }


    @Test
    public void stringencyTestInvalid() {
        String nullStringency = "invalid";
        HistogramStringency s = ProteinScanWebService.getStringency(nullStringency);
        assert (s == null);
    }


    @Test
    public void stringencyTestEmptyString() {
        String nullStringency = "";
        HistogramStringency s = ProteinScanWebService.getStringency(nullStringency);
        assert (s == null);
    }


    @Test
    public void stringencyTestNull() {
        String nullStringency = null;
        HistogramStringency s = ProteinScanWebService.getStringency(nullStringency);
        assert (s == null);
    }

    @Test
    public void proteinScanWebServiceMotifTest() {
        motifInputShortNames = "...";
    }

}
