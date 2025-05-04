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

    /**
     *
     * @param rn
     */
    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenuNet1 = rn;
    }

    /**
     *
     * @param rn
     */
    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenuNet2 = rn;
    }

    /**
     *
     * @param sf
     */
    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    /**
     *
     * @param nbe
     */
    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfants = nbe;
    }

    /**
     *
     * @param nbesh
     */
    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsHandicapes = nbesh;
    }

    /**
     *
     * @param pi
     */
    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    /**
     *
     */
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

    /**
     *
     * @return
     */
    @Override
    public int getRevenuNetDeclatant1() {
        return revenuNet1;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRevenuNetDeclatant2() {
        return revenuNet2;
    }

    /**
     *
     * @return
     */
    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContributionExceptionnelle();
    }

    /**
     *
     * @return
     */
    @Override
    public int getRevenuFiscalReference() {
        return (int) simulateur.getRevenuFiscalReference();
    }

    /**
     *
     * @return
     */
    @Override
    public int getAbattement() {
        return (int) simulateur.getAbattement();
    }

    /**
     *
     * @return
     */
    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNombreParts();
    }

    /**
     *
     * @return
     */
    @Override
    public int getImpotAvantDecote() {
        return (int) simulateur.getImpotAvantDecote();
    }

    /**
     *
     * @return
     */
    @Override
    public int getDecote() {
        return (int) simulateur.getDecote();
    }

    /**
     * 
     * @return
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}
