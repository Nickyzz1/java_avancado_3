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
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainController implements Initializable
{
    static final double PixelToMeter = 50, EnergyLoss = 0.9;

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

    protected Ball[] Bolas = new Ball[2];

    protected void Fisica(Ball Bola)
    {
        Bola.setYSpeed(Bola.getYSpeed() + 0.5);
        Bola.ApplySpeed();
        if((Bola.getY() + Bola.getRadius()) * PixelToMeter > Tela.getHeight())
        {
            Bola.setY(Bola.getY() + (Tela.getHeight() / PixelToMeter - (Bola.getY() + Bola.getRadius())));
            Bola.setYSpeed(-Bola.getYSpeed() * EnergyLoss);
            Bola.setXSpeed(Bola.getXSpeed() * (EnergyLoss * 0.98));
        }
        if((Bola.getX() + Bola.getRadius()) * PixelToMeter > Tela.getWidth())
        {
            Bola.setX(Bola.getX() + (Tela.getWidth() / PixelToMeter - (Bola.getX() + Bola.getRadius())));
            Bola.setXSpeed(-Bola.getXSpeed() * EnergyLoss);
            Bola.setYSpeed(Bola.getYSpeed() * (EnergyLoss * 0.98));
        }
        if((Bola.getY() - Bola.getRadius()) * PixelToMeter < 0)
        {
            Bola.setY(Bola.getY() - (Bola.getY() - Bola.getRadius()));
            Bola.setYSpeed(-Bola.getYSpeed() * EnergyLoss);
            Bola.setXSpeed(Bola.getXSpeed() * (EnergyLoss * 0.98));
        }
        if((Bola.getX() - Bola.getRadius()) * PixelToMeter < 0)
        {
            Bola.setX(Bola.getX() - (Bola.getX() - Bola.getRadius()));
            Bola.setXSpeed(-Bola.getXSpeed() * EnergyLoss);
            Bola.setYSpeed(Bola.getYSpeed() * (EnergyLoss * 0.98));
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        Tela.setFocusTraversable(true);
        for(int i = 0; i < Bolas.length; ++i)
        {
            Bolas[i] = new Ball
            (
                (Tela.getWidth() - RNG() % Tela.getWidth()) / PixelToMeter,
                ((Tela.getHeight() / 2) - RNG() % (Tela.getHeight() / 2)) / PixelToMeter,
                RNG() % 10 + 1,
                0.2 * (RNG() % 10 + 1)
            );
            Bolas[i].setXSpeed(2.0 - 0.4 * (RNG() % 10 + 1));
        }
        Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                for(int i = 0; i < Bolas.length; ++i)
                {
                    Fisica(Bolas[i]);
                }
                Draw();
                Box.requestLayout();
            }
        }, 50, 50);
        Draw();
    }

    public void Draw()
    {
        GraphicsContext gc = Tela.getGraphicsContext2D();
        gc.clearRect(0, 0, Tela.getWidth(), Tela.getHeight());
        gc.setFill(Color.rgb(255, 128, 0));
        gc.fillRect(Tela.getLayoutX(), Tela.getLayoutY(), Tela.getWidth(), Tela.getHeight());
        gc.setFill(Color.rgb(0, 0, 0));
        for(int i = 0; i < Bolas.length; ++i)
        {
            if(i == CurrentBall)
            {
                gc.setFill(Color.rgb(0, 0, 255));
            }else
            {
                gc.setFill(Color.rgb(0, 0, 0));
            }
            gc.fillOval(
                (Bolas[i].getX() - Bolas[i].getRadius()) * PixelToMeter, 
                (Bolas[i].getY() - Bolas[i].getRadius()) * PixelToMeter,
                Bolas[i].getRadius() * 2 * PixelToMeter,
                Bolas[i].getRadius() * 2 * PixelToMeter
            );
            gc.setFill(Color.rgb(255, 255, 255));
            gc.fillText(String.format("%.2f", Bolas[i].Mass), Bolas[i].getX() * PixelToMeter, Bolas[i].getY() * PixelToMeter);
        }
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
                for(int i = 0; i < Bolas.length; ++i)
                {
                    Bolas[i] = new Ball
                    (
                        (Tela.getWidth() - RNG() % Tela.getWidth()) / PixelToMeter,
                        ((Tela.getHeight() / 2) - RNG() % (Tela.getHeight() / 2)) / PixelToMeter,
                        RNG() % 10 + 1,
                        0.2 * (RNG() % 10 + 1)
                    );
                    Bolas[i].setXSpeed(2.0 - 0.4 * (RNG() % 10 + 1));
                }
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
            default:
            break;
        }
    }
}