package mmt.source.com.schoolproject.model;

public class Student {

    private String stuId;
    private String stuName;
    private String stuGen;
    private String stuStandard;
    private String stuDob;
    private String stuAddr;
    private String stuPincode;
    private String stuArea;
    private String createDate;
    private String routeId;
    private String stuPickUpTime;
    private String stuDropTime;
    private String action;
    private String usrId;

    Student() {
        stuId = "-1";
        routeId = "-1";
    }
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuGen() {
        return stuGen;
    }

    public void setStuGen(String stuGen) {
        this.stuGen = stuGen;
    }

    public String getStuStandard() {
        return stuStandard;
    }

    public void setStuStandard(String stuStandard) {
        this.stuStandard = stuStandard;
    }

    public String getStuDob() {
        return stuDob;
    }

    public void setStuDob(String stuDob) {
        this.stuDob = stuDob;
    }

    public String getStuAddr() {
        return stuAddr;
    }

    public void setStuAddr(String stuAddr) {
        this.stuAddr = stuAddr;
    }

    public String getStuPincode() {
        return stuPincode;
    }

    public void setStuPincode(String stuPincode) {
        this.stuPincode = stuPincode;
    }

    public String getStuArea() {
        return stuArea;
    }

    public void setStuArea(String stuArea) {
        this.stuArea = stuArea;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStuPickUpTime() {
        return stuPickUpTime;
    }

    public void setStuPickUpTime(String stuPickUpTime) {
        this.stuPickUpTime = stuPickUpTime;
    }

    public String getStuDropTime() {
        return stuDropTime;
    }

    public void setStuDropTime(String stuDropTime) {
        this.stuDropTime = stuDropTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
