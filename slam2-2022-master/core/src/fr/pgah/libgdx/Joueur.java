package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Joueur extends Protagoniste {

    double facteurTaille2;

    public Joueur() {

        initialiser();

    }

    private void initialiser() {

        img = new Texture("joueur.jpg");

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        facteurTaille2 = 0.35;

        longueurImage = (int) (img.getWidth() * facteurTaille2);
        hauteurImage = (int) (img.getHeight() * facteurTaille2);

        rect= new Rectangle(coordX, coordY, longueurImage, hauteurImage);

        coordX = 0;
        coordY = 0;
        pas = 8;

    }

    protected void deplacer() {

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            coordX = coordX - pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            coordY = coordY + pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            coordY = coordY - pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            coordX = coordX + pas;
        }

        rect.setPosition(coordX, coordY);
    }

    protected void resterDansLeCadre() {

        // Gestion bordure droite
        if (coordX + longueurImage > longueurFenetre) {
            coordX = longueurFenetre - longueurImage;

        }

        // Gestion bordure gauche
        if (coordX < 0) {
            coordX = 0;

        }

        // Gestion bordures haute
        if (coordY + hauteurImage > hauteurFenetre) {
            coordY = hauteurFenetre - hauteurImage;

        }

        // Gestion bordure basse
        if (coordY < 0) {
            coordY = 0;

        }

        rect.setPosition(coordX, coordY);
    }

    public void majEtat() {
        deplacer();
        resterDansLeCadre();
    }

    public void dessiner(SpriteBatch batch) {
        batch.begin();
        batch.draw(img, coordX, coordY, longueurImage, hauteurImage);
        batch.end();

    }

    protected boolean estEncollisionAvecSprite(ArrayList<Protagoniste> protagonistes) {
        for (Protagoniste ennemis : protagonistes) {
            if (estEncollisionAveclui(ennemis)) {
                return true;
            }
        }
        return false;
    }

    protected boolean estEncollisionAveclui(Protagoniste sprite) {
        if (rect.overlaps(sprite.rect) && sprite instanceof Ennemis) {
            return true;
        } else {
            return false;
        }

    }

}
