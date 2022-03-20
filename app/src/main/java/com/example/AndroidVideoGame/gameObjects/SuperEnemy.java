package com.example.AndroidVideoGame.gameObjects;

import com.example.AndroidVideoGame.Game;

import java.util.Random;

public class SuperEnemy extends  SuperGameObject{
    private final Game game;
    Random randomJgh = new Random();

    public SuperEnemy(Game game) {
        super(0, -80, 200);
        this.game = game;
        setRandomXPosition();
        setRandomYPosition();
    }

    public void setRandomXPosition() {
        // posicionar asteroide aleatoriamente en eje X
        super.posXJgh = randomJgh.nextInt(game.getWidth());
    }

    public void  setRandomYPosition() {
        // posicionar asteroides aleatoriamente en eje Y
        int max = 200;
        int min = 40;
        super.posYJgh = -(randomJgh.nextInt(max - min) + min);
    }
}
