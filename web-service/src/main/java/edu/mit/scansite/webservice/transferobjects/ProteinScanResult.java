package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProteinScanResult implements Serializable {
  private static final long serialVersionUID = 7100859133932319407L;
  
  private String proteinName;
  private String proteinSequence;
  private MotifSite [] predictedSite;
  
  public ProteinScanResult() {
  }

  public ProteinScanResult(MotifSite[] sites, String proteinName, String proteinSequence) {
    super();
    this.setPredictedSite(sites);
    this.proteinName = proteinName;
    this.proteinSequence = proteinSequence;
  }

  public String getProteinName() {
    return proteinName;
  }

  public void setProteinName(String proteinName) {
    this.proteinName = proteinName;
  }

  public String getProteinSequence() {
    return proteinSequence;
  }

  public void setProteinSequence(String proteinSequence) {
    this.proteinSequence = proteinSequence;
  }

  public MotifSite [] getPredictedSite() {
    return predictedSite;
  }

  public void setPredictedSite(MotifSite [] predictedSite) {
    this.predictedSite = predictedSite;
  }
  
}
