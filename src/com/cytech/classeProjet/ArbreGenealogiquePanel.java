package com.cytech.classeProjet;

 

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.List;
 

@SuppressWarnings("serial")
public class ArbreGenealogiquePanel extends JPanel {
    private ArbreGenealogique arbre;
    private Map<Personne, Point> positions = new HashMap<>();
    private Map<Personne, Dimension> dimensions = new HashMap<>();
    private Map<Personne, List<Personne>> enfantsParParent = new HashMap<>();
    private static final int LARGEUR_NOEUD = 120;
    private static final int HAUTEUR_NOEUD = 80;
    private static final int ESPACE_HORIZONTAL = 40;
    private static final int ESPACE_VERTICAL = 60;
    private static final int DISTANCE_COUPLE = 20; // Distance entre les conjoints
    private static final Color COULEUR_HOMME = new Color(173, 216, 230);  //  Bleu clair
    private static final Color COULEUR_FEMME = new Color(255, 182, 193);  // Rose clair
    private static final Color COULEUR_LIEN = new Color(100, 100, 100);   // Gris foncé
    private static final Color COULEUR_LIEN_COUPLE = new Color(255, 0, 0); // Rouge pour les liens de couple
    private static final Color COULEUR_TEXTE = new Color(50, 50, 50);     // Presque noir
    private static final Font FONT_PERSONNE = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_INFO = new Font("Arial", Font.PLAIN, 10);
    
    private int largeurTotale;
    private int hauteurTotale;

    public ArbreGenealogiquePanel(ArbreGenealogique arbre) {
        this.arbre = arbre;
        setBackground(Color.WHITE);
        calculerPositions();
    }

    private void calculerPositions() {
        if (arbre == null || arbre.getRacine() == null) return;
        
        positions.clear();
        dimensions.clear();
        enfantsParParent.clear();
        
        // Construire la structure enfants par parent
        for (Personne p : arbre.getPersonnes()) {
            for (Personne parent : p.getParents()) {
                if (arbre.getPersonnes().contains(parent)) {
                    enfantsParParent.putIfAbsent(parent, new ArrayList<>());
                    enfantsParParent.get(parent).add(p);
                }
            }
        }
        
        // Grouper les personnes par profondeur
        Map<Integer, List<Personne>> personnesParProfondeur = new HashMap<>();
        
        for (Personne p : arbre.getPersonnes()) {
            int profondeur = p.getProfondeur();
            personnesParProfondeur.putIfAbsent(profondeur, new ArrayList<>());
            personnesParProfondeur.get(profondeur).add(p);
        }
        
        // Trier les profondeurs (du plus haut - grands-parents au plus bas - petits-enfants)
        List<Integer> profondeurs = new ArrayList<>(personnesParProfondeur.keySet());
        Collections.sort(profondeurs);
        
        // Calculer la hauteur totale
        hauteurTotale = (profondeurs.size() * HAUTEUR_NOEUD) + ((profondeurs.size() + 1) * ESPACE_VERTICAL);
        
        // Première passe: positionner les personnes par niveau sans tenir compte des relations
        int maxLargeurNiveau = positionnerPersonnesParNiveau(personnesParProfondeur, profondeurs);
        largeurTotale = maxLargeurNiveau + (2 * ESPACE_HORIZONTAL);
        
        // Deuxième passe: positionner les couples côte à côte
        positionnerCouples();
        
        // Troisième passe: ajuster les positions pour éviter les chevauchements
        eviterChevauchements();
        
        // Quatrième passe: positionner les parents au-dessus de leurs enfants
        positionnerParentsAuDessusEnfants();
        
        // Recalculer la largeur totale après ajustements
        recalculerDimensionsTotales();
    }
    
