package com.utilities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeywordEvaluationTest {
    static KeywordEvaluation keywordEvaluation;

    @BeforeAll
    static void setUp() {
        keywordEvaluation = new KeywordEvaluation();
    }

    @Test
    void containsKeywords() {
        String target = "Europe is becoming a heat wave hot spot";
        String keywords = "It's hot today";

        assertTrue(keywordEvaluation.containsKeywords(target, keywords));
    }

    @Test
    void containsKeywords_DoesNotMatchAny() {
        String target = "Europe is becoming a heat wave hot spot";
        String keywords = "His keyboard sounds terrible";

        assertFalse(keywordEvaluation.containsKeywords(target, keywords));
    }

}