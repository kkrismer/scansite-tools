package edu.mit.scansite.sql.statements;

import edu.mit.scansite.model.DataSource;
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

public class GetDataSourcesStatement extends AbstractStatement {
    private Logger logger = LoggerFactory.getLogger(GetDataSourcesStatement.class);

    private boolean selectDataSourceId;
    private boolean selectDataSourceTypeId;
    private boolean selectIdentifierTypeId;
    private boolean selectDataSourceShortName;
    private boolean selectDataSourceDisplayName;
    private boolean selectDataSourceDescription;
    private boolean selectDataSourceVersion;
    private boolean selectDataSourceLastUpdate;
    private boolean selectDataSourceIsPrimary;

    private Integer dataSourceId;
    private Integer dataSourceTypeId;
    private Integer identifierTypeId;
    private String dataSourceShortName;
    private String dataSourceDisplayName;
    private String dataSourceDescription;
    private String dataSourceVersion;
    private String dataSourceLastUpdate;
    private Boolean dataSourceIsPrimary;

    private static final String TABLE_NAME = "dataSources";
    private static final String COLUMN_DATA_SOURCE_ID = "dataSourcesId";
    private static final String COLUMN_DATA_SOURCE_TYPE_ID = "dataSourceTypesId";
    private static final String COLUMN_IDENTIFIER_TYPE_ID = "identifierTypesId";
    private static final String COLUMN_DATA_SOURCE_SHORT_NAME = "dataSourcesShortName";
    private static final String COLUMN_DATA_SOURCE_DISPLAY_NAME = "dataSourcesDisplayName";
    private static final String COLUMN_DATA_SOURCE_DESCRIPTION = "dataSourcesDescription";
    private static final String COLUMN_DATA_SOURCE_VERSION = "dataSourcesVersion";
    private static final String COLUMN_DATA_SOURCE_LAST_UPDATE = "dataSourcesLastUpdate";
    private static final String COLUMN_DATA_SOURCE_IS_PRIMARY = "dataSourcesIsPrimary";

    // NO CONDITION
    public GetDataSourcesStatement(boolean selectDataSourceId, boolean selectDataSourceTypeId, boolean selectIdentifierTypeId,
                                   boolean selectDataSourceShortName, boolean selectDataSourceDisplayName,
                                   boolean selectDataSourceDescription, boolean selectDataSourceVersion,
                                   boolean selectDataSourceLastUpdate, boolean selectDataSourceIsPrimary) {

        super();

        init(selectDataSourceId, selectDataSourceTypeId, selectIdentifierTypeId, selectDataSourceShortName,
                selectDataSourceDisplayName, selectDataSourceDescription, selectDataSourceVersion, selectDataSourceLastUpdate,
                selectDataSourceIsPrimary, null, null, null, null,
                null, null, null, null, null);

        initTemplate();
        prepareStatement();
    }

    // WHERE RESTRICTION
    public GetDataSourcesStatement(boolean selectDataSourceId, boolean selectDataSourceTypeId, boolean selectIdentifierTypeId,
                                   boolean selectDataSourceShortName, boolean selectDataSourceDisplayName,
                                   boolean selectDataSourceDescription, boolean selectDataSourceVersion,
                                   boolean selectDataSourceLastUpdate, boolean selectDataSourceIsPrimary,
                                   Integer dataSourceId, Integer dataSourceTypeId, Integer identifierTypeId,
                                   String dataSourceShortName, String dataSourceDisplayName, String dataSourceDescription,
                                   String dataSourceVersion, String dataSourceLastUpdate, Boolean dataSourceIsPrimary) {
        super();

        init(selectDataSourceId, selectDataSourceTypeId, selectIdentifierTypeId, selectDataSourceShortName,
        selectDataSourceDisplayName, selectDataSourceDescription, selectDataSourceVersion, selectDataSourceLastUpdate,
        selectDataSourceIsPrimary, dataSourceId, dataSourceTypeId, identifierTypeId, dataSourceShortName,
        dataSourceDisplayName, dataSourceDescription, dataSourceVersion, dataSourceLastUpdate, dataSourceIsPrimary);

        initTemplate();
        prepareStatement();
    }

    private void init(boolean selectDataSourceId, boolean selectDataSourceTypeId, boolean selectIdentifierTypeId,
                      boolean selectDataSourceShortName, boolean selectDataSourceDisplayName,
                      boolean selectDataSourceDescription, boolean selectDataSourceVersion,
                      boolean selectDataSourceLastUpdate, boolean selectDataSourceIsPrimary,
                      Integer dataSourceId, Integer dataSourceTypeId, Integer identifierTypeId,
                      String dataSourceShortName, String dataSourceDisplayName, String dataSourceDescription,
                      String dataSourceVersion, String dataSourceLastUpdate, Boolean dataSourceIsPrimary) {
        this.selectDataSourceId = selectDataSourceId;
        this.selectDataSourceTypeId = selectDataSourceTypeId;
        this.selectIdentifierTypeId = selectIdentifierTypeId;
        this.selectDataSourceShortName = selectDataSourceShortName;
        this.selectDataSourceDisplayName = selectDataSourceDisplayName;
        this.selectDataSourceDescription = selectDataSourceDescription;
        this.selectDataSourceVersion = selectDataSourceVersion;
        this.selectDataSourceLastUpdate = selectDataSourceLastUpdate;
        this.selectDataSourceIsPrimary = selectDataSourceIsPrimary;

        this.dataSourceId = dataSourceId;
        this.dataSourceTypeId = dataSourceTypeId;
        this.identifierTypeId = identifierTypeId;
        this.dataSourceShortName = dataSourceShortName;
        this.dataSourceDisplayName = dataSourceDisplayName;
        this.dataSourceDescription = dataSourceDescription;
        this.dataSourceVersion = dataSourceVersion;
        this.dataSourceLastUpdate = dataSourceLastUpdate;
        this.dataSourceIsPrimary = dataSourceIsPrimary;
    }

