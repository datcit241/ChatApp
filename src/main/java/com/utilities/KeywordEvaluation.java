package com.utilities;

import java.util.*;

public class KeywordEvaluation {

    public boolean containsKeywords(String text, String keywords) {
        text = text.toLowerCase();
        keywords = keywords.toLowerCase();

        List<String> wordsInKeyword = (List<String>) Arrays.asList(keywords.split(" "));

        for (String word : text.split(" ")) {
            if (wordsInKeyword.contains(word)) {
                return true;
            }
        }

        return false;
    }

}
