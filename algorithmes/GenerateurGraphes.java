package algorithmes;

import modele.Arete;
import modele.Graphe;
import modele.Sommet;

public class GenerateurGraphes {

    public static void afficherTypesGraphes() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                         MENU DES GRAPHES");
        System.out.println("=".repeat(70));
        System.out.println("HO1 :");
        System.out.println("  1. Graphe non orienté simple (Hypothèse de base)");
        System.out.println("  2. Graphe non orienté Sommets de degrés pairs (test Problématique 2 cas 1)");
        System.out.println("  3. Graphe non orienté 2 sommets de degrés impairs (test Problématique 2 cas 2)");
        System.out.println("  4. Graphe non orienté Aucune contrainte de parité (test Problématique 2 cas 3)");
        System.out.println();
        System.out.println("HO2 :");
        System.out.println("  5. Graphe orienté simple (Hypothèse de base)");
        System.out.println("  6. Graphe orienté Sommets de degrés pairs (test Problématique 2 cas 1)");
        System.out.println("  7. Graphe orienté 2 sommets de degrés impairs (test Problématique 2 cas 2)");
        System.out.println("  8. Graphe orienté Aucune contrainte de parité (test Problématique 2 cas 3)");
        System.out.println();
        System.out.println("HO3 :");
        System.out.println("  9. Graphe mixte simple (Hypothèse de base)");
        System.out.println(" 10. Graphe mixte Sommets de degrés pairs (test Problématique 2 cas 1)");
        System.out.println(" 11. Graphe mixte 2 sommets de degrés impairs (test Problématique 2 cas 2)");
        System.out.println(" 12. Graphe mixte Aucune contrainte de parité (test Problématique 2 cas 3)");
        System.out.println();
        System.out.println("GRAPHE RÉALISTES :");
        System.out.println(" 13. Petite commune (graphe du sujet)");
        System.out.println(" 14. Grande commune (15 sommets)");
        System.out.println("=".repeat(70));
    }

    public static Graphe creerGrapheParType(int choix) {
        Graphe g;
        switch (choix) {
            case 1:
                g = creerHO1_Simple();
                break;
            case 2:
                g = creerHO1_DegresPairs();
                break;
            case 3:
                g = creerHO1_DeuxImpairs();
                break;
            case 4:
                g = creerHO1_CasGeneral();
                break;
            case 5:
                g = creerHO2_Simple();
                break;
            case 6:
                g = creerHO2_DegresPairs();
                break;
            case 7:
                g = creerHO2_DeuxImpairs();
                break;
            case 8:
                g = creerHO2_CasGeneral();
                break;
            case 9:
                g = creerHO3_Simple();
                break;
            case 10:
                g = creerHO3_DegresPairs();
                break;
            case 11:
                g = creerHO3_DeuxImpairs();
                break;
            case 12:
                g = creerHO3_CasGeneral();
                break;
            case 13:
                g = creerPetiteCommune();
                break;
            case 14:
                g = creerGrandeCommune();
                break;
            default:
                g = creerPetiteCommune();
                break;
        }
        return g;
    }

    // ==================== HO1 ====================
    private static Graphe creerHO1_Simple() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO1 : Graphe non orienté simple (Hypothèse de base)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 2);

        g.ajouterArete(new Arete(a, b, 2.0));
        return g;
    }

    private static Graphe creerHO1_DegresPairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO1 : Sommets de degrés pairs (Problématique 2 cas 1)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 3);
        Sommet c = new Sommet("C", "Point C", 2.3);
        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.0));
        g.ajouterArete(new Arete(c, a, 1.0));
        return g;
    }

    private static Graphe creerHO1_DeuxImpairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO1 : 2 sommets de degrés impairs (Problématique 2 cas 2)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 4);
        Sommet c = new Sommet("C", "Point C", 6);
        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.0));
        return g;
    }

    private static Graphe creerHO1_CasGeneral() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO1 : Aucune contrainte de parité (Problématique 2 cas 3)");
        // Ton code existant de la petite commune
        Sommet a = new Sommet("A", "Dépôt central");
        Sommet b = new Sommet("B", "Mairie", 4.8);
        Sommet c = new Sommet("C", "École", 3.2);
        Sommet d = new Sommet("D", "Hôpital", 5.1);
        Sommet e = new Sommet("E", "Centre commercial", 7.9);
        Sommet f = new Sommet("F", "Gare", 2.6);

        g.ajouterArete(new Arete(a, b, 1));
        g.ajouterArete(new Arete(b, c, 1.0));
        g.ajouterArete(new Arete(c, d, 1));
        g.ajouterArete(new Arete(d, e, 2));
        g.ajouterArete(new Arete(e, a, 3));
        g.ajouterArete(new Arete(b, e, 2.0));
        g.ajouterArete(new Arete(a, f, 1));

        return g;
    }

    // ==================== HO2 ====================
    private static Graphe creerHO2_Simple() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO2 : Graphe orienté simple (Hypothèse de base)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 4);
        g.ajouterArete(new Arete(a, b, 2.0, false));
        return g;
    }

    private static Graphe creerHO2_DegresPairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO2 : Sommets de degrés pairs (Problématique 2 cas 1)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 3.2);
        Sommet c = new Sommet("C", "Point C", 2.1);
        g.ajouterArete(new Arete(a, b, 1.0, false));
        g.ajouterArete(new Arete(b, c, 1.0, false));
        g.ajouterArete(new Arete(c, a, 1.0, false));
        return g;
    }

    private static Graphe creerHO2_DeuxImpairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO2 : 2 sommets de degrés impairs (Problématique 2 cas 2)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 1.5);
        Sommet c = new Sommet("C", "Point C", 3.7);
        g.ajouterArete(new Arete(a, b, 1.0, false));
        g.ajouterArete(new Arete(b, c, 1.0, false));
        return g;
    }

    private static Graphe creerHO2_CasGeneral() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO2 :Graphe orienté Aucune contrainte de parité (test Problématique 2 cas 3)");
        // Ton code existant de la petite commune
        Sommet a = new Sommet("A", "Dépôt central");
        Sommet b = new Sommet("B", "Mairie", 4.8);
        Sommet c = new Sommet("C", "École", 3.2);
        Sommet d = new Sommet("D", "Hôpital", 5.1);
        Sommet e = new Sommet("E", "Centre commercial", 7.9);
        Sommet f = new Sommet("F", "Gare", 2.6);

        g.ajouterArete(new Arete(a, b, 1.5, false));
        g.ajouterArete(new Arete(b, c, 1.0, false));
        g.ajouterArete(new Arete(c, d, 1.2, false));
        g.ajouterArete(new Arete(d, e, 2.3, false));
        g.ajouterArete(new Arete(e, a, 3.1, false));
        g.ajouterArete(new Arete(b, e, 2.0, false));
        g.ajouterArete(new Arete(b, f, 1.8, false));
        g.ajouterArete(new Arete(f, a, 1.8, false));
        return g;
    }

    // ==================== HO3 ====================
    private static Graphe creerHO3_Simple() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO3 : Graphe mixte simple (Hypothèse de base)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 2);
        Sommet c = new Sommet("C", "Point C", 1.2);

        g.ajouterArete(new Arete(a, b, 2.0));
        g.ajouterArete(new Arete(b, c, 2.0, false));
        return g;
    }

    private static Graphe creerHO3_DegresPairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO3 : Graphe mixte Sommets de degrés pairs (Problématique 2 cas 1)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 3);
        Sommet c = new Sommet("C", "Point C", 2.3);
        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.0, false));
        g.ajouterArete(new Arete(c, a, 1.0));
        return g;
    }

    private static Graphe creerHO3_DeuxImpairs() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO3 : Graphe mixte 2 sommets de degrés impairs (Problématique 2 cas 2)");
        Sommet a = new Sommet("A", "Dépôt");
        Sommet b = new Sommet("B", "Point B", 4);
        Sommet c = new Sommet("C", "Point C", 6);
        g.ajouterArete(new Arete(a, b, 1.0));
        g.ajouterArete(new Arete(b, c, 1.0, false));
        return g;
    }

    private static Graphe creerHO3_CasGeneral() {
        Graphe g = new Graphe();
        g.setTypeGraphe("HO3 : Graphe mixte Aucune contrainte de parité (test Problématique 2 cas 3)");

        Sommet a = new Sommet("A", "Dépôt central");
        Sommet b = new Sommet("B", "Mairie", 4.8);
        Sommet c = new Sommet("C", "École", 3.2);
        Sommet d = new Sommet("D", "Hôpital", 5.1);
        Sommet e = new Sommet("E", "Centre commercial", 7.9);

        g.ajouterArete(new Arete(a, b, 1));
        g.ajouterArete(new Arete(b, c, 1));
        g.ajouterArete(new Arete(c, d, 1));
        g.ajouterArete(new Arete(d, e, 2, false));
        g.ajouterArete(new Arete(e, a, 3, false));
        g.ajouterArete(new Arete(b, e, 2.0));
        g.ajouterArete(new Arete(c, a, 1, false));

        return g;
    }

    // ==================== GRAPHE RÉALISTES ====================
    public static Graphe creerPetiteCommune() {
        Graphe g = new Graphe();
        g.setTypeGraphe("Petite commune (graphe du sujet – HO2 mixte)");
        // Ton code existant de la petite commune
        Sommet a = new Sommet("A", "Dépôt central");
        Sommet b = new Sommet("B", "Mairie", 4.8);
        Sommet c = new Sommet("C", "École", 3.2);
        Sommet d = new Sommet("D", "Hôpital", 5.1);
        Sommet e = new Sommet("E", "Centre commercial", 7.9);

        g.ajouterArete(new Arete(a, b, 1.5));
        g.ajouterArete(new Arete(b, c, 1.0));
        g.ajouterArete(new Arete(c, d, 1.2));
        g.ajouterArete(new Arete(d, e, 2.3, false));
        g.ajouterArete(new Arete(e, a, 3.1, false));
        g.ajouterArete(new Arete(b, e, 2.0));
        g.ajouterArete(new Arete(c, a, 1.8, false));

        return g;
    }

    public static Graphe creerGrandeCommune() {
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
}