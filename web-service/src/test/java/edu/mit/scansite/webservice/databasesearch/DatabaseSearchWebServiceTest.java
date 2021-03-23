package edu.mit.scansite.webservice.databasesearch;

import edu.mit.scansite.webservice.otherservices.DatabaseSearchWebService;
import edu.mit.scansite.webservice.transferobjects.DbSearchResult;
import edu.mit.scansite.webservice.transferobjects.MotifSiteDbSearch;
import org.junit.Before;
import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;

/**
 * Created by Thomas on 3/2/2017.
 */
public class DatabaseSearchWebServiceTest {

    private String motifShortName;
    private String organismClass;
    private String speciesRestriction;
    private String phosphorylations;
    private String mwFrom;
    private String mwTo;
    private String pIFrom;
    private String pITo;
    private String keywordRestriction;
    private String sequenceRestriction;

    @Before
    public void setup() {
        motifShortName = "1433_m1";
        organismClass = "Mammals";
        speciesRestriction = "homo sapiens";
        phosphorylations = "0";
        mwFrom = "";
        mwTo = "";
        pIFrom = "";
        pITo = "";
        keywordRestriction = "";
        sequenceRestriction = "";
    }

    private void evaluateResults(DbSearchResult result, double median, double medianAbsDev,
             int numberOfProteinsInDb, int numberOfProteinsMatched, int numberOfSitesFound) {

        assert (result.getDatasourceNickName().equals(dsShortName));
        assert (result.getMedianScore().equals(median));
        assert (result.getMedianAbsoluteDeviationOfScores().equals(medianAbsDev));
        assert (result.getNumberOfProteinsInSelectedDatabase().equals(numberOfProteinsInDb));
        assert (result.getNumberOfProteinsMatchedBySearch().equals(numberOfProteinsMatched));
        assert (result.getNumberOfSitesFound().equals(numberOfSitesFound));
        assert (result.getPredictedSite().length == numberOfSitesFound);
        for (MotifSiteDbSearch site : result.getPredictedSite()) {
            assert (site != null);
            assert (site.getMotifShortName().equals(motifShortName));
        }
    }


    @Test
    public void databaseSearchWebServiceTest() {
        double median = 0.29375;
        double medianAbsDev = 0.024150000000000005;
        int numberOfProteinsInDb = 553941;
        int numberOfProteinsMatched = 20184;
        int numberOfSitesFound = 2534;

        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
                numberOfProteinsMatched, numberOfSitesFound);
    }


    @Test
    public void databaseSearchWebServiceNoSpeciesRestrictionTest() {
        speciesRestriction = "";

        double median = 0.293;
        double medianAbsDev = 0.02510000000000001;
        int numberOfProteinsInDb = 553941;
        int numberOfProteinsMatched = 66610;
        int numberOfSitesFound = 6751;

        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
                numberOfProteinsMatched, numberOfSitesFound);
    }


    @Test
    public void databaseSearchWebServiceMWTest() {
        mwFrom = "todo";
        mwTo   = "todo";

//        double median = 0;
//        double medianAbsDev = 0;
//        int numberOfProteinsInDb = 0;
//        int numberOfProteinsMatched = 0;
//        int numberOfSitesFound = 0;
//        int numberOfPredictedSites = 0;

//        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
//                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
//                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
//                numberOfProteinsMatched, numberOfSitesFound, numberOfPredictedSites);
    }


    @Test
    public void databaseSearchWebServicePITest() {
        pIFrom = "todo";
        pITo   = "todo";

//        double median = 0;
//        double medianAbsDev = 0;
//        int numberOfProteinsInDb = 0;
//        int numberOfProteinsMatched = 0;
//        int numberOfSitesFound = 0;
//        int numberOfPredictedSites = 0;
//
//        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
//                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
//                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
//                numberOfProteinsMatched, numberOfSitesFound, numberOfPredictedSites);
    }


    @Test
    public void databaseSearchWebServiceKeywordRestrictionTest() {
        keywordRestriction = "todo";

//        double median = 0;
//        double medianAbsDev = 0;
//        int numberOfProteinsInDb = 0;
//        int numberOfProteinsMatched = 0;
//        int numberOfSitesFound = 0;
//        int numberOfPredictedSites = 0;
//
//        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
//                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
//                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
//                numberOfProteinsMatched, numberOfSitesFound, numberOfPredictedSites);
    }


    @Test
    public void databaseSearchWebServiceSequenceRestrictionTest() {
            sequenceRestriction = "todo";

//        double median = 0;
//        double medianAbsDev = 0;
//        int numberOfProteinsInDb = 0;
//        int numberOfProteinsMatched = 0;
//        int numberOfSitesFound = 0;
//        int numberOfPredictedSites = 0;
//
//        evaluateResults(DatabaseSearchWebService.doDatabaseSearch(motifShortName, dsShortName,
//                organismClass, speciesRestriction, phosphorylations, mwFrom, mwTo, pIFrom, pITo,
//                keywordRestriction, sequenceRestriction), median, medianAbsDev, numberOfProteinsInDb,
//                numberOfProteinsMatched, numberOfSitesFound, numberOfPredictedSites);
        }
}
