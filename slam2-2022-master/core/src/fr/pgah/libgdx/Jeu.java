package fr.pgah.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jeu extends ApplicationAdapter {

  SpriteBatch batch;

  static int nb_coeur;
  private static int nb_ennemis;
  static int page;

  static CliqueSouris souris;

  private int boucle;

  private boolean rejouer;
  private boolean verif;
  private boolean jeu;

  private Protagonistes protagonistes;

  private Vie[] coeurs;

  private Scenario scenario;

  @Override
  public void create() {

    nb_coeur = 3;
    nb_ennemis = 7;

    batch = new SpriteBatch();
    protagonistes = new Protagonistes(nb_ennemis,batch);
    

    
    protagonistes.initialiserJoueur();
    protagonistes.initialisationEnnemies(nb_ennemis);
    initialiserVie();
    initialiserSouris();
    initialiserScenario();

    page = 0;
    boucle = 0;

    rejouer = false;
    jeu = false;

  }

  public void initialiserScenario() {
    scenario = new Scenario();
  }

  public void initialiserVie() {
    coeurs = new Vie[nb_coeur];

    for (int i = 0; i < nb_coeur; i++) {
      coeurs[i] = new Vie();
    }
  }

  public void initialiserSouris() {
    souris = new CliqueSouris();
  }

  private void reinitialiserArrierePlan() {
    // Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }



  public void majEtat() {

    protagonistes.majEtat();

    souris.majEtat();
  }

  public void dessin() {
    batch.begin();

    for (int i = 0; i < nb_coeur; i++) {
      coeurs[i].dessiner(batch);
    }

    protagonistes.dessiner();

    souris.dessiner();

    batch.end();
  }

  public void afficher() {
    scenario.histoire();

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      page = page + 1;
    }

    if (page == 1) {
      scenario.instruction();
    }

  }

  public void majEtatJeu() {

    protagonistes.majEtatJeu();

  }

  public void difficultee() {

    protagonistes.difficultee();
  }

  @Override
  public void render() {
    jeux();
    recommencer();

  }

  public void jeux() {
    if (page <= 1) {
      reinitialiserArrierePlan();
      afficher();
    }

    if (page == 2) {
      if (protagonistes.gameOver == false && protagonistes.victoire == false) {
        reinitialiserArrierePlan();
        dessin();
        majEtatJeu();
        majEtat();
        difficultee();
      } else {
        majEtatJeu();
        scenario.passer();
      }
    }
    if (page == 3) {
      scenario.recommencer();

      protagonistes.dessiner_joueur();
      

      protagonistes.majEtatJoueur();;

      if (protagonistes.estEncollisionAvecValider(scenario)
          && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        rejouer = true;
        boucle = 1;
      }

      if (protagonistes.estEncollisionAvecRefuser(scenario)
          && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        rejouer = false;
        verif = true;
        page = page + 1;
      }

    }

    if (rejouer == false && verif == true && page == 4) {
      scenario.credit();
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        page = page + 1;
      }
    }

    if (page == 5) {
      scenario.remerciement();
      jeu = true;
    }

    if (jeu == true && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
      System.exit(0);
    }

  }

  public void recommencer() {
    if (rejouer == true) {
      reinitialiserArrierePlan();
      create();
      do {
        jeux();
      } while (boucle == 1);
    }

  }

}
