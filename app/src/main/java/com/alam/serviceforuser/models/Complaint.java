package com.alam.serviceforuser.models;

public class Complaint {
    private String Id,ComplainType,Name,Complaint,EntryDate,Status;

    public Complaint(String id, String complainType, String name, String complaint, String entryDate, String status) {
        Id = id;
        ComplainType = complainType;
        Name = name;
        Complaint = complaint;
        EntryDate = entryDate;
        Status = status;
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

    public String getComplaint() {
        return Complaint;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public String getStatus() {
        return Status;
    }
}
