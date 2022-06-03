package acme.testing.patron.patronage;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PatronPatronageCreateServiceTest extends TestHarness {
	
	@ParameterizedTest
	@Order(10)
	@CsvFileSource(resources = "/patron/patronage/patronage-create.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventorId) {
		
		
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
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("legalStuff",legalStuff);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("startDate",startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("moreInfo", moreInfo);

		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/patron/patronage/patronage-create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex, final String code, final String legalStuff, 
		final String budget, final String startDate, final String endDate,final String moreInfo, final String inventorId) {
		
		
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
		super.checkErrorsExist();

		
		super.signOut();
	}

}
