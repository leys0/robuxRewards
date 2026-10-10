package org.example.fakerobuxsite.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "globalConfig")
public class GlobalConfig {

    @Id
    private String id;
    private String username;
    private String missionLink;
    private String ttkUser;

    public GlobalConfig() {}

    public GlobalConfig(String username, String missionLink, String ttkUser) {
        this.username = username;
        this.missionLink = missionLink;
        this.ttkUser = ttkUser;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMissionLink() { return missionLink; }
    public void setMissionLink(String missionLink) { this.missionLink = missionLink; }

    public String getTtkUser() { return ttkUser; }
    public void setTtkUser(String ttkUser) { this.ttkUser = ttkUser; }

}