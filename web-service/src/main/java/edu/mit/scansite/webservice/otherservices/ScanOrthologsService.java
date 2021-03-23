package edu.mit.scansite.webservice.otherservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.features.OrthologScanFeature;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.HistogramStringency;
import edu.mit.scansite.shared.transferobjects.LightWeightMotifGroup;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.shared.transferobjects.MotifGroup;
import edu.mit.scansite.shared.transferobjects.Ortholog;
import edu.mit.scansite.shared.transferobjects.ScanResultSite;
import edu.mit.scansite.shared.transferobjects.User;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.proteinscan.ProteinScanUtils;
import edu.mit.scansite.webservice.proteinscan.ProteinScanWebService;
import edu.mit.scansite.webservice.transferobjects.MotifSite;
import edu.mit.scansite.webservice.transferobjects.OrthologScanResult;
import edu.mit.scansite.webservice.transferobjects.OrthologScanResultEntry;

/**
 * @author Thomas Bernwinkler
 * @author Konstantin Krismer
 */
// todo [optional]: Make motif group and site position optional and add a
// sequence pattern parameter
@Path("/scanorthologs/identifier={identifier: \\S+}/dsshortname={dsshortname: [A-Za-z]+}/orthologydsshortname={orthologydsshortname: [A-Za-z]+}/alignmentradius={alignmentradius: [0-9]+}/stringency={stringency: [A-Za-z]+}/motifgroup={motifgroup: \\S+}/siteposition={siteposition: [0-9]+}")
public class ScanOrthologsService {

	@GET
	@Produces({ MediaType.APPLICATION_XML })
	public static OrthologScanResult scanOrthologs(@PathParam("siteposition") String sitePositionStr,
			@PathParam("motifgroup") String motifGroupsStr,
			@PathParam("orthologydsshortname") String orthologyDsShortName,
			@PathParam("identifier") String proteinIdentifier, @PathParam("dsshortname") String proteinDsShortName,
			@PathParam("alignmentradius") String alignmentRadiusStr, @PathParam("stringency") String stringencyValue) {
		// String sequencePatternStr = "";

		Integer sitePosition;
		if (sitePositionStr != null && !sitePositionStr.isEmpty()) {
			sitePosition = Integer.parseInt(sitePositionStr);
		} else {
			throw new ScansiteWebServiceException("Running OrthologScan service failed: No site position available");
		}
		LightWeightMotifGroup motifGroup = getMotifGroup(motifGroupsStr);
		// SequencePattern pattern = getSequencePattern(sequencePatternStr);

		Integer alignmentRadius;
		if (alignmentRadiusStr != null && !alignmentRadiusStr.isEmpty()) {
			alignmentRadius = Integer.parseInt(alignmentRadiusStr);
		} else {
			throw new ScansiteWebServiceException("Running OrthologScan service failed: No alignment radius available");
		}

		LightWeightProtein protein = ProteinScanUtils.getLightWeightProtein(proteinIdentifier, proteinDsShortName);
		HistogramStringency stringency = ProteinScanWebService.getStringency(stringencyValue);

		try {
			final User user = null;
			DataSource ds = ServiceLocator.getDaoFactory().getDataSourceDao().get(orthologyDsShortName);
			OrthologScanFeature feature = new OrthologScanFeature();
			edu.mit.scansite.shared.dispatch.features.OrthologScanResult result;
			if (motifGroup != null) {
				System.out.println("Running ortholog scan based on motif groups");
				result = feature.scanOrthologsByMotifGroup(sitePosition, motifGroup, ds, protein, stringency,
						alignmentRadius, user);
			} else {
				throw new ScansiteWebServiceException("Running OrthologScan service failed. Motif group missing");
				// System.out.println("Running ortholog scan based on sequence pattern(s)");
				// result = feature.scanOrthologsBySequencePattern(pattern, ds, protein,
				// stringency, alignmentRadius, publicOnly);
			}

			if (result.isSuccess()) {
				OrthologScanResult scanResult = new OrthologScanResult();
				List<OrthologScanResultEntry> resultEntries = new ArrayList<>();
				List<Ortholog> orthologs = result.getOrthologs();

				for (Ortholog ortholog : orthologs) {
					OrthologScanResultEntry entry = new OrthologScanResultEntry();
					entry.setProteinName(ortholog.getProtein().getIdentifier());
					HashMap<String, Set<String>> annotationsMap = ortholog.getProtein().getAnnotations();
					Set<String> annotationKeys = annotationsMap.keySet();
					String annotations = "";
					for (String annotationKey : annotationKeys) {
						Set<String> categoryAnnotations = annotationsMap.get(annotationKey);
						annotations += annotationKey + ": ";
						for (String categoryAnnotation : categoryAnnotations) {
							annotations += categoryAnnotation;
						}
						annotations += "\n";
					}
					entry.setAnnotation(annotations);
					entry.setMolWeight(ortholog.getProtein().getMolecularWeight());
					entry.setpI(ortholog.getProtein().getpI());
					MotifSite[] motifSites = new MotifSite[ortholog.getPhosphorylationSites().size()];
					for (int i = 0; i < ortholog.getPhosphorylationSites().size(); i++) {
						ScanResultSite site = ortholog.getPhosphorylationSites().get(i);
						motifSites[i] = new MotifSite(site.getScore(), site.getPercentile(),
								site.getMotif().getDisplayName(), site.getMotif().getShortName(), site.getSite(),
								site.getSiteSequence());
					}
					entry.setPredictedSite(motifSites);
					resultEntries.add(entry);
				}

				OrthologScanResultEntry[] resultEntryArray = new OrthologScanResultEntry[resultEntries.size()];
				for (int i = 0; i < resultEntries.size(); i++) {
					resultEntryArray[i] = resultEntries.get(i);
				}
				scanResult.setOrthologousProteins(resultEntryArray);
				scanResult.setSequenceAlignment(result.getSequenceAlignment().getHTMLFormattedAlignment());

				return scanResult;
			} else {
				throw new ScansiteWebServiceException(result.getFailureMessage());
			}
		} catch (Exception e) {
			throw new ScansiteWebServiceException("Running OrthologScan service failed.");
		}
	}

	// private static SequencePattern getSequencePattern(String sequencePatternStr)
	// {
	// //todo if the second choice is going to be introduced
	// return null;
	// }

	private static LightWeightMotifGroup getMotifGroup(String motifGroupStr) {
		final boolean publicOnly = true;
		List<MotifGroup> motifGroups;
		try {
			motifGroups = ServiceLocator.getDaoFactory().getGroupsDao().getAll(publicOnly);
			for (MotifGroup motifGroup : motifGroups) {
				if (motifGroup.getShortName().equals(motifGroupStr)) {
					return motifGroup;
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
