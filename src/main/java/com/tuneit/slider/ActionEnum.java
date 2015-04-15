package com.tuneit.slider;

public enum ActionEnum {
    ADD_ITEM("add"),
    INSERT_ITEM("insert"),
    DELETE_ITEM("delete"),
    SAVE("save");
    
    private String name;
    
    private ActionEnum(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
