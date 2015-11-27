package com.pandaandthekid.tms.verses;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class TMSVerse implements IMemorizable {

    private String topic;
    private String subTopic;

    private String tmsIndex;

    private String bibleVersion = "NIV";
    private String scripture;
    private String verse;

    private int currentIndex = 0;
    private ArrayList<String> memorizable = new ArrayList<>();

    public static final String DIVIDER = "%%%%%%";

    public TMSVerse(Context context, TMSBundle pack, int cardNumber) {
        if (cardNumber <= 0 || cardNumber > 12) {
            String exception = String.format("Card Number %s does not exist for pack %s", cardNumber, pack);
            throw new IllegalArgumentException(exception);
        }

        tmsIndex = pack.toString() + '-' + cardNumber;

        int topicId = context.getResources().getIdentifier(
                String.format("topic_%s", pack.toString().toLowerCase()), "string", context.getPackageName());
        topic = context.getString(topicId);
        int subtopicId = context.getResources().getIdentifier(
                String.format("sub_topic_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        subTopic = context.getString(subtopicId);
        int scriptureId = context.getResources().getIdentifier(
                String.format("scripture_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        scripture = context.getString(scriptureId);
        int verseId = context.getResources().getIdentifier(
                String.format("verse_%s_%s", pack.toString().toLowerCase(), cardNumber), "string", context.getPackageName());
        verse = context.getString(verseId);

        buildMemorizable();
    }

    public String getNextWord() {
        if (currentIndex >= memorizable.size()) {
            throw new IndexOutOfBoundsException("There are no more workds to return");
        }
        String next = memorizable.get(currentIndex);
        currentIndex++;
        return next;
    }

    public String getPreviousWord() {
        if (currentIndex > 0) {
            return memorizable.get(currentIndex - 1);
        } else {
            throw new IndexOutOfBoundsException("You are at the beginning of the verse.");
        }
    }

    public boolean isEmpty() {
        return currentIndex == memorizable.size();
    }

    public String getTmsIndex() {
        return tmsIndex;
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

    public String getScripture() {
        return scripture;
    }

    private void buildMemorizable() {
        memorizable.add(tmsIndex);
        memorizable.add(' ' + subTopic);
        memorizable.add(DIVIDER);
        memorizable.addAll(Arrays.asList(verse.split("(?=\\s+)")));
        memorizable.add(DIVIDER);
        memorizable.addAll(Arrays.asList(scripture.split("(?=\\s+)")));
    }
}
