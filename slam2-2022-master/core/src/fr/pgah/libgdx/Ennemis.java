package fr.pgah.libgdx;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ennemis extends Protagoniste {

    private int vitesseRotation;
    private boolean versLaDroite;
    private boolean versLeHaut;

    private double facteurTaille;

    private float rotation;

    private Random generateurAleatoire;

    public Ennemis() {

        initialiser();

    }

    private void initialiser() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        img = new Texture("projectile.jpg");

        generateurAleatoire = new Random();
        facteurTaille = 0.35;

        resistance = 3;

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

    protected void deplacer() {

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

    protected void resterDansLeCadre() {
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

        if ((Protagonistes.getFragile() < 8)) {
            batch.draw(img, coordX, coordY, longueurImage / 2, hauteurImage / 2, longueurImage, hauteurImage, 1, 1,
                    rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);

        } else if (Protagonistes.getFragile() >= 8 && Protagonistes.getFragile() < 16) {
            img = new Texture("ennemie1.png");
            batch.draw(img, coordX, coordY, longueurImage / 2, hauteurImage / 2, longueurImage, hauteurImage, 1, 1,
                    rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);

        } else if ((Protagonistes.getFragile() >= 16)) {
            img = new Texture("ennemie2.png");
            batch.draw(img, coordX, coordY, longueurImage / 2, hauteurImage / 2, longueurImage, hauteurImage, 1, 1,
                    rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
        }

    }

    protected boolean estEncollisionAvec(CliqueSouris souris) {

        if (estEncollisionAveclui(souris)) {
            return true;

        }

        return false;
    }

    protected boolean estEncollisionAveclui(CliqueSouris souris) {
        if (rect.overlaps(souris.rect)) {
            return true;
        } else {
            return false;
        }
    }

    protected  int getResistance(){
        return resistance;
    }

    protected void perteDeResistance() {
        int degatsAvenir = CliqueSouris.getDegats();
        resistance = resistance - degatsAvenir;
    }

}
