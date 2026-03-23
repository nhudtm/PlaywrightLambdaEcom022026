# Playwright Lambda Ecommerce Automation (Java + TestNG)

![Playwright Tests](https://github.com/nhudtm/PlaywrightLambdaEcom022026/actions/workflows/ui-tests.yml/badge.svg)

UI automation framework for LambdaTest Ecommerce Playground using **Playwright (Java)**, **TestNG**, and **Maven**.

## Tech Stack
- Java 17
- Maven
- Playwright `1.58.0`
- TestNG `7.10.2`
- ExtentReports `5.0.8`
- Jira integration — automatically creates a Jira bug ticket when a test fails

## Project Structure
```text
src/main/java
  commons/            # Base test, factory, constants
  pageObjects/        # Page Object Model classes
  listener/           # Extent listener
  jiraConfig/         # Jira integration/listener
  utils/              # Config utilities

src/test/java
  auth/               # Auth state generation + auth tests
  tests/              # Test suites by feature

src/test/resources
  config/config.properties
  testrunner/*.xml    # TestNG suite files

extentReport/         # HTML report output
screenshots/          # Failure/pass screenshots from listener
```

## Prerequisites
- JDK 17 installed
- Maven 3.8+ installed
- Internet access for Playwright browser binaries and target app

Check versions:
```bash
java -version
mvn -version
```

## Setup
1. Clone the repository.
2. Install dependencies:
   ```bash
   mvn -q -DskipTests clean compile
   ```
3. Install Playwright browsers:
```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```
4. Update test configuration in:
   - `src/test/resources/config/config.properties`

> The config file can contain secrets (Jira/API credentials). Keep it local and never commit real values.

## Running Tests
Default run (uses `runOnDEV.xml` through Maven Surefire):
```bash
mvn test
```

Run with another environment selector (mapped to `runOn${environment}.xml`):
```bash
mvn test -Denvironment=DEV
```

Run a specific TestNG suite directly:
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/parallel.xml
```

Run the full TEST suite (Chrome + Firefox, parallel):
```bash
mvn test -Denvironment=TEST
```

## CI/CD (GitHub Actions)
Workflow file: `.github/workflows/ui-tests.yml`

### Trigger
- On push to `main`/`master`
- On pull request
- Manual trigger (`workflow_dispatch`)

### What the pipeline does
- Sets up JDK 17 with Maven cache
- Builds `src/test/resources/config/config.properties` from GitHub Secrets (runtime only)
- Installs Playwright browsers and dependencies
- Runs TestNG suite via `mvn -Denvironment=TEST test`
- Uploads artifacts (`target/surefire-reports`, `extentReport`, `screenshots`) even on failure

### Required GitHub Secrets
- `APP_DEV_URL`
- `TEST_EMAIL`
- `TEST_PASSWORD`

### Optional GitHub Secrets (Jira/BrowserStack)
- `JIRA_SITE_URL`
- `JIRA_USERNAME`
- `JIRA_API_KEY`
- `JIRA_PROJECT_KEY`
- `BROWSERSTACK_USERNAME`
- `BROWSERSTACK_ACCESS_KEY`

### Security Notes
- Do not commit real credentials to the repository.
- The framework now supports environment-variable based config resolution (env/system properties first, then local file fallback).
- Browser headless mode is CI-friendly via `HEADLESS=true`.

## Reports
After execution:
- Extent HTML report: `extentReport/TestExecutionReport.html`
- TestNG reports: `target/surefire-reports/`
- Screenshots: `screenshots/`

## Test Coverage (Demo)
Implemented demo test cases are currently grouped as below:

- **Auth State**
  - Generate browser-specific login state before execution (`auth.AuthStateTest` + `auth.AuthStateGenerator`).
  - Storage state files are created under `target/storage-states/`.

- **Home** (`tests.home.HomeTC`)
  - `TC01` Verify home page title.
  - `TC02` Verify navigation to login page.
  - `TC03` View product details from top products.
  - `TC04` Add product to cart from home page.
  - `TC05` Quick view action.
  - `TC06` Add to wishlist without login (alert validation).
  - `TC07` Add product to compare.

- **Home (Auto Login Flow)** (`tests.home.HomeTC_WithAutoLogin`)
  - Validate wishlist action message using pre-generated auth state.

- **Account - Login** (`tests.account.LoginTC`)
  - Invalid email formats (DataProvider-based).
  - Blank credentials / blank password.
  - Case-sensitive email handling.
  - Valid login success.
  - Non-existing email.
  - Invalid password.
  - Exceeded login attempts warning.

- **Account - Forgot Password** (`tests.account.ForgotPasswordTC`)
  - Reset with valid email.
  - Blank email.
  - Email with spaces.
  - Uppercase email.
  - Invalid email format.
  - Non-existing email.
  - Cancel/back flow.

## Known Limitations / Work In Progress
- [ ] Allure Reports integration
- [ ] Data-driven tests with external data files

## Notes
- The framework reads URL keys from TestNG parameters (for example `devUrl`) and resolves them from `config.properties`.
- Browsers are passed by TestNG parameter `browserName` in suite XML.
- Current default suite (`runOnDEV.xml`) runs tests in parallel at test level.

## Quick Command Summary
```bash
# Compile only
mvn clean compile

# Execute default suite
mvn test

# Execute explicit suite
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/runOnDEV.xml
```
