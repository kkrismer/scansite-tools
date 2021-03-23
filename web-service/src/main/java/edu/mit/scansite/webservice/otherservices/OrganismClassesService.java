package edu.mit.scansite.webservice.otherservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.shared.transferobjects.OrganismClass;
import edu.mit.scansite.webservice.WebService;
import edu.mit.scansite.webservice.transferobjects.OrganismClasses;

/**
 * @author Tobieh
 * @author Thomas Bernwinkler
 */

@Path("/organismclasses")
public class OrganismClassesService extends WebService {
    /**
     * @return An object containing all valid organism classes.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public static OrganismClasses getOrganismClasses() {
        OrganismClass[] ocs = OrganismClass.values();
        String[] oClasses = new String[ocs.length];
        for (int i = 0; i < ocs.length; ++i) {
            oClasses[i] = ocs[i].toString();
        }
        return new OrganismClasses(oClasses);
    }
}
