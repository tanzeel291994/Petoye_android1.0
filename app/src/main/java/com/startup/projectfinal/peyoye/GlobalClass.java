package com.startup.projectfinal.peyoye;

import android.app.Application;

/**
 * Created by tariqueshaikh on 1/8/16.
 */
public class GlobalClass extends Application
{
    private String uid;
    public String getUid()
    {
        return uid;
    }

    public void setUid(String v_uid)
    {
        uid = v_uid;
    }

}
