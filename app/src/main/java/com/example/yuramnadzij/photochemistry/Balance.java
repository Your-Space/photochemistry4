package com.example.yuramnadzij.photochemistry;

import java.util.Arrays;

import static java.lang.Character.isLetter;

public class Balance extends  ChemicalReaction {
    private ChemicalReaction reaction;
    private ChemicalElement[] fSide;
    private ChemicalElement[] sSide;
    private ChemicalElement[] forChecking;
    private ChemicalElement[] forCheckingAn;
   // private boolean cannotBalance;

    Balance(ChemicalReaction reaction){
        this.reaction = reaction;
     //   cannotBalance = false;
        reaction.setReaction(startBalanceTheReaction());
    }

    private boolean checkTheBalance(){
//      first side
        int numberOfElOnSide = 0;
        for (int i = 0; i < reaction.oneSide.length; i++){
            numberOfElOnSide += reaction.oneSide[i].getCountOfElements();
        }
        fSide = new ChemicalElement[numberOfElOnSide];
        for (int i = 0; i < numberOfElOnSide; i++)
            fSide[i] = new ChemicalElement();
        for (int i = 0; i < numberOfElOnSide; i++){
            for(int j = 0; j < reaction.oneSide.length; j++)
            {
                for(int k = 0; k < reaction.oneSide[j].getCountOfElements(); k++){
                    fSide[i].setElement(reaction.oneSide[j].getElements()[k].getElement());
                    fSide[i].setIndex(reaction.oneSide[j].getElements()[k].getIndex());
                    i++;
                }
            }
        }

        forChecking = new ChemicalElement[1];
        forChecking[0] = new ChemicalElement();
        boolean gotIt;
        for(int i = 0; i < numberOfElOnSide; i++){
            if(fSide[i].getIndex() == -3) continue;
            gotIt = false;
            for(int j = 0; j < numberOfElOnSide; j++){
                if(i == j) continue;
                if(fSide[j].getIndex() == -3) continue;
                if(i == 0 && !gotIt) {
                    forChecking[i] = fSide[i];
                    gotIt = true;
                }
                if(!gotIt && i != 0){
                    forChecking = Arrays.copyOf(forChecking, forChecking.length + 1);
                    forChecking[forChecking.length - 1] = fSide[i];
                    gotIt = true;
                }
                if(fSide[i].getElement().equals(fSide[j].getElement())){
                    forChecking[forChecking.length - 1].setIndex(forChecking[forChecking.length - 1].getIndex()
                            + fSide[j].getIndex());
                    fSide[j].setIndex(-3);
                }
            }
        }

//          first side

//          second side
        int numberOfElAnSide = 0;
        for (int i = 0; i < reaction.anotherSide.length; i++){
            numberOfElAnSide += reaction.anotherSide[i].getCountOfElements();
        }
        sSide = new ChemicalElement[numberOfElAnSide];
        for (int i = 0; i < numberOfElAnSide; i++)
            sSide[i] = new ChemicalElement();
        for (int i = 0; i < numberOfElAnSide; i++){
            for(int j = 0; j < reaction.anotherSide.length; j++)
            {
                for(int k = 0; k < reaction.anotherSide[j].getCountOfElements(); k++){
                    sSide[i].setElement(reaction.anotherSide[j].getElements()[k].getElement());
                    sSide[i].setIndex(reaction.anotherSide[j].getElements()[k].getIndex());
                    i++;
                }
            }
        }

        forCheckingAn = new ChemicalElement[1];
        forCheckingAn[0] = new ChemicalElement();
        boolean gotItt;
        for(int i = 0; i < numberOfElAnSide; i++){
            if(sSide[i].getIndex() == -3) continue;
            gotItt = false;
            for(int j = 0; j < numberOfElAnSide; j++){
                if(i == j) continue;
                if(sSide[j].getIndex() == -3) continue;
                if(i == 0 && !gotItt) {
                    forCheckingAn[i] = sSide[i];
                    gotItt = true;
                }
                if(!gotItt && i != 0){
                    forCheckingAn = Arrays.copyOf(forCheckingAn, forCheckingAn.length + 1);
                    forCheckingAn[forCheckingAn.length - 1] = sSide[i];
                    gotItt = true;
                }
                if(sSide[i].getElement().equals(sSide[j].getElement())){
                    forCheckingAn[forCheckingAn.length - 1].setIndex(forCheckingAn[forCheckingAn.length - 1].getIndex()
                            + sSide[j].getIndex());
                    sSide[j].setIndex(-3);
                }
            }
        }
////////////////////////////////////
        if(forChecking.length != forCheckingAn.length) {
            //cannotBalance = true;
            return false;
        }
        else{
            boolean similar = false;
            for(int i = 0; i < forChecking.length; i++){
                for(int j = 0; j < forCheckingAn.length; j++){
                    if(forChecking[i].getElement().equals(forCheckingAn[j].getElement()) &&
                            forChecking[i].getIndex() == forCheckingAn[j].getIndex()) {
                        similar = true;
                        break;
                    }
                    else similar = false;
                }
                if(!similar) {
                   // cannotBalance = true;
                    return false;
                }
            }
        }
        return true;
    }

