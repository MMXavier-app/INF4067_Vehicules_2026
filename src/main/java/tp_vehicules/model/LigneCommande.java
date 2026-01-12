package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "lignes_commande")
@Getter @Setter
public class LigneCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer quantite = 1;
    
    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;
    
    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    
    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
    
    public Double getSousTotal() {
        return prixUnitaire * quantite;
    }
}
