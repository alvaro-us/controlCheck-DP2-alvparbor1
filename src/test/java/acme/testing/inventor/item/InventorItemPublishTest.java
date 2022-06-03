package acme.testing.inventor.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorItemPublishTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String name) {
		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, name);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();
 
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/item/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String name, final String code, final String technology, final String description, final String itemType, final String retailPrice, final String moreInfo) {

		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(2, "desc");

		super.checkColumnHasValue(recordIndex, 0, name);
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("technology", technology);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("itemType", itemType);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("moreInfo", moreInfo);
		
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		

		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingTest() {
		// a) Publish an item without the Inventor role
		// b) Publish an item which is already published
		// c) Publish an item of other inventor
	}

	// Ancillary methods ------------------------------------------------------

}
