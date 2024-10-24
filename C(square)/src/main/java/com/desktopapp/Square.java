package com.desktopapp;

import javafx.scene.paint.Color;

public class Square
{
    private Ball Bolas[] = new Ball[4];
    private Spring Molas[] = new Spring[6];

    public Square(double XPos, double YPos, double Edge, double Strength, Color Color)
    {
        for(int i = 0; i < Bolas.length; ++i)
        {
            Bolas[i] = new Ball(XPos + i, YPos + i, 5, 0.5);
            Bolas[i].setColor(Color);
        }
        Molas[0] = new Spring(Bolas[0], Bolas[1], Edge, Strength);
        Molas[2] = new Spring(Bolas[0], Bolas[3], Edge, Strength);
        Molas[3] = new Spring(Bolas[1], Bolas[2], Edge, Strength);
        Molas[5] = new Spring(Bolas[2], Bolas[3], Edge, Strength);
        
        double Diagonal = Math.sqrt(Math.pow(Edge, 2) * 2);
        
        Molas[4] = new Spring(Bolas[1], Bolas[3], Diagonal, Strength);
        Molas[1] = new Spring(Bolas[0], Bolas[2], Diagonal, Strength);
    }

    public Square(Ball Ball1, Ball Ball2, Ball Ball3, Ball Ball4, double Edge, double Strength)
    {
        Bolas[0] = Ball1;
        Bolas[1] = Ball2;
        Bolas[2] = Ball3;
        Bolas[3] = Ball4;

        Molas[0] = new Spring(Bolas[0], Bolas[1], Edge, Strength);
        Molas[2] = new Spring(Bolas[0], Bolas[3], Edge, Strength);
        Molas[3] = new Spring(Bolas[1], Bolas[2], Edge, Strength);
        Molas[5] = new Spring(Bolas[2], Bolas[3], Edge, Strength);
        
        double Diagonal = Math.sqrt(Math.pow(Edge, 2) * 2);
        
        Molas[4] = new Spring(Bolas[1], Bolas[3], Diagonal, Strength);
        Molas[1] = new Spring(Bolas[0], Bolas[2], Diagonal, Strength);
    }

    public void ApplyForce()
    {
        for(int i = 0; i < Molas.length; ++i)
        {
            Molas[i].ApplyForce();
        }
    }

    public Ball getBall(int index)
    {
        return Bolas[index];
    }
    public Spring getSpring(int index)
    {
        return Molas[index];
    }
}