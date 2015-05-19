package com.pandaandthekid.tms.verses;

import com.pandaandthekid.tms.verses.bean.TMSBundle;

import java.util.ArrayList;

/**
 * Created by juneahn on 5/16/15.
 */
public class TMSVerse implements IMemorizable {

    private String topic;
    private String subTopic;

    private String bibleVersion = "NIV";
    private ArrayList<String> scripture;
    private String verse;


    public TMSVerse(TMSBundle pack, int cardNumber) {
        if (cardNumber <= 0 || cardNumber > 12) {
            String exception = String.format("Card Number %s does not exist for pack %s", cardNumber, pack);
            throw new IllegalArgumentException(exception);
        }


    }

    public String getNext() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }
}
