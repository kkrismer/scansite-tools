package edu.mit.scansite.sql.statements;

import edu.mit.scansite.model.MotifGroup;
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

public class GetMotifGroupsStatement extends AbstractStatement {
    private Logger logger = LoggerFactory.getLogger(GetMotifGroupsStatement.class);

    private boolean selectMotifGroupId;
    private boolean selectMotifGroupDisplayName;
    private boolean selectMotifGroupShortName;

    private Integer motifGroupId;
    private String motifGroupDisplayName;
    private String motifGroupShortName;

    private static final String TABLE_NAME = "motifGroups";
    private static final String COLUMN_MOTIF_GROUP_ID = "motifGroupsId";
    private static final String COLUMN_MOTIF_GROUP_DISPLAY_NAME = "motifGroupsDisplayName";
    private static final String COLUMN_MOTIF_GROUP_SHORT_NAME = "motifGroupsShortName";

    public GetMotifGroupsStatement(boolean selectMotifGroupId, boolean selectMotifGroupDisplayName, boolean selectMotifGroupShortName) {
        super();
        init(selectMotifGroupId, selectMotifGroupDisplayName, selectMotifGroupShortName, null,
                null, null);
        initTemplate();
        prepareStatement();
    }

    public GetMotifGroupsStatement(boolean selectMotifGroupId, boolean selectMotifGroupDisplayName,
                                   boolean selectMotifGroupShortName, Integer motifGroupId,
                                   String motifGroupDisplayName, String motifGroupShortName) {
        super();
        init(selectMotifGroupId, selectMotifGroupDisplayName, selectMotifGroupShortName, motifGroupId,
                motifGroupDisplayName, motifGroupShortName);
        initTemplate();
        prepareStatement();
    }

    private void init(boolean selectMotifGroupId, boolean selectMotifGroupDisplayName,
                      boolean selectMotifGroupShortName, Integer motifGroupId,
                      String motifGroupDisplayName, String motifGroupShortName) {
        this.selectMotifGroupId = selectMotifGroupId;
        this.selectMotifGroupDisplayName = selectMotifGroupDisplayName;
        this.selectMotifGroupShortName = selectMotifGroupShortName;
        this.motifGroupId = motifGroupId;
        this.motifGroupDisplayName = motifGroupDisplayName;
        this.motifGroupShortName = motifGroupShortName;
    }

    public GetMotifGroupsStatement() {
        super();
        initTemplate();
        prepareStatement();
    }

    @Override
    protected void initTemplate() {
        String selectedColumns = selectMotifGroupId ? COLUMN_MOTIF_GROUP_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifGroupDisplayName ? COLUMN_MOTIF_GROUP_DISPLAY_NAME + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectMotifGroupShortName ? COLUMN_MOTIF_GROUP_SHORT_NAME + SEPARATOR : StringUtils.EMPTY;

        if (selectedColumns.endsWith(SEPARATOR)) {
            selectedColumns = selectedColumns.substring(0, selectedColumns.lastIndexOf(SEPARATOR)); // idx-1?
        }

        String conditions = "";
        if (motifGroupId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_GROUP_ID));
            parameters.add(Arrays.asList(String.valueOf(motifGroupId), "Integer"));
        }
        if (motifGroupDisplayName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_GROUP_DISPLAY_NAME));
            parameters.add(Arrays.asList(motifGroupDisplayName, "String"));
        }
        if (motifGroupShortName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_MOTIF_GROUP_SHORT_NAME));
            parameters.add(Arrays.asList(motifGroupShortName, "String"));
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
    public List<MotifGroup> process() {
        try {
            ResultSet rs = stmt.executeQuery();
            List<MotifGroup> motifGroups = new ArrayList<>();

            while (rs.next()) {
                MotifGroup motifGroup = new MotifGroup();
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_GROUP_ID)) {
                    motifGroup.setMotifGroupId(rs.getInt(COLUMN_MOTIF_GROUP_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_GROUP_DISPLAY_NAME)) {
                    motifGroup.setMotifGroupDisplayName(rs.getString(COLUMN_MOTIF_GROUP_DISPLAY_NAME));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_MOTIF_GROUP_SHORT_NAME)) {
                    motifGroup.setMotifGroupShortName(rs.getString(COLUMN_MOTIF_GROUP_SHORT_NAME));
                }

                motifGroups.add(motifGroup);
            }

            return motifGroups;
        } catch (SQLException e) {
            logger.error("Could not retrieve Motif Group values from database!", e);
        }
        return null;
    }
}