    private int positionnerPersonnesParNiveau(Map<Integer, List<Personne>> personnesParProfondeur, List<Integer> profondeurs) {
        int maxLargeurNiveau = 0;
        
        for (Integer profondeur : profondeurs) {
            List<Personne> personnesNiveau = personnesParProfondeur.get(profondeur);
            
            // Trier les personnes (par exemple, par nom puis prénom)
            personnesNiveau.sort(Comparator.comparing(Personne::getNom).thenComparing(Personne::getPrenom));
            
            int largeurNiveau = calculerLargeurNiveau(personnesNiveau);
            maxLargeurNiveau = Math.max(maxLargeurNiveau, largeurNiveau);
            
            // Positionner chaque personne du niveau
            int startX = ESPACE_HORIZONTAL;
            int y = ESPACE_VERTICAL + (profondeur * (HAUTEUR_NOEUD + ESPACE_VERTICAL));
            
            for (Personne p : personnesNiveau) {
                positions.put(p, new Point(startX, y));
                dimensions.put(p, new Dimension(LARGEUR_NOEUD, HAUTEUR_NOEUD));
                startX += LARGEUR_NOEUD + ESPACE_HORIZONTAL;
            }
        }
        
        return maxLargeurNiveau;
    }
    
    private int calculerLargeurNiveau(List<Personne> personnes) {
        // Calculer la largeur nécessaire pour ce niveau
        return (personnes.size() * LARGEUR_NOEUD) + ((personnes.size() - 1) * ESPACE_HORIZONTAL) + (2 * ESPACE_HORIZONTAL);
    }
    
    private void positionnerCouples() {
        // Identifier tous les couples et les positionner côte à côte
        Set<Personne> personnesTraitees = new HashSet<>();
        
        for (Personne p : arbre.getPersonnes()) {
            if (personnesTraitees.contains(p)) continue;
            
            Personne conjoint = p.getConjoint();
            if (conjoint != null && arbre.getPersonnes().contains(conjoint) && !personnesTraitees.contains(conjoint)) {
                personnesTraitees.add(p);
                personnesTraitees.add(conjoint);
                
                // Calculer la position moyenne des deux conjoints
                Point posP = positions.get(p);
                Point posConj = positions.get(conjoint);
                
                if (posP != null && posConj != null) {
                    int xMoyen = (posP.x + posConj.x) / 2;
                    
                    // Repositionner les conjoints côte à côte
                    positions.put(p, new Point(xMoyen - LARGEUR_NOEUD - DISTANCE_COUPLE/2, posP.y));
                    positions.put(conjoint, new Point(xMoyen + DISTANCE_COUPLE/2, posConj.y));
                }
            }
        }
    }
    
    private void eviterChevauchements() {
        // Regrouper les personnes par niveau
        Map<Integer, List<Personne>> personnesParY = new HashMap<>();
        
        for (Personne p : arbre.getPersonnes()) {
            Point pos = positions.get(p);
            if (pos != null) {
                int y = pos.y;
                personnesParY.putIfAbsent(y, new ArrayList<>());
                personnesParY.get(y).add(p);
            }
        }
        
        // Pour chaque niveau, trier les personnes par X et vérifier/ajuster les chevauchements
        for (int y : personnesParY.keySet()) {
            List<Personne> personnesNiveau = personnesParY.get(y);
            
            // Trier par position X
            personnesNiveau.sort(Comparator.comparing(p -> positions.get(p).x));
            
            // Vérifier et ajuster les positions pour éviter les chevauchements
            for (int i = 1; i < personnesNiveau.size(); i++) {
                Personne p1 = personnesNiveau.get(i-1);
                Personne p2 = personnesNiveau.get(i);
                
                Point pos1 = positions.get(p1);
                Point pos2 = positions.get(p2);
                
                int finP1 = pos1.x + LARGEUR_NOEUD;
                int debutP2 = pos2.x;
                
                // Si chevauchement, décaler p2 et tous les suivants
                if (finP1 + ESPACE_HORIZONTAL > debutP2) {
                    int decalage = finP1 + ESPACE_HORIZONTAL - debutP2;
                    
                    // Décaler p2 et tous les éléments suivants
                    for (int j = i; j < personnesNiveau.size(); j++) {
                        Personne pj = personnesNiveau.get(j);
                        Point posj = positions.get(pj);
                        positions.put(pj, new Point(posj.x + decalage, posj.y));
                    }
                }
            }
        }
    }
    
