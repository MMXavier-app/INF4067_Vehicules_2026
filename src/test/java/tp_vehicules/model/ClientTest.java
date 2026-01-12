package tp_vehicules.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {
    
    @Test
    void testCompositePatternAjoutFiliale() {
        // Test COMPOSITE PATTERN - Ajout de filiale
        Societe societeMere = new Societe();
        societeMere.setNom("Société Mère");
        societeMere.setSiret("123456789");
        
        Societe filiale = new Societe();
        filiale.setNom("Filiale");
        filiale.setSiret("987654321");
        
        // Application du Composite Pattern
        societeMere.ajouterFiliale(filiale);
        
        assertThat(societeMere.getFiliales()).hasSize(1);
        assertThat(filiale.getSocieteMere()).isEqualTo(societeMere);
        assertThat(societeMere.getNombreFiliales()).isEqualTo(1);
    }
    
    @Test
    void testCompositePatternChiffreAffaires() {
        // Test calcul CA avec filiales
        Societe societeMere = new Societe();
        societeMere.setNom("Groupe Test");
        
        Societe filiale1 = new Societe();
        filiale1.setNom("Filiale 1");
        
        Societe filiale2 = new Societe();
        filiale2.setNom("Filiale 2");
        
        // Créer des commandes
        Commande cmdMere = new Commande();
        cmdMere.setMontantTotal(10000.0);
        cmdMere.setClient(societeMere);
        
        Commande cmdFiliale1 = new Commande();
        cmdFiliale1.setMontantTotal(5000.0);
        cmdFiliale1.setClient(filiale1);
        
        Commande cmdFiliale2 = new Commande();
        cmdFiliale2.setMontantTotal(3000.0);
        cmdFiliale2.setClient(filiale2);
        
        // Ajouter filiales
        societeMere.ajouterFiliale(filiale1);
        societeMere.ajouterFiliale(filiale2);
        
        // Calcul CA total (doit être 18000)
        Double caTotal = societeMere.getChiffreAffaireTotal();
        
        assertThat(caTotal).isEqualTo(18000.0);
    }
    
    @Test
    void testCompositePatternSuppressionFiliale() {
        Societe societe = new Societe();
        Societe filiale = new Societe();
        
        societe.ajouterFiliale(filiale);
        assertThat(societe.getFiliales()).hasSize(1);
        
        societe.supprimerFiliale(filiale);
        assertThat(societe.getFiliales()).hasSize(0);
        assertThat(filiale.getSocieteMere()).isNull();
    }
}
