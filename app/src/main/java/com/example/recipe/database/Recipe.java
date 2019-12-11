package com.example.recipe.database;

public class Recipe {

    private int id;
    private String name;
    private String endOfWarrantyDate;
    private String latitude;
    private String longitude;

    public Recipe() {
    }

    public Recipe(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.endOfWarrantyDate = builder.endOfWarrantyDate;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEndOfWarrantyDate() {
        return endOfWarrantyDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int id;
        private String name;
        private String endOfWarrantyDate;
        private String latitude;
        private String longitude;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder endOfWarrantyDate(String endOfWarrantyDate) {
            this.endOfWarrantyDate = endOfWarrantyDate;
            return this;
        }

        public Builder latitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Recipe build(){
            return new Recipe(this);
        }
    }
}
