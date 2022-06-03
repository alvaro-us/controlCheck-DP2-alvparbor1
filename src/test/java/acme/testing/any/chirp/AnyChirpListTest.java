package acme.testing.any.chirp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TemporalAwareTestHarness;

public class AnyChirpListTest extends  TemporalAwareTestHarness  {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/chirp.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final int deltaDays, final String title, final String author, final String body, final String email) {
		String moment;
		super.clickOnMenu("Anonymous", "List all chirps");
		super.checkListingExists();
		super.sortListing(0, "desc");
		
		moment = super.computeDeltaMoment(deltaDays);
		
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, author);
		super.checkColumnHasValue(recordIndex, 3, body);
		super.checkColumnHasValue(recordIndex, 4, email);




	}

	// Ancillary methods ------------------------------------------------------

	
	
	
}
