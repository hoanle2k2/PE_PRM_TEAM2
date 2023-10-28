package com.example.pe_prm_team2.model;

public class UserDetail {
    private User data;
    private Support support;

    public UserDetail(User data, Support support) {
        this.data = data;
        this.support = support;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }
}
