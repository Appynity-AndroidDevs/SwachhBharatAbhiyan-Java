package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class TableDataCountPojo {

    public class WorkHistory {

        private String houseCollection;
        private String LiquidCollection;
        private String StreetCollection;

        private String date;

        private String pointCollection;

        private String DumpYardCollection;
        private String DumpYardPlantCollection; // added by rahul

        public String getDumpYardPlantCollection() {
            return DumpYardPlantCollection;
        }

        public void setDumpYardPlantCollection(String dumpYardPlantCollection) {
            DumpYardPlantCollection = dumpYardPlantCollection;
        }

        public String getHouseCollection() {
            return houseCollection;
        }

        public void setHouseCollection(String houseCollection) {
            this.houseCollection = houseCollection;
        }

        public String getLiquidCollection() {
            return LiquidCollection;
        }

        public void setLiquidCollection(String liquidCollection) {
            this.LiquidCollection = liquidCollection;
        }

        public String getStreetCollection() {
            return StreetCollection;
        }

        public void setStreetCollection(String StreetCollection) {
            this.StreetCollection = StreetCollection;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPointCollection() {
            return pointCollection;
        }

        public void setPointCollection(String pointCollection) {
            this.pointCollection = pointCollection;
        }

        public String getDumpYardCollection() {
            return DumpYardCollection;
        }

        public void setDumpYardCollection(String dumpYardCollection) {
            DumpYardCollection = dumpYardCollection;
        }

        @Override
        public String toString() {
            return "TableDataCountPojo{"
                    + "houseCollection='" + houseCollection + '\''
                    + ", date='" + date + '\''
                    + ", pointCollection='" + pointCollection + '\''
                    + ", DumpYardCollection='" + DumpYardCollection + '\''
                    + ", LiquidCollection='" + LiquidCollection + '\''
                    + ", StreetCollection='" + StreetCollection + '\''
                    + ", DumpYardPlantCollection='" + DumpYardPlantCollection + '\''
                    + '}';
        }
    }

    public class LocationCollectionCount {
        int CollectionCount;
        int LocationCount;

        public int getCollectionCount() {
            return CollectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            CollectionCount = collectionCount;
        }

        @Override
        public String toString() {
            return "LocationCollectionCount{" +
                    "CollectionCount=" + CollectionCount +
                    ", LocationCount=" + LocationCount +
                    '}';
        }

        public int getLocationCount() {
            return LocationCount;
        }

        public void setLocationCount(int locationCount) {
            LocationCount = locationCount;
        }
    }

    public class WasteAddHistory {

    }
}
