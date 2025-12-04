package algorithmes;

import modele.Arete;
import modele.Graphe;
import modele.Sommet;

/**
 * Classe pour générer des graphes fictifs pour les tests.
 * Fournit plusieurs types de graphes adaptés aux problématiques du projet.
 */
public class GenerateurGraphes {

    /**
     * Affiche les types de graphes disponibles.
     */
    public static void afficherTypesGraphes() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         GRAPHE FICTIFS DISPONIBLES");
        System.out.println("=".repeat(50));
        System.out.println("1. Petite commune (graphe du sujet - HO2 mixte)");
        System.out.println("2. Graphe eulérien parfait (HO1 - tous degrés pairs)");
        System.out.println("3. Graphe linéaire (cul-de-sac - 2 sommets impairs)");
        System.out.println("4. Graphe avec sens uniques forts (HO2 extrême)");
        System.out.println("5. Grande commune réaliste (15 sommets - HO2)");
        System.out.println("6. Graphe non connexe (test de robustesse)");
        System.out.println("=".repeat(50));
    }

    /**
     * Crée un graphe en fonction du type choisi.
     * 
     * @param choix Le type de graphe.
     * @return Le graphe créé.
     */
    public static Graphe creerGrapheParType(int choix) {
        return switch (choix) {
            case 1 -> creerPetiteCommune();
            case 2 -> creerGrapheEulerien();
            case 3 -> creerGrapheLineaire();
            case 4 -> creerGrapheAvecSensUniques();
            case 5 -> creerGrapheGrandeCommune();
            case 6 -> creerGrapheNonConnexe();
            default -> creerPetiteCommune();
        };
    }

    /**
     * Crée un graphe pour une petite commune (7 sommets).
     * 
     * @return Le graphe.
     */
    public static Graphe creerPetiteCommune() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Petite commune (graphe du sujet - HO2 mixte)");
        // 7 sommets avec lettres simples
        Sommet A = new Sommet("A", "Dépôt central");
        Sommet B = new Sommet("B", "Mairie", 4.8);
        Sommet C = new Sommet("C", "École", 3.2);
        Sommet D = new Sommet("D", "Hôpital", 5.1);
        Sommet E = new Sommet("E", "Centre commercial", 7.9);
        Sommet F = new Sommet("F", "Gare", 2.6);
        Sommet G = new Sommet("G", "Parc", 1.8);

        // Arêtes (double sens sauf 2 sens uniques → 4 sommets impairs)
        g.ajouterArete(new Arete(A, B, 1.5));
        g.ajouterArete(new Arete(B, C, 1.0));
        g.ajouterArete(new Arete(C, D, 1.2));
        g.ajouterArete(new Arete(D, E, 2.3, false)); // D → E (sens unique)
        g.ajouterArete(new Arete(E, A, 3.1, false)); // E → A (sens unique)
        g.ajouterArete(new Arete(B, E, 2.0));
        g.ajouterArete(new Arete(A, F, 1.8));
        g.ajouterArete(new Arete(F, G, 1.4));
        g.ajouterArete(new Arete(G, B, 1.6));
        // g.ajouterArete(new Arete(F, E, 2.5, false)); // F → E (sens unique)

        return g;
    }

    /**
     * Crée un graphe eulérien parfait (tous degrés pairs)
     * 
     * @return Graphe eulérien
     */
    public static Graphe creerGrapheEulerien() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Graphe eulérien parfait (HO1 - tous degrés pairs)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 3.6);
        Sommet c = new Sommet("C", "Point C", 1.6);
        Sommet d = new Sommet("D", "Point D", 4.7);
        // Tous degrés 2 → eulérien
        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.0));
        g.ajouterArete(new Arete(c, d, 1.0));
        g.ajouterArete(new Arete(d, a, 1.0));

        return g;
    }

    /**
     * Crée un graphe pour une grande commune réaliste (12 sommets avec lettres)
     * 
     * @return Graphe grande commune
     */
    public static Graphe creerGrapheGrandeCommune() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Grande commune réaliste (15 sommets - HO2)");
        Sommet A = new Sommet("A", "Dépôt central");
        Sommet B = new Sommet("B", "Mairie", 4.8);
        Sommet C = new Sommet("C", "École primaire", 3.2);
        Sommet D = new Sommet("D", "Hôpital", 5.1);
        Sommet E = new Sommet("E", "Centre commercial", 7.9);
        Sommet F = new Sommet("F", "Gymnase", 2.6);
        Sommet G = new Sommet("G", "Bibliothèque", 1.8);
        Sommet H = new Sommet("H", "Marché", 4.3);
        Sommet I = new Sommet("I", "Parc", 2.7);
        Sommet J = new Sommet("J", "Gare", 3.9);
        Sommet K = new Sommet("K", "Résidence", 4.1);
        Sommet L = new Sommet("L", "Zone industrielle", 5.4);
        // Pas de J→L pour garder 6 impairs
        g.ajouterArete(new Arete(A, B, 1.5));
        g.ajouterArete(new Arete(B, C, 1.0));
        g.ajouterArete(new Arete(C, D, 1.2, false)); // C → D
        g.ajouterArete(new Arete(D, E, 2.3, false)); // D → E
        g.ajouterArete(new Arete(E, A, 3.1, false)); // E → A
        g.ajouterArete(new Arete(B, E, 2.0));
        g.ajouterArete(new Arete(A, F, 1.8, false)); // A → F
        g.ajouterArete(new Arete(F, B, 1.1));
        g.ajouterArete(new Arete(F, G, 1.4));
        g.ajouterArete(new Arete(G, H, 1.6));
        g.ajouterArete(new Arete(H, I, 2.0));
        g.ajouterArete(new Arete(I, J, 1.5));
        g.ajouterArete(new Arete(J, K, 1.8));
        g.ajouterArete(new Arete(K, L, 2.2));
        g.ajouterArete(new Arete(L, A, 2.5));
        g.ajouterArete(new Arete(C, F, 1.9, false)); // C → F
        g.ajouterArete(new Arete(D, J, 2.4, false)); // D → J
        g.ajouterArete(new Arete(E, K, 2.1, false)); // E → K

        return g;
    }

    /**
     * Crée un graphe linéaire simple
     * 
     * @return Graphe linéaire
     */
    public static Graphe creerGrapheLineaire() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Graphe linéaire (cul-de-sac - 2 sommets impairs)");
        Sommet a = new Sommet("A", "Début");
        Sommet b = new Sommet("B", "Point 1", 3.0);
        Sommet c = new Sommet("C", "Point 2", 4.5);
        Sommet d = new Sommet("D", "Point 3", 2.8);
        Sommet e = new Sommet("E", "Fin", 5.2);

        g.ajouterArete(new Arete(a, b, 2.0));
        g.ajouterArete(new Arete(b, c, 1.5));
        g.ajouterArete(new Arete(c, d, 3.0));
        g.ajouterArete(new Arete(d, e, 2.5));
        return g;
    }

    /**
     * Crée un graphe avec uniquement des sens uniques
     * 
     * @return Graphe sens unique
     */
    public static Graphe creerGrapheAvecSensUniques() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Graphe avec sens uniques forts (HO2 extrême)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Mairie", 4.8);
        Sommet c = new Sommet("C", "École", 3.5);
        Sommet d = new Sommet("D", "Hôpital", 1.7);

        g.ajouterArete(new Arete(a, b, 1.0, false)); // A → B
        g.ajouterArete(new Arete(b, c, 1.0, false)); // B → C
        g.ajouterArete(new Arete(c, d, 1.0, false)); // C → D
        g.ajouterArete(new Arete(d, a, 1.0, false)); // D → A
        return g;
    }

    /**
     * Crée un graphe non connexe )
     * 
     * @return Graphe non connexe
     */

    public static Graphe creerGrapheNonConnexe() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Graphe non connexe (test de robustesse)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Îlot 1", 5);
        Sommet c = new Sommet("C", "Îlot 2", 4);
        Sommet d = new Sommet("D", "Îlot isolé", 2.4);

        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.5));
        // D est isolé → non connexe

        return g;
    }

}