package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemHelper itemHelper = ItemFactory.getItem(item);
            itemHelper.apply(item);
        }
    }

    static abstract class ItemHelper {
        void increaseQuality(Item item) {
            item.quality = Math.min(item.quality + 1, 50);
        }

        void decreaseQuality(Item item) {
            decreaseQuality(item, 1);
        }

        void decreaseQuality(Item item, int rate) {
            item.quality = Math.max(item.quality - rate, 0);
        }

        abstract void apply(Item item);
    }

    static class ItemFactory {
        public static ItemHelper getItem(Item item) {
            return switch (item.name) {
                case "Aged Brie" -> new AgedBrieItemHelper();
                case "Backstage passes to a TAFKAL80ETC concert" -> new BackstageItemHelper();
                case "Sulfuras, Hand of Ragnaros" -> new SulfurItemHelper();
                case "Conjured" -> new ConjuredItemHelper();
                default -> new OtherItemHelper();
            };
        }
    }

    static class AgedBrieItemHelper extends ItemHelper {
        @Override
        void apply(Item item) {
            increaseQuality(item);
            item.sellIn -= 1;
            if (item.sellIn < 0) {
                increaseQuality(item);
            }
        }
    }

    static class BackstageItemHelper extends ItemHelper {
        @Override
        void apply(Item item) {
            increaseQuality(item);

            if (item.quality < 50 && item.sellIn < 11) {
                increaseQuality(item);
            }
            if (item.quality < 50 && item.sellIn < 6) {
                increaseQuality(item);
            }

            item.sellIn -= 1;
            if (item.sellIn < 0) {
                item.quality = 0;
            }
        }
    }

    static class SulfurItemHelper extends ItemHelper {
        @Override
        void apply(Item item) {
            // Being a legendary item, never has to be sold or decreases in Quality
        }
    }

    static class OtherItemHelper extends ItemHelper {
        @Override
        void apply(Item item) {
            decreaseQuality(item);
            item.sellIn -= 1;
            if (item.sellIn < 0) {
                decreaseQuality(item);
            }
        }
    }

    static class ConjuredItemHelper extends ItemHelper {
        @Override
        void apply(Item item) {
            decreaseQuality(item, 2);
            item.sellIn -= 1;
        }
    }

}
