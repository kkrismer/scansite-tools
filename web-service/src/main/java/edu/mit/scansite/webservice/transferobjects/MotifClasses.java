package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MotifClasses implements Serializable {
  private static final long serialVersionUID = -8626936812676813427L;

  private String [] motifClass;
  
  public MotifClasses() {
  }
  
  public MotifClasses(String[] motifClasses) {
    this.setMotifClass(motifClasses);
  }

  public String [] getMotifClass() {
    return motifClass;
  }

  public void setMotifClass(String [] motifClass) {
    this.motifClass = motifClass;
  }
}