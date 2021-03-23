package edu.mit.scansite.webservice.otherservices;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.features.PredictLocalizationFeature;
import edu.mit.scansite.shared.dispatch.features.PredictProteinsLocalizationResult;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.GOTermEvidence;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.proteinscan.ProteinScanUtils;
import edu.mit.scansite.webservice.transferobjects.PredictLocationResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Thomas on 3/9/2017.
 *
 */
@Path("/predictlocation/localizationdsshortname={localizationdsshortname: [A-Za-z]+}/identifier={identifier: \\S+}/dsshortname={dsshortname: [A-Za-z]+}")
public class PredictLocationService {

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public static PredictLocationResult predictLocation (
            @PathParam("localizationdsshortname") String localizationDsShortName,
            @PathParam("identifier") String proteinIdentifier,
            @PathParam("dsshortname") String dsShortName//,
//            @PathParam("motifclass") String motifClassStr
    ) {
        LightWeightProtein protein = ProteinScanUtils.getLightWeightProtein(proteinIdentifier, dsShortName);

//        MotifClass motifClass;
//        try {
//            motifClass = MotifClass.getDbValue(motifClassStr.toUpperCase());
//        } catch (Exception e) {
//            throw new ScansiteWebServiceException("Given motif class is invalid!");
//        }

        try {
            DataSource ds = ServiceLocator.getDaoFactory().getDataSourceDao().get(localizationDsShortName);
            PredictLocalizationFeature feature = new PredictLocalizationFeature();
            edu.mit.scansite.shared.dispatch.features.PredictProteinsLocalizationResult result;

            result = (PredictProteinsLocalizationResult) feature.doPredictProteinLocalization(ds, protein);

            int numberProteinLocations = result.getTotalProteinLocalizations();
            String localization = result.getLocalization().getType().getName();
            int predictionScore = result.getLocalization().getScore();
            String[] goTermsCodes = new String[result.getLocalization().getGoTerms().size()];

            int idx = 0;
            for (GOTermEvidence goTerm : result.getLocalization().getGoTerms()) {
                goTermsCodes[idx] = goTerm.getGoTerm().getId() + "\t\t" + goTerm.getGoTerm().getName();
                idx++;
            }
            return new PredictLocationResult(numberProteinLocations, localization, predictionScore, goTermsCodes);
        } catch (Exception e) {
            throw new ScansiteWebServiceException("Running localization prediction service failed.");
        }
    }
}
