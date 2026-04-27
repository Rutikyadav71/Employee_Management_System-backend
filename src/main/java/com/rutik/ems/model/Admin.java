package com.rutik.ems.model;
import jakarta.persistence.*;
@Entity @Table(name="admins")
public class Admin {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;
    private String name;
    @Column(unique=true) private String email;
    private String password;
    private String role="ROLE_ADMIN";
    @Column(nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE") private Boolean frozen=false;
    @Column(name="is_main",nullable=false,columnDefinition="BOOLEAN DEFAULT FALSE") private Boolean isMain=false;
    @Column(name="profile_image_url",length=500) private String profileImageUrl;
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String n){name=n;}
    public String getEmail(){return email;} public void setEmail(String e){email=e;}
    public String getPassword(){return password;} public void setPassword(String p){password=p;}
    public String getRole(){return role;} public void setRole(String r){role=r;}
    public Boolean getFrozen(){return frozen!=null&&frozen;} public void setFrozen(Boolean f){frozen=f!=null&&f;}
    public Boolean getIsMain(){return isMain!=null&&isMain;} public void setIsMain(Boolean m){isMain=m!=null&&m;}
    public String getProfileImageUrl(){return profileImageUrl;}
    public void setProfileImageUrl(String url){profileImageUrl=url;}
}