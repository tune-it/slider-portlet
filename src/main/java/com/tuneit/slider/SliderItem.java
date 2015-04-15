package com.tuneit.slider;

import java.util.Date;

public class SliderItem {
    
    private String imagePath; 
    
    private String url;
    
    private String title;
    
    private String alt;
    
    private String effect;
    
    private long classPK;
    
    
    public SliderItem() {
        this("", "", "", "", EffectEnum.RANDOM);
    }

    public SliderItem(String imagePath, String url, String title, String alt, EffectEnum effect) {
        this.imagePath = imagePath;
        this.url = url;
        this.title = title;
        this.alt = alt;
        this.effect = effect.getName();
        this.classPK = new Date().getTime();
    }
    
    public boolean isEmpty() {
        
        return ("".equals(imagePath)) && ("".equals(url)) && ("".equals(title)) && ("".equals(alt));
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public long getClassPK() {
        return classPK;
    }

    public void setClassPK(long classPK) {
        this.classPK = classPK;
    }
    
    
    
    
    
}
