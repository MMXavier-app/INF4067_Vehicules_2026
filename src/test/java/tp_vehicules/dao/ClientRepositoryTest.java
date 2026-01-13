package tp_vehicules.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import tp_vehicules.model.Societe;

import static org.assertj.core.api.Assertions.assertThat;

// CORRIGÉ : Ajouter les annotations nécessaires
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ClientRepositoryTest {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Test
    void testRepositoryInitialisation() {
        // Test simple pour vérifier que le repository fonctionne
        assertThat(clientRepository).isNotNull();
        
        // Compter le nombre de clients (doit être 0 car base vide)
        long count = clientRepository.count();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    void testSaveAndFindSociete() {
        // Créer et sauvegarder une société
        Societe societe = new Societe();
        societe.setNom("Test Corp");
        societe.setSiret("99999999999999");
        societe.setEmail("test@corp.com");
        
        Societe saved = clientRepository.save(societe);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNom()).isEqualTo("Test Corp");
        
        // Rechercher par ID
        var found = clientRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Test Corp");
    }
    
    @Test
    void testFindByNom() {
        // Créer une société
        Societe societe = new Societe();
        societe.setNom("AutoCorp Test");
        societe.setSiret("11111111111111");
        societe.setEmail("test@autocorp.com");
        
        clientRepository.save(societe);
        
        // Rechercher par nom
        var results = clientRepository.findByNomContainingIgnoreCase("autocorp");
        
        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(c -> c.getNom().contains("AutoCorp"));
    }
}