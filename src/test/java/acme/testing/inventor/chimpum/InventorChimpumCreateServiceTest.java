package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateServiceTest extends TestHarness {
	
	@ParameterizedTest
	@Order(10)
	@CsvFileSource(resources = "/inventor/chimpum/chimpum-create.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void positiveTest(final int recordIndex, final String pattern,final String title, final String description,final String budget
		,final String startDate,final String endDate, final String moreInfo){
		
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my items");
		super.sortListing(2, "asc");
		super.clickOnListingRecord(0);
		
		super.clickOnButton("Create chimpum");
		super.fillInputBoxIn("pattern", pattern);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate",startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Create");
		
		super.clickOnMenu("Inventor", "List my chimpums");

		super.sortListing(1, "asc");
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, budget);
		super.checkColumnHasValue(recordIndex, 3, startDate);
		super.checkColumnHasValue(recordIndex, 4, endDate);

		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/chimpum-create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void negativeTest(final int recordIndex,final String pattern, final String title, final String description,final String budget
		,final String startDate,final String endDate, final String moreInfo) {
		
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my items");
		super.sortListing(2, "asc");
		super.clickOnListingRecord(0);
		
		super.clickOnButton("Create chimpum");
		super.fillInputBoxIn("pattern", pattern);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("startDate",startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		
		super.signOut();
	}

}
