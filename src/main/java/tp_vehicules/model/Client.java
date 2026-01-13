package tp_vehicules.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(unique = true)
    private String email;
    
    private String telephone;
    
    // COMPOSITE PATTERN : Référence à la société mère
    @ManyToOne
    @JoinColumn(name = "societe_mere_id")
    private Societe societeMere;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Commande> commandes = new ArrayList<>();
    
    // Méthode du COMPOSITE PATTERN (à implémenter)
    public abstract Double getChiffreAffaireTotal();
    
    public Client() {}
    
    public Client(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }
}
