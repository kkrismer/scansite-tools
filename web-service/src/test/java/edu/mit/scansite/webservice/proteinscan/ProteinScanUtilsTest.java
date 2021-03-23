package edu.mit.scansite.webservice.proteinscan;

import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.shared.transferobjects.LightWeightProtein;
import edu.mit.scansite.webservice.ScansiteTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/9/2017.
 * Extensive testing of ProteinScanUtils class
 */
public class ProteinScanUtilsTest {
    private static final String REFERENCE_VERTEBRATA = "Vertebrata";
    private static final String REFERENCE_YEAST = "Saccharomyces cerevisiae";
    private static final String REFERENCE_PARAM_NAME = "/referenceproteome=targetvalue";
    private static final String MOTIF_PARAM_NAME = "/motifshortnames=targetvalue";
    // database scan or database search
    private static final String DS_SPECIES_RESTRICTION = "/speciesrestriction=targetvalue";
    private static final String DS_NO_OF_PHOSPHORYLATIONS = "/numberofphosphorylations=targetvalue";
    private static final String DS_MW_FROM = "/molweightfrom=targetvalue";
    private static final String DS_MW_TO = "/molweightto=targetvalue";
    private static final String DS_PI_FROM = "/isoelectricpointfrom=targetvalue";
    private static final String DS_PI_TO = "/isoelectricpointto=targetvalue";
    private static final String DS_KEYWORD = "/keywordrestriction=targetvalue";
    private static final String DS_SEQUENCE = "/sequencerestriction=targetvalue";
    // sequence match
    private static final String SM_SPECIES_RESTRICTION = "/speciesrestriction=targetvalue";
    private static final String SM_PHOSPHORYLATION_NO = "/numberofphosphorylations=targetvalue";
    private static final String SM_MW_FROM = "/molweightfrom=targetvalue";
    private static final String SM_MW_TO = "/molweightto=targetvalue";
    private static final String SM_PI_FROM = "/isoelectricpointfrom=targetvalue";
    private static final String SM_PI_TO = "/isoelectricpointto=targetvalue";
    private static final String SM_KEYWORD = "/keywordrestriction=targetvalue";

    // other test values
    private static final String NULL_VALUE = null;
    private static final String EMPTY_VALUE = "";
    private static final String MISMATCH = "/mismatch=targetvalue";

    //mismatches
    //no slash
    private static final String NS_REFERENCE_PARAM_NAME = "referenceproteome=targetvalue";
    private static final String NS_MOTIF_PARAM_NAME = "motifshortnames=targetvalue";
    // database scan or database search
    private static final String NS_DS_SPECIES_RESTRICTION = "speciesrestriction=targetvalue";
    private static final String NS_DS_NO_OF_PHOSPHORYLATIONS = "numberofphosphorylations=targetvalue";
    private static final String NS_DS_MW_FROM = "molweightfrom=targetvalue";
    private static final String NS_DS_MW_TO = "molweightto=targetvalue";
    private static final String NS_DS_PI_FROM = "isoelectricpointfrom=targetvalue";
    private static final String NS_DS_PI_TO = "isoelectricpointto=targetvalue";
    private static final String NS_DS_KEYWORD = "keywordrestriction=targetvalue";
    private static final String NS_DS_SEQUENCE = "sequencerestriction=targetvalue";
    // sequence match
    private static final String NS_SM_SPECIES_RESTRICTION = "speciesrestriction=targetvalue";
    private static final String NS_SM_PHOSPHORYLATION_NO = "numberofphosphorylations=targetvalue";
    private static final String NS_SM_MW_FROM = "molweightfrom=targetvalue";
    private static final String NS_SM_MW_TO = "molweightto=targetvalue";
    private static final String NS_SM_PI_FROM = "isoelectricpointfrom=targetvalue";
    private static final String NS_SM_PI_TO = "isoelectricpointto=targetvalue";
    private static final String NS_SM_KEYWORD = "keywordrestriction=targetvalue";

