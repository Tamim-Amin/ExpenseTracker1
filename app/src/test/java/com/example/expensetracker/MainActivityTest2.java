package com.example.expensetracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainActivityTest2 {
    @Test
    public void testGetCategoryColor_Food() {
        assertEquals(0xFF10B981, MainActivity.getCategoryColor("food"));
    }

    @Test
    public void testGetCategoryColor_Unknown() {
        assertEquals(0xFF6B7280, MainActivity.getCategoryColor("random"));
    }
}
