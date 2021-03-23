package edu.mit.scansite.sql.statements;

import static edu.mit.scansite.sql.constants.GeneralConstants.AND;
import static edu.mit.scansite.sql.constants.GeneralConstants.FROM;
import static edu.mit.scansite.sql.constants.GeneralConstants.LIKE;
import static edu.mit.scansite.sql.constants.GeneralConstants.SELECT;
import static edu.mit.scansite.sql.constants.GeneralConstants.TABLE_PROTEINS_ENSEMBL_HUMAN;
import static edu.mit.scansite.sql.constants.GeneralConstants.TABLE_PROTEINS_ENSEMBL_MOUSE;
import static edu.mit.scansite.sql.constants.GeneralConstants.TABLE_PROTEINS_SWISSPROT;
import static edu.mit.scansite.sql.constants.GeneralConstants.TABLE_PROTEINS_YEAST;
import static edu.mit.scansite.sql.constants.GeneralConstants.WHERE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mit.scansite.model.Protein;
import edu.mit.scansite.sql.processing.ProcessingUtils;

public class GetProteinsStatement extends AbstractStatement {
	private Logger logger = LoggerFactory.getLogger(GetProteinsStatement.class);

	private String tableName; // is derived from dataSourceShortName
	private String sqlComparator; // '=' or 'LIKE'

	private boolean selectProteinIdentifier;
	private boolean selectTaxaId;
	private boolean selectProteinClass;
	private boolean selectProteinMolecularWeight;
	private boolean selectProteinPI;
	private boolean selectProteinPiPhos1;
	private boolean selectProteinPiPhos2;
	private boolean selectProteinPiPhos3;
	private boolean selectProteinSequence;

	private String proteinIdentifier;
	private Integer taxaId;
	private String proteinClass;
	private Double proteinMolecularWeight;
	private Double proteinPI;
	private Double proteinPiPhos1;
	private Double proteinPiPhos2;
	private Double proteinPiPhos3;
	private String proteinSequence;

	private static final String COLUMN_PROTEIN_IDENTIFIER = "proteinsIdentifier";
	private static final String COLUMN_TAXA_ID = "taxaId";
	private static final String COLUMN_PROTEIN_CLASS = "proteinsClass";
	private static final String COLUMN_PROTEIN_MOL_WEIGHT = "proteinsMolecularWeight";
	private static final String COLUMN_PROTEIN_PI = "proteinsPI";
	private static final String COLUMN_PROTEIN_PI_PHOS1 = "proteinsPiPhos1";
	private static final String COLUMN_PROTEIN_PI_PHOS2 = "proteinsPiPhos2";
	private static final String COLUMN_PROTEIN_PI_PHOS3 = "proteinsPiPhos3";
	private static final String COLUMN_PROTEIN_SEQUENCE = "proteinsSequence";

	public GetProteinsStatement(String dataSourceShortName, boolean selectProteinIdentifier, boolean selectTaxaId,
			boolean selectProteinClass, boolean selectProteinMolecularWeight, boolean selectProteinPI,
			boolean selectProteinPiPhos1, boolean selectProteinPiPhos2, boolean selectProteinPiPhos3,
			boolean selectProteinSequence) {
		super();
		init(dataSourceShortName, sqlComparator, selectProteinIdentifier, selectTaxaId, selectProteinClass,
				selectProteinMolecularWeight, selectProteinPI, selectProteinPiPhos1, selectProteinPiPhos2,
				selectProteinPiPhos3, selectProteinSequence, null, null, null, null, null, null, null, null, null);

		initTemplate();
		prepareStatement();
	}

