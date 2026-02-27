package jiraConfig;

import commons.GlobalConstants;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class JiraListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        JiraCreateIssue annotation = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(JiraCreateIssue.class);

        if (annotation != null && annotation.isCreateIssue()) {
            JiraServiceProvider jira = new JiraServiceProvider(
                    GlobalConstants.JIRA_SITE_URL,
                    GlobalConstants.JIRA_USERNAME,
                    GlobalConstants.JIRA_API_KEY,
                    GlobalConstants.JIRA_PROJECT_KEY
            );

            String issueSummary = result.getMethod().getMethodName() + " Failed in Automation Testing";

            String issueDescription =
                    "Failure Reason:\n" +
                            result.getThrowable().getMessage() +
                            "\n\nStacktrace:\n" +
                            ExceptionUtils.getStackTrace(result.getThrowable());

            jira.createJiraIssue("Bug", issueSummary, issueDescription);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
    }
}