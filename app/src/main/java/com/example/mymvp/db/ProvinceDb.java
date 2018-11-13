package com.example.mymvp.db;

import org.litepal.crud.DataSupport;

public class ProvinceDb extends DataSupport {
    /**
     * code : 1
     * name : 北京
     */

    private int id;
    private String name;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
