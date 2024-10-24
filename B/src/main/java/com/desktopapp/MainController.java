package com.desktopapp;

import java.net.URL;

import java.util.Date;
import java.math.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;


public class MainController
{
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
    protected Button BtnMain;
    @FXML
    protected void ClickBtnMain(MouseEvent Event) throws Exception
    {
        BtnMain.setText(String.format("%d", Integer.parseInt(BtnMain.getText()) + 1));

        double SceneWidth = BtnMain.getScene().getWidth();
        double SceneHeight = BtnMain.getScene().getHeight();

        BtnMain.relocate(RNG() % (SceneWidth - 100), RNG() % (SceneHeight - 100));
    }

    @FXML
    protected void HoverBtnMain(MouseEvent Event) throws Exception
    {
        double SceneWidth = BtnMain.getScene().getWidth();
        double SceneHeight = BtnMain.getScene().getHeight();

        double BtnX = BtnMain.getLayoutX();
        double BtnY = BtnMain.getLayoutY();

        double BtnW = BtnMain.getWidth();
        double BtnH = BtnMain.getHeight();

        double BtnCX = BtnX + BtnW / 2;
        double BtnCY = BtnY + BtnH / 2;
        
        double MouseX = Event.getSceneX();
        double MouseY = Event.getSceneY();

        double DestX;
        double DestY;
        
        /*
        if(MouseX)
        {
            if(MouseY > 50)
            {
                DestX = BtnX + MouseX - 105;
                DestY = BtnY + MouseY - 105;
            }else
            {
                DestX = BtnX + MouseX - 105;
                DestY = BtnY + MouseY + 5;
            }
        }else
        {
            if(MouseY > 50)
            {
                DestX = BtnX + MouseX + 5;
                DestY = BtnY + MouseY - 105;
            }else
            {
                DestX = BtnX + MouseX + 5;
                DestY = BtnY + MouseY + 5;
            }
        }
        */

        if(DestX < -99)
        {
            DestX = SceneWidth - 100;
        }else
        if(DestX >= SceneWidth)
        {
            DestX = 0;
        }

        if(DestY < -99)
        {
            DestY = SceneHeight - 100;
        }else
        if(DestY >= SceneHeight)
        {
            DestY = 0;
        }

        BtnMain.relocate(DestX, DestY);
    }
}