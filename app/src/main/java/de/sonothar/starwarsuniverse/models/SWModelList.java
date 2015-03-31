package de.sonothar.starwarsuniverse.models;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Oleur on 22/12/2014.
 * Generic list model
 */
public class SWModelList<T> implements Serializable {
    public int count;
    public String next;
    public String previous;
    public ArrayList<T> results;

    public boolean hasMore() {
        return !TextUtils.isEmpty(next);
    }

    public int getNextPage(){
        if (next == null || next.isEmpty()){
            return -1;
        }

        return getPage(next);
    }

    public int getPreviousPage(){
        if (previous == null || previous.isEmpty()){
            return -1;
        }

        return getPage(previous);
    }

    private int getPage(String url){
        int idx = url.indexOf("page=");
        return idx < 0 ? -1 :
            Integer.valueOf(next.substring(idx));
    }

}
