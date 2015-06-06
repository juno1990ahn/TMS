package com.pandaandthekid.tms.verses;

public interface IMemorizable {

    String getNextWord();
    String getPreviousWord();
    String getVerse();
    boolean isEmpty();
}
