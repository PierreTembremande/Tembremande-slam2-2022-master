package fr.pgah.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Vie {
    private Texture img = new Texture("coeur.png");
    private int hauteurFenetre;
    private int longueurImage;
    private int hauteurImage;
    private int[] coordX;
    private int coordY;

    double reduction;

    Rectangle rect;

    public Vie() {

        coordX = new int[Jeu.nb_coeur];
        hauteurFenetre = Gdx.graphics.getHeight();

        reduction = 0.20;

        longueurImage = (int) (img.getWidth() * reduction);
        hauteurImage = (int) (img.getHeight() * reduction);

        for (int i = 0; i < Jeu.nb_coeur; i++) {
            coordX[i] = i * longueurImage;
        }

        coordY = (hauteurFenetre - hauteurImage);
    }

    public void dessiner(SpriteBatch batch) {

        for (int i = 0; i < Jeu.nb_coeur; i++) {
            batch.draw(img, coordX[i], coordY, longueurImage, hauteurImage);
        }

    }

}
