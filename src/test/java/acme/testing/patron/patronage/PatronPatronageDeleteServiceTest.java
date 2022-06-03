package acme.testing.patron.patronage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageDeleteServiceTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	
	public void positiveTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventor) {
		
		super.signIn("patron1", "patron1");
		super.clickOnMenu("Patron", "List my patronages");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("legalStuff", legalStuff);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate",startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Create");
		super.clickOnMenu("Patron", "List my patronages");

		super.sortListing(1, "asc");
		super.clickOnListingRecord(0);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		
		super.signOut();
	
		
		
	}

}
