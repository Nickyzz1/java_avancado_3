package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController implements Initializable
{
    static final double PixelToMeter = 50, EnergyLoss = 0.7, Friction = EnergyLoss * 0.98, FramesPerSecond = 30, Gravity = 10;
    static boolean DoGravity = true;

    public static Scene CreateScene() throws Exception
    {
        URL SceneUrl = MainController.class.getResource("MainWindow.fxml");
        Parent Root = FXMLLoader.load(SceneUrl);
        Scene Scene = new Scene(Root);
        return Scene;
    }

    protected static long RNGNumber = new Date().getTime();

    public static int RNG()
    {
        RNGNumber = RNGNumber * 48271 % 2147483647;
        return (int) RNGNumber;
    }

    @FXML
    protected VBox Box;
    @FXML
    protected Canvas Tela;

    protected int CurrentBall = 0;

    protected Timer Timer = new Timer();

    protected Ball[] Bolas = new Ball[4];

    protected Spring[] Molas = new Spring[0];

    protected Square Quadrado;

    protected void Fisica(Ball Bola)
    {
        if(DoGravity)
        {
            Bola.setYSpeed(Bola.getYSpeed() + Gravity / FramesPerSecond);
        }
        Bola.ApplySpeed();
        if((Bola.getY() + Bola.getRadius()) * PixelToMeter > Tela.getHeight())
        {
            Bola.setY(Bola.getY() + (Tela.getHeight() / PixelToMeter - (Bola.getY() + Bola.getRadius())));
            Bola.setYSpeed(-Bola.getYSpeed() * EnergyLoss);
            Bola.setXSpeed(Bola.getXSpeed() * Friction);
        }
        if((Bola.getX() + Bola.getRadius()) * PixelToMeter > Tela.getWidth())
        {
            Bola.setX(Bola.getX() + (Tela.getWidth() / PixelToMeter - (Bola.getX() + Bola.getRadius())));
            Bola.setXSpeed(-Bola.getXSpeed() * EnergyLoss);
            Bola.setYSpeed(Bola.getYSpeed() * Friction);
        }
        if((Bola.getY() - Bola.getRadius()) * PixelToMeter < 0)
        {
            Bola.setY(Bola.getY() - (Bola.getY() - Bola.getRadius()));
            Bola.setYSpeed(-Bola.getYSpeed() * EnergyLoss);
            Bola.setXSpeed(Bola.getXSpeed() * Friction);
        }
        if((Bola.getX() - Bola.getRadius()) * PixelToMeter < 0)
        {
            Bola.setX(Bola.getX() - (Bola.getX() - Bola.getRadius()));
            Bola.setXSpeed(-Bola.getXSpeed() * EnergyLoss);
            Bola.setYSpeed(Bola.getYSpeed() * Friction);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        Spring.setFPS((long)FramesPerSecond);
        Tela.setFocusTraversable(true);
        Reset();
        Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                for(int i = 0; i < Molas.length; ++i)
                {
                    Molas[i].ApplyForce();
                }
                for(int i = 0; i < Bolas.length; ++i)
                {
                    Fisica(Bolas[i]);
                }
                Quadrado.ApplyForce();
                for(int i = 0; i < 4; ++i)
                {
                    Fisica(Quadrado.getBall(i));
                }
                Draw();
                Box.requestLayout();
            }
        }, (long)(1000 / FramesPerSecond), (long) (1000 / FramesPerSecond));
        Draw();
    }

    public void DrawBall(GraphicsContext gc, Ball Bola)
    {
        gc.setFill(Bola.getColor());
        gc.setStroke(Color.rgb(0, 0, 0));
        gc.fillOval
        (
            (Bola.getX() - Bola.getRadius()) * PixelToMeter, 
            (Bola.getY() - Bola.getRadius()) * PixelToMeter,
            Bola.getRadius() * 2 * PixelToMeter,
            Bola.getRadius() * 2 * PixelToMeter
        );
        gc.strokeOval
        (
            (Bola.getX() - Bola.getRadius()) * PixelToMeter, 
            (Bola.getY() - Bola.getRadius()) * PixelToMeter,
            Bola.getRadius() * 2 * PixelToMeter,
            Bola.getRadius() * 2 * PixelToMeter
        );
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillText(String.format("%.2f", Bola.getMass()), Bola.getX() * PixelToMeter, Bola.getY() * PixelToMeter);
    }

    public void Draw()
    {
        GraphicsContext gc = Tela.getGraphicsContext2D();
        gc.clearRect(0, 0, Tela.getWidth(), Tela.getHeight());
        gc.setFill(Color.rgb(255, 128, 0));
        gc.fillRect(Tela.getLayoutX(), Tela.getLayoutY(), Tela.getWidth(), Tela.getHeight());

        gc.setLineWidth(5);
        gc.setStroke(Color.rgb(0,128,0));
        for(int i = 0; i < Molas.length; i++)
        {
            gc.strokeLine
            (
                Molas[i].GetBall1().getX() * PixelToMeter,
                Molas[i].GetBall1().getY() * PixelToMeter,
                Molas[i].GetBall2().getX() * PixelToMeter,
                Molas[i].GetBall2().getY() * PixelToMeter
            );
        }
        
        for(int i = 0; i < 6; ++i)
        {
            gc.strokeLine
            (
                Quadrado.getSpring(i).GetBall1().getX() * PixelToMeter,
                Quadrado.getSpring(i).GetBall1().getY() * PixelToMeter,
                Quadrado.getSpring(i).GetBall2().getX() * PixelToMeter,
                Quadrado.getSpring(i).GetBall2().getY() * PixelToMeter
            );
        }


        gc.setLineWidth(2);
        gc.setFill(Color.rgb(0, 0, 0));
        for(int i = 0; i < Bolas.length; ++i)
        {
            if(i == CurrentBall)
            {
                continue;
            }else
            {
                DrawBall(gc, Bolas[i]);
            }
        }

        for(int i = 0; i < 4; ++i)
        {
            DrawBall(gc, Quadrado.getBall(i));
        }

        gc.setFill(Color.rgb(255, 0, 0));
        gc.setStroke(Color.rgb(255, 0, 0));
        gc.fillOval
        (
            (Bolas[CurrentBall].getX() - Bolas[CurrentBall].getRadius()) * PixelToMeter, 
            (Bolas[CurrentBall].getY() - Bolas[CurrentBall].getRadius()) * PixelToMeter,
            Bolas[CurrentBall].getRadius() * 2 * PixelToMeter,
            Bolas[CurrentBall].getRadius() * 2 * PixelToMeter
        );
        gc.strokeOval
        (
            (Bolas[CurrentBall].getX() - Bolas[CurrentBall].getRadius()) * PixelToMeter, 
            (Bolas[CurrentBall].getY() - Bolas[CurrentBall].getRadius()) * PixelToMeter,
            Bolas[CurrentBall].getRadius() * 2 * PixelToMeter,
            Bolas[CurrentBall].getRadius() * 2 * PixelToMeter
        );
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillText(String.format("%.2f", Bolas[CurrentBall].getMass()), Bolas[CurrentBall].getX() * PixelToMeter, Bolas[CurrentBall].getY() * PixelToMeter);
    }

    public void Reset()
    {
        for(int i = 0; i < Bolas.length; ++i)
        {
            Bolas[i] = new Ball
            (
                (Tela.getWidth() - RNG() % Tela.getWidth()) / PixelToMeter,
                ((Tela.getHeight() / 2) - RNG() % (Tela.getHeight() / 2)) / PixelToMeter,
                //RNG() % 10 + 1,
                5,
                //0.2 * (RNG() % 5 + 3)
                1
            );
            Bolas[i].setColor(Color.rgb(RNG() % 256, RNG() % 256, RNG() % 256));
            Bolas[i].setXSpeed(2.0 - 0.4 * (RNG() % 10 + 1));
        }
        for (int i = 0; i < Molas.length; i++)
        {
            Molas[i] = new Spring(Bolas[RNG() % Bolas.length], Bolas[RNG() % Bolas.length], 5.0 - 0.5 * (RNG() % 10), RNG() % 5 + 1); 
            //Molas[i] = new Spring(Bolas[0], Bolas[1], 5.0, 1); 
        }
        Quadrado = new Square
        (
            Bolas[0],
            Bolas[1],
            Bolas[2],
            Bolas[3],
            5.0,
            50
        );
    }

    @FXML
    protected void BallControl(KeyEvent Event)
    {
        switch(Event.getCode())
        {
            case W:
            {
                Bolas[CurrentBall].setYSpeed(Bolas[CurrentBall].getYSpeed() - 3);
            }
            break;
            case A:
            {
                Bolas[CurrentBall].setXSpeed(Bolas[CurrentBall].getXSpeed() - 1);
            }
            break;
            case S:
            {
                Bolas[CurrentBall].setYSpeed(Bolas[CurrentBall].getYSpeed() + 1);
            }
            break;
            case D:
            {
                Bolas[CurrentBall].setXSpeed(Bolas[CurrentBall].getXSpeed() + 1);
            }
            break;
            case SPACE:
            {
                Reset();
                Draw();
                Box.requestLayout();
            }
            break;
            case X:
            {
                ++CurrentBall;
                if(CurrentBall >= Bolas.length)
                {
                    CurrentBall = 0;
                }
            }
            break;
            case C:
            {
                Bolas[CurrentBall].setXSpeed(0);
                Bolas[CurrentBall].setYSpeed(0);
            }
            break;
            case G:
            {
                DoGravity = !DoGravity;
            }
            break;
            default:
            break;
        }
    }
}