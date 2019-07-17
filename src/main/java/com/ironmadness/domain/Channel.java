package com.ironmadness.domain;

import javax.persistence.*;

@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameChannel;
    private String text;
    private String channel_avatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Channel() {
    }

    public Channel(String nameChannel, String text, String channel_avatar, User author) {
        this.nameChannel = nameChannel;
        this.text = text;
        this.channel_avatar = channel_avatar;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameChannel() {
        return nameChannel;
    }

    public void setNameChannel(String nameChannel) {
        this.nameChannel = nameChannel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getChannel_avatar() {
        return channel_avatar;
    }

    public void setChannel_avatar(String channel_avatar) {
        this.channel_avatar = channel_avatar;
    }
}
