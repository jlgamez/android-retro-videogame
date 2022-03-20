package com.example.AndroidVideoGame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.AndroidVideoGame.gameObjects.Coin;
import com.example.AndroidVideoGame.gameObjects.SuperEnemy;
import com.example.AndroidVideoGame.gameObjects.SuperGameObject;

import java.io.IOException;

public class Game extends View {

    private SuperGameObject basketJgh;
    public Coin[] coinsJgh;
    public Coin[] enemiesJgh;
    public SuperEnemy[] superAsteroidStorm;

    Rect backgroundWorldJgh;
    private final Bitmap bitmapBackgroundJgh = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    private final Bitmap bitmapNaveJgh = BitmapFactory.decodeResource(getResources(), R.drawable.shuttle2);
    private final Bitmap bitmapCoinJgh = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
    private final Bitmap bitmapEnemy = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
    private final Bitmap bitmapSuperAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.super_asteroid);

    public int scoreJgh;
    private final Paint scorePaintJgh = new Paint();

    public MediaPlayer gameMusicPlayerJgh;
    private MediaPlayer coinSoundEffectJgh;
    private MediaPlayer errorSoundEffectJgh;
    public MediaPlayer superAsteroidSoundJgh;

    public Game(Context context) {
        super(context);
    }

    public Game(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // setear los media players
        gameMusicPlayerJgh = MediaPlayer.create(context, R.raw.game_music);
        coinSoundEffectJgh = MediaPlayer.create(context, R.raw.coin_sound);
        errorSoundEffectJgh = MediaPlayer.create(context, R.raw.error_sound);
        superAsteroidSoundJgh = MediaPlayer.create(context, R.raw.asteroid_collision_sound);

        gameMusicPlayerJgh.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playSound(gameMusicPlayerJgh);
            }
        });
        gameMusicPlayerJgh.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                gameMusicPlayerJgh.start();
            }
        });

    }

    public Game(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Game(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setWorldHeightAndWidth() {
        int worldWidthJgh = Resources.getSystem().getDisplayMetrics().widthPixels;
        int worldHeightJgh = Resources.getSystem().getDisplayMetrics().heightPixels;
        backgroundWorldJgh = new Rect(0,0, worldWidthJgh, worldHeightJgh);
    }

    public void initialiseGameObjects(int numCoins, int numEnemies) {
        // cargar nave
        this.basketJgh = new SuperGameObject(getWidth()/2, getHeight() -100, 200);
        // cargar monedas segun dificultad y 3 super enemigos
        coinsJgh = new Coin[numCoins];
        enemiesJgh = new Coin[numEnemies];
        superAsteroidStorm = new SuperEnemy[3];
        for (int i = 0; i < coinsJgh.length; i++) {
            coinsJgh[i] = new Coin(this, false);
        }
        for (int j = 0; j < enemiesJgh.length; j++) {
            enemiesJgh[j] = new Coin(this, true);
        }
        for (int k = 0; k < superAsteroidStorm.length; k++) {
            superAsteroidStorm[k] = new SuperEnemy(this);
        }

    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

        // renderizar mundo
        canvas.drawBitmap(bitmapBackgroundJgh, null, backgroundWorldJgh, null);

        // renderizar nave
        canvas.drawBitmap(bitmapNaveJgh, null, basketJgh.makeRect(), null);

        //renderizar monedas
        for (Coin c : coinsJgh) {
            canvas.drawBitmap(bitmapCoinJgh, null, c.makeRect(), null);
            updateYPositions(c);
            collision(c);
        }
        for (Coin c : enemiesJgh) {
            canvas.drawBitmap(bitmapEnemy, null, c.makeRect(), null);
            updateYPositions(c);
            collision(c);
        }

        for (SuperEnemy spE : superAsteroidStorm) {
            canvas.drawBitmap(bitmapSuperAsteroid, null, spE.makeRect(), null);
            updateYPositions(spE);
            collision(spE);
        }

        // actualizar puntuacion
        updateScore(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionJgh = event.getAction();
        if (MotionEvent.ACTION_MOVE == actionJgh) {
            // actualizar posicion X del objeto nave
            basketJgh.posXJgh = (int) event.getX();
            this.invalidate();
        }
        return true;
    }

    private void updateYPositions(Object gameObject) {
        if (gameObject.getClass() == Coin.class) {
            Coin currentCoinJgh = (Coin) gameObject;
            if (currentCoinJgh.posYJgh > this.getHeight()) {
                currentCoinJgh.posYJgh = 80;
                currentCoinJgh.setRandomXPosition();
            }
        } else {
            SuperEnemy currentSuperAsteroidJgh = (SuperEnemy) gameObject;
            if (currentSuperAsteroidJgh.posYJgh > (this.getHeight() * 6)) {
                currentSuperAsteroidJgh.setRandomYPosition();
                currentSuperAsteroidJgh.setRandomXPosition();
            }
        }
    }

    private void collision(Object gameObject) {
        if (gameObject.getClass() == Coin.class) {
            Coin coin = (Coin) gameObject;
            if (Rect.intersects(basketJgh.getGameObjectRect(), coin.getGameObjectRect())) {
                if (coin.isEnemy) {
                    playSound(errorSoundEffectJgh);
                } else {
                    playSound(coinSoundEffectJgh);
                }
                scoreJgh += coin.getPoints();
                // recargar moneda en pantalla
                coin.posYJgh = 80;
                coin.setRandomXPosition();
            }
        } else {
            SuperEnemy superEnemy = (SuperEnemy) gameObject;
            if (Rect.intersects(basketJgh.getGameObjectRect(), superEnemy.getGameObjectRect())) {
                playSound(superAsteroidSoundJgh);
                scoreJgh -= 5;
                // recargar super asteroide
                superEnemy.posYJgh = this.getHeight() + 100;
                superEnemy.setRandomXPosition();
            }
        }

        // impedimos que la puntuacion baje de cero
        if (scoreJgh < 1) {
            scoreJgh = 0;
        }
    }

    private void updateScore(Canvas canvas) {
        scorePaintJgh.setColor(Color.WHITE);
        scorePaintJgh.setTextSize(200);
        scorePaintJgh.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(
                String.valueOf(scoreJgh),
                100,
                200,
                scorePaintJgh
        );
    }

    private void playSound(MediaPlayer mpJgh) {
        if (mpJgh.isPlaying()) {
            try {
                mpJgh.stop();
                mpJgh.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mpJgh.start();
    }
}
