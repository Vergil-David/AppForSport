package com.example.befit.model;

public class foodManager {
        private final String name;
        private final String imageUrl;
        private final String servingUnit;
        private double servingQty;
        private double calories, servingGram;
        private double totalFat, protein, carbohydrates;

        private final double originalCalories, originalServingGram;
        private final double originalTotalFat, originalProtein, originalCarbohydrates;

        public foodManager(String name, String imageUrl, String servingUnit, double servingQty
                , double calories, double servingGram, double totalFat, double protein, double carbohydrates) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.servingUnit = servingUnit;

            this.calories = this.originalCalories = roundToTwoDecimals(calories / servingQty);
            this.servingGram = this.originalServingGram = roundToTwoDecimals(servingGram / servingQty);
            this.totalFat = this.originalTotalFat = roundToTwoDecimals(totalFat / servingQty);
            this.protein = this.originalProtein = roundToTwoDecimals(protein / servingQty);
            this.carbohydrates = this.originalCarbohydrates = roundToTwoDecimals(carbohydrates / servingQty);

            this.servingQty = 1;
        }
        public void increaseQty() {
            servingQty++;
            updateNutritionalValues();
        }
        public void reduceQty() {
            if (servingQty > 1) {
                servingQty--;
                updateNutritionalValues();
            }
        }
        private void updateNutritionalValues() {
            calories = roundToTwoDecimals(originalCalories * servingQty);
            servingGram = roundToTwoDecimals(originalServingGram * servingQty);
            totalFat = roundToTwoDecimals(originalTotalFat * servingQty);
            protein = roundToTwoDecimals(originalProtein * servingQty);
            carbohydrates = roundToTwoDecimals(originalCarbohydrates * servingQty);
        }
        private double roundToTwoDecimals(double value) {
            return Math.round(value * 100.0) / 100.0;
        }
        public double getServingQty() {return servingQty;}
        public String getName() {return name;}
        public String getImageUrl() {return imageUrl;}
        public String getServingUnit() {return servingUnit;}
        public double getProtein() {return protein;}
        public double getCarbohydrates() {return carbohydrates;}
        public double getTotalFat() {return totalFat;}
        public double getCalories() {return calories;}
        public double getServingGram() {return servingGram;}
        public FoodItem getFoodItem() {
            return new FoodItem(name, imageUrl, servingUnit, (int)calories, (int)servingGram, totalFat, protein, carbohydrates);
        }
}


