package com.prakhar.tms.modals;

import android.media.Image;

import java.util.ArrayList;

public class UserDetails {
    private String UserType;
    private String UserEmail;
    private String UserName;
    private String UserAdd;
    private String UserCont;
    private String UserEmgCont;
    private String UserId;
    private String UserAadhar;
    private String UserPan;
    private String UserGST;
    private String UserDL;
    private ArrayList<Image> Imgpath;


    public UserDetails(String userType, String userEmail, String userName, String userAdd, String userCont, String userEmgCont, String userAadhar, String userPan, String userGST, String userDL, ArrayList<Image> imgpath) {
        UserType = userType;
        UserName = userName;
        UserAdd = userAdd;
        UserCont = userCont;
        UserEmgCont = userEmgCont;
        UserAadhar = userAadhar;
        UserPan = userPan;
        UserGST = userGST;
        UserDL = userDL;
        Imgpath = imgpath;
        UserEmail = userEmail;
    }

    public UserDetails(String userType, String userEmail, String userName, String userAdd, String userCont, String userEmgCont, String userAadhar, String userPan, String userGST, String userDL) {
        UserType = userType;
        UserEmail = userEmail;
        UserName = userName;
        UserAdd = userAdd;
        UserCont = userCont;
        UserEmgCont = userEmgCont;
        UserAadhar = userAadhar;
        UserPan = userPan;
        UserGST = userGST;
        UserDL = userDL;
    }

    public UserDetails(String userType, String userName, String userAdd, String userCont, String userEmgCont, String userDL, String userPan, String userAadhar) {
        UserType = userType;
        UserName = userName;
        UserAdd = userAdd;
        UserCont = userCont;
        UserEmgCont = userEmgCont;
        UserAadhar = userAadhar;
        UserDL = userDL;
        UserPan = userPan;
    }


    public UserDetails() {
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserAadhar() {
        return UserAadhar;
    }

    public void setUserAadhar(String userAadhar) {
        UserAadhar = userAadhar;
    }

    public String getUserPan() {
        return UserPan;
    }

    public void setUserPan(String userPan) {
        UserPan = userPan;
    }

    public String getUserGST() {
        return UserGST;
    }

    public void setUserGST(String userGST) {
        UserGST = userGST;
    }

    public String getUserDL() {
        return UserDL;
    }

    public void setUserDL(String userDL) {
        UserDL = userDL;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAdd() {
        return UserAdd;
    }

    public void setUserAdd(String userAdd) {
        UserAdd = userAdd;
    }

    public String getUserCont() {
        return UserCont;
    }

    public void setUserCont(String userCont) {
        UserCont = userCont;
    }

    public String getUserEmgCont() {
        return UserEmgCont;
    }

    public void setUserEmgCont(String userEmgCont) {
        UserEmgCont = userEmgCont;
    }

    public ArrayList<Image> getImgpath() {
        return Imgpath;
    }

    public void setImgpath(ArrayList<Image> imgpath) {
        Imgpath = imgpath;
    }


}
