package acme.testing.authenticated.announcement;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuthenticatedAnnouncementCreateTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/announcement/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String title, final String body, final String critical, final String confirmation, final String moreInfo) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Create announcement");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("confirmation", confirmation);
		
		super.clickOnSubmit("Register");
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/announcement/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negative(final int recordIndex, final String title, final String body, final String critical,  final String confirmation,final String moreInfo) {
		super.signIn("administrator", "administrator");

		super.clickOnMenu("Administrator", "Create announcement");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("confirmation", confirmation);
		
		super.clickOnSubmit("Register");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