    @Override
    protected void initTemplate() {
        String selectedColumns = selectDataSourceId ? COLUMN_DATA_SOURCE_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceTypeId ? COLUMN_DATA_SOURCE_TYPE_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectIdentifierTypeId ? COLUMN_IDENTIFIER_TYPE_ID + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceShortName ? COLUMN_DATA_SOURCE_SHORT_NAME + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceDisplayName ? COLUMN_DATA_SOURCE_DISPLAY_NAME + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceDescription ? COLUMN_DATA_SOURCE_DESCRIPTION + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceVersion ? COLUMN_DATA_SOURCE_VERSION + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceLastUpdate ? COLUMN_DATA_SOURCE_LAST_UPDATE + SEPARATOR : StringUtils.EMPTY;
        selectedColumns += selectDataSourceIsPrimary ? COLUMN_DATA_SOURCE_IS_PRIMARY + SEPARATOR : StringUtils.EMPTY;

        if (selectedColumns.endsWith(SEPARATOR)) {
            selectedColumns = selectedColumns.substring(0, selectedColumns.lastIndexOf(SEPARATOR)); // idx-1?
        }

        String conditions = "";
        if (dataSourceId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_ID));
            parameters.add(Arrays.asList(String.valueOf(dataSourceId), "Integer"));
        }
        if (dataSourceTypeId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_TYPE_ID));
            parameters.add(Arrays.asList(String.valueOf(dataSourceTypeId), "Integer"));
        }
        if (identifierTypeId != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_IDENTIFIER_TYPE_ID));
            parameters.add(Arrays.asList(String.valueOf(identifierTypeId), "Integer"));
        }
        if (dataSourceShortName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_SHORT_NAME));
            parameters.add(Arrays.asList(dataSourceShortName, "String"));
        }
        if (dataSourceDisplayName != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_DISPLAY_NAME));
            parameters.add(Arrays.asList(dataSourceDisplayName, "String"));
        }
        if (dataSourceDescription != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_DESCRIPTION));
            parameters.add(Arrays.asList(dataSourceDescription, "String"));
        }
        if (dataSourceVersion != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_VERSION));
            parameters.add(Arrays.asList(dataSourceVersion, "String"));
        }
        if (dataSourceLastUpdate != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_LAST_UPDATE));
            parameters.add(Arrays.asList(dataSourceLastUpdate, "String"));
        }
        if (dataSourceIsPrimary != null) {
            conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_DATA_SOURCE_IS_PRIMARY));
            parameters.add(Arrays.asList(String.valueOf(dataSourceIsPrimary), "Boolean"));
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
    public List<DataSource> process() {
        try {
            ResultSet rs = stmt.executeQuery();
            List<DataSource> dataSources = new ArrayList<>();

            while (rs.next()) {
                DataSource ds = new DataSource();
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_ID)) {
                    ds.setDataSourceId(rs.getInt(COLUMN_DATA_SOURCE_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_TYPE_ID)) {
                    ds.setDataSourceTypeId(rs.getInt(COLUMN_DATA_SOURCE_TYPE_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_IDENTIFIER_TYPE_ID)) {
                    ds.setIdentifierTypeId(rs.getInt(COLUMN_IDENTIFIER_TYPE_ID));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_SHORT_NAME)) {
                    ds.setDataSourceShortName(rs.getString(COLUMN_DATA_SOURCE_SHORT_NAME));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_DISPLAY_NAME)) {
                    ds.setDataSourceDisplayName(rs.getString(COLUMN_DATA_SOURCE_DISPLAY_NAME));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_DESCRIPTION)) {
                    ds.setDataSourceDescription(rs.getString(COLUMN_DATA_SOURCE_DESCRIPTION));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_DESCRIPTION)) {
                    ds.setDataSourceDescription(rs.getString(COLUMN_DATA_SOURCE_DESCRIPTION));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_VERSION)) {
                    ds.setDataSourceVersion(rs.getString(COLUMN_DATA_SOURCE_VERSION));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_LAST_UPDATE)) {
                    ds.setDataSourceLastUpdate(rs.getString(COLUMN_DATA_SOURCE_LAST_UPDATE));
                }
                if (ProcessingUtils.hasColumn(rs, COLUMN_DATA_SOURCE_IS_PRIMARY)) {
                    ds.setDataSourceIsPrimary(rs.getBoolean(COLUMN_DATA_SOURCE_IS_PRIMARY));
                }

                dataSources.add(ds);
            }

            return dataSources;
        } catch (SQLException e) {
            logger.error("Could not retrieve DataSource values from database!", e);
        }
        return null;
    }
}
