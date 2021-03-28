package com.prakhar.tms.modals;

public class BiddingVehicle {


    private String BiddingTitle;
    private String BiddingDes;
    private String BiddingPrice;

    private String VehicleName;
    private String VehicleModel;
    private String VehicleNo;

    private String VehicleType;
    private String NoOfTires;
    private String LoadingCap;
    private String KmDriven;
    private String Average;

    private String VehicleOwner;
    private String OwnerCont;
    private String Ownerloc;

    private String UserId;

    public BiddingVehicle() {

    }

    public BiddingVehicle(String biddingTitle, String biddingDes, String biddingPrice, String vehicleName, String vehicleModel, String vehicleNo, String vehicleType, String noOfTires, String loadingCap, String kmDriven, String average, String vehicleOwner, String ownerCont, String ownerloc, String userId) {
        BiddingTitle = biddingTitle;
        BiddingDes = biddingDes;
        BiddingPrice = biddingPrice;
        VehicleName = vehicleName;
        VehicleModel = vehicleModel;
        VehicleNo = vehicleNo;
        VehicleType = vehicleType;
        NoOfTires = noOfTires;
        LoadingCap = loadingCap;
        KmDriven = kmDriven;
        Average = average;
        VehicleOwner = vehicleOwner;
        OwnerCont = ownerCont;
        Ownerloc = ownerloc;
        UserId = userId;
    }

    public String getBiddingTitle() {
        return BiddingTitle;
    }

    public void setBiddingTitle(String biddingTitle) {
        BiddingTitle = biddingTitle;
    }

    public String getBiddingDes() {
        return BiddingDes;
    }

    public void setBiddingDes(String biddingDes) {
        BiddingDes = biddingDes;
    }

    public String getBiddingPrice() {
        return BiddingPrice;
    }

    public void setBiddingPrice(String biddingPrice) {
        BiddingPrice = biddingPrice;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getNoOfTires() {
        return NoOfTires;
    }

    public void setNoOfTires(String noOfTires) {
        NoOfTires = noOfTires;
    }

    public String getLoadingCap() {
        return LoadingCap;
    }

    public void setLoadingCap(String loadingCap) {
        LoadingCap = loadingCap;
    }

    public String getKmDriven() {
        return KmDriven;
    }

    public void setKmDriven(String kmDriven) {
        KmDriven = kmDriven;
    }

    public String getAverage() {
        return Average;
    }

    public void setAverage(String average) {
        Average = average;
    }

    public String getVehicleOwner() {
        return VehicleOwner;
    }

    public void setVehicleOwner(String vehicleOwner) {
        VehicleOwner = vehicleOwner;
    }

    public String getOwnerCont() {
        return OwnerCont;
    }

    public void setOwnerCont(String ownerCont) {
        OwnerCont = ownerCont;
    }

    public String getOwnerloc() {
        return Ownerloc;
    }

    public void setOwnerloc(String ownerloc) {
        Ownerloc = ownerloc;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
