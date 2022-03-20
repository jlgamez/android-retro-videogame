package com.example.AndroidVideoGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.example.AndroidVideoGame.gameObjects.Coin;
import com.example.AndroidVideoGame.gameObjects.SuperEnemy;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    public Game gameJgh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameJgh = (Game) findViewById(R.id.game_screen);

        // obtener nivel dificultad
        int numCoinsJgh;
        int numEnemiesJgh;
        String difficultyJgh = getIntent().getStringExtra("GAME_LEVEL");
        int speed;
        if (difficultyJgh.equals("Easy")) {
            numCoinsJgh = 4;
            numEnemiesJgh = 2;
            speed = 20;

        } else if (difficultyJgh.equals("Normal")) {
            numCoinsJgh = 3;
            numEnemiesJgh = 3;
            speed = 15;

        } else {
            numCoinsJgh = 3;
            numEnemiesJgh = 3;
            speed = 10;
        }


        ViewTreeObserver observerJh = gameJgh.getViewTreeObserver();
        observerJh.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameJgh.setWorldHeightAndWidth();
                gameJgh.initialiseGameObjects(numCoinsJgh, numEnemiesJgh);
            }
        });

        Timer timerJgh = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (gameJgh.scoreJgh > 49) { finishGame();}
                for (Coin c : gameJgh.coinsJgh) {
                    c.posYJgh += 10;
                }
                for (Coin enemyCoin : gameJgh.enemiesJgh) {
                    enemyCoin.posYJgh += 10;
                }
                gameJgh.invalidate();
            }
        };

        TimerTask drawSuperAsteroidJgh = new TimerTask() {
            @Override
            public void run() {
                if (gameJgh.scoreJgh > 49) { finishGame();}
                for (SuperEnemy spE : gameJgh.superAsteroidStorm) {
                    spE.posYJgh += 20;
                }
                gameJgh.invalidate();
            }
        };

        timerJgh.schedule(task, 100, speed);
        timerJgh.schedule(drawSuperAsteroidJgh, 20000, speed);
    }
    @Override
    public void onBackPressed() {
        gameJgh.gameMusicPlayerJgh.stop();
        gameJgh.gameMusicPlayerJgh.release();
        gameJgh.gameMusicPlayerJgh = null;
        super.onBackPressed();
    }

    public void finishGame() {
        gameJgh.gameMusicPlayerJgh.stop();
        gameJgh.gameMusicPlayerJgh.release();
        gameJgh.gameMusicPlayerJgh = null;
        Intent finishIntentJgh = new Intent(this, MainActivity.class);
        startActivity(finishIntentJgh);
    }




}