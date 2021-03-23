package edu.mit.scansite.model;

public class DataSource {
    private int dataSourceId;
    private int dataSourceTypeId;
    private int identifierTypeId;
    private String dataSourceShortName;
    private String dataSourceDisplayName;
    private String dataSourceDescription;
    private String dataSourceVersion;
    private String dataSourceLastUpdate;
    private boolean dataSourceIsPrimary;

    public DataSource() {
    }

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public int getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId(int dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }

    public int getIdentifierTypeId() {
        return identifierTypeId;
    }

    public void setIdentifierTypeId(int identifierTypeId) {
        this.identifierTypeId = identifierTypeId;
    }

    public String getDataSourceShortName() {
        return dataSourceShortName;
    }

    public void setDataSourceShortName(String dataSourceShortName) {
        this.dataSourceShortName = dataSourceShortName;
    }

    public String getDataSourceDisplayName() {
        return dataSourceDisplayName;
    }

    public void setDataSourceDisplayName(String dataSourceDisplayName) {
        this.dataSourceDisplayName = dataSourceDisplayName;
    }

    public String getDataSourceDescription() {
        return dataSourceDescription;
    }

    public void setDataSourceDescription(String dataSourceDescription) {
        this.dataSourceDescription = dataSourceDescription;
    }

    public String getDataSourceVersion() {
        return dataSourceVersion;
    }

    public void setDataSourceVersion(String dataSourceVersion) {
        this.dataSourceVersion = dataSourceVersion;
    }

    public String getDataSourceLastUpdate() {
        return dataSourceLastUpdate;
    }

    public void setDataSourceLastUpdate(String dataSourceLastUpdate) {
        this.dataSourceLastUpdate = dataSourceLastUpdate;
    }

    public boolean isDataSourceIsPrimary() {
        return dataSourceIsPrimary;
    }

    public void setDataSourceIsPrimary(boolean dataSourceIsPrimary) {
        this.dataSourceIsPrimary = dataSourceIsPrimary;
    }
}
