import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import models.IssueBody;
import models.Repo;
import convector.JsonConvector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static constants.User.LOGIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



public class IssueTest extends Base {
    static String repoName;
    static String path;

    @BeforeAll
    public static void createRepo(){
        repoName = "IssueTest";
        Repo newrepo = new Repo(repoName, "for issuers");
        requeetGet().contentType(ContentType.JSON)
                .body(newrepo)
                .post("/user/repos");
        path = "/repos/" + LOGIN + "/" + repoName + "/issues";
    }

    @Test
    public void firstTask(){
        IssueBody issueBody = requeetGet().contentType(ContentType.JSON)
                .body(JsonConvector.jsonToString("/task1.json"))
                .post(path)
                .body()
                .as(IssueBody.class);
        List<Map<String, Object>> issues = requeetGet().get(path).as(new TypeRef<List<Map<String, Object>>>(){});
        int indexOfThisIssue = 0;
        for(Map is : issues){
            if(is.get("id").equals(issueBody.id)){
                indexOfThisIssue = issues.indexOf(is);
            }
        }

        assertEquals(issueBody.id, issues.get(indexOfThisIssue).get("id"));

    }

    @Test
    public void secondTask(){
        IssueBody issueBody = requeetGet().contentType(ContentType.JSON)
                .body(JsonConvector.jsonToString("/task2.json"))
                .post(path)
                .body()
                .as(IssueBody.class);
        requeetGet().contentType(ContentType.JSON)
                .body(JsonConvector.jsonToString("/task2_v2.json"))
                .patch(issueBody.url);

        IssueBody issueBody2 = requeetGet()
                .get(issueBody.url)
                .body()
                .as(IssueBody.class);

        assertNotEquals(issueBody.body, issueBody2.body);
    }

    @Test
    public void thirdTask(){

        IssueBody issueBody = requeetGet().contentType(ContentType.JSON)
                .body(JsonConvector.jsonToString("/task3.json"))
                .post(path)
                .body()
                .as(IssueBody.class);

        String lockpath = issueBody.url + "/lock";

        requeetGet().contentType(ContentType.JSON)
                .body(JsonConvector.jsonToString("/task3lock.json"))
                .put(lockpath).then().statusCode(204);

        IssueBody issueBody2 =   requeetGet().contentType(ContentType.JSON).get(issueBody.url).body().as(IssueBody.class);

        assertThat(issueBody2.locked).isTrue();
    }


   @AfterAll
   public static void deleteRepo(){
       String path = "/repos/" + LOGIN + "/" + repoName;
       requeetGet().
                delete(path);
   }



}