    private void positionnerParentsAuDessusEnfants() {
        // Pour chaque groupe parent-enfants, positionner le parent (ou le couple) au-dessus du centre des enfants
        for (Personne parent : arbre.getPersonnes()) {
            List<Personne> enfants = enfantsParParent.getOrDefault(parent, Collections.emptyList());
            
            if (!enfants.isEmpty()) {
                Personne conjoint = parent.getConjoint();
                boolean aConjoint = conjoint != null && arbre.getPersonnes().contains(conjoint);
                
                // Calculer le centre du groupe d'enfants
                int xMinEnfants = enfants.stream()
                        .mapToInt(e -> positions.get(e).x)
                        .min()
                        .orElse(0);
                int xMaxEnfants = enfants.stream()
                        .mapToInt(e -> positions.get(e).x + LARGEUR_NOEUD)
                        .max()
                        .orElse(0);
                
                int centreEnfants = (xMinEnfants + xMaxEnfants) / 2;
                
                // Positionner parent(s) au-dessus du centre des enfants
                Point posParent = positions.get(parent);
                
                if (aConjoint) {
                    // Calculer position pour le couple
                    int xParent = centreEnfants - LARGEUR_NOEUD - DISTANCE_COUPLE/2;
                    positions.put(parent, new Point(xParent, posParent.y));
                    
                    Point posConjoint = positions.get(conjoint);
                    int xConjoint = centreEnfants + DISTANCE_COUPLE/2;
                    positions.put(conjoint, new Point(xConjoint, posConjoint.y));
                } else {
                    // Positionner le parent seul
                    int xParent = centreEnfants - LARGEUR_NOEUD/2;
                    positions.put(parent, new Point(xParent, posParent.y));
                }
            }
        }
        
        // Après avoir repositionné, vérifier à nouveau les chevauchements
        eviterChevauchements();
    }
    
    private void recalculerDimensionsTotales() {
        int xMax = 0;
        int yMax = 0;
        
        for (Personne p : arbre.getPersonnes()) {
            Point pos = positions.get(p);
            if (pos != null) {
                xMax = Math.max(xMax, pos.x + LARGEUR_NOEUD);
                yMax = Math.max(yMax, pos.y + HAUTEUR_NOEUD);
            }
        }
        
        largeurTotale = xMax + ESPACE_HORIZONTAL;
        hauteurTotale = yMax + ESPACE_VERTICAL;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (arbre == null || arbre.getRacine() == null) return;
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dessiner les liens
        dessinerLiens(g2d);
        
        // Dessiner les personnes
        for (Personne p : arbre.getPersonnes()) {
            dessinerPersonne(g2d, p);
        }
        
        g2d.dispose();
    }
    
