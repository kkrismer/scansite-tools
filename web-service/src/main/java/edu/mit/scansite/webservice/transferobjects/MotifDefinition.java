package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MotifDefinition implements Serializable {
  private static final long serialVersionUID = -2150800498753234736L;
  
  private String motifGroupName;
  private String motifName;
  private String motifShortName;
  private String motifClass;
  
  public MotifDefinition() {
  }

  public MotifDefinition(String motifGroupName, String motifName, String motifShortName, String motifClass) {
    super();
    this.motifGroupName = motifGroupName;
    this.motifName = motifName;
    this.motifShortName = motifShortName;
    this.motifClass = motifClass;
  }

  public String getMotifGroupName() {
    return motifGroupName;
  }

  public void setMotifGroupName(String motifGroupName) {
    this.motifGroupName = motifGroupName;
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
  
  public String getMotifClass() {
    return motifClass;
  }
  
  public void setMotifClass(String motifClass) {
    this.motifClass = motifClass;
  }
}