    //no equals
    private static final String NE_REFERENCE_PARAM_NAME = "/referenceproteome targetvalue";
    private static final String NE_MOTIF_PARAM_NAME = "/motifshortnames targetvalue";
    // database scan or database search
    private static final String NE_DS_SPECIES_RESTRICTION = "/speciesrestriction targetvalue";
    private static final String NE_DS_NO_OF_PHOSPHORYLATIONS = "/numberofphosphorylations targetvalue";
    private static final String NE_DS_MW_FROM = "/molweightfrom targetvalue";
    private static final String NE_DS_MW_TO = "/molweightto targetvalue";
    private static final String NE_DS_PI_FROM = "/isoelectricpointfrom targetvalue";
    private static final String NE_DS_PI_TO = "/isoelectricpointto targetvalue";
    private static final String NE_DS_KEYWORD = "/keywordrestriction targetvalue";
    private static final String NE_DS_SEQUENCE = "/sequencerestriction targetvalue";
    // sequence match
    private static final String NE_SM_SPECIES_RESTRICTION = "/speciesrestriction targetvalue";
    private static final String NE_SM_PHOSPHORYLATION_NO = "/numberofphosphorylations targetvalue";
    private static final String NE_SM_MW_FROM = "/molweightfrom targetvalue";
    private static final String NE_SM_MW_TO = "/molweightto targetvalue";
    private static final String NE_SM_PI_FROM = "/isoelectricpointfrom targetvalue";
    private static final String NE_SM_PI_TO = "/isoelectricpointto targetvalue";
    private static final String NE_SM_KEYWORD = "/keywordrestriction targetvalue";

    private static List<String> matchingValues;
    private static List<String> noSlashValues;
    private static List<String> noEqualsValues;
    private static List<String> specialMismatchValues;

    private static final String targetValue = "targetvalue";

    @Before
    public void setup() {
        matchingValues = new ArrayList<>();
        noSlashValues  = new ArrayList<>();
        noEqualsValues = new ArrayList<>();
        specialMismatchValues = new ArrayList<>();

        matchingValues.add(REFERENCE_VERTEBRATA);
        matchingValues.add(REFERENCE_YEAST);
        matchingValues.add(REFERENCE_PARAM_NAME);
        matchingValues.add(MOTIF_PARAM_NAME);
        matchingValues.add(DS_SPECIES_RESTRICTION);
        matchingValues.add(DS_NO_OF_PHOSPHORYLATIONS);
        matchingValues.add(DS_MW_FROM);
        matchingValues.add(DS_MW_TO);
        matchingValues.add(DS_PI_FROM);
        matchingValues.add(DS_PI_TO);
        matchingValues.add(DS_KEYWORD);
        matchingValues.add(DS_SEQUENCE);
        matchingValues.add(SM_SPECIES_RESTRICTION);
        matchingValues.add(SM_PHOSPHORYLATION_NO);
        matchingValues.add(SM_MW_FROM);
        matchingValues.add(SM_MW_TO);
        matchingValues.add(SM_PI_FROM);
        matchingValues.add(SM_PI_TO);
        matchingValues.add(SM_KEYWORD);

        noSlashValues.add(NS_REFERENCE_PARAM_NAME);
        noSlashValues.add(NS_MOTIF_PARAM_NAME);
        noSlashValues.add(NS_DS_SPECIES_RESTRICTION);
        noSlashValues.add(NS_DS_NO_OF_PHOSPHORYLATIONS);
        noSlashValues.add(NS_DS_MW_FROM);
        noSlashValues.add(NS_DS_MW_TO);
        noSlashValues.add(NS_DS_PI_FROM);
        noSlashValues.add(NS_DS_PI_TO);
        noSlashValues.add(NS_DS_KEYWORD);
        noSlashValues.add(NS_DS_SEQUENCE);
        noSlashValues.add(NS_SM_SPECIES_RESTRICTION);
        noSlashValues.add(NS_SM_PHOSPHORYLATION_NO);
        noSlashValues.add(NS_SM_MW_FROM);
        noSlashValues.add(NS_SM_MW_TO);
        noSlashValues.add(NS_SM_PI_FROM);
        noSlashValues.add(NS_SM_PI_TO);
        noSlashValues.add(NS_SM_KEYWORD);

        noEqualsValues.add(NE_REFERENCE_PARAM_NAME);
        noEqualsValues.add(NE_MOTIF_PARAM_NAME);
        noEqualsValues.add(NE_DS_SPECIES_RESTRICTION);
        noEqualsValues.add(NE_DS_NO_OF_PHOSPHORYLATIONS);
        noEqualsValues.add(NE_DS_MW_FROM);
        noEqualsValues.add(NE_DS_MW_TO);
        noEqualsValues.add(NE_DS_PI_FROM);
        noEqualsValues.add(NE_DS_PI_TO);
        noEqualsValues.add(NE_DS_KEYWORD);
        noEqualsValues.add(NE_DS_SEQUENCE);
        noEqualsValues.add(NE_SM_SPECIES_RESTRICTION);
        noEqualsValues.add(NE_SM_PHOSPHORYLATION_NO);
        noEqualsValues.add(NE_SM_MW_FROM);
        noEqualsValues.add(NE_SM_MW_TO);
        noEqualsValues.add(NE_SM_PI_FROM);
        noEqualsValues.add(NE_SM_PI_TO);
        noEqualsValues.add(NE_SM_KEYWORD);

        specialMismatchValues.add(NULL_VALUE);
        specialMismatchValues.add(EMPTY_VALUE);
        specialMismatchValues.add(MISMATCH);

    }


