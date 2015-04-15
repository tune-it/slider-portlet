package com.tuneit.slider;

public enum ThemeEnum {
    
    DEFAULT("default"),
    ORMAN("orman"),
    PASCAL("pascal");
    
    private String name;
    
    private ThemeEnum(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
