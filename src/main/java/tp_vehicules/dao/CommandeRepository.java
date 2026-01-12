package tp_vehicules.dao;

import tp_vehicules.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    
    // Commandes par client (avec filiales si société)
    @Query("SELECT c FROM Commande c WHERE " +
           "c.client.id = :clientId OR c.client.societeMere.id = :clientId")
    List<Commande> findByClientAvecFiliales(Long clientId);
    
    List<Commande> findByClientId(Long clientId);
    
    // Pour TEMPLATE METHOD : statistiques taxes par pays
    @Query("SELECT c.paysLivraison, AVG(c.tauxTaxeApplique), SUM(c.montantTaxe) " +
           "FROM Commande c GROUP BY c.paysLivraison")
    List<Object[]> getStatistiquesTaxesParPays();
}
