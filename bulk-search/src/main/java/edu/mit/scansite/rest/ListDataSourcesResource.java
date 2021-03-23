package edu.mit.scansite.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.mit.scansite.model.DataSource;
import edu.mit.scansite.sql.statements.GetDataSourcesStatement;

@Path("/data-source-display-names")
public class ListDataSourcesResource {
	private List<String> dataSourceNames = null;
	private static final int DATA_SOURCE_TYPE_PROTEIN = 1;

	@Context
	ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> provideDataSourceDisplayNames() {
		if (dataSourceNames == null || dataSourceNames.isEmpty()) {
			parseDataSources(DATA_SOURCE_TYPE_PROTEIN);
		}
		return dataSourceNames;
	}

	private void parseDataSources(int dataSourceType) {
		dataSourceNames = new ArrayList<>();

		GetDataSourcesStatement statement = new GetDataSourcesStatement(false, false, false, false, true, false, false,
				false, false, null, dataSourceType, null, null, null, null, null, null, null);

		List<DataSource> ds = statement.process();
		if (ds != null) {
			ds.forEach(entry -> dataSourceNames.add(entry.getDataSourceDisplayName()));
		}
	}
}
