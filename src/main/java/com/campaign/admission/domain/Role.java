package com.campaign.admission.domain;

public enum Role {
    ADMIN, STUDENT;

    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
