package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commandes")
@Getter @Setter
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_commande", unique = true)
    private String numeroCommande;
    
    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatCommande etat = EtatCommande.EN_COURS;
    
    public enum EtatCommande {
        EN_COURS, VALIDE, PAYE, LIVRE, ANNULE
    }
    
    @Column(name = "montant_total", precision = 10, scale = 2)
    private Double montantTotal = 0.0;
    
    @Column(name = "montant_taxe")
    private Double montantTaxe = 0.0;
    
    @Column(name = "pays_livraison")
    private String paysLivraison = "France";
    
    // Pour TEMPLATE METHOD PATTERN (Besoin 10)
    @Column(name = "taux_taxe_applique")
    private Double tauxTaxeApplique;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    // Méthodes pour le Template Method
    public void calculerTaxes(Double taux) {
        this.tauxTaxeApplique = taux;
        this.montantTaxe = montantTotal * taux;
    }
    
    public Double getMontantTTC() {
        return montantTotal + montantTaxe;
    }
}