    private void getConstantLost(){
        for(int i = 0; i < reaction.oneSide.length; i++){
                boolean ch = false;
                String newSide = "";
                for(int j = 0; j < reaction.oneSide[i].getFormula().length(); j++){
                    if(isLetter(reaction.oneSide[i].getFormula().charAt(j)) && !ch)
                        ch = true;
                    if(ch)
                        newSide += reaction.oneSide[i].getFormula().charAt(j);
                }
                reaction.oneSide[i].setFormula(newSide);
                reaction.oneSide[i].setChemicalElements();
                reaction.oneSide[i].setChemicalElementsFormulaAndIndex();
                reaction.oneSide[i].setConstant(1);
        }

        for(int i = 0; i < reaction.anotherSide.length; i++){
                boolean ch = false;
                String newSide = "";
                for(int j = 0; j < reaction.anotherSide[i].getFormula().length(); j++){
                    if(isLetter(reaction.anotherSide[i].getFormula().charAt(j)) && !ch)
                        ch = true;
                    if(ch)
                        newSide += reaction.anotherSide[i].getFormula().charAt(j);
                }
                reaction.anotherSide[i].setFormula(newSide);
                reaction.anotherSide[i].setChemicalElements();
                reaction.anotherSide[i].setChemicalElementsFormulaAndIndex();
                reaction.anotherSide[i].setConstant(1);
        }
    }


    private void setTheDifference(){
        fSide = new ChemicalElement[1];
        fSide[0] = new ChemicalElement();
        sSide = new ChemicalElement[1];
        sSide[0] = new ChemicalElement();
        for(int i = 0; i < forChecking.length; i++){
            for(int j = 0; j < forCheckingAn.length; j++){
                if(forChecking[i].getElement().equals(forCheckingAn[j].getElement())){
                    if(forChecking[i].getIndex() == forCheckingAn[j].getIndex()) break;
                    int p = forChecking[i].getIndex() - forCheckingAn[j].getIndex();
                    if(p > 0){
                        if(sSide[0].getIndex() == 0) {
                            sSide[0].setElement(forChecking[i].getElement());
                            sSide[0].setIndex(p);
                        } else {
                            sSide = Arrays.copyOf(sSide, sSide.length + 1);
                            sSide[sSide.length - 1] = new ChemicalElement();
                            sSide[sSide.length - 1].setElement(forChecking[i].getElement());
                            sSide[sSide.length - 1].setIndex(p);
                        }
                    } else {
                        if(fSide[0].getIndex() == 0) {
                            fSide[0].setElement(forCheckingAn[j].getElement());
                            fSide[0].setIndex(Math.abs(p));
                        } else {
                            fSide = Arrays.copyOf(fSide, fSide.length + 1);
                            fSide[fSide.length - 1] = new ChemicalElement();
                            fSide[fSide.length - 1].setElement(forCheckingAn[j].getElement());
                            fSide[fSide.length - 1].setIndex(Math.abs(p));
                        }
                    }
                    break;
                }
            }
        }
    }

