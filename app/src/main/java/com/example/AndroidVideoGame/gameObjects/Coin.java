package com.example.AndroidVideoGame.gameObjects;

import java.util.Random;

import com.example.AndroidVideoGame.Game;

public class Coin extends SuperGameObject {
    private final Game game;
    public boolean isEnemy;
    private final int points;
    Random randomJgh = new Random();

    public Coin(Game game, boolean isEnemy) {
        super(0, 80, 80);
        this.game = game;
        setRandomXPosition();
        this.isEnemy = isEnemy;
        // setear tamaño de objeto y puntuacion
        if (isEnemy) {
            super.incrementJhg = 100;
            this.points = -2;
        } else {
            this.points = 1;
        }
    }

    public int getPoints() {
        return points;
    }

    public void setRandomXPosition() {
        // posicionar la moneda aleatoriamente en eje X
        super.posXJgh = randomJgh.nextInt(game.getWidth());
    }





}