    private void dessinerLiens(Graphics2D g2d) {
        g2d.setColor(COULEUR_LIEN);
        g2d.setStroke(new BasicStroke(1.5f));
        
        // Dessiner les liens parent-enfant
        Set<String> liensParenteTraces = new HashSet<>();
        
        for (Lien lien : arbre.getLiensParente().getLiens()) {
            Personne source = lien.getPersonneSource();
            Personne destination = lien.getPersonneDestination();
            
            // Clé unique pour éviter de dessiner deux fois le même lien
            String cleUnique = source.hashCode() + "-" + destination.hashCode();
            
            if (positions.containsKey(source) && positions.containsKey(destination) && !liensParenteTraces.contains(cleUnique)) {
                liensParenteTraces.add(cleUnique);
                
                Point posSource = positions.get(source);
                Point posDest = positions.get(destination);
                
                if (lien.getType() == TypeRelation.PARENT_ENFANT) {
                    // Ligne verticale pour parent-enfant
                    int x1 = posSource.x + LARGEUR_NOEUD / 2;
                    int y1 = posSource.y + HAUTEUR_NOEUD;
                    int x2 = posDest.x + LARGEUR_NOEUD / 2;
                    int y2 = posDest.y;
                    
                    g2d.drawLine(x1, y1, x2, y2);
                    
                    // Flèche descendante
                    dessinerFleche(g2d, x1, y1, x2, y2);
                }
            }
        }
        
        // Dessiner les liens de fratrie
        Set<String> liensFratrieTraces = new HashSet<>();
        
        for (Lien lien : arbre.getLiensParente().getLiens()) {
            Personne source = lien.getPersonneSource();
            Personne destination = lien.getPersonneDestination();
            
            // Clé unique pour les fratries (minId-maxId pour ne pas les dessiner deux fois)
            String cleUnique = Math.min(source.hashCode(), destination.hashCode()) + "-" + 
                              Math.max(source.hashCode(), destination.hashCode());
            
            if (positions.containsKey(source) && positions.containsKey(destination) && 
                !liensFratrieTraces.contains(cleUnique) && lien.getType() == TypeRelation.FRERE_SOEUR) {
                liensFratrieTraces.add(cleUnique);
                
                Point posSource = positions.get(source);
                Point posDest = positions.get(destination);
                
                // Déterminer qui est à gauche et qui est à droite
                Personne gauche = (posSource.x < posDest.x) ? source : destination;
                Personne droite = (gauche == source) ? destination : source;
                Point posGauche = positions.get(gauche);
                Point posDroite = positions.get(droite);
                
                // Ligne horizontale pour frères/sœurs
                int x1 = posGauche.x + LARGEUR_NOEUD;
                int y1 = posGauche.y + HAUTEUR_NOEUD / 2;
                int x2 = posDroite.x;
                int y2 = posDroite.y + HAUTEUR_NOEUD / 2;
                
                // Ligne pointillée pour les fratries
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
                g2d.drawLine(x1, y1, x2, y2);
                g2d.setStroke(new BasicStroke(1.5f));
            }
        }
        
        // Dessiner les liens d'union (couples)
        Set<String> couplesTraites = new HashSet<>();
        
        for (Personne p : arbre.getPersonnes()) {
            Personne conjoint = p.getConjoint();
            
            if (conjoint != null && arbre.getPersonnes().contains(conjoint)) {
                String idCouple = Math.min(p.hashCode(), conjoint.hashCode()) + "-" + Math.max(p.hashCode(), conjoint.hashCode());
                
                if (!couplesTraites.contains(idCouple)) {
                    couplesTraites.add(idCouple);
                    
                    // Déterminer qui est à gauche et qui est à droite
                    Personne gauche = (positions.get(p).x < positions.get(conjoint).x) ? p : conjoint;
                    Personne droite = (gauche == p) ? conjoint : p;
                    
                    Point posGauche = positions.get(gauche);
                    Point posDroite = positions.get(droite);
                    
                    // Ligne rouge pour le couple (remplace le cœur)
                    int x1 = posGauche.x + LARGEUR_NOEUD;
                    int y1 = posGauche.y + HAUTEUR_NOEUD / 2;
                    int x2 = posDroite.x;
                    int y2 = posDroite.y + HAUTEUR_NOEUD / 2;
                    
                    // Utiliser une ligne rouge solide pour les couples
                    g2d.setColor(COULEUR_LIEN_COUPLE);
                    g2d.setStroke(new BasicStroke(2.0f));  // Ligne plus épaisse
                    g2d.drawLine(x1, y1, x2, y2);
                    
                    // Revenir aux paramètres précédents
                    g2d.setColor(COULEUR_LIEN);
                    g2d.setStroke(new BasicStroke(1.5f));
                }
            }
        }
    }
    
