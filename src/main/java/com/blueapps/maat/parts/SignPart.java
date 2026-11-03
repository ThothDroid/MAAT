package com.blueapps.maat.parts;

import android.graphics.Rect;

public class SignPart extends Part {

    private final String id;
    private final Rect bound;

    public SignPart(String id, Rect bound){
        this.id = id;
        this.bound = bound;
    }

    public String getId(){
        return id;
    }

    public Rect getBound(){
        return bound;
    }

}
