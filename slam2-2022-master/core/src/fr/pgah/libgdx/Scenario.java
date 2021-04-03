package fr.pgah.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Scenario {

    BitmapFont font = new BitmapFont(Gdx.files.internal("police.fnt"), Gdx.files.internal("police.png"), false);
    SpriteBatch batch = new SpriteBatch();
    Texture valider = new Texture("oui.png");
    Texture refuser =new Texture("non.png");
    Texture fini= new Texture("chateau.jpg");

    int longueurFenetre;
    int hauteurFenetre;

    int longueurImgO;
    int hauteurImgO;

    int longueurImgN;
    int hauteurImgN;

    CharSequence histoire;
    CharSequence instruction;
    CharSequence passer;
    CharSequence credit;
    CharSequence rejouer;
    CharSequence fin;

    Rectangle rectO;
    Rectangle rectN;

    public Scenario() {
        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        hauteurImgO =  valider.getHeight() ;
        longueurImgO = valider.getWidth();

        hauteurImgN = valider.getHeight();
        longueurImgN = valider.getWidth();

        rectO = new Rectangle(140, 225, longueurImgO, hauteurImgO);
        rectN = new Rectangle(220, 225, longueurImgN, hauteurImgN);

        histoire = "Le chateau est attaque par des monstres!!\n\n Preux chevalier tuez-les ou perissez.\n\n Prenez cette epee et COMBATTEZ!\n\n Attention ces derniers sont faibles mais nombreux\n\n le temps est leurs allie.\n\n\n\n [Appuyer sur ESPACE pour commencer]";
        instruction = "La touche Q sert pour se diriger ver la gauche.\nLa touche Z sert pour se diriger vers le haut.\nLa touche S sert pour se diriger vers le bas.\nLa touche D sert pour se diriger la droite.\n\nCliquez sur les image avec votre souris\nPour tuer les monstres.\n\nVous perdez si votre vie tombe a 0.\n\nAttention si vous donnez trop de coup votre epee se brisse\n\n\n[Appuyez sur ESPACE pour continuer]";
        passer = "[Appuyer sur la barre ESPACE]";
        credit = "Base sur le code de monsieur Patrice Gahide\n\n\n amélioration, modification et innovation apporte par : \n\n Monsieur Pierre Tembremande\n\n [Appuyer sur ESPACE]";
        rejouer="Voulez-vous recommencer une partie?\n\noui        ou        non\n\n\n [appuyer sur ENTREE pour valider]";
        fin="!!MERCI D'AVOIR JOUER!!";
    }

    public void histoire() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, histoire, longueurFenetre / 8, hauteurFenetre - 80);
        batch.end();
    }

    public void instruction() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, instruction, 0, hauteurFenetre);
        batch.end();
    }

    public void passer() {
        batch.begin();
        font.draw(batch, passer, longueurFenetre / 4, hauteurFenetre / 4);
        batch.end();
    }

    public void credit() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, credit, longueurFenetre/8, hauteurFenetre - 80);
        batch.end();
    }

    public void recommencer(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, rejouer, longueurFenetre/6, hauteurFenetre -160);

        batch.draw(valider, 140, 225);
        rectO.setPosition(140, 225);

        batch.draw(refuser, 220, 225);
        rectN.setPosition(220, 225);

        batch.end();

    }

    public void remerciement() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(fini,0,0);
        font.draw(batch, fin, 40, hauteurFenetre-40);
        batch.end();
    }

    public boolean estEncollisionAvecValider(Joueur joueur) {

        if (estEncollisionAvecV(joueur)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAvecV(Joueur joueur) {
        if (rectO.overlaps(joueur.rectj)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estEncollisionAvecRefuser(Joueur joueur) {

        if (estEncollisionAvecR(joueur)) {
            return true;
        }

        return false;
    }

    private boolean estEncollisionAvecR(Joueur joueur) {
        if (rectN.overlaps(joueur.rectj)) {
            return true;
        } else {
            return false;
        }
    }
}
