package com.slow.adventure.ui.Role;

public class RoleData {
    String name,content;
    int image;
    public RoleData(String name,int image,String content){
        this.name = name;
        this.image = image;
        this.content = content;
    }

    public String getRoleName() {
        return name;
    }

    public String getRoleContent() {
        return content;
    }

    public int getRoleImage() {
        return image;
    }
}
