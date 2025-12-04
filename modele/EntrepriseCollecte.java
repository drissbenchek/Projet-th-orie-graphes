
package modele;

/**
 * Classe représentant une entreprise de collecte des déchets.
 * Hérite de la classe abstraite Acteur pour partager les attributs communs
 * comme le nom et le plan de la commune.
 * Cette classe gère les informations spécifiques à l'entreprise, telles que le
 * nombre de camions et la capacité de chaque camion.
 * Elle est utilisée pour simuler l'entité responsable de la collecte pratique
 * des déchets.
 */
public class EntrepriseCollecte extends Acteur {
    // Nombre de camions disponibles pour l'entreprise
    private int nombreCamions;
    // Capacité maximale de chaque camion en tonnes
    private double capaciteCamion;

    /**
     * Constructeur de l'entreprise de collecte.
     * Initialise le nom, le nombre de camions, la capacité, et synchronise le plan
     * de la commune avec la collectivité.
     * 
     * @param nom            Le nom de l'entreprise (ex: "GreenCollect Pro").
     * @param nombreCamions  Le nombre de camions disponibles.
     * @param capaciteCamion La capacité maximale de chaque camion en tonnes.
     * @param collectivite   La collectivité associée, pour synchroniser le plan
     *                       routier.
     */
    public EntrepriseCollecte(String nom, int nombreCamions, double capaciteCamion, Collectivite collectivite) {
        super(nom);
        this.nombreCamions = nombreCamions;
        this.capaciteCamion = capaciteCamion;
        this.planCommune = collectivite.getPlanCommune();// Synchronisation avec la collectivité
        System.out
                .println("Entreprise " + nom + " créée avec " + nombreCamions + " camions de " + capaciteCamion + " t");
    }

    /**
     * Implémentation de la méthode abstraite pour présenter l'entreprise.
     * Affiche les informations spécifiques à l'entreprise.
     */
    @Override
    public void presenter() {
        System.out.println("Entreprise " + nom + " - " + nombreCamions + " camions de " + capaciteCamion + " t");
    }

    /**
     * Récupère le nombre de camions disponibles.
     * 
     * @return Le nombre de camions.
     */
    public int getNombreCamions() {
        return nombreCamions;
    }

    /**
     * Récupère la capacité maximale d'un camion.
     * 
     * @return La capacité en tonnes.
     */
    public double getCapaciteCamion() {
        return capaciteCamion;
    }

    /**
     * Met à jour le plan de la commune pour l'entreprise.
     * Surcharge la méthode héritée pour ajouter un message spécifique à
     * l'entreprise.
     * 
     * @param plan Le nouveau graphe routier.
     */
    @Override
    public void setPlanCommune(Graphe plan) {
        super.setPlanCommune(plan);
        System.out.println("Entreprise " + nom + " prend en compte le nouveau plan.");
    }
}
