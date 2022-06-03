package acme.testing.patron.patronage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronagePublishServiceTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-publish.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventor) {
		
		super.signIn("patron2", "patron2");

		super.clickOnMenu("Patron", "List my patronages");
		super.sortListing(1, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Publish");
		super.clickOnMenu("Patron", "List my patronages");
		super.checkNotErrorsExist();

		
		super.signOut();


	}
}