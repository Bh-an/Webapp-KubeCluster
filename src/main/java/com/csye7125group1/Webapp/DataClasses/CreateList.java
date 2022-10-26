package com.csye7125group1.Webapp.DataClasses;

import javax.validation.constraints.NotNull;

public class CreateList {

    @NotNull
    private String listname;

    public CreateList() {
    }

    public CreateList(String listname) {
        this.listname = listname;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }
}
