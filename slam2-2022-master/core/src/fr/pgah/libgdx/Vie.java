package fr.pgah.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Vie {
    Texture img = new Texture("coeur.png");
    int longueurFenetre;
    int hauteurFenetre;
    int longueurImage;
    int hauteurImage;
    int[] coordX;
    int coordY;

    double reduction;

    Rectangle rect;

    public Vie() {

        coordX = new int[Intro.NB_COEUR];

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        reduction = 0.20;

        longueurImage = (int) (img.getWidth() * reduction);
        hauteurImage = (int) (img.getHeight() * reduction);

        for (int i = 0; i < Intro.NB_COEUR; i++) {
            coordX[i] = i * longueurImage;
        }

        coordY = (hauteurFenetre - hauteurImage);
    }

    public void dessiner(SpriteBatch batch) {

        for (int i = 0; i < Intro.NB_COEUR; i++) {
            batch.draw(img, coordX[i], coordY, longueurImage, hauteurImage);
        }

    }

}
