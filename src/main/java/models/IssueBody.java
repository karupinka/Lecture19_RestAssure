package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueBody {
    @JsonProperty("url")
    public String url;
    @JsonProperty("id")
    public int id;
    @JsonProperty("number")
    public int number;
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;
    @JsonProperty("locked")
    public boolean locked;

    public IssueBody(String url, int id, int number, String title, String body, boolean locked) {
        this.url = url;
        this.id = id;
        this.number = number;
        this.title = title;
        this.body = body;
        this.locked = locked;
    }

    public IssueBody() {
    }


}
