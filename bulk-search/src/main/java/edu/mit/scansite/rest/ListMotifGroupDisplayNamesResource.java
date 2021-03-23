package edu.mit.scansite.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.model.MotifGroup;
import edu.mit.scansite.sql.statements.GetMotifGroupsStatement;

@Path("/motif-group-display-names")
public class ListMotifGroupDisplayNamesResource {
	private List<String> motifGroupDisplayNames = null;

	@Context
	ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> provideDataSourceDisplayNames() {
		if (motifGroupDisplayNames == null || motifGroupDisplayNames.isEmpty()) {
			parseMotifDisplayNames();
		}
		return motifGroupDisplayNames;
	}

	private void parseMotifDisplayNames() {
		motifGroupDisplayNames = new ArrayList<>();
		GetMotifGroupsStatement statement = new GetMotifGroupsStatement(false, true, false);
		List<MotifGroup> motifGroups = statement.process();
		if (motifGroups != null) {
			motifGroups.forEach(entry -> motifGroupDisplayNames.add(entry.getMotifGroupDisplayName()));
		}
	}
}
