package edu.mit.scansite.webservice.databasesearch;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.features.DatabaseScanFeature;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.DatabaseException;
import edu.mit.scansite.shared.dispatch.features.DatabaseScanResult;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.MotifClass;
import edu.mit.scansite.shared.transferobjects.MotifSelection;
import edu.mit.scansite.shared.transferobjects.OrganismClass;
import edu.mit.scansite.shared.transferobjects.RestrictionProperties;
import edu.mit.scansite.shared.transferobjects.User;

/**
 * @author Thomas Bernwinkler
 * @author Konstantin Krismer
 */
public class DatabaseScanFeatureTest {

	private MotifSelection motifSelection;
	private DataSource dataSource;
	private RestrictionProperties restrictionProperties;
	private int outputListSize;
	private boolean doCreateFiles;
	private User user;
	private String realPath;
	private DatabaseScanFeature feature;

	@Before
	public void setup() throws DatabaseException {

		dataSource = ServiceLocator.getDaoFactory().getDataSourceDao().get(dsShortName);

		OrganismClass oc = OrganismClass.getByStringRepresentation("Mammals");
		String speciesRestrictionRegex = "homo sapiens";
		int nPhos = 0;
		double mwFrom = 0.0;
		double mwTo = 0.0;
		double piFrom = 0.0;
		double piTo = 0.0;
		String keywordRestrictionRegex = null;
		List<String> seqRestrictions = null;

		restrictionProperties = new RestrictionProperties(oc, speciesRestrictionRegex, nPhos, mwFrom, mwTo, piFrom,
				piTo, keywordRestrictionRegex, seqRestrictions);

		motifSelection = new MotifSelection();
		motifSelection.setMotifClass(MotifClass.MAMMALIAN);

		outputListSize = 0;
		doCreateFiles = false;
		user = null;
		realPath = null;

		feature = new DatabaseScanFeature();
	}

	public void evaluateResult(DatabaseScanResult result) {
		assert (result != null);
		assert (result.isSuccess());
		assert (result.getDataSource().getShortName().equals(dataSource.getShortName()));
		assert (result.getDbSearchSites() != null);
	}

	@Test
	public void DatabaseScanFeatureMotifClassTest() throws DataAccessException {
		evaluateResult(feature.doDatabaseSearch(motifSelection, dataSource, restrictionProperties, outputListSize,
				doCreateFiles, user, realPath, false));
	}

	@Test
	public void DatabaseScanFeatureSpecificMotifTest() throws DataAccessException {
		Set<String> shortMotifNames = new HashSet<>();
		shortMotifNames.add("1433_m1");

		motifSelection.setMotifShortNames(shortMotifNames);

		evaluateResult(feature.doDatabaseSearch(motifSelection, dataSource, restrictionProperties, outputListSize,
				doCreateFiles, user, realPath, false));
	}

	@Test
	public void DatabaseScanFeatureSpecificMotifsTest() throws DataAccessException {
		Set<String> shortMotifNames = new HashSet<>();
		shortMotifNames.add("Cdk5_Kin");
		shortMotifNames.add("EGFR_Kin");

		motifSelection.setMotifShortNames(shortMotifNames);

		evaluateResult(feature.doDatabaseSearch(motifSelection, dataSource, restrictionProperties, outputListSize,
				doCreateFiles, user, realPath, false));
	}
}
