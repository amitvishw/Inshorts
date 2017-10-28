package me.imhere.inshorts;

import java.util.ArrayList;

class DataContainer {

    private static DataContainer instance = null;

    static synchronized DataContainer getInstance()
    {
        return instance != null?instance: (instance = new DataContainer());
    }

    ArrayList<News> newses;
    ArrayList<Integer> favs;

}

