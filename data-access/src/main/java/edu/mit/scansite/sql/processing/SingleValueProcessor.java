//package edu.mit.scansite.sql.processing;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static edu.mit.scansite.sql.constants.GeneralConstants.PROTEIN_IDENTIFIER;
//
//public class SingleValueProcessor {
//    private Logger logger = LoggerFactory.getLogger(SingleValueProcessor.class);
//
//    public List<String> process(PreparedStatement stmt) {
//        try {
//            ResultSet rs = stmt.executeQuery();
//            List<String> values = new ArrayList<>();
//
//            while (rs.next()) {
//                if (ProcessingUtils.hasColumn(rs, PROTEIN_IDENTIFIER)) {
//                    values.add(rs.getString(PROTEIN_IDENTIFIER));
//                }
//            }
//
//            return values;
//        } catch (SQLException e) {
//            logger.error("Could not retrieve Motif values from database!", e);
//        }
//        return null;
//    }
//}
