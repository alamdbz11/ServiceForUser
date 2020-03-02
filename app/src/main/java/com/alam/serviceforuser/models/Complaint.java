package com.alam.serviceforuser.models;

import java.io.Serializable;

public class Complaint implements Serializable {
    private String Id,ComplainType,Name,Address,PhoneNo,Complaint,EntryDate,Status,UrlPhoto,UrlPhoto2,UrlPhoto3;

    public Complaint(String id, String complainType, String name, String address, String phoneNo, String complaint, String entryDate, String status, String urlPhoto, String urlPhoto2, String urlPhoto3) {
        Id = id;
        ComplainType = complainType;
        Name = name;
        Address = address;
        PhoneNo = phoneNo;
        Complaint = complaint;
        EntryDate = entryDate;
        Status = status;
        UrlPhoto = urlPhoto;
        UrlPhoto2 = urlPhoto2;
        UrlPhoto3 = urlPhoto3;
    }

    public String getId() {
        return Id;
    }

    public String getComplainType() {
        return ComplainType;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getComplaint() {
        return Complaint;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public String getStatus() {
        return Status;
    }

    public String getUrlPhoto() {
        return UrlPhoto;
    }

    public String getUrlPhoto2() {
        return UrlPhoto2;
    }

    public String getUrlPhoto3() {
        return UrlPhoto3;
    }
}
