package com.gildedrose;

import java.util.Map;

class GildedRose {
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private final Map<String, ItemHelper> itemHelperMap = Map.of(
        "Aged Brie", new AgedBrieItemHelper(),
        "Backstage passes to a TAFKAL80ETC concert", new BackstageItemHelper(),
        "Sulfuras, Hand of Ragnaros", new ItemHelper() {
        },
        "Conjured", new ConjuredItemHelper()
    );
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemHelper itemHelper = getItem(item);
            itemHelper.apply(item);
        }
    }

    private ItemHelper getItem(Item item) {
        return itemHelperMap.getOrDefault(item.name, new OtherItemHelper());
    }

    private interface ItemHelper {
        default void increaseQuality(Item item) {
            increaseQuality(item, 1);
        }

        default void increaseQuality(Item item, int rate) {
            item.quality = Math.min(item.quality + rate, MAX_QUALITY);
        }

        default void decreaseQuality(Item item) {
            decreaseQuality(item, 1);
        }

        default void decreaseQuality(Item item, int rate) {
            item.quality = Math.max(item.quality - rate, MIN_QUALITY);
        }

        default void decreaseSellIn(Item item) {
            item.sellIn -= 1;
        }

        default void apply(Item item) {
            // Override this method when needed
        }
    }

    private static class AgedBrieItemHelper implements ItemHelper {
        @Override
        public void apply(Item item) {
            increaseQuality(item);
            decreaseSellIn(item);
            if (item.sellIn < 0) {
                increaseQuality(item);
            }
        }
    }

    private static class BackstageItemHelper implements ItemHelper {
        @Override
        public void apply(Item item) {
            increaseQuality(item);

            if (item.sellIn < 6) {
                increaseQuality(item, 2);
            } else if (item.sellIn < 11) {
                increaseQuality(item);
            }

            decreaseSellIn(item);
            if (item.sellIn < 0) {
                item.quality = 0;
            }
        }
    }

    private static class OtherItemHelper implements ItemHelper {
        @Override
        public void apply(Item item) {
            decreaseQuality(item);
            decreaseSellIn(item);
            if (item.sellIn < 0) {
                decreaseQuality(item);
            }
        }
    }

    private static class ConjuredItemHelper implements ItemHelper {
        @Override
        public void apply(Item item) {
            decreaseQuality(item, 2);
            decreaseSellIn(item);
        }
    }

}