    private String startBalanceTheReaction(){
        if(checkTheBalance()){
            return reaction.getReaction();
        }
        //if(cannotBalance) return reaction.getReaction();
        getConstantLost();
        if(checkTheBalance()){
            return reaction.getReaction();
        }
        int pos, el, sk, pos1, el1, sk1;
        boolean ch;
        do{
            pos = -1;
            el = 1234;
            sk = 0;
            pos1 = -1;
            el1 = 1234;
            sk1 = 0;
            ch = false;
            setTheDifference();
            if(fSide[0].getIndex() != 0) {
                for (int i = 0; i < fSide.length; i++) {
                    for (int j = 0; j < reaction.oneSide.length; j++) {
                        for (int k = 0; k < reaction.oneSide[j].getElements().length; k++) {
                            if (reaction.oneSide[j].getElements()[k].getElement().equals(fSide[i].getElement())) {
                                if(reaction.oneSide[j].getElements().length < el1){
                                    pos1 = j;
                                    el1 = reaction.oneSide[j].getElements().length;
                                    sk1 = k;
                                }
                            }
                        }
                        if(j == reaction.oneSide.length - 1 && pos1 != -1){
                            if((fSide[i].getIndex() % 2 == 1 && reaction.oneSide[pos1].getElements()[sk1].getIndex()/reaction.oneSide[pos1].getConstant() != 1)
                                    || fSide[i].getIndex() < (reaction.oneSide[pos1].getElements()[sk1].getIndex()/reaction.oneSide[pos1].getConstant())){
                                if(sSide.length >= 1 && sSide[0].getIndex() != 0) break;
                                int Number = 0;
                                for (int d = 0; d < reaction.anotherSide.length; d++){
                                    for(int f = 0; f < reaction.anotherSide[d].getElements().length; f++) {
                                        if (reaction.anotherSide[d].getElements()[f].getElement().equals(fSide[i].getElement())) {
                                            Number = reaction.anotherSide[d].getElements()[f].getIndex();
                                        }
                                    }
                                }
                                if(minimalDivider(reaction.oneSide[pos1].getElements()[sk1].getIndex(),
                                        Number)/reaction.oneSide[pos1].getElements()[sk1].getIndex() * reaction.oneSide[pos1].getConstant() == 1){
                                    continue;
                                }
                                reaction.oneSide[pos1].setConstant(minimalDivider(reaction.oneSide[pos1].getElements()[sk1].getIndex(),
                                        Number)/reaction.oneSide[pos1].getElements()[sk1].getIndex());
                                ch = true;
                                i = fSide.length;
                                break;
                            }
                            reaction.oneSide[pos1].setConstant(reaction.oneSide[pos1].getConstant()
                                    + fSide[i].getIndex() / (reaction.oneSide[pos1].getElements()[sk1].getIndex()/reaction.oneSide[pos1].getConstant()));
                            ch = true;
                            i = fSide.length;
                        }
                    }
                }
            }
            if(sSide[0].getIndex() != 0 && !ch) {
                for (int i = 0; i < sSide.length; i++) {
                    for (int j = 0; j < reaction.anotherSide.length; j++) {
                        for (int k = 0; k < reaction.anotherSide[j].getElements().length; k++) {
                            if (reaction.anotherSide[j].getElements()[k].getElement().equals(sSide[i].getElement())) {
                                if(reaction.anotherSide[j].getElements().length < el){
                                    pos = j;
                                    el = reaction.anotherSide[j].getElements().length;
                                    sk = k;
                                }
                            }
                        }
                        if((j == reaction.anotherSide.length - 1 && pos != -1)){
                            if((sSide[i].getIndex() % 2 == 1 &&
                                    reaction.anotherSide[pos].getElements()[sk].getIndex()/reaction.anotherSide[pos].getConstant() != 1)
                                    || sSide[i].getIndex() < (reaction.anotherSide[pos].getElements()[sk].getIndex()/reaction.anotherSide[pos].getConstant())) {
                                int Number = 0;
                                for (int d = 0; d < reaction.oneSide.length; d++){
                                    for(int f = 0; f < reaction.oneSide[d].getElements().length; f++) {
                                        if (reaction.oneSide[d].getElements()[f].getElement().equals(sSide[i].getElement())) {
                                            Number = reaction.oneSide[d].getElements()[f].getIndex();
                                        }
                                    }
                                }
                                reaction.anotherSide[pos].setConstant(minimalDivider(reaction.anotherSide[pos].getElements()[sk].getIndex(),
                                        Number)/reaction.anotherSide[pos].getElements()[sk].getIndex());
                                i = sSide.length;
                                break;
                            }
                            reaction.anotherSide[pos].setConstant(reaction.anotherSide[pos].getConstant()
                                    + sSide[i].getIndex() / (reaction.anotherSide[pos].getElements()[sk].getIndex()/reaction.anotherSide[pos].getConstant()));
                            i = sSide.length;
                        }
                    }
                }
            }
        } while (!checkTheBalance());

        //lower constant
        int lowC = maxDivider(reaction.oneSide[0].getConstant(), reaction.anotherSide[0].getConstant());
        for(int i = 0; i < reaction.oneSide.length; i++){
            for(int j = 0; j < reaction.anotherSide.length; j++){
                int n = maxDivider(reaction.oneSide[i].getConstant(), reaction.anotherSide[j].getConstant());
                if(lowC > n) lowC = n;
            }
        }
        boolean divide = true;
        for(int i = 0; i < reaction.oneSide.length; i++)
            if(reaction.oneSide[i].getConstant() % lowC != 0)
                divide = false;
        for(int i = 0; i < reaction.anotherSide.length; i++)
            if(reaction.anotherSide[i].getConstant() % lowC != 0)
                divide = false;
        if(divide){
            for(int i = 0; i < reaction.oneSide.length; i++)
                reaction.oneSide[i].setConstant(reaction.oneSide[i].getConstant()/lowC);
            for(int i = 0; i < reaction.anotherSide.length; i++)
                reaction.anotherSide[i].setConstant(reaction.anotherSide[i].getConstant()/lowC);
        }

        return createBalancedReaction(reaction.oneSide, reaction.anotherSide);
    }


    private String createBalancedReaction(ChemicalFormula[] one, ChemicalFormula[] two){
        String reaction = "";
        for (int i = 0; i < one.length; i++){
            reaction += one[i].getFormula();
            if(i != one.length - 1){
                reaction += "+";
            }
        }
        reaction += "=";
        for (int i = 0; i < two.length; i++){
            reaction += two[i].getFormula();
            if(i != two.length - 1){
                reaction += "+";
            }
        }
        return reaction;
    }

    private static int minimalDivider ( int fi, int si) {
        int m = 0;
        int count = 1;
        while (m == 0) {
            if (count % fi == 0 && count % si == 0)
                m = count;
            count++;
        }
        return m;
    }

    private static int maxDivider(int fi,int si) {
        int min;
        int m = 0;
        if(fi > si)
            min = si;
        else
            min = fi;
        for(int count = 1;count <= min;count++) {
            if(fi % count == 0 && si % count == 0){
                if(count > m)
                    m = count;
            }
        }
        return m;
    }

}