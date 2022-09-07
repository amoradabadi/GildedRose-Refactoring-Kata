package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    static abstract class MyItem extends Item {
        public MyItem(String name, int sellIn, int quality) {
            super(name, sellIn, quality);
        }

        abstract Item apply();
    }

    static class MyItemFactory {
        public static MyItem getItem(Item item) {
            return switch (item.name) {
                case "Aged Brie" -> new AgedBrieItem(item);
                case "Backstage passes to a TAFKAL80ETC concert" -> new BackstageItem(item);
                case "Sulfuras, Hand of Ragnaros" -> new SulfurItem(item);
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
            if (this.quality < 50) {
                this.quality += 1;
            }

            this.sellIn -= 1;

            if (this.sellIn < 0) {
                if (this.quality < 50) {
                    this.quality += 1;
                }
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
            if (this.quality < 50) {
                this.quality += 1;

                if (this.sellIn < 11) {
                    if (this.quality < 50) {
                        this.quality += 1;
                    }
                }

                if (this.sellIn < 6) {
                    if (this.quality < 50) {
                        this.quality += 1;
                    }
                }
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
            if (this.quality > 0) {
                this.quality -= 1;
            }

            this.sellIn -= 1;

            if (this.sellIn < 0) {
                if (this.quality > 0) {
                    this.quality -= 1;
                }
            }
            return this;
        }
    }

    public void updateQuality() {
        for (Item item : items) {
            MyItem myItem = MyItemFactory.getItem(item);
            Item newItem = myItem.apply();
            item.quality = newItem.quality;
            item.sellIn = newItem.sellIn;
//            if (!(item.name.equals("Aged Brie")
//                || item.name.equals("Backstage passes to a TAFKAL80ETC concert"))) {
//                if (item.quality > 0) {
//                    if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
//                        item.quality = item.quality - 1;
//                    }
//                }
//            } else {
//                if (item.quality < 50) {
//                    item.quality = item.quality + 1;
//
//                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (item.sellIn < 11) {
//                            if (item.quality < 50) {
//                                item.quality = item.quality + 1;
//                            }
//                        }
//
//                        if (item.sellIn < 6) {
//                            if (item.quality < 50) {
//                                item.quality = item.quality + 1;
//                            }
//                        }
//                    }
//                }
//            }

//            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
//                item.sellIn = item.sellIn - 1;
//            }

//            if (item.sellIn < 0) {
//                if (!item.name.equals("Aged Brie")) {
//                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (item.quality > 0) {
//                            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
//                                item.quality = item.quality - 1;
//                            }
//                        }
//                    } else {
//                        item.quality = 0;
//                    }
//                } else {
//                    if (item.quality < 50) {
//                        item.quality = item.quality + 1;
//                    }
//                }
//            }
        }
    }


}
