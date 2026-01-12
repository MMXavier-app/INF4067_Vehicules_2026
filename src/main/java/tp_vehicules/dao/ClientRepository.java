package tp_vehicules.dao;

import tp_vehicules.model.Client;
import tp_vehicules.model.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    // Pour COMPOSITE PATTERN : récupérer sociétés avec leurs filiales
    @Query("SELECT s FROM Societe s LEFT JOIN FETCH s.filiales WHERE s.id = :id")
    Societe findSocieteWithFiliales(Long id);
    
    @Query("SELECT s FROM Societe s LEFT JOIN FETCH s.filiales")
    List<Societe> findAllSocietesWithFiliales();
    
    // Calcul chiffre d'affaires avec filiales (Composite)
    @Query("SELECT SUM(c.montantTotal) FROM Commande c WHERE " +
           "c.client.id = :clientId OR c.client.societeMere.id = :clientId")
    Double calculerChiffreAffairesAvecFiliales(Long clientId);
    
    // Clients par nom
    List<Client> findByNomContainingIgnoreCase(String nom);
}
