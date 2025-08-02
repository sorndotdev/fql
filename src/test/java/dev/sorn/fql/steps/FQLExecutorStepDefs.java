package dev.sorn.fql.steps;

import dev.sorn.fql.FQLExecutor;
import dev.sorn.fql.api.FQLResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static dev.sorn.fql.FQLExecutor.fqlExecutor;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FQLExecutorStepDefs {
    private FQLExecutor executor;
    private FQLResult result;

    @Given("I have a FQL executor")
    public void iHaveAFQLExecutor() {
        executor = fqlExecutor();
    }

    @When("I execute")
    public void iExecute() {
        result = executor.execute();
    }

    @Then("return FQL result")
    public void returnFQLResult() {
        assertNotNull(result);
    }
}