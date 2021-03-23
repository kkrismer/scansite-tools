package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringencyValues implements Serializable {
  private static final long serialVersionUID = 1017403856716902570L;
  
  private String [] stringencyValue;

  public StringencyValues() {
  }    
  public StringencyValues(String[] stringencyValues) {
    this.stringencyValue = stringencyValues;
  }

  public String[] getStringencyValue() {
    return stringencyValue;
  }
  
  public void setStringencyValue(String[] stringencyValue) {
    this.stringencyValue = stringencyValue;
  }
}
