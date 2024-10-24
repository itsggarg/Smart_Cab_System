package com.smartcab.smart_cab_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cab_status")
public class CabStatus {

    @Id
    @Column(name = "cabid", nullable = false)
    private Long cabId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "currentcapacity")
    private int currentCapacity;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "cabid", referencedColumnName = "cabid", insertable = false, updatable = false)
    private CabDetail cab;

    // Getters and Setters
    public Long getCabId() {
        return cabId;
    }

    public void setCabId(Long cabId) {
        this.cabId = cabId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CabDetail getCab() {
        return cab;
    }

    public void setCab(CabDetail cab) {
        this.cab = cab;
    }
}
