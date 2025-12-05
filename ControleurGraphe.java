
package controleur;

import algorithmes.*;
import modele.*;

import java.util.List;

/**
 * Contrôleur principal du projet – Architecture MVC.
 * Il orchestre la logique métier, les algorithmes, et les interactions entre le
 * modèle et la vue.
 * Gère la collectivité et l'entreprise de collecte.
 */
public class ControleurGraphe {
    private Graphe graphe; // Graphe routier actuel
    private Sommet depot; // Dépôt (point de départ)
    private final Collectivite collectivite; // Collectivité associée
    private final EntrepriseCollecte entreprise; // Entreprise associée

    /**
     * Classe interne pour retourner un chemin + distance (utilisée pour Dijkstra).
     */
    public static class ResultatChemin {
        public final List<Sommet> chemin;
        public final double distance;

        public ResultatChemin(List<Sommet> chemin, double distance) {
            this.chemin = chemin;
            this.distance = distance;
        }
    }

    /**
     * Constructeur du contrôleur.
     * Initialise la collectivité, l'entreprise, le graphe et le dépôt.
     * 
     * @param collectivite La collectivité.
     * @param entreprise   L'entreprise de collecte.
     */
    public ControleurGraphe(Collectivite collectivite, EntrepriseCollecte entreprise) {
        this.collectivite = collectivite;
        this.entreprise = entreprise;
        this.graphe = collectivite.getPlanCommune();
        if (graphe != null) {
            this.depot = graphe.getDepot();
        }
    }

    /**
     * Charge un nouveau graphe et met à jour les acteurs (collectivité et
     * entreprise).
     * 
     * @param type Le type de graphe à charger.
     */
    public void chargerGraphe(int type) {
        Graphe nouveau = GenerateurGraphes.creerGrapheParType(type);

        // On utilise la méthode héritée setPlanCommune()
        collectivite.setPlanCommune(nouveau);
        entreprise.setPlanCommune(nouveau); // L'entreprise aussi reçoit le nouveau plan

        this.graphe = nouveau;
        this.depot = graphe.getDepot();

        System.out.println("Nouveau plan routier appliqué à la collectivité et à l'entreprise !");
    }

    /**
     * Calcule le plus court chemin avec distance (Dijkstra).
     * 
     * @param destination Le sommet de destination.
     * @return Le résultat (chemin + distance).
     */
    public ResultatChemin calculerPlusCourtCheminAvecDistance(Sommet destination) {
        List<Sommet> chemin = Dijkstra.cheminLePlusCourt(graphe, depot, destination);
        double distance = 0.0;

        for (int i = 0; i < chemin.size() - 1; i++) {
            Arete a = graphe.getArete(chemin.get(i), chemin.get(i + 1));
            if (a != null) {
                distance += a.getLongueur();
            }
        }

        return new ResultatChemin(chemin, distance);
    }

    /**
     * Calcule le plus court chemin sans distance (version simplifiée).
     * 
     * @param destination Le sommet de destination.
     * @return Le chemin.
     */
    public List<Sommet> calculerPlusCourtChemin(Sommet destination) {
        return Dijkstra.cheminLePlusCourt(graphe, depot, destination);
    }

    /**
     * Résout le TSP exact pour une tournée d'encombrants.
     * 
     * @param points Les points à visiter.
     * @return Le résultat (circuit + distance).
     */
    public List<Object> resoudreTSPExact(List<Sommet> points) {
        return TSPPetit.resoudreTSPExact(graphe, depot, points);
    }

    /**
     * Résout le problème du Postier Chinois.
     * 
     * @return Le résultat (circuit, distance, duplications).
     */
    public PostierChinois.ResultatPostierChinois resoudrePostierChinois() {
        return PostierChinois.resoudrePostierChinois(graphe);
    }

    /**
     * Calcule les tournées avec l’approche Plus Proche Voisin (Thème 2).
     * 
     * @param capaciteCamion La capacité du camion.
     * @return Liste des tournées.
     */
    public List<Tournee> calculerTourneesAvecCapacite(double capaciteCamion) {
        List<Sommet> tousLesPoints = graphe.getSommets();
        return ApprochePointsCollecte.approcheAvecCapacite(graphe, depot, tousLesPoints, capaciteCamion);
    }

    /**
     * Calcule les tournées avec l’approche MST (Thème 2).
     * 
     * @param capaciteCamion La capacité du camion.
     * @return Liste des tournées.
     */
    public List<Tournee> calculerTourneesMST(double capaciteCamion) {
        List<Sommet> tousLesPoints = graphe.getSommets();
        return ApprocheMST.resoudreAvecMST(graphe, depot, tousLesPoints, capaciteCamion);
    }

    // GETTERS
    public Graphe getGraphe() {
        return graphe;
    }

    public Sommet getDepot() {
        return depot;
    }

    public Collectivite getCollectivite() {
        return collectivite;
    }

    public EntrepriseCollecte getEntreprise() {
        return entreprise;
    }
}