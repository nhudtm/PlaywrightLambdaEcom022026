package jiraConfig;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JiraServiceProvider {
    private final String JIRA_BASE_URL;
    private final String JIRA_USERNAME;
    private final String JIRA_API_KEY;
    private final String JIRA_PROJECT_KEY;

    public JiraServiceProvider(String jiraUrl, String username, String password, String projectKey) {
        // jiraUrl phải là: https://automationtest.atlassian.net
        this.JIRA_BASE_URL = jiraUrl;
        this.JIRA_USERNAME = username;
        this.JIRA_API_KEY = password; // API Key được truyền vào trường password
        this.JIRA_PROJECT_KEY = projectKey;
        // Đặt Base URI chung cho tất cả các Request
        RestAssured.baseURI = JIRA_BASE_URL + "/rest/api/3";

    }

    Header defaultHeader = new Header("Content-Type", "application/json; charset=UTF-8");
    Header acceptHeader = new Header("Accept", "application/json");


    public static Header getAuthorizationHeader1(String email, String token) {
        if (email == null || token == null) {
            throw new IllegalArgumentException("Email and token must not be null");
        }
        String cred = email + ":" + token;
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredStr = new String(encodedCred);
        return new Header("Authorization", "Basic " + encodedCredStr);
    }

    // Hàm thiết lập Request Spec chung (Xác thực và Headers)
    private RequestSpecification getRequestSpec() {
        return given()
                .header(defaultHeader)
                .header(acceptHeader)
                .header(getAuthorizationHeader1(JIRA_USERNAME, JIRA_API_KEY));
    }

    // ----------------------------------------------------
    // PHƯƠNG THỨC 1: KIỂM TRA ISSUE TỒN TẠI (Thay thế searchIssues)
    // ----------------------------------------------------
    private int searchIssueCount(String summary, String projectKey) {
        // Xây dựng JQL: Tìm issue trong projectKey có Summary chứa summary
        String jqlQuery = String.format("project = %s AND summary ~ \"%s\"",
                projectKey, summary);

        Map<String, Object> body = new HashMap<>();
        body.put("jql", jqlQuery);
        body.put("maxResults", 1);

        Response response = getRequestSpec()
                .body(body)
                .when()
                .post("/search/jql"); // ✅ search must use POST

//        response.then().log().all();
        response.then().statusCode(200);
        return response.jsonPath().getList("issues").size();
    }

    // ----------------------------------------------------
    // PHƯƠNG THỨC 2: TẠO ISSUE JIRA (Issue chính)
    // ----------------------------------------------------
    public void createJiraIssue(String issueType, String summary, String description) {

        if (searchIssueCount(summary, JIRA_PROJECT_KEY) != 0) {
            System.out.println("Same Issue Already Exists on Jira");
            return;
        }

        try {
            Map<String, Object> fields = new HashMap<>();
            fields.put("summary", summary);

            fields.put("project", Map.of("key", JIRA_PROJECT_KEY));
            fields.put("issuetype", Map.of("name", issueType));

            // ✅ Description in ADF
            Map<String, Object> textNode = Map.of(
                    "type", "text",
                    "text", description
            );

            Map<String, Object> paragraph = Map.of(
                    "type", "paragraph",
                    "content", java.util.List.of(textNode)
            );

            Map<String, Object> descriptionADF = Map.of(
                    "type", "doc",
                    "version", 1,
                    "content", java.util.List.of(paragraph)
            );

            fields.put("description", descriptionADF);

            Map<String, Object> body = Map.of("fields", fields);

            Response response = getRequestSpec()
                    .body(body)
                    .when()
                    .post("/issue");

            response.then().statusCode(201);

            String issueKey = response.jsonPath().getString("key");
            System.out.println("✅ Issue created: " + JIRA_BASE_URL + "/browse/" + issueKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
