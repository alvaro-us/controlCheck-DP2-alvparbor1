package acme.testing.any.chirp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyChirpCreateTest extends TestHarness {
	@ParameterizedTest
    @CsvFileSource(resources = "/any/chirp/positive-create.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void positiveTest(final int recordIndex, final String title, final String author, final String body,
        final String email, final String confirm) {

        super.clickOnMenu("Anonymous","List all chirps");
        super.checkListingExists();
        super.clickOnButton("New Chirp");

        super.fillInputBoxIn("title", title);
        super.fillInputBoxIn("author", author);
        super.fillInputBoxIn("body", body);
        super.fillInputBoxIn("email", email);
        super.fillInputBoxIn("confirm", confirm);
        super.clickOnSubmit("Create Chirp");

        super.checkListingExists();
        super.sortListing(0, "desc");
        super.checkColumnHasValue(recordIndex, 1, title);
        super.checkColumnHasValue(recordIndex, 2, author);
        super.checkColumnHasValue(recordIndex, 3, body);
        super.checkColumnHasValue(recordIndex, 4, email);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/any/chirp/negative-create.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void negativeTest(final int recordIndex, final String title, final String author, final String body, final String email, 
        final String confirm) {

        super.clickOnMenu("Anonymous", "List all chirps");
        super.checkListingExists();
        super.clickOnButton("New Chirp");

        super.fillInputBoxIn("title",title);
        super.fillInputBoxIn("author",author);
        super.fillInputBoxIn("body",body);
        super.fillInputBoxIn("email",email);
        super.fillInputBoxIn("confirm", confirm);
        super.clickOnSubmit("Create Chirp");

        super.checkErrorsExist();

    }
}
