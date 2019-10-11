package com.ironmadness.domain;

import javax.persistence.*;

@Entity
@Table(name = "api")
public class Api{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "vk_id")
    private Integer userId;
    @Column(name = "token")
    private String accessToken;
    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Api() {
    }

    public Api(Integer userId, String accessToken, String type) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
