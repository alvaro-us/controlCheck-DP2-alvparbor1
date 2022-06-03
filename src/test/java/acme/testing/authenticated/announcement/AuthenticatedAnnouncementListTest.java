package acme.testing.authenticated.announcement;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TemporalAwareTestHarness;

public class AuthenticatedAnnouncementListTest extends TemporalAwareTestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/announcement/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex,final int deltaDays, final String title, final String body, final String critical, final String moreInfo) {
		super.signIn("administrator", "administrator");
		String moment;
		super.clickOnMenu("Authenticated", "List announcements");
		super.checkListingExists();
		super.sortListing(0, "desc");
		
		moment = super.computeDeltaMoment(deltaDays);
		
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, body);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("creationMoment", moment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("critical", critical);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		
		super.signOut();

	}
}
