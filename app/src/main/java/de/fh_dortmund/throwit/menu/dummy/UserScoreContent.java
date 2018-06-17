package de.fh_dortmund.throwit.menu.dummy;

import android.hardware.usb.UsbRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class UserScoreContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<UserScoreItem> ITEMS = new ArrayList<UserScoreItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, UserScoreItem> ITEM_MAP = new HashMap<String, UserScoreItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createUserScoreItem(String.valueOf(i)));
        }
    }

    public static List<UserScoreItem> returnReverse(){
        List<UserScoreItem> reverseList = ITEMS;
        Collections.reverse(reverseList);
        return reverseList;
    }

    public static List<UserScoreItem> returnHighScore(){

        class ScoreItemComperator implements Comparator<UserScoreItem> {
            public int compare(UserScoreItem item1, UserScoreItem item2) {
                return ((int) item1.score - (int) item2.score);
            }
        }
        Collections.sort(ITEMS, new ScoreItemComperator());
        Collections.reverse(ITEMS);

        return ITEMS;
    }

    public static List<UserScoreItem> returnHighHeight(){

        class HeightItemComperator implements Comparator<UserScoreItem> {
            public int compare(UserScoreItem item1, UserScoreItem item2) {
                return ((int) item1.height - (int) item2.height);
            }
        }
        Collections.sort(ITEMS, new HeightItemComperator());
        Collections.reverse(ITEMS);

        return ITEMS;
    }


    private static void addItem(UserScoreItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.id), item);
    }

    private static UserScoreItem createUserScoreItem(String position) {
        return new UserScoreItem(position, "placeholder"+position, Integer.valueOf(position)*100, Integer.valueOf(position)*10);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class UserScoreItem {
        public final String id;
        public final String name;
        public final double score;
        public final double height;

        public UserScoreItem(String id,String name, double score, double height) {
            this.id = id;
            this.name = name;
            this.score = score;
            this.height = height;
        }

        @Override
        public String toString() {
            return String.valueOf(score);
        }
    }
}
