package edu.mit.scansite.webservice.otherservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.shared.transferobjects.HistogramStringency;
import edu.mit.scansite.webservice.WebService;
import edu.mit.scansite.webservice.transferobjects.StringencyValues;

@Path("/stringency")
public class StringencyValuesService extends WebService {
  /**
   * @return An object containing all valid stringency values.
   */
  @GET
  @Produces( { MediaType.APPLICATION_XML })
  public static StringencyValues getStringencyValues() {
    HistogramStringency[] stringencies = HistogramStringency.values();
    String [] strs = new String[stringencies.length];
    for (int i = 0; i < stringencies.length; ++i) {
      strs[i] = stringencies[i].getName();
    }
    return new StringencyValues(strs);
  }
}
