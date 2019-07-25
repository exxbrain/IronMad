package com.ironmadness.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Поле имени не может быть пустым")
    private String name;
    @NotBlank(message = "Поле описания не может быть пустым")
    private String text;

    private String avatar;

    public Channel() {
    }

    public Channel(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameChannel) {
        this.name = nameChannel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
