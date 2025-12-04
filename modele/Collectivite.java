
package modele;

import java.util.*;

/**
 * Classe représentant une collectivité (ex: mairie).
 * Hérite de la classe abstraite Acteur pour partager les attributs communs
 * comme le nom et le plan de la commune.
 * Cette classe gère les travaux en cours et les contraintes horaires
 * spécifiques à la collectivité.
 * Elle est utilisée pour simuler l'entité responsable de la gestion
 * administrative de la commune.
 */
public class Collectivite extends Acteur {
    // Liste des rues en travaux
    private List<String> travauxEnCours = new ArrayList<>();
    // Contraintes horaires par secteur
    private Map<String, String> contraintesHoraires = new HashMap<>();

    /**
     * Constructeur de la collectivité.
     * Initialise le nom et les listes/map vides.
     * 
     * @param nom Le nom de la collectivité (ex: "EcoVille Agglomération").
     */
    public Collectivite(String nom) {
        super(nom);
    }

    /**
     * Implémentation de la méthode abstraite pour présenter la collectivité.
     * Affiche les informations spécifiques à la collectivité.
     */
    @Override
    public void presenter() {
        System.out.println("Collectivité : " + nom + " Gestion du plan de la commune");
    }

    /**
     * Signale des travaux sur une rue.
     * Ajoute la rue à la liste des travaux en cours.
     * 
     * @param rue Le nom de la rue en travaux.
     */
    public void signalerTravaux(String rue) {
        travauxEnCours.add(rue);
        System.out.println("Travaux signalés sur : " + rue);
    }

    /**
     * Ajoute une contrainte horaire pour un secteur.
     * 
     * @param secteur Le nom du secteur (ex: "Centre-ville").
     * @param plage   La plage horaire (ex: "9h-17h").
     */
    public void ajouterContrainteHoraire(String secteur, String plage) {
        contraintesHoraires.put(secteur, plage);
    }

    /**
     * Met à jour le plan de la commune pour la collectivité.
     * Surcharge la méthode héritée pour ajouter un message spécifique à la
     * collectivité.
     * 
     * @param plan Le nouveau graphe routier.
     */
    @Override
    public void setPlanCommune(Graphe plan) {
        super.setPlanCommune(plan);
        System.out.println("Collectivité " + nom + " valide le nouveau plan routier.");
    }
}
