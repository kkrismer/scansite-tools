package edu.mit.scansite.webservice.transferobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BooleanResult implements Serializable {

  private static final long serialVersionUID = -3592116278953278586L;

  private Boolean isSuccess = false;

  public BooleanResult() {
  }
  
  public BooleanResult(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
  
  public Boolean getIsSuccess() {
    return isSuccess;
  }
  
  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
}
