package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DbSearchResult implements Serializable {
  private static final long serialVersionUID = -7326446674916050976L;

  private MotifSiteDbSearch [] predictedSite;
  private String datasourceNickName;
  private Double medianScore = 0D;
  private Double medianAbsoluteDeviationOfScores = 0D;
  private Integer numberOfProteinsMatchedBySearch = 0;
  private Integer numberOfProteinsInSelectedDatabase = 0;
  private Integer numberOfSitesFound = 0;

  public DbSearchResult() {
  }

  public DbSearchResult(MotifSiteDbSearch[] sites, String databaseNickName, 
      Double medianScore, Double medianAbsoluteDeviationOfScores, 
      Integer numberOfProteinsMatchedBySearch, Integer numberOfSitesFound, int numberOfProteinsInSelectedDatabase) {
    super();
    this.setPredictedSite(sites);
    this.datasourceNickName = databaseNickName;
    this.medianScore = medianScore;
    this.medianAbsoluteDeviationOfScores = medianAbsoluteDeviationOfScores;
    this.numberOfProteinsMatchedBySearch = numberOfProteinsMatchedBySearch;
    this.numberOfProteinsInSelectedDatabase = numberOfProteinsInSelectedDatabase;
    this.numberOfSitesFound = numberOfSitesFound;
  }

  public String getDatasourceNickName() {
    return datasourceNickName;
  }

  public void setDatasourceNickName(String databaseNickName) {
    this.datasourceNickName = databaseNickName;
  }

  public MotifSiteDbSearch [] getPredictedSite() {
    return predictedSite;
  }

  public void setPredictedSite(MotifSiteDbSearch [] predictedSite) {
    this.predictedSite = predictedSite;
  }
  
  public Double getMedianScore() {
    return medianScore;
  }

  public void setMedianScore(Double medianScore) {
    this.medianScore = medianScore;
  }

  public Double getMedianAbsoluteDeviationOfScores() {
    return medianAbsoluteDeviationOfScores;
  }

  public void setMedianAbsoluteDeviationOfScores(Double medianAbsoluteDeviationOfScores) {
    this.medianAbsoluteDeviationOfScores = medianAbsoluteDeviationOfScores;
  }

  public Integer getNumberOfProteinsMatchedBySearch() {
    return numberOfProteinsMatchedBySearch;
  }

  public void setNumberOfProteinsMatchedBySearch(Integer numberOfProteinsMatchedBySearch) {
    this.numberOfProteinsMatchedBySearch = numberOfProteinsMatchedBySearch;
  }

  public Integer getNumberOfProteinsInSelectedDatabase() {
    return numberOfProteinsInSelectedDatabase;
  }

  public void setNumberOfProteinsInSelectedDatabase(Integer numberOfProteinsInSelectedDatabase) {
    this.numberOfProteinsInSelectedDatabase = numberOfProteinsInSelectedDatabase;
  }

  public Integer getNumberOfSitesFound() {
    return numberOfSitesFound;
  }

  public void setNumberOfSitesFound(Integer numberOfSitesFound) {
    this.numberOfSitesFound = numberOfSitesFound;
  }
  
  
}
