package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MotifSite implements Serializable {
  private static final long serialVersionUID = -3248631084774335734L;

  protected Double score;
  protected Double percentile;
  protected String motifName;
  protected String motifShortName;
  protected String site;
  protected String siteSequence;
  
  public MotifSite() {
  }

  public MotifSite(Double score, Double percentile, String motifName, String motifShortName, String site,
      String siteSequence) {
    super();
    this.score = score;
    this.percentile = percentile;
    this.motifName = motifName;
    this.motifShortName = motifShortName;
    this.site = site;
    this.siteSequence = siteSequence;
  }
  
  public Double getScore() {
    return score;
  }
  
  public void setScore(Double score) {
    this.score = score;
  }
  
  public Double getPercentile() {
    return percentile;
  }
  
  public void setPercentile(Double percentile) {
    this.percentile = percentile;
  }

  public String getMotifName() {
    return motifName;
  }

  public void setMotifName(String motifName) {
    this.motifName = motifName;
  }

  public String getMotifShortName() {
    return motifShortName;
  }

  public void setMotifShortName(String motifShortName) {
    this.motifShortName = motifShortName;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getSiteSequence() {
    return siteSequence;
  }

  public void setSiteSequence(String siteSequence) {
    this.siteSequence = siteSequence;
  }

}
