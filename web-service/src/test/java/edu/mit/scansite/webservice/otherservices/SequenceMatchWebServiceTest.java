package edu.mit.scansite.webservice.otherservices;

import edu.mit.scansite.webservice.transferobjects.SequenceMatchResult;
import org.junit.Before;
import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;

/**
 * Created by Thomas on 3/14/2017.
 *
 */
public class SequenceMatchWebServiceTest {
    private String sequenceMatchRegex;
    private String organismClass;
    private String speciesRestrictionRegex;
    private String numberOfPhosphorylations;
    private String mwFrom;
    private String mwTo;
    private String piFrom;
    private String piTo;
    private String keywordRestrictionRegex;


    @Before
    public void setup() {
        sequenceMatchRegex = "A+VCA";
        organismClass = "Mammals";
        speciesRestrictionRegex = "human";
        numberOfPhosphorylations = "0";
        mwFrom = "";
        mwTo = "";
        piFrom = "";
        piTo = "";
        keywordRestrictionRegex = "cell";
    }

    @Test
    public void sequenceMatchWebServiceTest() {

        int proteinsInDb = 553941;
        int proteinMatches = 42;

        SequenceMatchResult result = SequenceMatchWebService.doSequenceMatch(sequenceMatchRegex,
                dsShortName, organismClass, speciesRestrictionRegex, numberOfPhosphorylations,
                mwFrom, mwTo, piFrom, piTo, keywordRestrictionRegex);

        assert (result.getDatasourceNick().equals(dsShortName));
        assert (result.getNumberOfProteinsInSelectedDatabase().equals(proteinsInDb));
        assert (result.getNumberOfProteinsMatchedBySearch().equals(proteinMatches));
        assert (result.getSequenceMatch() != null);
        assert (result.getSequenceMatch().length > 0);
        assert (result.getSequenceMatch().length == 42);
    }

    @Test
    public void potentialFurtherTests() {
        // could test further parameter settings and using most of the pre assigned values
    }
}
