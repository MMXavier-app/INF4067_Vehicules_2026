package tp_vehicules.dao;

import tp_vehicules.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    
    // Pour COMMAND PATTERN : véhicules anciens en stock
    @Query("SELECT v FROM Vehicule v WHERE v.dateAjoutStock < :dateLimite " +
           "AND v.enStock = true")
    List<Vehicule> findVehiculesAnciensStock(Date dateLimite);
    
    // Véhicules en solde
    List<Vehicule> findByPourcentageSoldeGreaterThan(Double pourcentage);
    
    // Recherche par marque
    List<Vehicule> findByMarque(String marque);
    
    List<Vehicule> findByEnStockTrue();
}
