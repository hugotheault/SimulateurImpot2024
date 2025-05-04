package com.kerware.simulateur;

public class AdaptateurSimulateur implements ICalculateurImpot {

    private Simulateur simulateur = new Simulateur();

    private int revenusNetDecl1 = 0;
    private int revenusNetDecl2 = 0;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;


    /**
     *
     * @param rn
     */
    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenusNetDecl1 = rn;
    }

    /**
     *
     * @param rn
     */
    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenusNetDecl2 = rn;
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
        this.nbEnfantsACharge = nbe;
    }

    /**
     *
     * @param nbesh
     */
    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsSituationHandicap = nbesh;
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
         simulateur.calculImpot(revenusNetDecl1, revenusNetDecl2 ,
                 situationFamiliale, nbEnfantsACharge, nbEnfantsSituationHandicap, parentIsole);
    }

    /**
     *
     * @return
     */
    @Override
    public int getRevenuNetDeclatant1() {
        return revenusNetDecl1;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRevenuNetDeclatant2() {
        return revenusNetDecl2;
    }

    /**
     *
     * @return
     */
    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContribExceptionnelle();
    }

    /**
     *
     * @return
     */
    @Override
    public int getRevenuFiscalReference() {
        return (int)simulateur.getRevenuReference();
    }

    /**
     *
     * @return
     */
    @Override
    public int getAbattement() {
        return (int)simulateur.getAbattement();
    }

    /**
     *
     * @return
     */
    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNbParts();
    }

    /**
     *
     * @return
     */
    @Override
    public int getImpotAvantDecote() {
        return (int)simulateur.getImpotAvantDecote();
    }

    /**
     *
     * @return
     */
    @Override
    public int getDecote() {
        return (int)simulateur.getDecote();
    }

    /**
     *
     * @return
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int)simulateur.getImpotNet();
    }
}