	public GetProteinsStatement(String dataSourceShortName, String sqlComparator, boolean selectProteinIdentifier,
			boolean selectTaxaId, boolean selectProteinClass, boolean selectProteinMolecularWeight,
			boolean selectProteinPI, boolean selectProteinPiPhos1, boolean selectProteinPiPhos2,
			boolean selectProteinPiPhos3, boolean selectProteinSequence, String proteinIdentifier, Integer taxaId,
			String proteinClass, Double proteinMolecularWeight, Double proteinPI, Double proteinPiPhos1,
			Double proteinPiPhos2, Double proteinPiPhos3, String proteinSequence) {
		super();
		init(dataSourceShortName, sqlComparator, selectProteinIdentifier, selectTaxaId, selectProteinClass,
				selectProteinMolecularWeight, selectProteinPI, selectProteinPiPhos1, selectProteinPiPhos2,
				selectProteinPiPhos3, selectProteinSequence, proteinIdentifier, taxaId, proteinClass,
				proteinMolecularWeight, proteinPI, proteinPiPhos1, proteinPiPhos2, proteinPiPhos3, proteinSequence);

		initTemplate();
		prepareStatement();
	}

	private void init(String dataSourceShortName, String sqlComparator, boolean selectProteinIdentifier,
			boolean selectTaxaId, boolean selectProteinClass, boolean selectProteinMolecularWeight,
			boolean selectProteinPI, boolean selectProteinPiPhos1, boolean selectProteinPiPhos2,
			boolean selectProteinPiPhos3, boolean selectProteinSequence, String proteinIdentifier, Integer taxaId,
			String proteinClass, Double proteinMolecularWeight, Double proteinPI, Double proteinPiPhos1,
			Double proteinPiPhos2, Double proteinPiPhos3, String proteinSequence) {
		this.tableName = deriveTableName(dataSourceShortName);
		this.sqlComparator = sqlComparator;
		this.selectProteinIdentifier = selectProteinIdentifier;
		this.selectTaxaId = selectTaxaId;
		this.selectProteinClass = selectProteinClass;
		this.selectProteinMolecularWeight = selectProteinMolecularWeight;
		this.selectProteinPI = selectProteinPI;
		this.selectProteinPiPhos1 = selectProteinPiPhos1;
		this.selectProteinPiPhos2 = selectProteinPiPhos2;
		this.selectProteinPiPhos3 = selectProteinPiPhos3;
		this.selectProteinSequence = selectProteinSequence;
		this.proteinIdentifier = proteinIdentifier;
		this.taxaId = taxaId;
		this.proteinClass = proteinClass;
		this.proteinMolecularWeight = proteinMolecularWeight;
		this.proteinPI = proteinPI;
		this.proteinPiPhos1 = proteinPiPhos1;
		this.proteinPiPhos2 = proteinPiPhos2;
		this.proteinPiPhos3 = proteinPiPhos3;
		this.proteinSequence = proteinSequence;
	}

