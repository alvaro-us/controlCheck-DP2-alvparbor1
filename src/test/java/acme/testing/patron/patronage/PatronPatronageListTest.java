package acme.testing.patron.patronage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageListTest  extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-list.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex,final String status, final String code, final String legalStuff,
		final String creationDate, final String published) {
		super.signIn("patron2", "patron2");
	
		super.clickOnMenu("Patron", "List my patronages");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.checkColumnHasValue(recordIndex, 0, status);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, legalStuff);
		super.checkColumnHasValue(recordIndex, 3, creationDate);
		super.checkColumnHasValue(recordIndex, 4, published);
	}

}