    @Test
    public void processOptionalParameterMatchTestMatching() {
        for (String value : matchingValues) {
            if (value.equals(REFERENCE_VERTEBRATA) || value.equals(REFERENCE_YEAST)) {
                assert (ProteinScanUtils.processOptionalParameter(value).equals(value));
            } else {
                assert (ProteinScanUtils.processOptionalParameter(value).equals(targetValue));
            }
        }
    }


    @Test
    public void processOptionalParameterMatchTestNoSlash() {
        for (String value : noSlashValues) {
            assert (!ProteinScanUtils.processOptionalParameter(value).equals(targetValue));
            assert (ProteinScanUtils.processOptionalParameter(value).equals(value));
        }
    }


    @Test
    public void processOptionalParameterMatchTestNoEquals() {
        for (String value : noEqualsValues) {
            assert (!ProteinScanUtils.processOptionalParameter(value).equals(targetValue));
            assert (ProteinScanUtils.processOptionalParameter(value).equals(value));
        }
    }


    @Test
    public void processOptionalParameterMatchTestMismatching() {
        for (String value : specialMismatchValues) {
            if (value == null) {
                assert (ProteinScanUtils.processOptionalParameter(value) == null);
            } else {
                assert (!ProteinScanUtils.processOptionalParameter(value).equals(targetValue));
            }
        }
    }


    @Test
    public void checkReferenceProteomeTest() {
        List<String> vertebrataKeyWords = new ArrayList<>();
        vertebrataKeyWords.add("vertebrata");
        vertebrataKeyWords.add("Vertebrata");
        vertebrataKeyWords.add("VERTEBRATA");
        vertebrataKeyWords.add("some-ther-value-triggering-default");

        List<String> yeastKeyWords = new ArrayList<>();
        yeastKeyWords.add("yeast");
        yeastKeyWords.add("Yeast");
        yeastKeyWords.add("YEAST");
        yeastKeyWords.add("saccharomyces");
        yeastKeyWords.add("Saccharomyces");
        yeastKeyWords.add("SACCHAROMYCES");
        yeastKeyWords.add("cerevisiae");
        yeastKeyWords.add("Cerevisiae");
        yeastKeyWords.add("CEREVISIAE");
        yeastKeyWords.add("saccharomycescerevisiae");
        yeastKeyWords.add("SACCHAROMYCESCEREVISIAE");

        for (String keyWord : vertebrataKeyWords) {
            assert (ProteinScanUtils.checkReferenceProteome(keyWord).equals(REFERENCE_VERTEBRATA));
        }
        for (String keyWord : yeastKeyWords) {
            assert (ProteinScanUtils.checkReferenceProteome(keyWord).equals(REFERENCE_YEAST));
        }
    }


    @Test
    public void getLightWeightProteinTest() {
        String BRCA2_HUMAN_STRING = "BRCA2_HUMAN";
        String SWISSPROT = "swissprot";
        String UNIPROT_SEQUENCE = ScansiteTestUtils.sequence;
        LightWeightProtein BRCA2_HUMAN = ProteinScanUtils.getLightWeightProtein(BRCA2_HUMAN_STRING, SWISSPROT);
        assert (BRCA2_HUMAN != null);
        assert (BRCA2_HUMAN.getIdentifier().equals(BRCA2_HUMAN_STRING));
        assert (BRCA2_HUMAN.getSequence().equalsIgnoreCase(UNIPROT_SEQUENCE));
        assert (BRCA2_HUMAN.getDataSource().getShortName().equals(SWISSPROT));
    }


    @Test
    public void getLocalizationDataSourceTest() throws DataAccessException {
        DataSource ds = ProteinScanUtils.getLocalizationDataSource("Any string works: Test message");
        assert (ds != null);
        assert (ds.getShortName().equals("loctree"));
    }
}
