package no.dervis.pact;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import spark.Spark;

@Provider("carpark-provider")
@PactBroker(
        protocol = "http",
        host = "${PACTBROKER_URL:localhost}",
        authentication = @PactBrokerAuth(username = "${PACTBROKER_USERNAME:demo}", password = "${PACTBROKER_PASSWORD:demo}"),
        port = "80")
@IgnoreNoPactsToVerify
class PactCarProviderTest {

    private static CarController carController;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeAll
    public static void beforeAll() {
        carController = new CarController();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 9999, "/"));
    }

    @AfterAll
    public static void after() {
        Spark.stop();
    }

    @State("i have a list of cars")
    public void verifyPersonExists() {
        System.out.println("A car must exist");
        carController.addCar(new Car("car", "AB12345"));
    }


}
