package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "particuliers")
@PrimaryKeyJoinColumn(name = "client_id")
@Getter @Setter
public class Particulier extends Client {
    
    private String prenom;
    private String civilite;
    
    @Override
    public Double getChiffreAffaireTotal() {
        return this.getCommandes().stream()
            .mapToDouble(Commande::getMontantTotal)
            .sum();
    }
}
