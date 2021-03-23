package edu.mit.scansite.webservice.otherservices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.features.DatabaseScanFeature;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.dispatch.features.DatabaseScanResult;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.DatabaseSearchScanResultSite;
import edu.mit.scansite.shared.transferobjects.MotifSelection;
import edu.mit.scansite.shared.transferobjects.OrganismClass;
import edu.mit.scansite.shared.transferobjects.RestrictionProperties;
import edu.mit.scansite.shared.transferobjects.User;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.proteinscan.ProteinScanUtils;
import edu.mit.scansite.webservice.transferobjects.DbSearchResult;
import edu.mit.scansite.webservice.transferobjects.MotifSiteDbSearch;

@Path("/databasesearch/motifshortname={motifshortname: \\S+}/dsshortname={dsshortname: [A-Za-z]+}/organismclass={organismclass: [A-Za-z]+}{speciesrestriction: (/speciesrestriction=[\\s\\w]*)?}{numberofphosphorylations: (/numberofphosphorylations=[0-3])?}{molweightfrom: (/molweightfrom=\\d*)?}{molweightto: (/molweightto=\\d*)?}{isoelectricpointfrom: (/isoelectricpointfrom=\\d*\\.?\\d*)?}{isoelectricpointto: (/isoelectricpointto=\\d*\\.?\\d*)?}{keywordrestriction: (/keywordrestriction=[\\w\\s]*)?}{sequencerestriction: (/sequencerestriction=[\\w\\s]*)?}")
public class DatabaseSearchWebService {
	/**
	 * @param motiShortName
	 *            The shortname of the motif that will be searched for (A list of
	 *            valid motifs can be obtained by the getMotifDefinitions() method).
	 * @param datasourceShortName
	 *            The shortname of the datasource that will be searched (A list of
	 *            valid datasources can be obtained by the getDatasources() method).
	 * @param organismClass
	 *            A valid organism class (one of those returned by the
	 *            getOrganismClasses() method).
	 * @param speciesRestrictionRegex
	 *            A regular expression that restricts the search to proteins of a
	 *            single species.
	 * @param numberOfPhosphorylations
	 *            A number of phosphorylations >= 0 and <=3. This parameter is only
	 *            applied to searches using a molecular weight or pI limit. The MWs
	 *            or pIs of single or multiple phosphorylated proteins are then
	 *            checked for the given limit.
	 * @param molWeightFrom
	 *            A lower molecular weight limit.
	 * @param molWeightTo
	 *            An upper molecular weight limit.
	 * @param isoelectricPointFrom
	 *            A lower pI limit.
	 * @param isoelectricPointTo
	 *            An upper pI limit.
	 * @param keywordRestrictionRegex
	 *            A regular expression that restricts the search to proteins related
	 *            to a given keyword.
	 * @param sequenceRestrictionRegex
	 *            A pattern of AminoAcids that a otherservices's sequence has to
	 *            contain in order to show up in the result list.
	 * @return An object containing a the sites that are found searching the
	 *         selected database using the selected motif and considering the given
	 *         restrictions.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public static DbSearchResult doDatabaseSearch(@PathParam("motifshortname") String motiShortName,
			@PathParam("dsshortname") String datasourceShortName, @PathParam("organismclass") String organismClass,
			@DefaultValue("") @PathParam("speciesrestriction") String speciesRestrictionRegex,
			@DefaultValue("") @PathParam("numberofphosphorylations") String numberOfPhosphorylations,
			@DefaultValue("") @PathParam("molweightfrom") String molWeightFrom,
			@DefaultValue("") @PathParam("molweightto") String molWeightTo,
			@DefaultValue("") @PathParam("isoelectricpointfrom") String isoelectricPointFrom,
			@DefaultValue("") @PathParam("isoelectricpointto") String isoelectricPointTo,
			@DefaultValue("") @PathParam("keywordrestriction") String keywordRestrictionRegex,
			@DefaultValue("") @PathParam("sequencerestriction") String sequenceRestrictionRegex) {
		speciesRestrictionRegex = ProteinScanUtils.processOptionalParameter(speciesRestrictionRegex);
		numberOfPhosphorylations = ProteinScanUtils.processOptionalParameter(numberOfPhosphorylations);
		molWeightFrom = ProteinScanUtils.processOptionalParameter(molWeightFrom);
		molWeightTo = ProteinScanUtils.processOptionalParameter(molWeightTo);
		isoelectricPointFrom = ProteinScanUtils.processOptionalParameter(isoelectricPointFrom);
		isoelectricPointTo = ProteinScanUtils.processOptionalParameter(isoelectricPointTo);
		keywordRestrictionRegex = ProteinScanUtils.processOptionalParameter(keywordRestrictionRegex);
		sequenceRestrictionRegex = ProteinScanUtils.processOptionalParameter(sequenceRestrictionRegex);

		DataSource ds;
		try {
			ds = ServiceLocator.getDaoFactory().getDataSourceDao().get(datasourceShortName);
		} catch (Exception e) {
			throw new ScansiteWebServiceException("No valid datasource shortname given.");
		}
		if (datasourceShortName == null || datasourceShortName.isEmpty()) {
			throw new ScansiteWebServiceException("No valid datasource shortname given.");
		}

		OrganismClass oc = OrganismClass.getByStringRepresentation(organismClass);
		if (oc == null) {
			throw new ScansiteWebServiceException("No valid organismClass given.");
		}

		if (motiShortName == null || motiShortName.isEmpty()) {
			throw new ScansiteWebServiceException("No motif short name given!");
		}

		speciesRestrictionRegex = processRegex(speciesRestrictionRegex);
		keywordRestrictionRegex = processRegex(keywordRestrictionRegex);

		Integer nPhos = 0;
		if (numberOfPhosphorylations != null && !numberOfPhosphorylations.isEmpty()) {
			try {
				nPhos = Integer.parseInt(numberOfPhosphorylations);
				if (nPhos < 0 || nPhos > 3) {
					nPhos = 0;
				}
			} catch (Exception e) {
				System.out.println("Could not parse number of phosphorylations");
			}
		}

		Double mwFrom = parseParameter(molWeightFrom);
		Double mwTo = parseParameter(molWeightTo);
		Double piFrom = parseParameter(isoelectricPointFrom);
		Double piTo = parseParameter(isoelectricPointTo);

		Set<String> shortMotifNames = new HashSet<>();
		shortMotifNames.add(motiShortName);
		MotifSelection motifSelection = new MotifSelection();
		// optional: also set motif class mc
		motifSelection.setMotifShortNames(shortMotifNames);
		List<String> seqRestrictions = new ArrayList<>();
		if (sequenceRestrictionRegex != null && !sequenceRestrictionRegex.isEmpty()) {
			seqRestrictions.add(sequenceRestrictionRegex);
		} else {
			seqRestrictions = null;
		}
		RestrictionProperties restrictionProperties = new RestrictionProperties(oc, speciesRestrictionRegex, nPhos,
				mwFrom, mwTo, piFrom, piTo, keywordRestrictionRegex, seqRestrictions);

		final int outputListSize = 0; // returns all sites
		final boolean doCreateFiles = false;
		final User user = null;
		final String realPath = null;

		try {
			DatabaseScanFeature feature = new DatabaseScanFeature();
			DatabaseScanResult result = feature.doDatabaseSearch(motifSelection, ds, restrictionProperties,
					outputListSize, doCreateFiles, user, realPath, false);

			MotifSiteDbSearch[] sites;
			if (result.isSuccess() && result.getDbSearchSites() != null && result.getDbSearchSites().size() > 0) {
				sites = new MotifSiteDbSearch[result.getDbSearchSites().size()];
				int i = 0;
				for (DatabaseSearchScanResultSite site : result.getDbSearchSites()) {
					sites[i++] = new MotifSiteDbSearch(site.getScore(), site.getSite().getPercentile(),
							site.getSite().getMotif().getShortName(), site.getSite().getMotif().getShortName(),
							site.getSite().getSite(), site.getSite().getSiteSequence().replaceAll("\\<.*?\\>", ""),
							site.getProtein().getIdentifier());
				}
			} else {
				sites = new MotifSiteDbSearch[] {};
			}
			return new DbSearchResult(sites, datasourceShortName, result.getMedian(), result.getMedianAbsDev(),
					result.getNrOfProteinsFound(), result.getTotalNrOfSites(), result.getTotalNrOfProteinsInDb());
		} catch (DataAccessException e) {
			throw new ScansiteWebServiceException(
					"Running database search failed. Please try again later or, if this problem persists, contact the system's administrator!");
		}
	}

	private static String processRegex(String regex) {
		return (regex != null && regex.isEmpty()) ? null : regex;
	}

	// package private
	static Double parseParameter(String value) {
		if (value != null && !value.isEmpty()) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				System.out.println("Could not parse double value from parameters:" + value);
			}
		}
		return null;
	}
}
