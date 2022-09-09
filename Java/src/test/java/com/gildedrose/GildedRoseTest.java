package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    public static final String FOO = "foo";
    public static final String CONJURED = "Conjured";

    private static void verifyItem(int sellIn, int quality, String name, Item item) {
        assertEquals("%s, %d, %d".formatted(name, sellIn, quality), item.toString());
    }

    @Test
    void whenUpdatingQuality_shouldKeepName() {
        Item[] items = new Item[]{new Item(FOO, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(-1, 0, FOO, app.items[0]);
    }

    @Test
    void whenSellInIsOne_shouldNotDecreaseQualityTwice() {
        Item[] items = new Item[]{new Item(FOO, 1, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(0, 1, FOO, app.items[0]);
    }

    @Test
    void whenSellHasPassed_shouldDegradesTwiceAsFast() {
        Item[] items = new Item[]{new Item(FOO, 0, 4)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(-1, 2, FOO, app.items[0]);
    }

    @Test
    void whenUpdating_qualityShouldNotBeNegative() {
        Item[] items = new Item[]{new Item(FOO, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(app.items[0].quality >= 0);
    }

    @Test
    void whenHavingWrongQuality_itShouldNotBeMoreThanMax() {
        Item[] items = new Item[]{new Item(FOO, 0, 52)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void whenNameIsAgedBrie_shouldIncreaseQuality() {
        Item[] items = new Item[]{new Item(AGED_BRIE, 2, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(1, 3, AGED_BRIE, app.items[0]);
    }

    @Test
    void whenNameIsAgedBrieAndNoSellIn_shouldIncreaseQualityTwice() {
        Item[] items = new Item[]{new Item(AGED_BRIE, 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(-1, 4, AGED_BRIE, app.items[0]);
    }

    @Test
    void whenNameIsAgedBrieAndOneSellIn_shouldIncreaseQuality() {
        Item[] items = new Item[]{new Item(AGED_BRIE, 1, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(0, 3, AGED_BRIE, app.items[0]);
    }

    @Test
    void whenUpdatingQuality_qualityShouldNotBeMoreThanMax() {
        Item[] items = new Item[]{new Item(AGED_BRIE, 2, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(1, 50, AGED_BRIE, app.items[0]);
    }

    @Test
    void whenUpdatingQualityForSulfurs_shouldNotDecreaseQualityOrSell() {
        Item[] items = new Item[]{new Item(SULFURAS, 2, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(2, 5, SULFURAS, app.items[0]);
    }

    @Test
    void whenNameIsBackstage_shouldIncreaseQualityBy2ForSellingLessThan11() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 10, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(9, 4, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenNameIsBackstage_shouldIncreaseQualityBy3ForSellingLessThan6() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 5, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(4, 5, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenNameIsBackstage_shouldDropQualityToZeroAfterConcert() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 0, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(-1, 0, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenNameIsBackstageAndSellIn11_shouldIncreaseQualityByOne() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 11, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(10, 3, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenNameIsBackstageAndSellIn6_shouldIncreaseQualityByTwo() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 6, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(5, 4, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenNameIsBackstage_shouldNotDropQualityToZeroIfStillHasSellIn() {
        Item[] items = new Item[]{new Item(BACKSTAGE, 1, 2)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(0, 5, BACKSTAGE, app.items[0]);
    }

    @Test
    void whenConjured_shouldDegradesTwiceAsFast() {
        Item[] items = new Item[]{new Item(CONJURED, 10, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        verifyItem(9, 3, CONJURED, app.items[0]);
    }

}
