package edu.mit.scansite.rest;

import static edu.mit.scansite.sql.constants.GeneralConstants.LIKE;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import edu.mit.scansite.model.DataSource;
import edu.mit.scansite.model.Protein;
import edu.mit.scansite.sql.statements.GetDataSourcesStatement;
import edu.mit.scansite.sql.statements.GetProteinsStatement;

@Path("/protein-identifiers")
public class ProteinIdentifierResource {
	private List<String> identifiers;

	@Context
	ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> provideDataSourceDisplayNames(@QueryParam("dataSource") String dataSourceName,
			@QueryParam("searchPattern") String searchPattern) {
		identifiers = new ArrayList<>();
		if (!StringUtils.isBlank(dataSourceName) && !StringUtils.isBlank(searchPattern)) {
			final int dataSourceTypeId = 1;
			GetDataSourcesStatement dsStmt = new GetDataSourcesStatement(false, false, false, true, true, false, false,
					false, false, null, dataSourceTypeId, null, null, null, null, null, null, null);

			List<DataSource> dataSources = dsStmt.process();
			dataSourceName = mapDataSourceShortName(dataSources, dataSourceName);

			if (StringUtils.isBlank(dataSourceName)) {
				return identifiers;
			}

			// SELECT, PROTEIN_IDENTIFIER, FROM, talbeNamePlaceHolder, WHERE,
			// PROTEIN_IDENTIFIER, LIKE ...
			final String partialIdentifier = "%" + searchPattern + "%";
			GetProteinsStatement statement = new GetProteinsStatement(dataSourceName, LIKE, true, false, false, false,
					false, false, false, false, false, partialIdentifier, null, null, null, null, null, null, null,
					null);

			List<Protein> proteins = statement.process();
			if (proteins != null) {
				proteins.forEach(entry -> identifiers.add(entry.getProteinIdentifier()));
			}

		}

		return identifiers;
	}

	private String mapDataSourceShortName(List<DataSource> dataSources, String displayName) {
		for (DataSource dataSource : dataSources) {
			if (dataSource.getDataSourceDisplayName().equals(displayName)) {
				return dataSource.getDataSourceShortName();
			}
		}
		return null;
	}

	// private AvailableStatements mapDataSourceName(String dataSourceName) {
	// final String tablePrefix = "proteins_";
	// if
	// (dataSourceName.equalsIgnoreCase(TABLE_PROTEINS_ENSEMBL_HUMAN.substring(tablePrefix.length())))
	// {
	// return AvailableStatements.PROTEIN_IDENTIFIER_STATEMENT_ENSEMBL_HUMAN;
	// }
	// if
	// (dataSourceName.equalsIgnoreCase(TABLE_PROTEINS_ENSEMBL_MOUSE.substring(tablePrefix.length())))
	// {
	// return AvailableStatements.PROTEIN_IDENTIFIER_STATEMENT_ENSEMBL_MOUSE;
	// }
	// if
	// (dataSourceName.equalsIgnoreCase(TABLE_PROTEINS_SWISSPROT.substring(tablePrefix.length())))
	// {
	// return AvailableStatements.PROTEIN_IDENTIFIER_STATEMENT_SWISSPROT;
	// }
	// if
	// (dataSourceName.equalsIgnoreCase(TABLE_PROTEINS_YEAST.substring(tablePrefix.length())))
	// {
	// return AvailableStatements.PROTEIN_IDENTIFIER_STATEMENT_YEAST;
	// }
	// return null;
	// }

}
