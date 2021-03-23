package edu.mit.scansite.webservice.otherservices;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.features.SequenceMatchFeature;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.transferobjects.*;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.proteinscan.ProteinScanUtils;
import edu.mit.scansite.webservice.transferobjects.SequenceMatch;
import edu.mit.scansite.webservice.transferobjects.SequenceMatchResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/sequencematch/sequencematchregex={sequencematchregex}/dsshortname={dsshortname: [A-Za-z]+}/organismclass={organismclass: [A-Za-z]+}{speciesrestriction: (/speciesrestriction=[\\s\\w]*)?}{numberofphosphorylations: (/numberofphosphorylations=[0-3])?}{molweightfrom: (/molweightfrom=\\d*)?}{molweightto: (/molweightto=\\d*)?}{isoelectricpointfrom: (/isoelectricpointfrom=\\d*\\.?\\d*)?}{isoelectricpointto: (/isoelectricpointto=\\d*\\.?\\d*)?}{keywordrestriction: (/keywordrestriction=[\\s\\w]*)?}")
public class SequenceMatchWebService {
	/**
	 * @param sequenceMatchRegex
	 *            The sequence pattern you are interested in. Regular expressions
	 *            allowed.
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
	 * @return An object containing a list of otherservices accessions and counts
	 *         that say how often the given pattern was found in the proteins'
	 *         sequences.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public static SequenceMatchResult doSequenceMatch(@PathParam("sequencematchregex") String sequenceMatchRegex,
			@PathParam("dsshortname") String datasourceShortName, @PathParam("organismclass") String organismClass,
			@DefaultValue("") @PathParam("speciesrestriction") String speciesRestrictionRegex,
			@DefaultValue("") @PathParam("numberofphosphorylations") String numberOfPhosphorylations,
			@DefaultValue("") @PathParam("molweightfrom") String molWeightFrom,
			@DefaultValue("") @PathParam("molweightto") String molWeightTo,
			@DefaultValue("") @PathParam("isoelectricpointfrom") String isoelectricPointFrom,
			@DefaultValue("") @PathParam("isoelectricpointto") String isoelectricPointTo,
			@DefaultValue("") @PathParam("keywordrestriction") String keywordRestrictionRegex) {
		speciesRestrictionRegex = ProteinScanUtils.processOptionalParameter(speciesRestrictionRegex);
		numberOfPhosphorylations = ProteinScanUtils.processOptionalParameter(numberOfPhosphorylations);
		molWeightFrom = ProteinScanUtils.processOptionalParameter(molWeightFrom);
		molWeightTo = ProteinScanUtils.processOptionalParameter(molWeightTo);
		isoelectricPointFrom = ProteinScanUtils.processOptionalParameter(isoelectricPointFrom);
		isoelectricPointTo = ProteinScanUtils.processOptionalParameter(isoelectricPointTo);
		keywordRestrictionRegex = ProteinScanUtils.processOptionalParameter(keywordRestrictionRegex);

		if (sequenceMatchRegex == null || sequenceMatchRegex.isEmpty()) {
			throw new ScansiteWebServiceException("No sequence match regular expression given.");
		}
		Integer nPhos = 0;
		Double piFrom = DatabaseSearchWebService.parseParameter(isoelectricPointFrom);
		Double piTo = DatabaseSearchWebService.parseParameter(isoelectricPointTo);
		Double mwFrom = DatabaseSearchWebService.parseParameter(molWeightFrom);
		Double mwTo = DatabaseSearchWebService.parseParameter(molWeightTo);

		if (speciesRestrictionRegex != null && speciesRestrictionRegex.isEmpty()) {
			speciesRestrictionRegex = null;
		}

		if (keywordRestrictionRegex != null && keywordRestrictionRegex.isEmpty()) {
			keywordRestrictionRegex = null;
		}

		if (numberOfPhosphorylations != null && !numberOfPhosphorylations.isEmpty()) {
			try {
				nPhos = Integer.parseInt(numberOfPhosphorylations);
				if (nPhos < 0 || nPhos > 3) {
					nPhos = 0;
				}
			} catch (Exception e) {
				throw new ScansiteWebServiceException("No valid number of phosphorylations given.");
			}
		}

		if (datasourceShortName == null || datasourceShortName.isEmpty()) {
			throw new ScansiteWebServiceException("No valid datasource shortname given.");
		}
		DataSource ds;
		try {
			ds = ServiceLocator.getDaoFactory().getDataSourceDao().get(datasourceShortName);
		} catch (Exception e) {
			throw new ScansiteWebServiceException("No valid datasource shortname given.");
		}

		OrganismClass oc = OrganismClass.getByStringRepresentation(organismClass);
		if (oc == null) {
			throw new ScansiteWebServiceException("No valid organismClass given.");
		}

		List<SequencePattern> patterns = new ArrayList<>(1);
		SequencePattern pattern = new SequencePattern();
		pattern.addPosition(new PatternPosition(sequenceMatchRegex, false, false));
		patterns.add(pattern);

		// List<SequencePattern> sequencePatterns = new ArrayList<>();
		// SequencePattern sequencePattern = new SequencePattern();
		// sequencePatterns.add(sequencePattern); //sequenceMatchRegex
		// ds
		// List<String> seqList = new ArrayList<String>();
		// seqList.add(sequenceMatchRegex);
		RestrictionProperties restrictionProperties = new RestrictionProperties(oc, speciesRestrictionRegex, nPhos,
				mwFrom, mwTo, piFrom, piTo, keywordRestrictionRegex, null);
		// oc
		// HistogramStringency.valueOf("high");
		final boolean limitResultsToPhosphorylatedProteins = false;
		final boolean doCreateFiles = false;
		final User user = null;
		final String realPath = null;

		try {
			SequenceMatchFeature feature = new SequenceMatchFeature();
			edu.mit.scansite.shared.dispatch.features.SequenceMatchResult result = feature.doSequenceMatch(patterns, ds,
					restrictionProperties, limitResultsToPhosphorylatedProteins, doCreateFiles, user, realPath);

			SequenceMatch[] matches;
			if (result.getMatches() != null && result.getMatches().size() > 0) {
				matches = new SequenceMatch[result.getMatches().size()];
				int i = 0;
				for (ProteinSequenceMatch match : result.getMatches()) {
					matches[i++] = new SequenceMatch(match.getProtein().getIdentifier(),
							match.getNrOfSequenceMatches()[0]);
				}
			} else {
				matches = new SequenceMatch[] {};
			}
			return new SequenceMatchResult(sequenceMatchRegex, datasourceShortName, matches,
					result.getSequencePatternMatchCount(), result.getProteinsInDbCount());
		} catch (DataAccessException e) {
			throw new ScansiteWebServiceException(
					"Running sequence match search failed. Please try again later or, if this problem persists, contact the system's administrator.");
		}
	}
}
