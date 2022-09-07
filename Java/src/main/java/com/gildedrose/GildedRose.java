package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            MyItem myItem = MyItemFactory.getItem(item);
            Item newItem = myItem.apply();
            item.quality = newItem.quality;
            item.sellIn = newItem.sellIn;
        }
    }

    static abstract class MyItem extends Item {
        public MyItem(String name, int sellIn, int quality) {
            super(name, sellIn, quality);
        }

        void increaseQuality() {
            this.quality = Math.min(this.quality + 1, 50);
        }

        void decreaseQuality() {
            decreaseQuality(1);
        }

        void decreaseQuality(int rate) {
            this.quality = Math.max(this.quality - rate, 0);
        }

        abstract Item apply();
    }

    static class MyItemFactory {
        public static MyItem getItem(Item item) {
            return switch (item.name) {
                case "Aged Brie" -> new AgedBrieItem(item);
                case "Backstage passes to a TAFKAL80ETC concert" -> new BackstageItem(item);
                case "Sulfuras, Hand of Ragnaros" -> new SulfurItem(item);
                case "Conjured" -> new ConjuredItem(item);
                default -> new OtherItem(item);
            };
        }
    }

    static class AgedBrieItem extends MyItem {
        public AgedBrieItem(Item item) {
            super(item.name, item.sellIn, item.quality);
        }

        @Override
        Item apply() {
            increaseQuality();

            this.sellIn -= 1;

            if (this.sellIn < 0) {
                increaseQuality();
            }
            return this;
        }
    }

    static class BackstageItem extends MyItem {
        public BackstageItem(Item item) {
            super(item.name, item.sellIn, item.quality);
        }

        @Override
        Item apply() {
            increaseQuality();

            if (this.quality < 50 && this.sellIn < 11) {
                increaseQuality();
            }

            if (this.quality < 50 && this.sellIn < 6) {
                increaseQuality();
            }

            this.sellIn -= 1;

            if (this.sellIn < 0) {
                this.quality = 0;
            }
            return this;
        }

    }

    static class SulfurItem extends MyItem {
        public SulfurItem(Item item) {
            super(item.name, item.sellIn, item.quality);
        }

        @Override
        Item apply() {
            // Being a legendary item, never has to be sold or decreases in Quality
            return this;
        }
    }

    static class OtherItem extends MyItem {
        public OtherItem(Item item) {
            super(item.name, item.sellIn, item.quality);
        }

        @Override
        Item apply() {
            decreaseQuality();

            this.sellIn -= 1;

            if (this.sellIn < 0) {
                decreaseQuality();
            }
            return this;
        }
    }

    static class ConjuredItem extends MyItem {
        public ConjuredItem(Item item) {
            super(item.name, item.sellIn, item.quality);
        }

        @Override
        Item apply() {
            decreaseQuality(2);

            this.sellIn -= 1;

            return this;
        }
    }

}
