package com.rutik.ems.dto;
public class AuthResponse {
    private String token,role,name,email;
    private Integer empId,adminId;
    private String department;
    private Long salary;
    private Boolean isMain;
    public AuthResponse(){}
    public AuthResponse(String t,String r,String n,String e,Integer adminId,boolean a){token=t;role=r;name=n;email=e;this.adminId=adminId;}
    public AuthResponse(String t,String r,String n,String e,Integer eid,String dept,Long sal){token=t;role=r;name=n;email=e;empId=eid;department=dept;salary=sal;}
    public AuthResponse(String t,String r,String n,String e){token=t;role=r;name=n;email=e;}
    public AuthResponse(String t,String r,String n,String e,Integer eid){token=t;role=r;name=n;email=e;empId=eid;}
    public String getToken(){return token;} public void setToken(String t){token=t;}
    public String getRole(){return role;} public void setRole(String r){role=r;}
    public String getName(){return name;} public void setName(String n){name=n;}
    public String getEmail(){return email;} public void setEmail(String e){email=e;}
    public Integer getEmpId(){return empId;} public void setEmpId(Integer i){empId=i;}
    public Integer getAdminId(){return adminId;} public void setAdminId(Integer i){adminId=i;}
    public String getDepartment(){return department;} public void setDepartment(String d){department=d;}
    public Long getSalary(){return salary;} public void setSalary(Long s){salary=s;}
    public Boolean getIsMain(){return isMain;} public void setIsMain(Boolean m){isMain=m;}
}