package simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.AdaptateurCalculateurImpotReusine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdaptateurCalculateurImpotReusineTest {

    @Test
    public void testCalculImpot_CelibataireSansEnfants() {
        AdaptateurCalculateurImpotReusine adaptateur = new AdaptateurCalculateurImpotReusine();
        adaptateur.setRevenusNetDeclarant1(30000);
        adaptateur.setRevenusNetDeclarant2(0);
        adaptateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        adaptateur.setNbEnfantsACharge(0);
        adaptateur.setNbEnfantsSituationHandicap(0);
        adaptateur.setParentIsole(false);

        adaptateur.calculImpotSurRevenuNet();

        assertTrue(adaptateur.getImpotSurRevenuNet() > 0);
        assertEquals(30000, adaptateur.getRevenuNetDeclatant1());
        assertEquals(0, adaptateur.getRevenuNetDeclatant2());
        assertTrue(adaptateur.getNbPartsFoyerFiscal() > 0);
    }

    @Test
    public void testCalculImpot_CoupleAvecEnfants() {
        AdaptateurCalculateurImpotReusine adaptateur = new AdaptateurCalculateurImpotReusine();
        adaptateur.setRevenusNetDeclarant1(40000);
        adaptateur.setRevenusNetDeclarant2(30000);
        adaptateur.setSituationFamiliale(SituationFamiliale.MARIE);
        adaptateur.setNbEnfantsACharge(2);
        adaptateur.setNbEnfantsSituationHandicap(1);
        adaptateur.setParentIsole(false);

        adaptateur.calculImpotSurRevenuNet();

        assertTrue(adaptateur.getImpotSurRevenuNet() > 0);
        assertEquals(70000, adaptateur.getRevenuNetDeclatant1() + adaptateur.getRevenuNetDeclatant2());
        assertTrue(adaptateur.getNbPartsFoyerFiscal() > 2);
    }

    @Test
    public void testCalculImpot_ParentIsoleAvecEnfant() {
        AdaptateurCalculateurImpotReusine adaptateur = new AdaptateurCalculateurImpotReusine();
        adaptateur.setRevenusNetDeclarant1(28000);
        adaptateur.setRevenusNetDeclarant2(0);
        adaptateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        adaptateur.setNbEnfantsACharge(1);
        adaptateur.setNbEnfantsSituationHandicap(0);
        adaptateur.setParentIsole(true);

        adaptateur.calculImpotSurRevenuNet();

        assertTrue(adaptateur.getImpotSurRevenuNet() >= 0);
        assertTrue(adaptateur.getNbPartsFoyerFiscal() >= 2.0);
    }
}