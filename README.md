# Demo Test Automation Project

This lightweight test automation project demonstrates API tests and mobile tests using:

- [Java 21](https://www.oracle.com/java/technologies/javase-downloads/21-downloads.html)
- [Gradle](https://gradle.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Allure Reports](https://allurereport.org/)
- [Selenide](https://selenide.org/)
- [Rest Assured](https://rest-assured.io/)

## Prerequisites

Before running the tests, ensure that you have the following installed:

1. [Java 21](https://www.oracle.com/java/technologies/javase-downloads/21-downloads.html)
2. [Appium](http://appium.io/)
    - Verify appium installation: `appium -v`
    - Install appium doctor: `npm install -g appium-doctor`
    - Run appium doctor for iOS: `appium-doctor --ios`
    - Fix errors, if any

## Execution Modes

- API tests are configured to run in parallel.
- Mobile tests are configured to run sequentially for demonstration purposes.

## How to Run

1. Start the Appium server:

```bash
appium
```

2. Execute the following commands to run the tests and generate Allure reports:

```bash
./gradlew clean test allureServe
```

## How to open test results

This project is configured to open allure report after test execution `ignoreFailures = true`. Should you require to
reopen results, run command

```bash
./gradlew allureServe
```

Note: this requires to have allure results. If you'd run command `./gradlew clean` history would be wiped and report
will be empty.

## Additional notes

#### Appium Server

- The Appium server is configured to start by default at `http://127.0.0.1:4723`.
    - This is the default URL for connecting to the Appium server during test execution.

#### iOS demo app

- This project uses an iOS [demo app provided by Sauce Labs](https://github.com/saucelabs/sample-app-mobile). The app
  source code can be found in the `apps` folder at the project root.

#### Sensitive data exposure

- For the sake of simplicity in this demo project, URIs and authentication token are stored inside
  the `src/main/resources/application.properties` file. Please note that in the real world this would not be an option
  and a proper mechanism would be used.


