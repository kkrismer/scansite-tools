package edu.mit.scansite.webservice.proteinscan;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;
import static edu.mit.scansite.webservice.ScansiteTestUtils.motifClass;
import static edu.mit.scansite.webservice.ScansiteTestUtils.proteinIdentifier;
import static edu.mit.scansite.webservice.ScansiteTestUtils.sequence;
import static edu.mit.scansite.webservice.ScansiteTestUtils.stringencyStr;
import static edu.mit.scansite.webservice.proteinscan.ProteinScanUtils.REFERENCE_VERTEBRATA;

import org.junit.Before;
import org.junit.Test;

import edu.mit.scansite.server.features.ProteinScanFeature;
import edu.mit.scansite.shared.DatabaseException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.HistogramStringency;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.shared.transferobjects.MotifClass;
import edu.mit.scansite.shared.transferobjects.MotifSelection;
import edu.mit.scansite.shared.transferobjects.User;

/**
 * Created by Thomas on 3/13/2017.
 *
 */
public class ProteinScanFeatureTest {

	private ProteinScanFeature proteinScanFeature;

	private LightWeightProtein protein;
	private MotifSelection motifSelection;
	private HistogramStringency stringency;
	private boolean showDomains;
	private String histogramDataSource;
	private String histogramTaxon;
	private DataSource localizationDataSource;
	private boolean doCreateFiles;
	private User user;
	private String realPath;

	@Before
	public void setup() throws DatabaseException {
		proteinScanFeature = new ProteinScanFeature();

		protein = ProteinScanUtils.getLightWeightProtein(proteinIdentifier, dsShortName);

		MotifClass mc = MotifClass.getDbValue(motifClass.toUpperCase());
		motifSelection = new MotifSelection();
		motifSelection.setMotifClass(mc);

		stringency = ProteinScanWebService.getStringency(stringencyStr);
		showDomains = false;
		histogramDataSource = dsShortName;
		histogramTaxon = REFERENCE_VERTEBRATA;
		localizationDataSource = ProteinScanUtils.getLocalizationDataSource("error message");
		doCreateFiles = false;
		user = null;
		realPath = null;
	}

	@Test
	public void proteinScanFeatureTest() throws DatabaseException {
		edu.mit.scansite.shared.dispatch.features.ProteinScanResult result = proteinScanFeature.doProteinScan(protein,
				motifSelection, stringency, showDomains, histogramDataSource, histogramTaxon, localizationDataSource,
				doCreateFiles, user, realPath);

		int hitSize = 28;
		assert (result != null);
		assert (result.isSuccess());
		assert (proteinIdentifier.equals(result.getResults().getProtein().getIdentifier()));
		assert (result.getResults().getProtein().getSequence().equals(sequence));
		assert (result.getResults().getHits() != null);
		assert (!result.getResults().getHits().isEmpty());
		assert (result.getResults().getHits().size() == hitSize);
	}

}
