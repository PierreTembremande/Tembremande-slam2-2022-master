package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Protagonistes {

    private SpriteBatch batch;

    private ArrayList<Protagoniste> protagonistes;
    private Protagoniste indexSprite;

    private static int fragile;
    
    private int resistanceRestante;
    private int longueurFenetre;
    private int hauteurFenetre;
    private int duree;
    private int compteur;

    private boolean invincible;
    boolean gameOver;
    boolean victoire;
    private boolean stop;

    private Texture imgvictoire;
    private Texture imgdefaite;

    public Protagonistes(int nb_ennemis, SpriteBatch batch) {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        protagonistes = new ArrayList<Protagoniste>();
        this.batch = batch;
        imgvictoire = new Texture("victoire.jpg");
        imgdefaite = new Texture("gameover.png");

        fragile = 0;
        duree = 0;
        compteur = 180;

        invincible = false;
        gameOver = false;
        victoire = false;

    }

    public void initialiserJoueur() {
        protagonistes.add(new Joueur());
    }

    public void initialisationEnnemies(int nb_ennemis) {

        for (int i = 0; i < nb_ennemis; i++) {
            protagonistes.add(new Ennemis());
        }
    }

    public void majEtat() {

        for (Protagoniste protagoniste : protagonistes) {
            protagoniste.majEtat();
        }
    }

    public void dessiner() {
        for (Protagoniste protagoniste : protagonistes) {
            protagoniste.dessiner(batch);
        }
    }

    public void majEtatJeu() {

        for (Protagoniste protagoniste : protagonistes) {
            if (Jeu.souris.clicGauche() && protagoniste.estEncollisionAvec(Jeu.souris)) {
                indexSprite = protagoniste;
                fragile = fragile + 1;
                resistanceRestante= protagoniste.getResistance();
                
            }
        }

             if(resistanceRestante<=0){
                protagonistes.remove(indexSprite);
            }
        
        if (invincible == false) {
            for (Protagoniste protagoniste : protagonistes) {
                if (protagoniste.estEncollisionAvecSprite(protagonistes)) {
                    duree = 120;
                    invincible = true;
                    Jeu.nb_coeur = Jeu.nb_coeur - 1;
                }
            }
        }

        if (invincible == true) {
            duree = duree - 1;
        }

        if (duree == 0 && invincible == true) {
            invincible = false;
        }

        if (Jeu.nb_coeur == 0) {
            gameOver = true;
            if ((gameOver == true)) {
                Gdx.gl.glClearColor(0, 0, .25f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(imgdefaite, hauteurFenetre / 3, longueurFenetre / 3);
                batch.end();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Jeu.page = Jeu.page + 1;
                }

            }
        }

        if (protagonistes.size() == 1) {
            victoire = true;
            if ((victoire == true)) {
                Gdx.gl.glClearColor(0, 0, .25f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(imgvictoire, hauteurFenetre / 3, longueurFenetre / 3);
                batch.end();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Jeu.page = Jeu.page + 1;
                }
            }
        }
    }

    public void difficultee() {
        if (compteur != 0 && stop == false)

        {
            compteur = compteur - 1;
        }
        if (compteur <= 0) {
            protagonistes.add(new Ennemis());
            compteur = 180;
        }

        if (protagonistes.size() == 11) {
            stop = true;
        }

        if (protagonistes.size() != 10) {
            stop = false;
        }

    }

    public static int getFragile() {
        return fragile;
    }

    public void dessiner_joueur() {

        batch.begin();
        protagonistes.get(0).dessiner(batch);
        batch.end();
    }

    public void majEtatJoueur() {
        protagonistes.get(0).majEtat();
    }

    public boolean estEncollisionAvecValider(Scenario oui) {

        if (estEncollisionAvecV(oui)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAvecV(Scenario oui) {
        if (oui.rectO.overlaps(protagonistes.get(0).rect)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estEncollisionAvecRefuser(Scenario non) {

        if (estEncollisionAvecR(non)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAvecR(Scenario non) {
        if (non.rectN.overlaps(protagonistes.get(0).rect)) {
            return true;
        } else {
            return false;
        }
    }

}
