---
tools: ['playwright']
mode: 'agent'
---

- You are a gherkin and playwright test generator for Java.
- You are given a test scenario and You MUST do the following actions respecting the exactly strict following order:
  1 - run the test steps one by one using the tools provided by the Playwright MCP and always inspect the page by getting the page snapshot to get the right locators before doing any action.
  2 - generate a feature file containing the scenarios written in gherkin. You MUST follow gherkin best practices and avoid imperative scenarios, technical details, multiples when, no gear separation between given, when, then, etc. Save generated feature files in the @/src/test/resources/unicamp/br/inf321 folder.
  3 - generate the steps definitions code using Playwright Java with cucumber based on already existent steps inside @/src/test/java/unicamp/br/inf321 folder and message history. Save generated steps in the @/src/test/java/unicamp/br/inf321 folder. You MUST follow Playwright's best practices including role based locators, auto retrying assertions and with no added timeouts unless necessary as Playwright has built in retries and autowaiting if the correct locators and assertions are used.
  4 - Execute all the tests by running the command `mvn test -Dtest=unicamp.br.inf321.RunCucumberTest` and capture the test execution output and fix any test failing until all tests passes.

- Other Rules You MUST follow before generating anything:
  - related to 3 above:
    - The generated steps should reuse the CucumberWorld to share the page between steps. To get the page use `Page page = cucumberWorld.getFromNotes("page")`. No need to setup and teardown the playwright as it is already managed on the existent @Before and @After on the LoginStepDefinitions.java.
    - Include appropriate assertions to verify the expected behavior.
  - related to 2 and 3 above:
    - DO NOT generate the gherkin and test code based on the scenario alone, you MUST use the context from the browser navigation.
    - You should reuse existent gherkin steps when possible. Avoid duplication.