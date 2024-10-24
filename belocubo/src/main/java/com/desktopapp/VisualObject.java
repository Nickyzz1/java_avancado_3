package com.desktopapp;

import javafx.scene.canvas.GraphicsContext;

public interface VisualObject {
    void draw(GraphicsContext g);
    void interact(float dt);
}