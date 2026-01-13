package tp_vehicules.service.strategie;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TaxeStrategieTest {
    
    @Test
    void testTemplateMethodFrance() {
        // Test TEMPLATE METHOD PATTERN - France
        TaxeStrategie strategie = new TaxeFR();
        Double montantHT = 1000.0;
        
        // Template Method en action
        Double montantTTC = strategie.calculerMontantAvecTaxes(montantHT);
        
        // France: 20% de TVA sur 1000 = 200€ de taxes
        assertThat(montantTTC).isEqualTo(1200.0);
        assertThat(strategie.getDescription()).contains("FR");
    }
    
    @Test
    void testTemplateMethodBelgique() {
        // Test TEMPLATE METHOD PATTERN - Belgique
        TaxeStrategie strategie = new TaxeBE();
        Double montantHT = 1000.0;
        
        // Template Method avec hook de réduction
        Double montantTTC = strategie.calculerMontantAvecTaxes(montantHT);
        
        // Belgique: 21% avec réduction pour > 30000
        // Ici pas de réduction car montant < 30000
        assertThat(montantTTC).isEqualTo(1210.0); // 1000 + 210
        assertThat(strategie.getDescription()).contains("BE");
    }
    
    @Test
    void testTemplateMethodBelgiqueAvecReduction() {
        // Test hook de réduction
        TaxeStrategie strategie = new TaxeBE();
        Double montantHT = 35000.0; // > 30000 => réduction
        
        Double montantTTC = strategie.calculerMontantAvecTaxes(montantHT);
        
        // 21% de 35000 = 7350, moins 10% = 6615
        // Total = 35000 + 6615 = 41615
        assertThat(montantTTC).isEqualTo(41615.0);
    }
    
    @Test
    void testTemplateMethodUS() {
        // Test US avec différents états
        TaxeUS strategie = new TaxeUS();
        
        strategie.setEtat("NY");
        Double ttcNY = strategie.calculerMontantAvecTaxes(1000.0);
        
        strategie.setEtat("CA");
        Double ttcCA = strategie.calculerMontantAvecTaxes(1000.0);
        
        // NY: 8.875%, CA: 8.5%
        assertThat(ttcNY).isGreaterThan(ttcCA);
        assertThat(strategie.getDescription()).contains("US");
    }
    
    @Test
    void testTemplateMethodArrondi() {
        // Test des hooks d'arrondi
        TaxeFR strategieFR = new TaxeFR();
        TaxeUS strategieUS = new TaxeUS();
        
        // Montant avec beaucoup de décimales
        Double montantHT = 1234.567;
        
        Double ttcFR = strategieFR.calculerMontantAvecTaxes(montantHT);
        Double ttcUS = strategieUS.calculerMontantAvecTaxes(montantHT);
        
        // Vérifier arrondi à 2 décimales
        assertThat(ttcFR * 100).isEqualTo(Math.round(ttcFR * 100));
        assertThat(ttcUS * 100).isEqualTo(Math.round(ttcUS * 100));
    }
}
