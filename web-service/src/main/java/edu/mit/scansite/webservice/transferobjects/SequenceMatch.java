package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SequenceMatch implements Serializable {
  
  private static final long serialVersionUID = -5524434341184373268L;
  
  private String proteinAccession;
  private Integer numberOfMatches = 0;
  
  public SequenceMatch() {
  }
  
  public SequenceMatch(String proteinAccession, Integer numberOfMatches) {
    this.proteinAccession = proteinAccession;
    this.numberOfMatches = numberOfMatches;
  }

  public String getProteinAccession() {
    return proteinAccession;
  }

  public void setProteinAccession(String proteinAccession) {
    this.proteinAccession = proteinAccession;
  }

  public Integer getNumberOfMatches() {
    return numberOfMatches;
  }

  public void setNumberOfMatches(Integer numberOfMatches) {
    this.numberOfMatches = numberOfMatches;
  }
  
  
}
