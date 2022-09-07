package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Test
    void whenUpdatingQuality_shouldKeepName() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    void whenSellHasPassed_shouldDegradesTwiceAsFast() {
        Item[] items = new Item[]{new Item("foo", 0, 4)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
    }

    @Test
    void whenUpdatingQuality_itShouldNotBeNegative() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(app.items[0].quality >= 0);
    }

    @Test
    void whenNameIsAgedBrie_shouldIncreaseQuality() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
    }

    @Test
    void whenNameIsAgedBrieAndSellinZero_shouldIncreaseQualityTwice() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void whenUpdatingQuality_qualityShouldNotBeMoreThan50() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void whenUpdatingQualityForSulfurs_shouldNotDecreaseQuality() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 2, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
    }

    @Test
    void whenUpdatingQualityForSulfurs_shouldNotSell() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 2, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].sellIn);
    }

    @Test
    void whenNameIsBackstage_shouldIncreaseQualityBy2ForSellingLessThan10() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void whenNameIsBackstage_shouldIncreaseQualityBy3ForSellingLessThan5() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
    }

    @Test
    void whenNameIsBackstage_shouldDropQualityToZeroAfterConcert() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void whenConjured_shouldDegradesTwiceAsFast() {
        Item[] items = new Item[]{new Item("Conjured", 10, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
    }

}
