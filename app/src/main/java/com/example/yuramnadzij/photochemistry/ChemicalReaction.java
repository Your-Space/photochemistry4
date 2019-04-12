package com.example.yuramnadzij.photochemistry;

public class ChemicalReaction {
    private String reaction;
    protected ChemicalFormula[] oneSide;
    protected ChemicalFormula[] anotherSide;
    private String start;
    private String end;
    private int oneSideCount;
    private int anotherSideCount;

    ChemicalReaction() {
        reaction = "";
        oneSide = null;
        anotherSide = null;
        start = "";
        end = "";
        oneSideCount = 1;
        anotherSideCount = 1;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getReaction(){ return reaction; }

    public void setStart() {
        int i = 0;
        do {
            start += reaction.charAt(i);
            i++;
        }
        while (reaction.charAt(i) != '=');
    }

    public void setEnd(){
        int i = 0;
        do i++;
        while (reaction.charAt(i) != '=');
        i++;
        do {
            end += reaction.charAt(i);
            i++;
        }
        while (i != reaction.length());
    }

    public void setSides(){
        for(int i = 0; i < start.length(); i++)
            if(start.charAt(i) == '+') {
                oneSideCount++;
            }
        oneSide = new ChemicalFormula[oneSideCount];
        for(int i = 0; i < oneSideCount; i++)
            oneSide[i] = new ChemicalFormula();


        for(int i = 0; i < end.length(); i++)
            if(end.charAt(i) == '+') {
                anotherSideCount++;
            }
        anotherSide = new ChemicalFormula[anotherSideCount];
        for(int i = 0; i < anotherSideCount; i++)
            anotherSide[i] = new ChemicalFormula();
    }

    public void setSidesFormulas(){
        //one side
        int p = 0;
        for (int i = 0; i < start.length(); i++){
            if(start.charAt(i) == '+') {
                oneSide[p].setChemicalElements();
                oneSide[p].setChemicalElementsFormulaAndIndex();
                p++;
                i++;
            }
            oneSide[p].setFormula(oneSide[p].getFormula() + start.charAt(i));
        }
        oneSide[p].setChemicalElements();
        oneSide[p].setChemicalElementsFormulaAndIndex();

        //another side
        p = 0;
        for(int i = 0; i < end.length(); i++){
            if(end.charAt(i) == '+') {
                anotherSide[p].setChemicalElements();
                anotherSide[p].setChemicalElementsFormulaAndIndex();
                p++;
                i++;
            }
                anotherSide[p].setFormula(anotherSide[p].getFormula() + end.charAt(i));
        }
        anotherSide[p].setChemicalElements();
        anotherSide[p].setChemicalElementsFormulaAndIndex();
    }

}
