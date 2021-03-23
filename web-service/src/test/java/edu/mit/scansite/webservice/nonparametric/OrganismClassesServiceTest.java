package edu.mit.scansite.webservice.nonparametric;

import edu.mit.scansite.webservice.otherservices.OrganismClassesService;
import edu.mit.scansite.webservice.transferobjects.OrganismClasses;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/14/2017.
 */
public class OrganismClassesServiceTest {

    @Test
    public void organismClassesServiceTest() {
        OrganismClasses organismClasses = OrganismClassesService.getOrganismClasses();

        List<String> organismClassNames = new ArrayList<>();
        organismClassNames.add("Mammals");
        organismClassNames.add("Vertebrates");
        organismClassNames.add("Invertebrates");
        organismClassNames.add("Bacteria");
        organismClassNames.add("Fungi");
        organismClassNames.add("Viruses");
        organismClassNames.add("Plants");
        organismClassNames.add("Other");
        organismClassNames.add("All");

        int matches = 0;
        for (int i=0; i < organismClasses.getOrganismClasses().length; i++) {
            for (String organismClassName : organismClassNames) {
                if(organismClasses.getOrganismClasses()[i].equals(organismClassName)) {
                    matches++;
                }
            }
        }
        assert (matches == organismClassNames.size());
    }
}
