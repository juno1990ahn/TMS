package com.pandaandthekid.tms.verses.bean;

public enum TMSBundle {

    A_PACK("A", 0),
    B_PACK("B", 1),
    C_PACK("C", 2),
    D_PACK("D", 3),
    E_PACK("E", 4);

    public final static String CHOSEN_PACK = "com.pandaandthekids.tms.chosen_pack";
    public final static String CHOSEN_VERSE = "com.pandaandthekids.tms.chosen_verse";
    public final static TMSBundle[] bundleOrder;

    private String letter;
    private int index;
    TMSBundle(String letter, int index) {
        this.letter = letter;
        this.index = index;
    }

    static {
        bundleOrder = new TMSBundle[5];
        bundleOrder[0] = A_PACK;
        bundleOrder[1] = B_PACK;
        bundleOrder[2] = C_PACK;
        bundleOrder[3] = D_PACK;
        bundleOrder[4] = E_PACK;
    }

    public String toString() {
        return letter;
    }

    public int getIndex() {
        return index;
    }
}
