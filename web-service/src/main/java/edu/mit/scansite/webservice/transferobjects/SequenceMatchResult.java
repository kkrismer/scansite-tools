package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SequenceMatchResult implements Serializable {
  private static final long serialVersionUID = 3635800089804733360L;
  
  private String regex;
  private String datasourceNick;
  private SequenceMatch [] sequenceMatch;
  private Integer numberOfProteinsMatchedBySearch = 0;
  private Integer numberOfProteinsInSelectedDatabase = 0;
  
  public SequenceMatchResult() {
  }

  public SequenceMatchResult(String regex, String datasourceNick, SequenceMatch[] sequenceMatch,
      Integer numberOfProteinsMatchedBySearch, Integer numberOfProteinsInSelectedDatabase) {
    this.regex = regex;
    this.datasourceNick = datasourceNick;
    this.sequenceMatch = sequenceMatch;
    this.numberOfProteinsMatchedBySearch = numberOfProteinsMatchedBySearch;
    this.numberOfProteinsInSelectedDatabase = numberOfProteinsInSelectedDatabase;
  }

  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getDatasourceNick() {
    return datasourceNick;
  }

  public void setDatasourceNick(String datasourceNick) {
    this.datasourceNick = datasourceNick;
  }

  public SequenceMatch[] getSequenceMatch() {
    return sequenceMatch;
  }

  public void setSequenceMatch(SequenceMatch[] sequenceMatch) {
    this.sequenceMatch = sequenceMatch;
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
}
