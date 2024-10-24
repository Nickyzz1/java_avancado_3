package com.desktopapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spring implements VisualObject {

    public Spring(Mass massA, Mass massB, float size, float k) {
        this.massA = massA;
        this.massB = massB;
        this.size = size;
        this.k = k;
    }

    private Mass massA;
    private Mass massB;
    private float size;
    private float k;

    @Override
    public void draw(GraphicsContext g) {
        g.setStroke(Color.RED);
        g.setLineWidth(5f);

        var dx = massA.getX() + massA.getWeight() / 2 - massB.getX() - massB.getWeight() / 2;
        var dy = massA.getY() + massA.getWeight() / 2 - massB.getY() - massB.getWeight() / 2;
        var dist = (float)Math.sqrt(dx * dx + dy * dy);
        if (dist == 0)
            return;
        var ux = dx / dist;
        var uy = dy / dist;

        var tx = -uy;
        var ty = ux;

        var x = massB.getX() + massB.getWeight() / 2;
        var y = massB.getY() + massB.getWeight() / 2;
        
        var section = dist / 5;

        var nx = x + ux * section;
        var ny = y + uy * section;
        
        g.strokeLine(x, y, nx, ny);
        x = nx;
        y = ny;
        
        for (int i = 0; i < 3; i++) {
            nx = x + tx * 20;
            ny = y + ty * 20;
            
            g.strokeLine(x, y, nx, ny);
            
            x += ux * section;
            y += uy * section;
            
            g.strokeLine(x, y, nx, ny);
        }
        
        nx = x +  ux * section;
        ny = y + uy * section;
        
        g.strokeLine(x, y, nx, ny);
    }

    @Override
    public void interact(float dt) {
        var dx = massA.getX() - massB.getX();
        var dy = massA.getY() - massB.getY();
        var dist = (float)Math.sqrt(dx * dx + dy * dy);
        if (dist == 0)
            return;

        var def = dist - size;
        var force = -k * def;

        var ux = dx / dist;
        var uy = dy / dist;

        var fx = force * ux;
        var fy = force * uy;

        massA.applyForce(fx, fy, dt);
        massB.applyForce(-fx, -fy, dt);
    }   
}