package com.aloine.resolute40.auth.register.database.table;

import com.aloine.resolute40.auth.register.database.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;


@Table(database = MyDatabase.class)
@Parcel(analyze = {Farmer.class})

public class Farmer extends BaseModel {

    @PrimaryKey
    private int id;

    @Column
    private String phone_number;

    @Column
    private Double first_latitude;

    @Column
    private Double first_longitude;

    public int getId() {
        return id;
    }

    public Double getFirst_longitude() {
        return first_longitude;
    }

    public void setFirst_longitude(Double first_longitude) {
        this.first_longitude = first_longitude;
    }

    public Double getFirst_latitude() {
        return first_latitude;
    }

    public void setFirst_latitude(Double first_latitude) {
        this.first_latitude = first_latitude;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