    private void dessinerFleche(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        // Calculer l'angle de la ligne
        double angle = Math.atan2(y2 - y1, x2 - x1);
        
        // Taille de la flèche
        int taillePointe = 8;
        
        // Calculer les points de la pointe de flèche
        int x3 = (int) (x2 - taillePointe * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - taillePointe * Math.sin(angle - Math.PI / 6));
        int x4 = (int) (x2 - taillePointe * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - taillePointe * Math.sin(angle + Math.PI / 6));
        
        // Dessiner la pointe de flèche
        int[] xPoints = {x2, x3, x4};
        int[] yPoints = {y2, y3, y4};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    
    private void dessinerPersonne(Graphics2D g2d, Personne p) {
        Point pos = positions.get(p);
        if (pos == null) return;
        
        // Déterminer la couleur en fonction du genre
        Color couleurFond = (p.getGenre() == Genre.HOMME) ? COULEUR_HOMME : COULEUR_FEMME;
        
        // Mettre en évidence la racine
        if (p.equals(arbre.getRacine())) {
            g2d.setColor(new Color(255, 215, 0, 80));  // Or transparent pour la racine
            g2d.fillRoundRect(pos.x - 3, pos.y - 3, LARGEUR_NOEUD + 6, HAUTEUR_NOEUD + 6, 15, 15);
        }
        
        // Différencier les côtés paternel et maternel
        if (p.getCote() == Cote.PATERNEL) {
            // Bord bleu pour côté paternel
            g2d.setColor(new Color(0, 0, 150));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        } else if (p.getCote() == Cote.MATERNEL) {
            // Bord rouge pour côté maternel
            g2d.setColor(new Color(150, 0, 0));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        }
        
        // Rectangle arrondi pour la personne
        g2d.setColor(couleurFond);
        g2d.fillRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        
        // Texte pour la personne
        g2d.setColor(COULEUR_TEXTE);
        g2d.setFont(FONT_PERSONNE);
        
        FontMetrics fm = g2d.getFontMetrics();
        String nom = p.getNom();
        String prenom = p.getPrenom();
        
        // Tronquer les noms trop longs
        if (fm.stringWidth(nom) > LARGEUR_NOEUD - 10) {
            nom = tronquerTexte(nom, fm, LARGEUR_NOEUD - 10);
        }
        if (fm.stringWidth(prenom) > LARGEUR_NOEUD - 10) {
            prenom = tronquerTexte(prenom, fm, LARGEUR_NOEUD - 10);
        }
        
        int yNom = pos.y + 20;
        int yPrenom = pos.y + 40;
        
        // Centrer le texte
        int xNom = pos.x + (LARGEUR_NOEUD - fm.stringWidth(nom)) / 2;
        int xPrenom = pos.x + (LARGEUR_NOEUD - fm.stringWidth(prenom)) / 2;
        
        g2d.drawString(nom, xNom, yNom);
        g2d.drawString(prenom, xPrenom, yPrenom);
        
        // Afficher l'année de naissance
        g2d.setFont(FONT_INFO);
        fm = g2d.getFontMetrics();
        
        String anneeNaissance = "";
        if (p.getDateNaissance() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getDateNaissance());
            anneeNaissance = String.valueOf(cal.get(Calendar.YEAR));
        }
        
        // Afficher l'année de décès si disponible
        if (p.getDateDeces() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getDateDeces());
            anneeNaissance += " - " + cal.get(Calendar.YEAR);
        } else if (p.getDateNaissance() != null) {
            anneeNaissance += " - ";
        }
        
        int xAnnee = pos.x + (LARGEUR_NOEUD - fm.stringWidth(anneeNaissance)) / 2;
        int yAnnee = pos.y + 60;
        
        g2d.drawString(anneeNaissance, xAnnee, yAnnee);
    }
    
    private String tronquerTexte(String texte, FontMetrics fm, int largeurMax) {
        if (fm.stringWidth(texte) <= largeurMax) return texte;
        
        String resultat = texte;
        while (fm.stringWidth(resultat + "...") > largeurMax && resultat.length() > 0) {
            resultat = resultat.substring(0, resultat.length() - 1);
        }
        
        return resultat + "...";
    }

    @Override
    public Dimension getPreferredSize() {
        // Retourner une dimension calculée en fonction des positions des personnes
        return new Dimension(largeurTotale, hauteurTotale);
    }
    
    // Méthode pour mettre à jour l'arbre et recalculer les positions
    public void mettreAJour(ArbreGenealogique nouveauArbre) {
        this.arbre = nouveauArbre;
        calculerPositions();
        repaint();
    }
}