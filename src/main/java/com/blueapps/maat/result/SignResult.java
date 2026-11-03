package com.blueapps.maat.result;

import android.graphics.Rect;

public class SignResult extends Result {

    private final String id;
    private final Rect bound;

    public SignResult(String id, Rect bound){
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
