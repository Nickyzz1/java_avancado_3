package com.desktopapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List; 
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class DrawController implements Initializable {

    @FXML
    protected VBox box; 
    @FXML
    protected Canvas canvas; 

    private List<Float> values = new ArrayList<>(); 
    private List<Color> colors = new ArrayList<>(); 

    public static Scene createScene(Integer id) throws Exception {
        URL sceneUrl = DrawController.class.getResource("graph.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        DrawController controller = loader.getController();
        // controller.setId(id);
        return scene;
    }

    public void add(Float value, Color color) {
        values.add(value); 
        colors.add(color); 
    }

    @FXML
    private void interact(MouseEvent e) { 
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        float total = (float) values.stream().reduce(0f, (a, x)-> + x); 

        double cx = w/2;
        double cy = h/2;
        double dx = e.getX() - cx;
        double dy = e.getY() - cy;
        double angle = Math.atan2(dy, -dx) / Math.PI + 180;

        double currentArc = 0;
    }

    @FXML
    private void pressed(MouseEvent e) { 
        
    }

    @FXML
    private void released(MouseEvent e) { 
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        add(60f, Color.BLUE);
        add(60f, Color.YELLOW);
        draw();
    }

    private void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 

        double w = canvas.getWidth();
        double h = canvas.getHeight();
        
        float total = (float) values.stream().reduce(0f, (a, x)-> + x); 
        double currentArc = 0;

        for (int i = 0; i < values.size(); i++) {
            float value = values.get(i);
            Color color = colors.get(i);

            double arc = 360 * (value / total); 

            g.setFill(color);
            g.fillArc(0, 0, w, h, currentArc, arc, ArcType.ROUND); 

            currentArc += arc; 
        }
    }

    // private void setId(Integer id) {
       
    // }
}


// package com.desktopapp;

// import java.awt.Color; // Consider using javafx.scene.paint.Color instead
// import java.net.URL;
// import java.util.ResourceBundle;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.canvas.Canvas;
// import javafx.scene.layout.VBox;
// import javafx.scene.input.MouseEvent;

// public class DrawController implements Initializable { 

//     @FXML
//     protected VBox box; 
//     @FXML
//     protected Canvas canvas; 

//     public static Scene createScene(Integer id) throws Exception { 
//         URL sceneUrl = DrawController.class.getResource("graph.fxml");
//         FXMLLoader loader = new FXMLLoader(sceneUrl);
//         Parent root = loader.load();
//         Scene scene = new Scene(root);
//         DrawController controller = loader.getController();
//         controller.setId(id); 
//         return scene;
//     }

//     public void add(Float value, Color color)
//     {
//         this.value.add(color);
//         this.value.add(color);
//     }

//     @FXML
//     private void interact(MouseEvent e) { 
        
//     }

//     @FXML
//     private void pressed(MouseEvent e) { 
//     }

//     @FXML
//     private void released(MouseEvent e) { 

//     }

//     @Override
//     public void initialize(URL arg0, ResourceBundle arg1) {
//         add(60f, Color.BLUE);
//         add(60f, Color.YELLOW);
//         draw();
//     }

//     private void draw() {
//         var g = canvas.getGraphicsContext2D();
//         g.fillRect(0, 0, 100, 100); 
//         g.setFill(Color.YELLOW);

//         double w = canvas.getWidth();
//         double h - canvas.getHeight();
//         double sum = values.stream().reduce(0f, (a, x)-> + x);

//         double currentArc = 0;

//             for(int i = 0; i < values.size(); i++)
//             {
//                 float value = values.get(i);
//                 Color color = colors.get(i);

//                 double arc = 360 * values / total;

//                 g.setFill(color);
//                 g.getFillArc(0, 0, w, h, 0f, arc, ArcType.ROUND);

//                 currentArc += 1;
//             }

//     }

//     private void setId(Integer id) {
      
//     }
// } [
