package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "societes")
@PrimaryKeyJoinColumn(name = "client_id")
@Getter @Setter
public class Societe extends Client {
    
    @Column(name = "siret", unique = true, length = 14)
    private String siret;
    
    private String raisonSociale;
    
    @Column(precision = 15, scale = 2)
    private Double capital;
    
    // COMPOSITE PATTERN : une société peut avoir plusieurs filiales
    @OneToMany(mappedBy = "societeMere", fetch = FetchType.LAZY)
    private List<Client> filiales = new ArrayList<>();
    
    public Societe() {}
    
    public Societe(String siret, String raisonSociale) {
        this.siret = siret;
        this.raisonSociale = raisonSociale;
    }
    
    // Méthodes du Composite Pattern
    public void ajouterFiliale(Client filiale) {
        filiale.setSocieteMere(this);
        this.filiales.add(filiale);
    }
    
    public void supprimerFiliale(Client filiale) {
        filiale.setSocieteMere(null);
        this.filiales.remove(filiale);
    }
    
    @Override
    public Double getChiffreAffaireTotal() {
        Double caSociete = this.getCommandes().stream()
            .mapToDouble(Commande::getMontantTotal)
            .sum();
        
        Double caFiliales = filiales.stream()
            .mapToDouble(Client::getChiffreAffaireTotal)
            .sum();
            
        return caSociete + caFiliales;
    }
    
    // Méthode utilitaire
    public Integer getNombreFiliales() {
        return filiales.size();
    }
}
