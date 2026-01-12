package tp_vehicules.service.strategie;

import tp_vehicules.model.Vehicule;
import java.util.Stack;

/**
 * COMMAND PATTERN - Responsabilité Raph (Besoin 11)
 * Solder les véhicules restés en stock
 */
public class GestionnaireSoldes {
    
    // Interface Command
    public interface CommandeSolde {
        void executer();
        void annuler();
        String getDescription();
    }
    
    // Commande concrète pour solder un véhicule
    public class CommanderSoldeVehicule implements CommandeSolde {
        private Vehicule vehicule;
        private Double nouveauPourcentage;
        private Double ancienPourcentage;
        private String ancienEtat;
        
        public CommanderSoldeVehicule(Vehicule vehicule, Double pourcentage) {
            this.vehicule = vehicule;
            this.nouveauPourcentage = pourcentage;
            this.ancienPourcentage = vehicule.getPourcentageSolde();
            this.ancienEtat = vehicule.getEtatSolde();
        }
        
        @Override
        public void executer() {
            vehicule.setPourcentageSolde(nouveauPourcentage);
            vehicule.setEtatSolde("EN_SOLDE");
        }
        
        @Override
        public void annuler() {
            vehicule.setPourcentageSolde(ancienPourcentage);
            vehicule.setEtatSolde(ancienEtat);
        }
        
        @Override
        public String getDescription() {
            return String.format("Solde %.1f%% appliqué sur %s %s (ID: %d)",
                nouveauPourcentage, vehicule.getMarque(), vehicule.getModele(), vehicule.getId());
        }
    }
    
    // Invoker
    private Stack<CommandeSolde> historique = new Stack<>();
    private Stack<CommandeSolde> redoStack = new Stack<>();
    
    // Exécuter une commande
    public void executerCommande(CommandeSolde commande) {
        commande.executer();
        historique.push(commande);
        redoStack.clear();
    }
    
    // Annuler la dernière commande
    public void annulerDerniereCommande() {
        if (!historique.isEmpty()) {
            CommandeSolde commande = historique.pop();
            commande.annuler();
            redoStack.push(commande);
        }
    }
    
    // Refaire la dernière commande annulée
    public void refaireDerniereCommande() {
        if (!redoStack.isEmpty()) {
            CommandeSolde commande = redoStack.pop();
            commande.executer();
            historique.push(commande);
        }
    }
    
    // Exécuter plusieurs commandes en batch
    public void executerBatch(Iterable<CommandeSolde> commandes) {
        for (CommandeSolde commande : commandes) {
            executerCommande(commande);
        }
    }
    
    // Obtenir l'historique
    public java.util.List<String> getHistoriqueDescriptions() {
        java.util.List<String> descriptions = new java.util.ArrayList<>();
        for (CommandeSolde cmd : historique) {
            descriptions.add(cmd.getDescription());
        }
        return descriptions;
    }
    
    // Vider l'historique
    public void clearHistorique() {
        historique.clear();
        redoStack.clear();
    }
    
    // Vérifier si on peut annuler
    public boolean peutAnnuler() {
        return !historique.isEmpty();
    }
    
    // Vérifier si on peut refaire
    public boolean peutRefaire() {
        return !redoStack.isEmpty();
    }
}
