package com.tvo.cityguess.helpers;

import java.util.HashMap;

/**
 * Created by victor on 7/6/16.
 */
public class DialogsShownHashMap extends HashMap<String,Integer> {

    @Override
    public Integer get(Object key) {

        if( this != null && !this.isEmpty() && this.containsKey(key) ){
            return super.get(key);
        } else {
            return 0;
        }

    }

}
