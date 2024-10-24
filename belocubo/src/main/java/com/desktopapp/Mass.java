package com.desktopapp;

import javafx.scene.canvas.GraphicsContext;

public class Mass implements VisualObject {
    private float floor;
    public float getFloor() {
        return floor;
    }
    public void setFloor(float floor) {
        this.floor = floor;
    }

    private float weight;
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    private float x;
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    private float y;
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

    private float vx;
    public float getVx() {
        return vx;
    }
    public void setVx(float vx) {
        this.vx = vx;
    }

    private float vy;
    public float getVy() {
        return vy;
    }
    public void setVy(float vy) {
        this.vy = vy;
    }

    public void applyForce(float fx, float fy, float dt) {
        this.vx += fx / weight * dt;
        this.vy += fy / weight * dt;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.fillOval(x, y, weight, weight);
    }

    @Override
    public void interact(float dt) {
        this.vy += 980 * dt;

        this.x += this.vx * dt;
        this.y += this.vy * dt;

        if (this.vy < 0)
            return;

        if (this.y < this.floor)
            return;
        
        this.vy *= -0.8f;
        this.vx *= 0.9f;
    }
}
