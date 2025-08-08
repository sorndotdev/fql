package dev.sorn.fql.steps;

import dev.sorn.fql.api.Expression;
import dev.sorn.fql.api.FQLError;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static dev.sorn.fql.api.Expression.expression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExpressionStepDefs {
    private String rawExpression;
    private Expression parsedExpression;
    private Exception parsingException;

    @Given("an expression {string}")
    public void an_expression(String expression) {
        this.rawExpression = expression;
    }

    @When("I parse the expression")
    public void iParseTheExpression() {
        try {
            parsedExpression = expression(rawExpression);
            parsingException = null;
        } catch (Exception e) {
            parsedExpression = null;
            parsingException = e;
        }
    }

    @Then("an FQL error should occur")
    public void aParseErrorShouldOccur() {
        assertInstanceOf(FQLError.class, parsingException);
    }

    @Then("no parse error should occur")
    public void noParseErrorShouldOccur() {
        assertNull(parsingException, "Expected no parse error, but got: " + parsingException);
    }

    @And("the expression string should be {string}")
    public void theExpressionStringShouldBe(String expected) {
        assertNotNull(parsedExpression, "Parsed expression should not be null");
        assertEquals(expected, parsedExpression.toString());
    }
}