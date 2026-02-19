package com.example.tp1.dto;

import com.example.tp1.entity.AnnonceStatus;
import java.util.Date;

public class AnnonceDTO {
    private Long id;
    private String title;
    private String description;
    private String adress;
    private String mail;
    private Date date;
    private AnnonceStatus status;
    private String categoryLabel;
    private String authorName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public AnnonceStatus getStatus() { return status; }
    public void setStatus(AnnonceStatus status) { this.status = status; }
    public String getCategoryLabel() { return categoryLabel; }
    public void setCategoryLabel(String categoryLabel) { this.categoryLabel = categoryLabel; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}