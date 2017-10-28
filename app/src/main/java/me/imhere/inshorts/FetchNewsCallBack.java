package me.imhere.inshorts;

import java.util.ArrayList;

interface FetchNewsCallBack
{
    void done(ArrayList<News> newses);
    void error(String message);
}
