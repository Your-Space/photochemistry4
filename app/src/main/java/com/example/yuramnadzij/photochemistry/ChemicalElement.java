package com.example.yuramnadzij.photochemistry;

public class ChemicalElement {
    private String element;
    private int index;

    ChemicalElement(){
        element = "";
        index = 0;
    }

    public void setElement(String element){
        this.element = element;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getElement(){
        return element;
    }
}
