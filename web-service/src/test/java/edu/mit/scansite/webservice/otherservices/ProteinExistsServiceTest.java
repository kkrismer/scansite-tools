package edu.mit.scansite.webservice.otherservices;

import static edu.mit.scansite.webservice.ScansiteTestUtils.dsShortName;
import static edu.mit.scansite.webservice.ScansiteTestUtils.proteinIdentifier;

import org.junit.Test;

/**
 * @author Thomas Bernwinkler
 *
 */
public class ProteinExistsServiceTest {

	@Test
	public void proteinExistsServiceTest() {
		edu.mit.scansite.webservice.transferobjects.BooleanResult result;
		result = ProteinExistsService.isProteinInDatabase(proteinIdentifier, dsShortName);

		assert (result.getIsSuccess());
	}
}
