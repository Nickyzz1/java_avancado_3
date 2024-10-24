package com.desktopapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BinaryOperator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer.MarginType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class DrawController implements Initializable {
    
    public static Scene CreateScene() throws Exception
    {
        URL sceneUrl = DrawController.class
            .getResource("DrawScene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    ArrayList<VisualObject> objs = new ArrayList<>();

    @FXML
    private VBox box;

    @FXML
    private Canvas canva;

    @FXML
    private void interact(MouseEvent e) {
    }

    @FXML
    private void pressed(MouseEvent e) {

    }

    @FXML
    private void released(MouseEvent e) {

    }

    Timer timer = new Timer();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        Mass mass1 = new Mass();
        mass1.setX(50);
        mass1.setY(50);
        mass1.setVx(400);
        mass1.setWeight(20);
        mass1.setFloor((float)canva.getHeight());
        objs.add(mass1);

        Mass mass2 = new Mass();
        mass2.setX(250);
        mass2.setY(50);
        mass2.setWeight(20);
        mass2.setFloor((float)canva.getHeight());
        objs.add(mass2);

        Mass mass3 = new Mass();
        mass3.setX(50);
        mass3.setY(250);
        mass3.setWeight(20);
        mass3.setFloor((float)canva.getHeight());
        objs.add(mass3);

        Mass mass4 = new Mass();
        mass4.setX(250);
        mass4.setY(250);
        mass4.setWeight(20);
        mass4.setFloor((float)canva.getHeight());
        objs.add(mass4);
        
        float k = 1000 * 1000;
        float sqrt2 = (float)Math.sqrt(2);

        objs.add(new Spring(mass1, mass2, 200, k));
        objs.add(new Spring(mass1, mass3, 200, k));
        objs.add(new Spring(mass2, mass4, 200, k));
        objs.add(new Spring(mass3, mass4, 200, k));
        objs.add(new Spring(mass1, mass4, sqrt2 * 200, k));
        objs.add(new Spring(mass2, mass3, sqrt2 * 200, k));

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                var g = canva.getGraphicsContext2D();

                g.clearRect(
                    0, 0,
                    canva.getWidth(),
                    canva.getHeight()
                );
                
                int N = 10000;
                for (int i = 0; i < N; i++)
                {
                    for (var obj : objs) {
                        obj.interact(0.05f / N);
                    }
                }

                for (var obj : objs) {
                    obj.draw(g);
                }

                box.requestLayout();
            }
        }, 50, 50);
    }
}