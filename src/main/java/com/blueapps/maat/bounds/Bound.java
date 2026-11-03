package com.blueapps.maat.bounds;

import android.graphics.Rect;

import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.MaatAdapter;
import com.blueapps.maat.ValuePair;
import com.blueapps.maat.result.Result;

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Bound {

    protected BoundProperty property;

    protected float width = 0;
    protected float height = 0;

    protected Element element;

    public Bound(Element element){
        this.element = element;
    }

    public Rect getBound(BoundProperty property, MaatAdapter adapter){
        this.property = property;
        return new Rect();
    }

    public ArrayList<Result> getBounds(Rect newBound){
        ArrayList<Result> bounds = new ArrayList<>();
        bounds.add(new Result());
        return bounds;
    }

    public ArrayList<String> getIds(boolean RTL){
        return new ArrayList<>();
    }

    public int getSignCount(){
        return 1;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

}
