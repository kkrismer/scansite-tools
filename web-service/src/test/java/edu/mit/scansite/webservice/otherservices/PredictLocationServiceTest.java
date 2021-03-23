package edu.mit.scansite.webservice.otherservices;

import edu.mit.scansite.webservice.transferobjects.PredictLocationResult;
import org.junit.Test;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;
import static edu.mit.scansite.webservice.ScansiteTestUtils.proteinIdentifier;

/**
 * Created by Thomas on 3/14/2017.
 *
 */
public class PredictLocationServiceTest {

    @Test
    public void predictLocationServiceTest() {
        String localizationDsShortName = "loctree";

        String localization = "nucleus";
        int goTermsNo = 1;
        Integer proteinLocationsNo = 2036782;
        Integer predictionScore = 81;

        PredictLocationResult result = PredictLocationService.predictLocation(localizationDsShortName, proteinIdentifier, dsShortName);
        assert (result != null);
        assert (result.getLocalization().equals(localization));
        assert (result.getGoTermsCodes().length == goTermsNo);
        assert (result.getNumberProteinLocations().equals(proteinLocationsNo));
        assert (result.getPredictionScore().equals(predictionScore));
        for (int i=0; i < result.getGoTermsCodes().length; i++) {
            String code = result.getGoTermsCodes()[i];
            assert (code != null);
            assert (!code.isEmpty());
        }
    }
}
