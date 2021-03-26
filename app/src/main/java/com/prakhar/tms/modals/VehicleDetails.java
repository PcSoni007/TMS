package com.prakhar.tms.modals;

import android.media.Image;

import java.util.ArrayList;

public class VehicleDetails {

    private String VehicleName;
    private String VehicleModel;
    private String VehicleNo;

    private String VehicleType;
    private String NoOfTires;
    private String Average;
    private String Availability;
    private String LoadingCap;

    private String VehicleOwner;
    private String OwnerCont;
    private String OwnerLoc;

    private String UserId;

    public VehicleDetails(String vehicleName, String vehicleModel, String vehicleNo, String vehicleType, String noOfTires, String average, String availability, String loadingCap, String vehicleOwner, String ownerCont, String ownerLoc, String userId) {
        VehicleName = vehicleName;
        VehicleModel = vehicleModel;
        VehicleNo = vehicleNo;
        VehicleType = vehicleType;
        NoOfTires = noOfTires;
        Average = average;
        Availability = availability;
        LoadingCap = loadingCap;
        VehicleOwner = vehicleOwner;
        OwnerCont = ownerCont;
        OwnerLoc = ownerLoc;
        UserId = userId;
    }

    public VehicleDetails(String vehicleName, String vehicleNo, String vehicleType, String noOfTires, String average, String availability, String loadingCap, String vehicleOwner, String userId) {
        VehicleName = vehicleName;
        VehicleNo = vehicleNo;
        VehicleType = vehicleType;
        NoOfTires = noOfTires;
        Average = average;
        Availability = availability;
        LoadingCap = loadingCap;
        VehicleOwner = vehicleOwner;
        UserId = userId;
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

    public String getTotNoVehicle() {
        return Average;
    }

    public void setTotNoVehicle(String totNoVehicle) {
        Average = totNoVehicle;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getLoadingCap() {
        return LoadingCap;
    }

    public void setLoadingCap(String loadingCap) {
        LoadingCap = loadingCap;
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

    public String getOwnerLoc() {
        return OwnerLoc;
    }

    public void setOwnerLoc(String ownerLoc) {
        OwnerLoc = ownerLoc;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
