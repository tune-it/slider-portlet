package com.tuneit.slider;

/**
 * 
 * @author nicola
 *
 */

public enum EffectEnum {
    RANDOM("random"),
    FADE("fade"),
    SLICE_DOWN("sliceDown"),
    SLICE_DOWN_RIGHT("sliceDownRight"),
    SLICE_DOWN_LEFT("sliceDownLeft"),
    SLICE_UP("sliceUp"),
    SLICE_UP_RIGHT("sliceUpRight"),
    SLICE_UP_LEFT("sliceUpLeft"),
    SLICE_UP_DOWN("sliceUpDown"),
    SLICE_UP_DOWN_RIGHT("sliceUpDownRight"),
    SLICE_UP_DOWN_LEFT("sliceUpDownLeft"),
    FOLD("fold"),
    SLIDE_IN_RIGHT("slideInRight"),
    SLIDE_IN_LEFT("slideInLeft"),
    BOX_RANDOM("boxRandom"),
    BOX_RAIN("boxRain"),
    BOX_RAIN_REVERSE("boxRainReverse"),
    BOX_RAIN_GROW("boxRainGrow"),
    BOX_RAIN_GROW_REVERSE("boxRainGrowReverse");
    
    private String name;
    
    private EffectEnum(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