	@Override
	protected void initTemplate() {
		String selectedColumns = selectProteinIdentifier ? COLUMN_PROTEIN_IDENTIFIER + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectTaxaId ? COLUMN_TAXA_ID + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinClass ? COLUMN_PROTEIN_CLASS + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinMolecularWeight ? COLUMN_PROTEIN_MOL_WEIGHT + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinPI ? COLUMN_PROTEIN_PI + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinPiPhos1 ? COLUMN_PROTEIN_PI_PHOS1 + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinPiPhos2 ? COLUMN_PROTEIN_PI_PHOS2 + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinPiPhos3 ? COLUMN_PROTEIN_PI_PHOS3 + SEPARATOR : StringUtils.EMPTY;
		selectedColumns += selectProteinSequence ? COLUMN_PROTEIN_SEQUENCE + SEPARATOR : StringUtils.EMPTY;

		if (selectedColumns.endsWith(SEPARATOR)) {
			selectedColumns = selectedColumns.substring(0, selectedColumns.lastIndexOf(SEPARATOR)); // idx-1?
		}

		String conditions = handleStringCondition(StringUtils.EMPTY, proteinIdentifier, COLUMN_PROTEIN_IDENTIFIER);

		if (taxaId != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_TAXA_ID));
			parameters.add(Arrays.asList(String.valueOf(taxaId), "Integer"));
		}

		conditions = handleStringCondition(conditions, proteinClass, COLUMN_PROTEIN_CLASS);

		if (proteinMolecularWeight != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_PROTEIN_MOL_WEIGHT));
			parameters.add(Arrays.asList(String.valueOf(proteinMolecularWeight), "Double"));
		}
		if (proteinPI != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_PROTEIN_PI));
			parameters.add(Arrays.asList(String.valueOf(proteinPI), "Double"));
		}
		if (proteinPiPhos1 != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_PROTEIN_PI_PHOS1));
			parameters.add(Arrays.asList(String.valueOf(proteinPiPhos1), "Double"));
		}
		if (proteinPiPhos2 != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_PROTEIN_PI_PHOS2));
			parameters.add(Arrays.asList(String.valueOf(proteinPiPhos2), "Double"));
		}
		if (proteinPiPhos3 != null) {
			conditions = utils.addCondition(conditions, AND, String.format("%s = ?", COLUMN_PROTEIN_PI_PHOS3));
			parameters.add(Arrays.asList(String.valueOf(proteinPiPhos3), "Double"));
		}
		conditions = handleStringCondition(conditions, proteinSequence, COLUMN_PROTEIN_SEQUENCE);

		if (StringUtils.isBlank(conditions)) {
			stmtTemplate = String.format("%s %s %s %s", SELECT, selectedColumns, FROM, tableName);
		} else {
			stmtTemplate = String.format("%s %s %s %s %s %s", SELECT, selectedColumns, FROM, tableName, WHERE,
					conditions);
		}
	}

	private String handleStringCondition(String conditions, String value, String column) {
		if (value != null) {
			if (!StringUtils.isBlank(sqlComparator) && sqlComparator.equalsIgnoreCase(LIKE)) {
				conditions = utils.addCondition(conditions, AND, String.format("%s LIKE ?", column));
			} else {
				conditions = utils.addCondition(conditions, AND, String.format("%s = ?", column));
			}
			parameters.add(Arrays.asList(value, "String"));
		}
		return conditions;
	}

	@Override
	protected void prepareStatement() {
		stmt = utils.getStatement(connection, stmtTemplate, parameters);
	}

	@Override
	public List<Protein> process() {
		try {
			ResultSet rs = stmt.executeQuery();
			List<Protein> proteins = new ArrayList<>();

			while (rs.next()) {
				Protein p = new Protein();
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_IDENTIFIER)) {
					p.setProteinIdentifier(rs.getString(COLUMN_PROTEIN_IDENTIFIER));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_TAXA_ID)) {
					p.setTaxaId(rs.getInt(COLUMN_TAXA_ID));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_CLASS)) {
					p.setProteinClass(rs.getString(COLUMN_PROTEIN_CLASS));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_MOL_WEIGHT)) {
					p.setProteinMolecularWeight(rs.getDouble(COLUMN_PROTEIN_MOL_WEIGHT));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_PI)) {
					p.setProteinPI(rs.getDouble(COLUMN_PROTEIN_PI));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_PI_PHOS1)) {
					p.setProteinPiPhos1(rs.getDouble(COLUMN_PROTEIN_PI_PHOS1));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_PI_PHOS2)) {
					p.setProteinPiPhos2(rs.getDouble(COLUMN_PROTEIN_PI_PHOS2));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_PI_PHOS3)) {
					p.setProteinPiPhos3(rs.getDouble(COLUMN_PROTEIN_PI_PHOS3));
				}
				if (ProcessingUtils.hasColumn(rs, COLUMN_PROTEIN_SEQUENCE)) {
					p.setProteinSequence(rs.getString(COLUMN_PROTEIN_SEQUENCE));
				}

				proteins.add(p);
			}

			return proteins;
		} catch (SQLException e) {
			logger.error("Could not retrieve DataSource values from database!", e);
		}
		return null;
	}

	private String deriveTableName(String dataSourceShortName) {
		switch (dataSourceShortName) {
		case "swissprot":
			return TABLE_PROTEINS_SWISSPROT;
		case "ensemblHuman":
			return TABLE_PROTEINS_ENSEMBL_HUMAN;
		case "ensemblMouse":
			return TABLE_PROTEINS_ENSEMBL_MOUSE;
		case "yeast":
			return TABLE_PROTEINS_YEAST;
		}
		return null;
	}
}
