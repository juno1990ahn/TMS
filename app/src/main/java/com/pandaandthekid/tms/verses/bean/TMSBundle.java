package com.pandaandthekid.tms.verses.bean;

/**
 * Created by juneahn on 5/16/15.
 */
public enum TMSBundle {
    A_PACK("A"),
    B_PACK("B"),
    C_PACK("C"),
    D_PACK("D"),
    E_PACK("E");

    private String letter;
    TMSBundle(String letter) {
        this.letter = letter;
    }

    public String toString() {
        return letter;
    }
}
