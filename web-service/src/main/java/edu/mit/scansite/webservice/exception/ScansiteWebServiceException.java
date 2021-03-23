package edu.mit.scansite.webservice.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScansiteWebServiceException extends WebApplicationException {
  private static final long serialVersionUID = 7462125211501837018L;

  public ScansiteWebServiceException() {
  }

  public ScansiteWebServiceException(String message) {
    super(Response.status(400)
        .entity("<error>" + message + "</error>").type(MediaType.TEXT_XML).build());
  }
  
}
