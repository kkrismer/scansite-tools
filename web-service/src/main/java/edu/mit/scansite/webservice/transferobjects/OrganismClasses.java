package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrganismClasses implements Serializable {
  private static final long serialVersionUID = 2487417078945193564L;
  
  private String [] organismClass;
  
  public OrganismClasses() {
  }
  
  public OrganismClasses(String[] organismClasses) {
    this.organismClass = organismClasses;
  }
  
  public String[] getOrganismClasses() {
    return organismClass;
  }
  
  public void setOrganismClasses(String[] organismClasses) {
    this.organismClass = organismClasses;
  }
}
