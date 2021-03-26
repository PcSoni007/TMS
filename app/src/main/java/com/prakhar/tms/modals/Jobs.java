package com.prakhar.tms.modals;

public class Jobs {

    private String JobTitle;
    private String JobDes;
    private String GoodsName;
    private String GoodsQuant;
    private String GoodsDes;
    private String Source;
    private String Destination;
    private String JobStatus;
    private String JobId;
    private String JobDate;
    private String JobPay;
    private String JobType;
    private String UserId;

    public Jobs(String userId, String jobTitle, String jobDes, String goodsName, String goodsQuant, String goodsDes, String source, String destination, String jobStatus, String jobId, String jobDate, String jobPay, String jobType) {
        UserId = userId;
        JobTitle = jobTitle;
        JobDes = jobDes;
        GoodsName = goodsName;
        GoodsQuant = goodsQuant;
        GoodsDes = goodsDes;
        Source = source;
        Destination = destination;
        JobStatus = jobStatus;
        JobId = jobId;
        JobDate = jobDate;
        JobPay = jobPay;
        JobType = jobType;
    }

    public Jobs() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getJobPay() {
        return JobPay;
    }

    public void setJobPay(String jobPay) {
        JobPay = jobPay;
    }


    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobDes() {
        return JobDes;
    }

    public void setJobDes(String jobDes) {
        JobDes = jobDes;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getGoodsQuant() {
        return GoodsQuant;
    }

    public void setGoodsQuant(String goodsQuant) {
        GoodsQuant = goodsQuant;
    }

    public String getGoodsDes() {
        return GoodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        GoodsDes = goodsDes;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getJobStatus() {
        return JobStatus;
    }

    public void setJobStatus(String jobStatus) {
        JobStatus = jobStatus;
    }

    public String getJobId() {
        return JobId;
    }

    public void setJobId(String jobId) {
        JobId = jobId;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }


}
