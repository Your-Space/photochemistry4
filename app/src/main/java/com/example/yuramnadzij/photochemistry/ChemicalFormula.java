package com.example.yuramnadzij.photochemistry;

import static java.lang.Character.isLetter;
import static java.lang.Character.isUpperCase;
import  static  java.lang.Character.isDigit;

public class ChemicalFormula {
    protected String formula;
    private boolean isChild;
    private ChemicalElement[] elements;
    private ChemicalFormula[] child;
    private int countOfElements;
    private int constant;

    ChemicalFormula(){
        formula = "";
        constant = 0;
        countOfElements = 0;
        isChild = false;
    }

    public void setFormula(String formula){
        this.formula = formula;
    }

    private void setChild(boolean child) {
        isChild = child;
    }

    public String getFormula(){
        return formula;
    }

    public int getCountOfElements() { return countOfElements; }

    public void setChemicalElements(){
        if(isChild)
            countOfElements = 0;
        for(int i = 0; i < formula.length(); i++)
            if(isUpperCase(formula.charAt(i)) && isLetter(formula.charAt(i))) {
                countOfElements++;
            }
        elements = new ChemicalElement[countOfElements];
        for(int i = 0; i < countOfElements; i++)
            elements[i] = new ChemicalElement();
    }

    private void setChild(){
        int p = 0;
        for(int i = 0; i < formula.length(); i++){
            if(formula.charAt(i) == '(') p++;
        }
        if(p != 0){
            child = new ChemicalFormula[p];
            for(int i = 0; i < p; i++) {
                child[i] = new ChemicalFormula();
                child[i].setChild(true);
            }
        } else
            child = null;
    }

    public void setChemicalElementsFormulaAndIndex(){
        if(constant == 0) {
            for (int i = 0; i < formula.length(); i++) {
                if (isLetter(formula.charAt(i))) {
                    if (constant == 0) constant = 1;
                    break;
                }
                if (isDigit(formula.charAt(i)))
                    constant = constant * 10 + (formula.charAt(0) - '0');
            }
        }
        setChild();
        int countEl = -1;
        int c = -1;
        for(int i = 0; i < formula.length(); i++){
            if(formula.charAt(i) == '('){
                i++;
                c++;
                do{
                    child[c].setFormula(child[c].getFormula() + formula.charAt(i));
                    i++;
                } while(formula.charAt(i) != ')');
                i++;
                if(i == formula.length()) {
                    child[c].setChemicalElements();
                    child[c].setChemicalElementsFormulaAndIndex();
                    child[c].setConstant(1);
                    for(int j = 0; j < child[c].elements.length; j++){
                        countEl++;
                        elements[countEl].setElement(child[c].elements[j].getElement());
                        elements[countEl].setIndex(child[c].elements[j].getIndex());
                        if(elements[countEl].getIndex() == 0)
                            elements[countEl].setIndex(constant);
                        else elements[countEl].setIndex(elements[countEl].getIndex() * constant);
                    }
                    break;
                }
                int number = 0;
                do {
                    number = number*10 + (formula.charAt(i) - '0');
                    i++;
                    if(i == formula.length()) break;
                } while(isDigit(formula.charAt(i)));
                child[c].setChemicalElements();
                child[c].setChemicalElementsFormulaAndIndex();
                child[c].setConstant(number);
                for(int j = 0; j < child[c].elements.length; j++){
                    countEl++;
                    elements[countEl].setElement(child[c].elements[j].getElement());
                    elements[countEl].setIndex(child[c].elements[j].getIndex());
                    if(elements[countEl].getIndex() == 0)
                        elements[countEl].setIndex(constant);
                    else elements[countEl].setIndex(elements[countEl].getIndex() * constant);
                }
                //Ba3(PO4)2+H2O=Ba(OH)2+H3PO4
            } else if(isUpperCase(formula.charAt(i)) && isLetter(formula.charAt(i))){
                countEl++;
                do{
                    if(isLetter(formula.charAt(i)))
                        elements[countEl].setElement(elements[countEl].getElement() + (formula.charAt(i)));
                    else {
                        elements[countEl].setIndex(elements[countEl].getIndex() * 10 + (formula.charAt(i) - '0'));
                    }
                    i++;
                    if(i == formula.length())
                        break;
                } while((!isUpperCase(formula.charAt(i)) && isLetter(formula.charAt(i))) ||
                    isDigit(formula.charAt(i)));
                i--;
                if(elements[countEl].getIndex() == 0)
                    elements[countEl].setIndex(constant);
                else elements[countEl].setIndex(elements[countEl].getIndex() * constant);
            }
        }
    }

    public ChemicalElement[] getElements(){
        return elements;
    }

    public int getConstant(){
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
        String form = "";
        boolean first = false;
        int p = 0;
        for(int i = 0; i < formula.length(); i++){
            if(isLetter(formula.charAt(i)) && !first) {
                first = true;
                p = i;
            }
        }
        for(int i = p; i < formula.length(); i++){
            form += formula.charAt(i);
        }
        if(constant != 1)
            formula = constant + form;
        setChemicalElements();
        setChemicalElementsFormulaAndIndex();
    }

}
