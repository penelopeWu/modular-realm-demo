package com.penelopewu.shiro.modular.realm.demo;

public enum LoginType {
    USER("User"),
    ADMIN("Admin");
    private String type;
    private LoginType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginType{" +
                "type='" + type + '\'' +
                '}';
    }
}
