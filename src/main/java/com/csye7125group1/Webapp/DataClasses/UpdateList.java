package com.csye7125group1.Webapp.DataClasses;

public class UpdateList {

    private String oldlistname;
    private String newlistname;

    public UpdateList(String oldlistname, String newlistname) {
        this.oldlistname = oldlistname;
        this.newlistname = newlistname;
    }

    public String getOldlistname() {
        return oldlistname;
    }

    public void setOldlistname(String oldlistname) {
        this.oldlistname = oldlistname;
    }

    public String getNewlistname() {
        return newlistname;
    }

    public void setNewlistname(String newlistname) {
        this.newlistname = newlistname;
    }
}
