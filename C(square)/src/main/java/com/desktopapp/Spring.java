package com.desktopapp;

public class Spring
{
    private static long FPS;
    public static void setFPS(long fps){FPS = fps;}

    private double Strength, Length;
    private Ball Ball1, Ball2;

    public void SetStrength(double Strength){this.Strength = Strength;}
    public double GetStrength(){return Strength;}
    public void SetLength(double Length){this.Length = Length;}
    public double GetLength(){return Length;}
    public void SetBall1(Ball Ball1){this.Ball1 = Ball1;}
    public Ball GetBall1(){return Ball1;}
    public void SetBall2(Ball Ball2){this.Ball2 = Ball2;}
    public Ball GetBall2(){return Ball2;}

    public Spring(Ball Ball1, Ball Ball2, double Length, double Strength)
    {
        this.Ball1 = Ball1;
        this.Ball2 = Ball2;
        this.Length = Length;
        this.Strength = Strength;
    }

    public void ApplyForce()
    {
        double Distance = Math.sqrt(Math.pow(Ball1.getX() - Ball2.getX(), 2) + Math.pow(Ball1.getY() - Ball2.getY(), 2));
        double Force = Strength * (Length - Distance);

        if(Distance == 0){Distance = 1;}

        double Cosseno = (Ball1.getX() - Ball2.getX()) / Distance;
        double Seno = (Ball1.getY() - Ball2.getY()) / Distance;

        double Ball1Accel = Force / Ball1.getMass();

        Ball1.setXSpeed(Ball1.getXSpeed() + (Ball1Accel * Cosseno) / FPS);

        Ball1.setYSpeed(Ball1.getYSpeed() + (Ball1Accel * Seno) / FPS);

        double Ball2Accel = Force / Ball2.getMass();

        Ball2.setXSpeed(Ball2.getXSpeed() + (Ball2Accel * -Cosseno) / FPS);

        Ball2.setYSpeed(Ball2.getYSpeed() + (Ball2Accel * -Seno) / FPS);
    }
}