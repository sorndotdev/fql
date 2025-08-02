package dev.sorn.fql.steps;

import dev.sorn.fql.FQLExpression;
import dev.sorn.fql.api.FQLError;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static dev.sorn.fql.FQLExpression.fqlExpression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FQLExpressionStepDefs {
    private FQLExpression parsedExpression;
    private Exception parsingException;

    @When("I parse the FQL expression {string}")
    public void iParseTheExpression(String expressionString) {
        try {
            parsedExpression = fqlExpression(expressionString);
            parsingException = null;
        } catch (Exception e) {
            parsedExpression = null;
            parsingException = e;
        }
    }

    @Then("no parse error should occur")
    public void noParseErrorShouldOccur() {
        if (parsingException != null) {
            parsingException.printStackTrace();
        }
        assertNull(parsingException, "Expected no parse error, but got: " + parsingException);
    }

    @And("the expression string should be {string}")
    public void theExpressionStringShouldBe(String expected) {
        assertNotNull(parsedExpression, "Parsed expression should not be null");
        assertEquals(expected, parsedExpression.toString());
    }

    @Then("an FQL error should occur")
    public void aParseErrorShouldOccur() {
        assertInstanceOf(FQLError.class, parsingException);
    }
}