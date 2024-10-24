package com.desktopapp;

public class Ball
{
    public Point Position, Speed;
    public double Mass, Radius;

    public double getX() {return Position.getX();}
    public void setX(double x) {Position.setX(x);}
    public double getY() {return Position.getY();}
    public void setY(double y) {Position.setY(y);}
    public double getRadius(){return this.Radius;}
    public void setRadius(double Radius){this.Radius = Radius;}
    public double getMass(){return this.Mass;}
    public void setMass(double Mass){this.Mass = Mass;}
 
    public double getXSpeed() {return Speed.getX();}
    public void setXSpeed(double x) {Speed.setX(x);}
    public double getYSpeed() {return Speed.getY();}
    public void setYSpeed(double y) {Speed.setY(y);}
    
    public Ball(){}

    public Ball(double X, double Y, double Mass, double Radius)
    {
        this.Position = new Point(X, Y);
        this.Speed = new Point();
        this.Mass = Mass;
        this.Radius = Radius;
    }

    public void ApplySpeed()
    {
        Position.setX(Position.getX() + Speed.getX());
        Position.setY(Position.getY() + Speed.getY());
    }
}
