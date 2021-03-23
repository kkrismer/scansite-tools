package edu.mit.scansite.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.model.Motif;
import edu.mit.scansite.sql.statements.GetMotifsStatement;

@Path("/motif-display-names")
public class ListMotifDisplayNamesResource {
	private List<String> motifDisplayNames = null;

	@Context
	ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> provideDataSourceDisplayNames() {
		if (motifDisplayNames == null || motifDisplayNames.isEmpty()) {
			parseMotifDisplayNames();
		}
		return motifDisplayNames;
	}

	private void parseMotifDisplayNames() {
		motifDisplayNames = new ArrayList<>();
		GetMotifsStatement statement = new GetMotifsStatement(false, true, false, false, false, false, false, false);

		List<Motif> motifs = statement.process();
		if (motifs != null) {
			motifs.forEach(entry -> motifDisplayNames.add(entry.getMotifDisplayName()));
		}
	}
}
