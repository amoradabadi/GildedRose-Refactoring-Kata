package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void whenSellHasPassed_shouldDegradesTwiceAsFast() {
        Item[] items = new Item[]{new Item("foo", 1, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void whenUpdatingQuality_shouldNotBeNegative() {
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
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void whenUpdatingQuality_qualityShouldNotBeMoreThan50() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void whenUpdatingQualityForSulfurs_shouldNotDecreaseQuality() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 2, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
    }

    @Test
    void whenUpdatingQualityForSulfurs_shouldNotSell() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 2, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].sellIn);
        app.updateQuality();
        assertEquals(2, app.items[0].sellIn);
    }

    // Q inc by 2 when days <= 10
    // Q inc by 3 when days <= 5
    // Q == 0 when days == 0
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
    void whenNameIsBackstage_shouldIncreaseQualityBy3ForAfterConcert() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

}
