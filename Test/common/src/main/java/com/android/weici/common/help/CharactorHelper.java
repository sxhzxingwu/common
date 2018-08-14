package com.android.weici.common.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Mouse on 2017/11/20.
 */

public class CharactorHelper {


    public static final String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static boolean isLetter(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    public static List<String> getRadomEnglishCharList(int left,List<String> list) {
        List<String> listLetters = new ArrayList<>();
        listLetters.addAll(Arrays.asList(letters));
        listLetters.removeAll(list);
        for(int i=0;i<left;i++){
            String remove = listLetters.remove(new Random().nextInt(listLetters.size()));
            list.add(remove);
        }
        return list;
    }

}
