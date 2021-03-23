package edu.mit.scansite.sql.statements;

import edu.mit.scansite.model.Motif;
import edu.mit.scansite.sql.processing.ProcessingUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.mit.scansite.sql.constants.GeneralConstants.*;

public class GetMotifsStatement extends AbstractStatement {
    private Logger logger = LoggerFactory.getLogger(GetMotifsStatement.class);

    private boolean selectMotifId;
    private boolean selectMotifDisplayName;
    private boolean selectMotifShortName;
    private boolean selectMotifGroupId;
    private boolean selectUserEmail;
    private boolean selectMotifOptimalScore;
    private boolean selectMotifIsPublic;
    private boolean selectMotifClass;

    private Integer motifId;
    private String motifDisplayName;
    private String motifShortName;
    private Integer motifGroupId;
    private String userEmail;
    private Double motifOptimalScore;
    private Boolean motifIsPublic;
    private String motifClass;

    private static final String TABLE_NAME = "motifs";
    private static final String COLUMN_MOTIF_ID = "motifsId";
    private static final String COLUMN_MOTIF_DISPLAY_NAME = "motifsDisplayName";
    private static final String COLUMN_MOTIF_SHORT_NAME = "motifsShortName";
    private static final String COLUMN_MOTIF_GROUP_ID = "motifGroupsId";
    private static final String COLUMN_USER_EMAIL = "usersEmail";
    private static final String COLUMN_MOTIF_OPTIMAL_SCORE = "motifsOptimalScore";
    private static final String COLUMN_MOTIF_IS_PUBLIC = "motifsIsPublic";
    private static final String COLUMN_MOTIF_CLASS = "motifsMotifClass";

    public GetMotifsStatement(boolean selectMotifId, boolean selectMotifDisplayName, boolean selectMotifShortName,
                              boolean selectMotifGroupId, boolean selectUserEmail, boolean selectMotifOptimalScore,
                              boolean selectMotifIsPublic, boolean selectMotifClass) {
        super();

        init(selectMotifId, selectMotifDisplayName, selectMotifShortName, selectMotifGroupId, selectUserEmail,
                selectMotifOptimalScore, selectMotifIsPublic, selectMotifClass, null, null,
                null, null, null, null, null, null);

        initTemplate();
        prepareStatement();
    }

    public GetMotifsStatement(boolean selectMotifId, boolean selectMotifDisplayName, boolean selectMotifShortName,
                              boolean selectMotifGroupId, boolean selectUserEmail, boolean selectMotifOptimalScore,
                              boolean selectMotifIsPublic, boolean selectMotifClass, Integer motifId,
                              String motifDisplayName, String motifShortName, Integer motifGroupId, String userEmail,
                              Double motifOptimalScore, Boolean motifIsPublic, String motifClass) {
       super();

       init(selectMotifId, selectMotifDisplayName, selectMotifShortName, selectMotifGroupId, selectUserEmail,
               selectMotifOptimalScore, selectMotifIsPublic, selectMotifClass, motifId, motifDisplayName,
               motifShortName, motifGroupId, userEmail, motifOptimalScore, motifIsPublic, motifClass);

       initTemplate();
       prepareStatement();
    }

    private void init(boolean selectMotifId, boolean selectMotifDisplayName, boolean selectMotifShortName,
                      boolean selectMotifGroupId, boolean selectUserEmail, boolean selectMotifOptimalScore,
                      boolean selectMotifIsPublic, boolean selectMotifClass, Integer motifId,
                      String motifDisplayName, String motifShortName, Integer motifGroupId, String userEmail,
                      Double motifOptimalScore, Boolean motifIsPublic, String motifClass) {
        this.selectMotifId = selectMotifId;
        this.selectMotifDisplayName = selectMotifDisplayName;
        this.selectMotifShortName = selectMotifShortName;
        this.selectMotifGroupId = selectMotifGroupId;
        this.selectUserEmail = selectUserEmail;
        this.selectMotifOptimalScore = selectMotifOptimalScore;
        this.selectMotifIsPublic = selectMotifIsPublic;
        this.selectMotifClass = selectMotifClass;
        this.motifId = motifId;
        this.motifDisplayName = motifDisplayName;
        this.motifShortName = motifShortName;
        this.motifGroupId = motifGroupId;
        this.userEmail = userEmail;
        this.motifOptimalScore = motifOptimalScore;
        this.motifIsPublic = motifIsPublic;
        this.motifClass = motifClass;
    }

