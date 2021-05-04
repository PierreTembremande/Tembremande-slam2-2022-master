package fr.pgah.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro extends ApplicationAdapter {

  SpriteBatch batch;

  static int NB_COEUR;
  static int NB_ENNEMIES;
  static int page;

  static CliqueSouris souris;

  int ajouts;
  int boucle;

  boolean rejouer;
  boolean verif;
  boolean jeu;

  Protagonistes protagonistes;

  Vie[] coeurs;

 

  Scenario scenario;

  @Override
  public void create() {

    NB_COEUR = 3;
    NB_ENNEMIES = 7;

    protagonistes = new Protagonistes();
    batch = new SpriteBatch();

    
    protagonistes.initialiserJoueur();
    protagonistes.initialisationEnnemies();
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
    coeurs = new Vie[NB_COEUR];

    for (int i = 0; i < NB_COEUR; i++) {
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

    for (int i = 0; i < NB_COEUR; i++) {
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

      protagonistes.protagonistes.get(0).dessiner(batch);

      protagonistes.protagonistes.get(0).majEtat();

      if (scenario.estEncollisionAvecValider(protagonistes.protagonistes.get(0))
          && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        rejouer = true;
        boucle = 1;
      }

      if (scenario.estEncollisionAvecRefuser(protagonistes.protagonistes.get(0))
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
