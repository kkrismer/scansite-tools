package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MotifDefinitions implements Serializable {
  private static final long serialVersionUID = 5908703321116833880L;
  
  private MotifDefinition[] motifDefinition;
  
  public MotifDefinitions() {
  }

  public MotifDefinitions(MotifDefinition[] motifDefinition) {
    super();
    this.motifDefinition = motifDefinition;
  }

  public MotifDefinition[] getMotifDefinition() {
    return motifDefinition;
  }

  public void setMotifDefinition(MotifDefinition[] motifDefinition) {
    this.motifDefinition = motifDefinition;
  }

  
  
}
