package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicules")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Vehicule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String modele;
    
    @Column(nullable = false)
    private String marque;
    
    @Column(name = "prix_base", nullable = false)
    private Double prixBase;
    
    @Column(name = "date_ajout_stock")
    @Temporal(TemporalType.DATE)
    private Date dateAjoutStock = new Date();
    
    @Column(name = "en_stock")
    private Boolean enStock = true;
    
    // Pour COMMAND PATTERN (Besoin 11) - Soldes
    @Column(name = "pourcentage_solde")
    private Double pourcentageSolde = 0.0;
    
    @Column(name = "etat_solde")
    private String etatSolde = "NORMAL";
    
    public Double getPrixFinal() {
        if (pourcentageSolde > 0) {
            return prixBase * (1 - pourcentageSolde / 100);
        }
        return prixBase;
    }
    
    public boolean estAncienStock() {
        long diff = new Date().getTime() - dateAjoutStock.getTime();
        long jours = diff / (1000 * 60 * 60 * 24);
        return jours > 180;
    }
}
