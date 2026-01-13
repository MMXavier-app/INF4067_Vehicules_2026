package tp_vehicules.service.strategie;

/**
 * TEMPLATE METHOD PATTERN - Responsabilité Raph (Besoin 10)
 * Calcul du montant d'une commande avec taxes
 */
public abstract class TaxeStrategie {
    
    // TEMPLATE METHOD (final) - squelette fixe de l'algorithme
    public final Double calculerMontantAvecTaxes(Double montantHT) {
        // Étape 1: Calculer le taux de base
        Double tauxBase = getTauxBase();
        
        // Étape 2: Appliquer les majorations éventuelles
        Double tauxAvecMajoration = appliquerMajorations(tauxBase);
        
        // Étape 3: Calculer le montant des taxes
        Double montantTaxes = montantHT * tauxAvecMajoration;
        
        // Étape 4: Appliquer les réductions éventuelles (HOOK)
        montantTaxes = appliquerReductions(montantTaxes, montantHT);
        
        // Étape 5: Arrondir selon les règles du pays
        montantTaxes = arrondirMontant(montantTaxes);
        
        // Étape 6: Calculer le total TTC
        return montantHT + montantTaxes;
    }
    
    // Méthode abstraite (doit être implémentée par les sous-classes)
    protected abstract Double getTauxBase();
    protected abstract String getCodePays();
    
    // HOOKS (peuvent être redéfinis par les sous-classes)
    protected Double appliquerMajorations(Double tauxBase) {
        // Par défaut, pas de majoration
        return tauxBase;
    }
    
    protected Double appliquerReductions(Double montantTaxes, Double montantHT) {
        // Par défaut, pas de réduction
        return montantTaxes;
    }
    
    protected Double arrondirMontant(Double montant) {
        // Arrondi standard à 2 décimales
        return Math.round(montant * 100.0) / 100.0;
    }
    
    // Méthode utilitaire
    public String getDescription() {
        return "Stratégie taxes pour " + getCodePays() + " - Taux: " + (getTauxBase() * 100) + "%";
    }
}

// Implémentation pour la France
class TaxeFR extends TaxeStrategie {
    @Override
    protected Double getTauxBase() {
        return 0.20; // TVA 20% en France
    }
    
    @Override
    protected String getCodePays() {
        return "FR";
    }
    
    @Override
    protected Double arrondirMontant(Double montant) {
        // Arrondi français: au centime supérieur
        return Math.ceil(montant * 100) / 100.0;
    }
}

// Implémentation pour la Belgique
class TaxeBE extends TaxeStrategie {
    @Override
    protected Double getTauxBase() {
        return 0.21; // TVA 21% en Belgique
    }
    
    @Override
    protected String getCodePays() {
        return "BE";
    }
    
    @Override
    protected Double appliquerReductions(Double montantTaxes, Double montantHT) {
        // En Belgique, réduction de 10% pour les véhicules électriques
        // (simplifié: si montant > 30000, considéré comme véhicule électrique)
        if (montantHT > 30000) {
            return montantTaxes * 0.9; // 10% de réduction
        }
        return montantTaxes;
    }
}

// Implémentation pour les États-Unis
class TaxeUS extends TaxeStrategie {
    private String etat = "NY";
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    @Override
    protected Double getTauxBase() {
        // Taux variables par état
        switch (etat) {
            case "NY": return 0.08875; // New York
            case "CA": return 0.0850;  // Californie
            case "TX": return 0.0825;  // Texas
            default: return 0.07;      // Taux par défaut
        }
    }
    
    @Override
    protected String getCodePays() {
        return "US-" + etat;
    }
    
    @Override
    protected Double arrondirMontant(Double montant) {
        // Arrondi US: au centime le plus proche
        return Math.round(montant * 100) / 100.0;
    }
}
