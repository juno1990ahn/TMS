package com.pandaandthekid.tms.verses;

import android.content.Context;

import com.pandaandthekid.tms.verses.bean.TMSBundle;

import java.util.ArrayList;
import java.util.Arrays;

public class TMSVerse implements IMemorizable {

    private String topic;
    private String subTopic;

    private String bibleVersion = "NIV";
    private ArrayList<String> scripture;
    private String verse;

    private int currentIndex = 0;

    public TMSVerse(Context context, TMSBundle pack, int cardNumber) {
        if (cardNumber <= 0 || cardNumber > 12) {
            String exception = String.format("Card Number %s does not exist for pack %s", cardNumber, pack);
            throw new IllegalArgumentException(exception);
        }

        int topicId = context.getResources().getIdentifier(
                String.format("topic_%s", pack.toString().toLowerCase()), "string", context.getPackageName());
        topic = context.getString(topicId);
        int subtopicId = context.getResources().getIdentifier(
                String.format("sub_topic_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        subTopic = context.getString(subtopicId);
        int scriptureId = context.getResources().getIdentifier(
                String.format("scripture_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        scripture = (ArrayList<String>) Arrays.asList(context.getString(scriptureId).split("\\s+"));
        int verseId = context.getResources().getIdentifier(
                String.format("verse_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        verse = context.getString(verseId);
    }

    public String getNextWord() {
        if (currentIndex >= scripture.size()) {
            throw new IndexOutOfBoundsException("There are no more workds to return");
        }
        String next = scripture.get(currentIndex);
        currentIndex++;
        return next;
    }

    public String getPreviousWord() {
        if (currentIndex > 0) {
            return scripture.get(currentIndex - 1);
        } else {
            throw new IndexOutOfBoundsException("You are at the beginning of the verse.");
        }
    }

    public boolean isEmpty() {
        return currentIndex == scripture.size();
    }

    public String getTopic() {
        return topic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public String getBibleVersion() {
        return bibleVersion;
    }

    public String getVerse() {
        return verse;
    }
}
