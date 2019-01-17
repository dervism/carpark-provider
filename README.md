# CarPark Provider

An example SparkJava-application showing how to write Pact Provider tests with JUnit and Maven and publish the verification results back to a Pact Broker. This is application is an example backend application for [CarParkJS](https://github.com/dervism/carparkjs).

The PactCarProviderTest class will start a local instance of the provider before running the Pact-verification. The provider is automatically stopped after the test has completed (regardless of the test result).

The project requires Maven version 3.6 or above. If you want build this project without manually installing Maven 3.6, then simply follow step 3 to build the project with MavenVM.

## Setup

1. You need a Pact Broker. You can register a free hosted Pact Broker here: https://pact.dius.com.au/, or you can spin up your own instance with Docker Compose. The instance will be available at http://localhost:80) without authentication.

    `$ cd docker-app && docker-compose up -d`

2. You need to export the broker url, and also username and password (only required if you use a hosted Pact Broker, or if your own Pact Broker is authenticated:

    ```$ export PACTBROKER_URL=http://localhost`
    $ export PACTBROKER_USERNAME=xxxxxxxxx && export PACTBROKER_PASSWORD=xxxxxxxxx```

3. Make sure you have Maven minimum version 3.6. If you don't, you can use Maven Version Manager (mvnvm) to automatically download and run it. First check your existing Maven version:

    `$ mvn -v`

If the version is less than 3.6.0, then get a copy of `mvnvm`:

    `$ mkdir -p ~/bin && curl -s https://bitbucket.org/mjensen/mvnvm/raw/master/mvn > ~/bin/mvn && chmod 0755 ~/bin/mvn`

4. You can now build and test the project:

    `$ mvn clean install`

If you use `mvnvm`, then use this command:

    $ ~/bin/mvn clean install

5. In order to automatically publish verification results, set the `pact.verifier.publishResults` environment variable to true:

    `$ mvn clean install -Dpact.verifier.publishResults=true`

Leave it unset, or set it to false, to disable automatic publishing of verification status to the Pact Broker. When building locally, leave it unset or set to false. When building in a build server, set it to true.
