package com.example.pcsuit;

import java.util.List;

public class HospitalData {

    String name,location, emergenceContact, description, id;

    public List<Doc> getDoctors() {
        return doctors;
    }

    List<Doc> doctors;


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public String getLocation() {
        return location;
    }

    public String getEmergenceContact() {
        return emergenceContact;
    }

    public String getDescription() {
        return description;
    }
}
