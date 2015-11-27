package com.pandaandthekid.tms.menu;


import com.pandaandthekid.tms.verses.TMSBundle;

import java.util.HashMap;
import java.util.Map;

public class NavMenuItem {
    public static final int ACTIVITY_LINK = 0;

    public static final String PACK = "pack";

    public int type;
    public String label;
    public Class activity;
    public Map<String, Object> metadata;

    public NavMenuItem(int type, String label, Class clazz) {
        this.type = type;
        this.label = label;
        this.activity = clazz;
    }

    public NavMenuItem(int type, String label, Class clazz, TMSBundle pack) {
        this.type = type;
        this.label = label;
        this.activity = clazz;

        this.metadata = new HashMap<>();
        metadata.put(PACK, pack);
    }
}
