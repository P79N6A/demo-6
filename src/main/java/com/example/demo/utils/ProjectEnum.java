package com.example.demo.utils;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-17
 **/
public enum ProjectEnum {

    WORKFLOW("workflow", "10.92.1.36,10.92.1.37,10.92.1.38"),
    P2P("p2p", "10.92.1.27,10.92.1.28"),
    BDCC("bdcc", "10.92.1.31,10.92.1.32"),
    MDM("mdm", "10.92.1.24,10.92.1.25"),
    EAWO("eawo", "10.92.1.101"),
    UAMS("uams", "10.92.1.92,10.92.1.93");

    private String project;
    private String ips;

    ProjectEnum(String project, String ips) {
        this.project = project;
        this.ips = ips;
    }

    public String getProject() {
        return project;
    }

    public String getIps() {
        return ips;
    }
}