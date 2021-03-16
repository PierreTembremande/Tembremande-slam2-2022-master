package fr.pgah.libgdx;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

    Rectangle rect;

    Texture img=new Texture("projectile.jpg");;

    int longueurFenetre;
    int hauteurFenetre;
    int coordX;
    int coordY;
    int vitesse;
    int vitesseRotation;
    int hauteurImg;
    int longueurImg;

    boolean versLaDroite;
    boolean versLeHaut;

    double facteurTaille;

    float rotation;
    
    Random generateurAleatoire;

    public Object dessin;

    public Sprite() {

        initialiser();

    }

    private void initialiser() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        generateurAleatoire = new Random();
        facteurTaille = 0.35;

        vitesse = 1 ;

        rotation = 1;
        vitesseRotation = 5 + generateurAleatoire.nextInt(21);
        rotation = rotation + vitesseRotation;

        hauteurImg = (int) (img.getHeight() * facteurTaille);
        longueurImg = (int) (img.getWidth() * facteurTaille);

        coordX = generateurAleatoire.nextInt(longueurFenetre - (longueurImg));
        coordY = generateurAleatoire.nextInt(hauteurFenetre - (hauteurImg));

        versLaDroite = generateurAleatoire.nextBoolean();
        versLeHaut = generateurAleatoire.nextBoolean();

        rect = new Rectangle(coordX, coordY, longueurImg, hauteurImg);

    }

    private void deplacer() {

        if (versLaDroite) {
            coordX = coordX + vitesse;
        } else {
            coordX = coordX - vitesse;
        }
        if (versLeHaut) {
            coordY = coordY + vitesse;
        } else {
            coordY = coordY - vitesse;
        }

        rect.setPosition(coordX, coordY);
    }

    private void resterDansLeCadre() {
        // Gestion bordure droite
        if (coordX + longueurImg > longueurFenetre) {
            coordX = longueurFenetre - longueurImg;
            versLaDroite = false;
        }

        // Gestion bordure gauche
        if (coordX < 0) {
            coordX = 0;
            versLaDroite = true;
        }

        // Gestion bordures haute
        if (coordY + hauteurImg > hauteurFenetre) {
            coordY = hauteurFenetre - hauteurImg;
            versLeHaut = false;
        }

        // Gestion bordure basse
        if (coordY < 0) {
            coordY = 0;
            versLeHaut = true;
        }

        rect.setPosition(coordX, coordY);
    }

    private void pivoter() {
        rotation = rotation + vitesseRotation;
    }

    public void majEtat() {
        deplacer();
        resterDansLeCadre();
        pivoter();
    }

    public void dessiner(SpriteBatch batch) {

        batch.draw(img, coordX, coordY, longueurImg / 2, hauteurImg / 2, longueurImg, hauteurImg, 1, 1, rotation, 0, 0,
                img.getWidth(), img.getHeight(), false, false);

    }

    public boolean estEncollisionAvec(Joueur joueur) {

        if(estEncollisionAveclui(joueur)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAveclui(Joueur joueur) {
        if (rect.overlaps(joueur.rectj)) {
            return true;
        } else {
            return false;
        }
    }
}
