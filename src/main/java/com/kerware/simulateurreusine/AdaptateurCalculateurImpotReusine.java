package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

public class AdaptateurCalculateurImpotReusine implements ICalculateurImpot {

    private final SimulateurReusine simulateur;
    private int revenuNet1;
    private int revenuNet2;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    public AdaptateurCalculateurImpotReusine() {
        this.simulateur = new SimulateurReusine();
    }

    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenuNet1 = rn;
    }

    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenuNet2 = rn;
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfants = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsHandicapes = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        simulateur.calculerImpot(
                revenuNet1,
                revenuNet2,
                situationFamiliale,
                nbEnfants,
                nbEnfantsHandicapes,
                parentIsole
        );
    }

    @Override
    public int getRevenuNetDeclatant1() {
        return revenuNet1;
    }

    @Override
    public int getRevenuNetDeclatant2() {
        return revenuNet2;
    }

    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContributionExceptionnelle();
    }

    @Override
    public int getRevenuFiscalReference() {
        return (int) simulateur.getRevenuFiscalReference();
    }

    @Override
    public int getAbattement() {
        return (int) simulateur.getAbattement();
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNombreParts();
    }

    @Override
    public int getImpotAvantDecote() {
        return (int) simulateur.getImpotAvantDecote();
    }

    @Override
    public int getDecote() {
        return (int) simulateur.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}
