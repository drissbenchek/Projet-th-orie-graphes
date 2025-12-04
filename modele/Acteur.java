package modele;

/**
 * Classe abstraite représentant un acteur dans le système de collecte des
 * déchets.
 * Elle sert de base pour les classes Collectivite et EntrepriseCollecte, en
 * utilisant l'héritage pour partager les attributs et méthodes communs.
 * Cette classe encapsule le nom de l'acteur et le plan de la commune (graphe
 * routier).
 */
public abstract class Acteur {
    // Nom de l'acteur (collectivité ou entreprise)
    protected String nom;
    // Graphe représentant le plan routier de la commune
    protected Graphe planCommune;

    /**
     * Constructeur de la classe Acteur.
     * Initialise le nom de l'acteur.
     * 
     * @param nom Le nom de l'acteur (ex: "NomCollectivite" pour une collectivité).
     */
    public Acteur(String nom) {
        this.nom = nom;
    }

    /**
     * Récupère le nom de l'acteur.
     * 
     * @return Le nom de l'acteur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le plan de la commune (graphe routier).
     * 
     * @return Le graphe représentant le plan routier.
     */
    public Graphe getPlanCommune() {
        return planCommune;
    }

    /**
     * Met à jour le plan de la commune pour l'acteur.
     * Affiche un message de confirmation pour le suivi des opérations.
     * 
     * @param plan Le nouveau graphe routier à associer à l'acteur.
     */
    public void setPlanCommune(Graphe plan) {
        this.planCommune = plan;
        System.out.println("\n Plan mis à jour pour: " + nom);
    }

    /**
     * Méthode abstraite pour présenter l'acteur.
     * Doit être implémentée par les classes filles (Collectivite et
     * EntrepriseCollecte) pour fournir une présentation spécifique.
     */
    public abstract void presenter();
}