    @Override
    protected void initTemplate() {
        String selectedColumns = selectMotifId ? COLUMN_MOTIF_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifDisplayName ? COLUMN_MOTIF_DISPLAY_NAME + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifShortName ? COLUMN_MOTIF_SHORT_NAME + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifGroupId ? COLUMN_MOTIF_GROUP_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectUserEmail ? COLUMN_USER_EMAIL + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifOptimalScore ? COLUMN_MOTIF_OPTIMAL_SCORE + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifIsPublic ? COLUMN_MOTIF_IS_PUBLIC + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifClass ? COLUMN_MOTIF_CLASS + SEPARATOR : StringUtils.EMPTY;

        if (selectedColumns.endsWith(SEPARATOR)) {
            selectedColumns = selectedColumns.substring(0, selectedColumns.lastIndexOf(SEPARATOR)); // idx-1?
        }

        String conditions = "";
        if (motifId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_ID));
            parameters.add(Arrays.asList(String.valueOf(motifId), "Integer"));
        }
        if (motifDisplayName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_DISPLAY_NAME));
            parameters.add(Arrays.asList(String.valueOf(motifDisplayName), "Integer"));
        }
        if (motifShortName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_SHORT_NAME));
            parameters.add(Arrays.asList(String.valueOf(motifShortName), "Integer"));
        }
        if (motifGroupId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_GROUP_ID));
            parameters.add(Arrays.asList(String.valueOf(motifGroupId), "Integer"));
        }
        if (userEmail != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_USER_EMAIL));
            parameters.add(Arrays.asList(String.valueOf(userEmail), "Integer"));
        }
        if (motifOptimalScore != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_OPTIMAL_SCORE));
            parameters.add(Arrays.asList(String.valueOf(motifOptimalScore), "Integer"));
        }
        if (motifIsPublic != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_IS_PUBLIC));
            parameters.add(Arrays.asList(String.valueOf(motifIsPublic), "Integer"));
        }
        if (motifClass != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_CLASS));
            parameters.add(Arrays.asList(String.valueOf(motifClass), "Integer"));
        }

        if (StringUtils.isBlank(conditions)) {
            stmtTemplate = String.format("%s %s %s %s",
                    SELECT, selectedColumns,
                    FROM, TABLE_NAME);
        } else {
            stmtTemplate = String.format("%s %s %s %s %s %s",
                    SELECT, selectedColumns,
                    FROM, TABLE_NAME,
                    WHERE, conditions);
        }
    }

    @Override
    protected void prepareStatement() {
        stmt = utils.getStatement(connection, stmtTemplate, parameters);
    }

    @Override
    public List<Motif> process() {
        try {
            ResultSet rs = stmt.executeQuery();
            List<Motif> motifs = new ArrayList<>();

            while (rs.next()) {
                Motif motif = new Motif();
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_ID)) {
                    motif.setMotifId(rs.getInt(COLUMN_MOTIF_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_DISPLAY_NAME)) {
                    motif.setMotifDisplayName(rs.getString(COLUMN_MOTIF_DISPLAY_NAME));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_SHORT_NAME)) {
                    motif.setMotifShortName(rs.getString(COLUMN_MOTIF_SHORT_NAME));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_GROUP_ID)) {
                    motif.setMotifGroupId(rs.getInt(COLUMN_MOTIF_GROUP_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_USER_EMAIL)) {
                    motif.setUserEmail(rs.getString(COLUMN_USER_EMAIL));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_OPTIMAL_SCORE)) {
                    motif.setMotifOptimalScore(rs.getDouble(COLUMN_MOTIF_OPTIMAL_SCORE));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_IS_PUBLIC)) {
                    motif.setMotifIsPublic(rs.getBoolean(COLUMN_MOTIF_IS_PUBLIC));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_CLASS)) {
                    motif.setMotifClass(rs.getString(COLUMN_MOTIF_CLASS));
                }

                motifs.add(motif);
            }

            return motifs;
        } catch (SQLException e) {
            logger.error("Could not retrieve Motif values from database!", e);
        }
        return null;
    }
}
