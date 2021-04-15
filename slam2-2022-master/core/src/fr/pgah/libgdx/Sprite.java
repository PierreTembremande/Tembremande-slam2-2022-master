package fr.pgah.libgdx;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sprite extends Protagoniste {

    int vitesseRotation;
    boolean versLaDroite;
    boolean versLeHaut;

    double facteurTaille;

    float rotation;

    Random generateurAleatoire;

    public Sprite() {

        initialiser();

    }

    private void initialiser() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        img = new Texture("projectile.jpg");

        generateurAleatoire = new Random();
        facteurTaille = 0.35;

        vitesse = 1;

        rotation = 1;
        vitesseRotation = 5 + generateurAleatoire.nextInt(21);
        rotation = rotation + vitesseRotation;

        hauteurImage = (int) (img.getHeight() * facteurTaille);
        longueurImage = (int) (img.getWidth() * facteurTaille);

        coordX = generateurAleatoire.nextInt(longueurFenetre - (longueurImage));
        coordY = generateurAleatoire.nextInt(hauteurFenetre - (hauteurImage));

        versLaDroite = generateurAleatoire.nextBoolean();
        versLeHaut = generateurAleatoire.nextBoolean();

        rect = new Rectangle(coordX, coordY, longueurImage, hauteurImage);

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
        if (coordX + longueurImage > longueurFenetre) {
            coordX = longueurFenetre - longueurImage;
            versLaDroite = false;
        }

        // Gestion bordure gauche
        if (coordX < 0) {
            coordX = 0;
            versLaDroite = true;
        }

        // Gestion bordures haute
        if (coordY + hauteurImage > hauteurFenetre) {
            coordY = hauteurFenetre - hauteurImage;
            versLeHaut = false;
        }

        // Gestion bordure basse
        if (coordY < 0) {
            coordY = 0;
            versLeHaut = true;
        }

        rect.setPosition(coordX, coordY);
    }

    // private void pivoter() {
    // rotation = rotation + vitesseRotation;
    // }

    public void majEtat() {
        deplacer();
        resterDansLeCadre();
        // pivoter();
    }

    public void dessiner(SpriteBatch batch) {

        batch.draw(img, coordX, coordY, longueurImage / 2, hauteurImage / 2, longueurImage, hauteurImage, 1, 1,
                rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);

    }

    public boolean estEncollisionAvec(CliqueSouris souris) {

        if (estEncollisionAveclui(souris)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAveclui(CliqueSouris souris) {
        if (rect.overlaps(souris.rect)) {
            return true;
        } else {
            return false;
        }
    }

}
