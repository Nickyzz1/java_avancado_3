package com.desktopapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main(String[] args)
    {
        launch();
    }
    @Override
    public void start(Stage arg0) throws Exception
    {
        Scene scene = MainController.CreateScene();
        arg0.setScene(scene);
        arg0.show();
    }
}