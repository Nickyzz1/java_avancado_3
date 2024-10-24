package com.desktopapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.math.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;


public class DrawController implements Initializable
{
    public static Scene CreateScene() throws Exception
    {
        URL SceneUrl = DrawController.class.getResource("DrawWindow.fxml");
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
    protected Canvas MainCanvas;

    Timer Timer = new Timer();

    @FXML
    protected VBox Box;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        Timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                Rotation += Math.PI / 50;
                Draw();
                Box.requestLayout();
            }
        }, 50, 50);
        Draw();
    }
    private int Rotation = 0;
    @FXML
    protected void Interact(MouseEvent Event)
    {
        /*
        double Width = MainCanvas.getWidth(), Height = MainCanvas.getHeight(), Sum = 0;

        if(Height > Width)
        {
            Height = Width;
        }else
        {
            Width = Height;
        }
        double CX = Width / 2, CY = Height / 2;

        for(int i = 0; i < Values.size(); ++i)
        {
            Sum += Values.get(i);
        }

        double X = Event.getX() - CX;
        double Y = Event.getY() - CY;

        double TargetAngle = 180 * Math.atan2(Y, -X) / Math.PI + 180, Angle = 0;

        for(int i = 0; i < Values.size(); ++i)
        {
            double Arc = 360 * Values.get(i) / Sum;
            if(TargetAngle > Angle && TargetAngle < Angle + Arc)
            {
                Selected = i;
            }
            Angle += Arc;
        }
        Draw();
        Box.requestLayout();
        */
    }
    @FXML
    protected void MouseRelease(MouseEvent Event)
    {

    }
    @FXML
    protected void MousePress(MouseEvent Event)
    {

    }

    public void Draw()
    {
        GraphicsContext gc = MainCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, MainCanvas.getWidth(), MainCanvas.getHeight());

        /*
        double Width = MainCanvas.getWidth(), Height = MainCanvas.getHeight(), Sum = 0;

        if(Height > Width)
        {
            Height = Width;
        }else
        {
            Width = Height;
        }

        for(int i = 0; i < Values.size(); ++i)
        {
            Sum += Values.get(i);
        }
        
        double Angle = 0.0;
        for(int i = 0; i < Values.size(); ++i)
        {
            double Arc = 360 * Values.get(i) / Sum;
            if(Selected == i)
            {
                gc.setFill(Colors.get(i).desaturate());
            }else
            {
                gc.setFill(Colors.get(i));
            }
            gc.fillArc(0, 0, Width, Height, Angle, Arc, ArcType.ROUND);
            Angle += Arc;
        }
        */
        gc.setFill(Color.rgb(255, 255, 0));

        double[][] Points = new double[2][10];
        double Theta = 0;

        for(int i = 0; i < 10; ++i)
        {
            double Rho = (i & 1) == 0 ? 200 : 80;

            double X = Rho * Math.cos(Theta);
            double Y = Rho * Math.sin(Theta);

            Points[0][i] = (X * Math.cos(Rotation) + Y * Math.sin(Rotation)) + MainCanvas.getWidth() / 2;
            Points[1][i] = (X * Math.sin(Rotation) - Y * Math.cos(Rotation)) + MainCanvas.getHeight() / 2;

            Theta += 2 * Math.PI / 10;
        }

        gc.fillPolygon(Points[0], Points[1], 10);
    }

    protected ArrayList<Float> Values = new ArrayList<>();
    protected ArrayList<Color> Colors = new ArrayList<>();
    public void Add(Float Value, Color Color)
    {
        Values.add(Value);
        Colors.add(Color);
    }
}