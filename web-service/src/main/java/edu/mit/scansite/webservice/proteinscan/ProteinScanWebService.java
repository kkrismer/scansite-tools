package edu.mit.scansite.webservice.proteinscan;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import edu.mit.scansite.server.features.ProteinScanFeature;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.HistogramStringency;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.shared.transferobjects.MotifClass;
import edu.mit.scansite.shared.transferobjects.MotifSelection;
import edu.mit.scansite.shared.transferobjects.ScanResultSite;
import edu.mit.scansite.shared.transferobjects.User;
import edu.mit.scansite.webservice.WebService;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.transferobjects.MotifSite;
import edu.mit.scansite.webservice.transferobjects.ProteinScanResult;

/**
 * @author Tobieh
 * @author Thomas Bernwinkler
 * @author Konstantin Krismer
 */

public class ProteinScanWebService extends WebService {
	private static final String MOTIF_SEPARATOR = "~";

	// package private
	static ProteinScanResult doProteinScan(LightWeightProtein protein, String referenceProteome,
			String dataSourceShortName, DataSource localizationDataSource, String inputMotifShortNames,
			String motifClass, String stringencyValue) {

		// check motif input
		MotifClass mc = MotifClass.getDbValue(motifClass.toUpperCase());

		if (StringUtils.isBlank(dataSourceShortName)) {
			dataSourceShortName = "swissprot";
		}

		// If explicitly motifs picked: use them -- else motif class
		String[] motifShortNames;
		Set<String> motifShortNamesSet = null;
		if (StringUtils.isNotBlank(inputMotifShortNames)) {
			motifShortNames = inputMotifShortNames.split(MOTIF_SEPARATOR);
			if (motifShortNames.length > 0) {
				motifShortNamesSet = new HashSet<>();
				Collections.addAll(motifShortNamesSet, motifShortNames);
			}
		}

		// check stringency input
		HistogramStringency stringency = getStringency(stringencyValue);
		if (stringency == null || dataSourceShortName.isEmpty()) {
			stringency = getStringency("High");
		}

		// LightWeightProtein protein = new LightWeightProtein(proteinName,
		// proteinSequence);
		MotifSelection motifSelection = new MotifSelection();
		motifSelection.setMotifShortNames(motifShortNamesSet);
		motifSelection.setMotifClass(mc);
		final boolean showDomains = false;
		final boolean doCreateFiles = false;
		final User user = null;
		final String realPath = null; // only necessary if doCreateFiles or if showDomains

		try {
			ProteinScanFeature feature = new ProteinScanFeature();

			edu.mit.scansite.shared.dispatch.features.ProteinScanResult res = feature.doProteinScan(protein,
					motifSelection, stringency, showDomains, dataSourceShortName, referenceProteome,
					localizationDataSource, doCreateFiles, user, realPath);

			if (res.isSuccess()) {
				ProteinScanResult result = new ProteinScanResult();
				result.setProteinName(res.getResults().getProtein().getIdentifier());
				result.setProteinSequence(res.getResults().getProtein().getSequence());
				if (res.getResults().getHits() == null || res.getResults().getHits().isEmpty()) {
					result.setPredictedSite(new MotifSite[] {});
				} else {
					MotifSite[] sites = new MotifSite[res.getResults().getHits().size()];
					for (int i = 0; i < sites.length; ++i) {
						ScanResultSite site = res.getResults().getHits().get(i);
						sites[i] = new MotifSite(site.getScore(), site.getPercentile(),
								site.getMotif().getDisplayName(), site.getMotif().getShortName(), site.getSite(),
								site.getSiteSequence().replaceAll("\\<.*?\\>", ""));
					}
					result.setPredictedSite(sites);
				}

				return result;
			} else {
				throw new ScansiteWebServiceException(res.getFailureMessage());
			}
		} catch (DataAccessException e) {
			throw new ScansiteWebServiceException("Running ProteinScan service failed.");
		}
	}

	public static HistogramStringency getStringency(String stringencyValue) {
		if (stringencyValue == null || stringencyValue.isEmpty()) {
			stringencyValue = "high";
		}

		for (HistogramStringency str : HistogramStringency.values()) {
			if (str.getName().equalsIgnoreCase(stringencyValue)) {
				return str;
			}
		}
		return null;
	}
}
