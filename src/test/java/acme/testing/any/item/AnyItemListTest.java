package acme.testing.any.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyItemListTest extends TestHarness {


	@ParameterizedTest
	@CsvFileSource(resources = "/any/item/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTestTools(final int recordIndex, final String name,final String code, final String technology,
		final String description, final String itemType, final String retailPrice, final String moreInfo ) {


		super.clickOnMenu("Anonymous","List published items");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, name);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, technology);


		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("technology", technology);
		super.checkInputBoxHasValue("description", description);  
		super.checkInputBoxHasValue("itemType", itemType); 
		super.checkInputBoxHasValue("retailPrice", retailPrice); 
		super.checkInputBoxHasValue("moreInfo", moreInfo);

	}
}