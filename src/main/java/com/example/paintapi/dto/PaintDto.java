package com.example.paintapi.dto;

public class PaintDto {
    private String name;
    private String type;
    private int red;
    private int green;
    private int blue;
    private int amount;
    private String category;

    public void setName(String Name) {
        this.name = Name;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public void setCategory(String Category) {
        this.category = Category;
    }

    public void setRed(int Red) {
        this.red = Red;
    }

    public void setGreen(int Green) {
        this.green = Green;
    }

    public void setBlue(int Blue) {
        this.blue = Blue;
    }

    public void setAmount(int Amount) {
        this.amount = Amount;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getCategory() {
        return this.category;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public int getAmount() {
        return this.amount;
    }
}
