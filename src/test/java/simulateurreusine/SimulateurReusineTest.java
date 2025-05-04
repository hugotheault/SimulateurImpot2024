package simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.SimulateurReusine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulateurReusineTest {

    @Test
    public void testCalculImpot_Normal() {
        SimulateurReusine simulateur = new SimulateurReusine();
        int impot = simulateur.calculerImpot(35000, 15000, SituationFamiliale.MARIE, 1, 0, false);

        assertTrue(impot > 0);
        assertEquals(2.5, simulateur.getNombreParts(), 0.01);
    }

    @Test
    public void testCalculImpot_EnfantsMax() {
        SimulateurReusine simulateur = new SimulateurReusine();
        int impot = simulateur.calculerImpot(60000, 30000, SituationFamiliale.MARIE, 7, 2, false);

        assertTrue(impot >= 0);
        assertEquals(9, simulateur.getNombreParts(), 0.1);
    }

    @Test
    public void testCalculImpot_Exception_EnfantsDepasse() {
        SimulateurReusine simulateur = new SimulateurReusine();
        assertThrows(IllegalArgumentException.class, () ->
                simulateur.calculerImpot(50000, 20000, SituationFamiliale.MARIE, 8, 0, false));
    }

    @Test
    public void testCalculImpot_Exception_EnfantsHandicapesSuperieur() {
        SimulateurReusine simulateur = new SimulateurReusine();
        assertThrows(IllegalArgumentException.class, () ->
                simulateur.calculerImpot(50000, 0, SituationFamiliale.CELIBATAIRE, 2, 3, false));
    }

    @Test
    public void testCalculImpot_Exception_RevenuNegatif() {
        SimulateurReusine simulateur = new SimulateurReusine();
        assertThrows(IllegalArgumentException.class, () ->
                simulateur.calculerImpot(-1000, 0, SituationFamiliale.VEUF, 1, 0, false));
    }

    @Test
    public void testCalculImpot_Exception_ParentIsoleMarie() {
        SimulateurReusine simulateur = new SimulateurReusine();
        assertThrows(IllegalArgumentException.class, () ->
                simulateur.calculerImpot(20000, 20000, SituationFamiliale.MARIE, 1, 0, true));
    }

    @Test
    public void testCalculImpot_Exception_DeclarantSeulAvecRevenu2() {
        SimulateurReusine simulateur = new SimulateurReusine();
        assertThrows(IllegalArgumentException.class, () ->
                simulateur.calculerImpot(25000, 15000, SituationFamiliale.CELIBATAIRE, 0, 0, false));
    }
}