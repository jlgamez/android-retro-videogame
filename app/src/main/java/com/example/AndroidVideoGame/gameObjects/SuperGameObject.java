package com.example.AndroidVideoGame.gameObjects;


import android.graphics.Rect;

public class SuperGameObject {
    public int posXJgh;
    public int posYJgh;
    public int incrementJhg;
    private Rect gameObjectRectF;


    public SuperGameObject(int posX, int posY, int increment) {
        this.posXJgh = posX;
        this.posYJgh = posY;
        this.incrementJhg = increment;
    }

    public Rect makeRect() {
        this.gameObjectRectF = new Rect(
                posXJgh,
                (posYJgh - incrementJhg),
                (posXJgh + incrementJhg),
                posYJgh
        );
        return this.gameObjectRectF;
    }

    public Rect getGameObjectRect() {
        return this.gameObjectRectF;
    }
}
