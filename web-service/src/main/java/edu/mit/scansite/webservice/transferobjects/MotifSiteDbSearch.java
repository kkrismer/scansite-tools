package edu.mit.scansite.webservice.transferobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MotifSiteDbSearch extends MotifSite {
  private static final long serialVersionUID = -3248631084774335734L;

  protected String proteinAccession;

  public MotifSiteDbSearch() {
  }
  
  public MotifSiteDbSearch(Double score, Double percentile, String motifName, String motifNickName, String site, String siteSequence,
      String proteinName) {
    super(score, percentile, motifName, motifNickName, site, siteSequence);
    this.proteinAccession = proteinName;
  }
  
  public String getProteinAccession() {
    return proteinAccession;
  }

  public void setProteinAccession(String proteinAccession) {
    this.proteinAccession = proteinAccession;
  }

}
