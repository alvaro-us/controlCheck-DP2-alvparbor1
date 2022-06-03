package acme.testing.patron.patronage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageUpdateServiceTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-update.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventor) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my patronages");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("legalStuff", legalStuff);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate",startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Update");
		super.checkNotErrorsExist();
		super.clickOnMenu("Patron", "List my patronages");

		super.signOut();


	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventor) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my patronages");
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("legalStuff", legalStuff);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate",startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.clickOnMenu("Patron", "List my patronages");

		
		super.signOut();


	}


}
