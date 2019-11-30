package com.example.recipe.database;

public class Recipe {

    private int id;
    private String name;
    private String endOfWarrantyDate;

    public Recipe() {
    }

    public Recipe(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.endOfWarrantyDate = builder.endOfWarrantyDate;
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

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int id;
        private String name;
        private String endOfWarrantyDate;

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

        public Recipe build(){
            return new Recipe(this);
        }
    }
}
