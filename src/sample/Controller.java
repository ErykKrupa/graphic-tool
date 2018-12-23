/** Controller.java
 * @aplication Multifunctional Graphical Tool 2018
 * @author Eryk Krupa
 * @version 1.0
 */

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static java.lang.Math.abs;

/**
 * Programs all behaviors and reactions to user actions.
 */
public class Controller
{
    /**
     * Determines current application mode.
     */
    private int mode=0;
    /**
     * Auxiliary variable which stores X coordinate or width of rectangle.
     */
    private double x1;
    /**
     * Auxiliary variable which stores Y coordinate or height of rectangle.
     */
    private double y1;
    /**
     * Is true when polygon is drawn currently.
     */
    private boolean flag = false;
    /**
     * Line for drawing rectangles.
     */
    private Line line = new Line();
    /**
     * Lines for drawing polygons.
     */
    private List<Line> lineList = new LinkedList<>();
    /**
     * Stores all circles.
     */
    private List<Circle> circleList = new LinkedList<>();
    /**
     * Stores all rectangles.
     */
    private List<Rectangle> rectangleList = new LinkedList<>();
    /**
     * Stores all polygons.
     */
    private List<Polygon> polygonList = new LinkedList<>();

    /**
     * Clean data field and removes the selections of figures.
     */
    private void clean()
    {
        figureLabelL.setText(""); figureLabelP.setText(""); fillLabelL.setText(""); fillLabelP.setText("");
        positionXLabelL.setText(""); positionXLabelP.setText(""); positionYLabelL.setText(""); positionYLabelP.setText("");
        sideXLabelL.setText(""); sideXLabelP.setText(""); sideYLabelL.setText(""); sideYLabelP.setText("");
        position.setText("");
        for(Circle x: circleList) x.setStrokeWidth(0);
        for(Rectangle x: rectangleList) x.setStrokeWidth(0);
        for(Polygon x: polygonList) x.setStrokeWidth(0);
    }
    /**
     * Stores some text for data field when circle is active.
     */
    private void circleData()
    {
        figureLabelL.setText("Figure:");
        figureLabelP.setText("Circle");
        fillLabelL.setText("Fill:");
        position.setText("Center position:");
        positionXLabelL.setText("X:");
        positionYLabelL.setText("Y:");
        sideXLabelL.setText("Radius:");
    }
    /**
     * Stores some text for data field when rectangle is active.
     */
    private void rectangleData()
    {
        figureLabelL.setText("Figure:");
        figureLabelP.setText("Rectangle");
        fillLabelL.setText("Fill:");
        position.setText("Position of top- left corner:");
        positionXLabelL.setText("X:");
        positionYLabelL.setText("Y:");
        sideXLabelL.setText("Width:");
        sideYLabelL.setText("Height:");
    }
    /**
     * Stores some text for data field when polygon is active.
     */
    private void polygonData()
    {
        figureLabelL.setText("Figure:");
        figureLabelP.setText("Polygon");
        fillLabelL.setText("Fill:");
        position.setText("First point position:");
        positionXLabelL.setText("X:");
        positionYLabelL.setText("Y:");
    }

    /**
     * Shows currently selected color.
     */
    @FXML private Circle colorCircle;
    /**
     * Element of data field.
     */
    @FXML private Label figureLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label figureLabelP;
    /**
     * Element of data field.
     */
    @FXML private Label fillLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label fillLabelP;
    /**
     * Element of data field.
     */
    @FXML private Label position;
    /**
     * Element of data field.
     */
    @FXML private Label positionXLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label positionXLabelP;
    /**
     * Element of data field.
     */
    @FXML private Label positionYLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label positionYLabelP;
    /**
     * Element of data field.
     */
    @FXML private Label sideXLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label sideXLabelP;
    /**
     * Element of data field.
     */
    @FXML private Label sideYLabelL;
    /**
     * Element of data field.
     */
    @FXML private Label sideYLabelP;

    /**
     * Saves project to .txt file.
     */
    @FXML void handleSaveClick ()
    {
        mode=0;
        clean();
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
            File file = fileChooser.showSaveDialog(Main.primaryStage);
            if (file != null)
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (Circle x : circleList)
                {
                    writer.write("circle ");
                    writer.write(x.getFill().toString().substring(2) + " ");
                    writer.write(Double.toString(x.getCenterX()) + " ");
                    writer.write(Double.toString(x.getCenterY()) + " ");
                    writer.write(Double.toString(x.getRadius()));
                    writer.append(System.lineSeparator());
                }
                for (Rectangle x : rectangleList)
                {
                    writer.write("rectangle ");
                    writer.write(x.getFill().toString().substring(2) + " ");
                    writer.write(Double.toString(x.getX()) + " ");
                    writer.write(Double.toString(x.getY()) + " ");
                    writer.write(Double.toString(x.getWidth()) + " ");
                    writer.write(Double.toString(x.getHeight()));
                    writer.append(System.lineSeparator());
                }
                for (Polygon x : polygonList)
                {
                    writer.write("polygon ");
                    writer.write(x.getFill().toString().substring(2) + " ");
                    for (int i = 0; i < x.getPoints().size(); i++)
                    {
                        if (i % 2 == 0) writer.write(Double.toString(x.getPoints().get(i) + x.getLayoutX()) + " ");
                        else writer.write(Double.toString(x.getPoints().get(i) + x.getLayoutY()) + " ");
                    }
                    writer.append(System.lineSeparator());
                }
                writer.close();
            }
        }
        catch(IOException ignored) {}
    }
    /**
     * Loads project from .txt file.
     */
    @FXML void handleLoadClick ()
    {
        mode=0;
        clean();
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
            File file = fileChooser.showOpenDialog(Main.primaryStage);
            if (file != null)
            {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                for (Line x : lineList)
                    Main.root.getChildren().remove(x);
                for (Circle x : circleList)
                    Main.root.getChildren().remove(x);
                for (Rectangle x : rectangleList)
                    Main.root.getChildren().remove(x);
                for (Polygon x : polygonList)
                    Main.root.getChildren().remove(x);
                lineList.clear();
                circleList.clear();
                rectangleList.clear();
                polygonList.clear();
                String temp;
                while ((temp = reader.readLine()) != null) {
                    String[] data = temp.split(" ");
                    switch (data[0]) {
                        case "circle":
                            circleList.add(new Circle(Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Color.web(data[1])));
                            Main.root.getChildren().add(circleList.get(circleList.size() - 1));
                            break;
                        case "rectangle":
                            rectangleList.add(new Rectangle(Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5])));
                            rectangleList.get(rectangleList.size() - 1).setFill(Color.web(data[1]));
                            Main.root.getChildren().add(rectangleList.get(rectangleList.size() - 1));
                            break;
                        case "polygon":
                            polygonList.add(new Polygon());
                            polygonList.get(polygonList.size() - 1).setFill(Color.web(data[1]));
                            for (int i = 2; i < data.length; i += 2)
                                polygonList.get(polygonList.size() - 1).getPoints().addAll(Double.parseDouble(data[i]), Double.parseDouble(data[i + 1]));
                            Main.root.getChildren().add(polygonList.get(polygonList.size() - 1));
                            break;
                    }
                }
                reader.close();
            }
        }
        catch(Exception ex)
        {
            for (Line x : lineList)
                Main.root.getChildren().remove(x);
            for (Circle x : circleList)
                Main.root.getChildren().remove(x);
            for (Rectangle x : rectangleList)
                Main.root.getChildren().remove(x);
            for (Polygon x : polygonList)
                Main.root.getChildren().remove(x);
            lineList.clear();
            circleList.clear();
            rectangleList.clear();
            polygonList.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Load Error");
            alert.setHeaderText(null);
            alert.setContentText("File is damaged!");
            alert.showAndWait();
        }

    }
    /**
     * Display info about application.
     */
    @FXML void handleInfoClick ()
    {
        mode=0;
        clean();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Multifunctional Graphical Tool 2018");
        alert.setContentText("MGT 2018 is application that enables you to draw rectangles, other polygons and circles.\nAuthor: Eryk Krupa.\nVersion: 1.0.\nAll rights reserved. ©");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Switches application mode to drawing circles.
     */
    @FXML void handleCircleClick () { mode = 1; clean(); }
    /**
     * Switches application mode to drawing rectangles.
     */
    @FXML void handleRectangleClick () { mode = 2; clean(); }
    /**
     * Switches application mode to drawing polygons.
     */
    @FXML void handlePolygonClick () { mode = 3; clean(); }
    /**
     * Switches application mode to moving figures.
     */
    @FXML void handleMovementClick ()
    {
        mode=4;
        clean();
        for(Circle x: circleList)
        {
            x.setOnMousePressed((event) ->
            {
                if (mode==4)
                {
                    clean();
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    circleData();
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getCenterX()));
                    positionYLabelP.setText(Integer.toString((int)x.getCenterY()));
                    sideXLabelP.setText(Integer.toString((int)x.getRadius()));
                }
            });
            x.setOnMouseDragged((event) ->
            {
                if(mode==4 && x.getRadius()<event.getX() && x.getRadius()<event.getY()
                        && event.getX()+x.getRadius()<780 && event.getY()+x.getRadius()<600)
                {
                    x.setCenterX(event.getX());
                    x.setCenterY(event.getY());
                    circleData();
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getCenterX()));
                    positionYLabelP.setText(Integer.toString((int)x.getCenterY()));
                    sideXLabelP.setText(Integer.toString((int)x.getRadius()));
                }
            });
        }
        for(Rectangle x: rectangleList)
        {
            x.setOnMousePressed((event) ->
            {
                if (mode==4)
                {
                    clean();
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    rectangleData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getX()));
                    positionYLabelP.setText(Integer.toString((int)x.getY()));
                    sideXLabelP.setText(Integer.toString((int)x.getWidth()));
                    sideYLabelP.setText(Integer.toString((int)x.getHeight()));
                }

            });
            x.setOnMouseDragged((event) ->
            {
                if(mode==4 && x.getWidth()/2<event.getX() && x.getHeight()/2<event.getY()
                        && event.getX()+x.getWidth()/2<780 && event.getY()+x.getHeight()/2<600)
                {
                    x.setX(event.getX()-x.getWidth()/2);
                    x.setY(event.getY()-x.getHeight()/2);
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    rectangleData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getX()));
                    positionYLabelP.setText(Integer.toString((int)x.getY()));
                    sideXLabelP.setText(Integer.toString((int)x.getWidth()));
                    sideYLabelP.setText(Integer.toString((int)x.getHeight()));
                }
            });
        }
        for(Polygon x: polygonList)
        {
            x.setOnMousePressed((event) ->
            {
                clean();
                if(mode==4)
                {
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    x1 = event.getX();
                    y1 = event.getY();
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    polygonData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int) (x.getLayoutX() + x.getPoints().get(0))));
                    positionYLabelP.setText(Integer.toString((int) (x.getLayoutY() + x.getPoints().get(1))));
                }
            });
            x.setOnMouseDragged((event) ->
            {
                clean();
                boolean flag1=true;
                if(mode==4)
                {
                    for(int i=0; i<x.getPoints().size(); i++)
                    {
                        if((i%2==0 && (x.getPoints().get(i) + x.getLayoutX() + event.getX() - x1<0 || 780<x.getPoints().get(i) + x.getLayoutX() + event.getX() - x1))
                                || (i%2==1 && (x.getPoints().get(i) + x.getLayoutY() + event.getY() - y1<0 || 600<x.getPoints().get(i) + x.getLayoutY() + event.getY() - y1)))
                        {
                            flag1 = false;
                            break;
                        }
                    }
                    if(flag1)
                    {
                        x.setLayoutX(x.getLayoutX() + event.getX() - x1);
                        x.setLayoutY(x.getLayoutY() + event.getY() - y1);
                        x.setStrokeWidth(5);
                        x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                        polygonData();
                        fillLabelP.setText(x.getFill().toString().substring(2));
                        positionXLabelP.setText(Integer.toString((int) (x.getLayoutX() + x.getPoints().get(0))));
                        positionYLabelP.setText(Integer.toString((int) (x.getLayoutY() + x.getPoints().get(1))));
                    }
                }
            });
        }

    }
    /**
     * Switches application mode to resizing figures.
     */
    @FXML void handleResizeClick ()
    {
        mode = 5;
        clean();
        for(Circle x: circleList)
        {
            x.setOnMousePressed((event) ->
            {
                if(mode==5)
                {
                    clean();
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    circleData();
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getCenterX()));
                    positionYLabelP.setText(Integer.toString((int)x.getCenterY()));
                    sideXLabelP.setText(Integer.toString((int)x.getRadius()));
                }
            });
            x.setOnScroll((event) ->
            {
                clean();
                if(0<event.getDeltaY() && mode==5 &&
                        0<x.getCenterX()-x.getRadius() && 0<x.getCenterY()-x.getRadius() &&
                        x.getRadius()+x.getCenterX()<780 && x.getRadius()+x.getCenterY()<600)
                    x.setRadius(x.getRadius()+5);
                else if(event.getDeltaY()<0 && mode==5 && 15<x.getRadius())
                    x.setRadius(x.getRadius()-5);
                circleData();
                x.setStrokeWidth(5);
                x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                fillLabelP.setText(x.getFill().toString().substring(2));
                positionXLabelP.setText(Integer.toString((int)x.getCenterX()));
                positionYLabelP.setText(Integer.toString((int)x.getCenterY()));
                sideXLabelP.setText(Integer.toString((int)x.getRadius()));
            });
        }
        for(Rectangle x: rectangleList)
        {
            boolean flag1;
            double att;
            if(x.getWidth()>x.getHeight())
            {
                flag1 = true;
                att = x.getWidth() / x.getHeight();
            }
            else
            {
                flag1 = false;
                att = x.getHeight() / x.getWidth();
            }
            x.setOnMousePressed((event) ->
            {
                if(mode==5)
                {
                    clean();
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    rectangleData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)x.getX()));
                    positionYLabelP.setText(Integer.toString((int)x.getY()));
                    sideXLabelP.setText(Integer.toString((int)x.getWidth()));
                    sideYLabelP.setText(Integer.toString((int)x.getHeight()));
                }
            });
            x.setOnScroll((event) ->
            {
                clean();
                if(0<event.getDeltaY() && mode==5 && 0<x.getX() && 0<x.getY() &&
                        x.getX()+x.getWidth()<780 && x.getY()+x.getHeight()<600)
                {
                    if(flag1)
                    {
                        x.setWidth(x.getWidth() + 5*att);
                        x.setHeight(x.getHeight() + 5);
                        x.setX(x.getX() - 2.5*att);
                        x.setY(x.getY() - 2.5);
                    }
                    else
                    {
                        x.setWidth(x.getWidth() + 5);
                        x.setHeight(x.getHeight() + 5*att);
                        x.setX(x.getX() - 2.5);
                        x.setY(x.getY() - 2.5*att);
                    }
                }
                else if(event.getDeltaY()<0 && mode==5 && 15<x.getWidth() && 15<x.getHeight())
                {
                    if(flag1)
                    {
                        x.setWidth(x.getWidth() - 5*att);
                        x.setHeight(x.getHeight() - 5);
                        x.setX(x.getX() + 2.5*att);
                        x.setY(x.getY() + 2.5);
                    }
                    else
                    {
                        x.setWidth(x.getWidth() - 5);
                        x.setHeight(x.getHeight() - 5*att);
                        x.setX(x.getX() + 2.5);
                        x.setY(x.getY() + 2.5*att);
                    }
                }
                x.setStrokeWidth(5);
                x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                rectangleData();
                fillLabelP.setText(x.getFill().toString().substring(2));
                positionXLabelP.setText(Integer.toString((int)x.getX()));
                positionYLabelP.setText(Integer.toString((int)x.getY()));
                sideXLabelP.setText(Integer.toString((int)x.getWidth()));
                sideYLabelP.setText(Integer.toString((int)x.getHeight()));
            });
        }
        for(Polygon x: polygonList)
        {
            x.setOnMousePressed((event) ->
            {
                clean();
                if(mode==5)
                {
                    Main.root.getChildren().remove(x);
                    Main.root.getChildren().add(x);
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    polygonData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int) (x.getLayoutX() + x.getPoints().get(0))));
                    positionYLabelP.setText(Integer.toString((int) (x.getLayoutY() + x.getPoints().get(1))));
                }
            });
            x.setOnScroll((event) ->
            {
                boolean flag1=true;
                if (mode == 5)
                {
                    double arr[]= new double[x.getPoints().size()];
                    for(int i = 0; i < x.getPoints().size(); i++)
                    {
                        if (i%2==0) arr[i] = event.getX() - x.getPoints().get(i);
                        else arr[i] = event.getY() - x.getPoints().get(i);
                    }

                    if(0<event.getDeltaY())
                    {
                        for (int i = 0; i < x.getPoints().size(); i++)
                        {
                            if (i%2==0 && (x.getPoints().get(i) - arr[i] / 30 + x.getLayoutX() < 0 || 780 < x.getPoints().get(i) - arr[i] / 30 + x.getLayoutX())
                                    || (i%2==1 && (x.getPoints().get(i) - arr[i] / 30 + x.getLayoutY() < 0 || 600 < x.getPoints().get(i) - arr[i] / 30 + x.getLayoutY())))
                            {
                                flag1 = false;
                                break;
                            }
                        }
                        if (flag1)
                        {
                            for (int i = 0; i < x.getPoints().size(); i++)
                                x.getPoints().set(i, x.getPoints().get(i) - arr[i] / 30);
                        }
                    }
                    else if(event.getDeltaY()<0)
                    {
                        flag1=false;
                        for (int i = 0; i < x.getPoints().size(); i++)
                            if (arr[i]>25)
                            {
                                flag1 = true;
                                break;
                            }
                        if (flag1)
                        {
                            for (int i = 0; i < x.getPoints().size(); i++)
                                x.getPoints().set(i, x.getPoints().get(i) + arr[i] / 30);
                        }
                    }
                    x.setStrokeWidth(5);
                    x.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    polygonData();
                    fillLabelP.setText(x.getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int) (x.getLayoutX() + x.getPoints().get(0))));
                    positionYLabelP.setText(Integer.toString((int) (x.getLayoutY() + x.getPoints().get(1))));
                }
            });
        }
    }
    /**
     * Switches application mode to filing figures. Opens color picker dialog.
     */
    @FXML void handleFillClick ()
    {
        mode=6;
        clean();
        String color;
        Optional<ButtonType> result = Main.alert.showAndWait();
        if(result.get() != ButtonType.CANCEL)
        {
            color = Main.colorPicker.getValue().toString().substring(2);
            colorCircle.setFill(Color.web(color));


            for (Circle x : circleList)
                x.setOnMouseClicked((event) ->
                {
                    if (mode == 6) {
                        circleData();
                        x.setFill(Color.web(color));
                        fillLabelP.setText(x.getFill().toString().substring(2));
                        positionXLabelP.setText(Integer.toString((int) x.getCenterX()));
                        positionYLabelP.setText(Integer.toString((int) x.getCenterY()));
                        sideXLabelP.setText(Integer.toString((int) x.getRadius()));
                    }
                });
            for (Rectangle x : rectangleList)
                x.setOnMouseClicked((event) ->
                {
                    if (mode == 6) {
                        rectangleData();
                        x.setFill(Color.web(color));
                        fillLabelP.setText(x.getFill().toString().substring(2));
                        positionXLabelP.setText(Integer.toString((int) x.getX()));
                        positionYLabelP.setText(Integer.toString((int) x.getY()));
                        sideXLabelP.setText(Integer.toString((int) x.getWidth()));
                        sideYLabelP.setText(Integer.toString((int) x.getHeight()));
                    }
                });
            for (Polygon x : polygonList)
                x.setOnMouseClicked((event) ->
                {
                    if (mode == 6) {
                        polygonData();
                        x.setFill(Color.web(color));
                        fillLabelP.setText(x.getFill().toString().substring(2));
                        positionXLabelP.setText(Integer.toString((int) (x.getLayoutX() + x.getPoints().get(0))));
                        positionYLabelP.setText(Integer.toString((int) (x.getLayoutY() + x.getPoints().get(1))));
                    }
                });
        }
    }
    /**
     * Switches application mode to removing figures.
     */
    @FXML void handleRemoveClick ()
    {
        mode=7;
        clean();
        for (Circle x: circleList)
            x.setOnMouseClicked((event) ->
            {
                if(mode==7)
                {
                    Main.root.getChildren().remove(x);
                    circleList.remove(x);
                }
            });
        for (Rectangle x: rectangleList)
            x.setOnMouseClicked((event)->
            {
                if(mode==7)
                {
                    Main.root.getChildren().remove(x);
                    rectangleList.remove(x);
                }
            });
        for(Polygon x: polygonList)
            x.setOnMouseClicked((event)->
            {
                if(mode==7)
                {
                    Main.root.getChildren().remove(x);
                    polygonList.remove(x);
                }
            });
    }
    /**
     * Removes all figures after user confirmation.
     */
    @FXML void handleRemoveAllClick ()
    {
        mode=8;
        clean();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove All");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure that you want to remove all figures?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            for (Line x: lineList)
                Main.root.getChildren().remove(x);
            for (Circle x: circleList)
                Main.root.getChildren().remove(x);
            for (Rectangle x: rectangleList)
                Main.root.getChildren().remove(x);
            for (Polygon x: polygonList)
                Main.root.getChildren().remove(x);
            lineList.clear();
            circleList.clear();
            rectangleList.clear();
            polygonList.clear();
        }
    }

    /**
     * Creates circle or lines for create polygon depending on current application mode.
     * @param e Stores information about cursor.
     */
    @FXML void handleClick(MouseEvent e)
    {
        switch (mode)
        {
            case 1:
                if(50<e.getX() && e.getX()<730 && 50<e.getY() && e.getY()<550)
                {
                    circleList.add( new Circle(e.getX(), e.getY(), 50));
                    circleList.get(circleList.size()-1).setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                    circleList.get(circleList.size()-1).setStrokeWidth(5);
                    circleList.get(circleList.size()-1).setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    Main.root.getChildren().add(circleList.get(circleList.size()-1));
                    circleData();
                    fillLabelP.setText(circleList.get(circleList.size()-1).getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int)circleList.get(circleList.size()-1).getCenterX()));
                    positionYLabelP.setText(Integer.toString((int)circleList.get(circleList.size()-1).getCenterY()));
                    sideXLabelP.setText(Integer.toString((int)circleList.get(circleList.size()-1).getRadius()));
                }
                break;
            case 3:
                flag=true;
                if(lineList.size()==0 || abs(e.getX()-lineList.get(0).getStartX())>15 || abs(e.getY()-lineList.get(0).getStartY())>15)
                {
                    lineList.add(new Line(e.getX(), e.getY(), e.getX(), e.getY()));
                    lineList.get(lineList.size() - 1).setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    Main.root.getChildren().add(lineList.get(lineList.size() - 1));
                }
                else if(lineList.size()>2 && abs(e.getX()-lineList.get(0).getStartX())<15 && abs(e.getY()-lineList.get(0).getStartY())<15)
                {
                    flag=false;
                    polygonList.add(new Polygon());
                    for (Line x : lineList)
                    {
                        polygonList.get(polygonList.size() - 1).getPoints().addAll(x.getStartX(), x.getStartY());
                        Main.root.getChildren().remove(x);
                    }
                    lineList.clear();
                    polygonList.get(polygonList.size()-1).setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                    Main.root.getChildren().add(polygonList.get(polygonList.size()-1));
                    polygonList.get(polygonList.size()-1).setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                    polygonList.get(polygonList.size()-1).setStrokeWidth(5);
                    polygonData();
                    fillLabelP.setText(polygonList.get(polygonList.size()-1).getFill().toString().substring(2));
                    positionXLabelP.setText(Integer.toString((int) (polygonList.get(polygonList.size()-1).getLayoutX()+polygonList.get(polygonList.size()-1).getPoints().get(0))));
                    positionYLabelP.setText(Integer.toString((int) (polygonList.get(polygonList.size()-1).getLayoutY()+polygonList.get(polygonList.size()-1).getPoints().get(1))));
                }
                break;
        }
    }

    /**
     * Creates line for create rectangle if proper application mode is active.
     * @param e Stores information about cursor.
     */
    @FXML void handleMousePressed(MouseEvent e)
    {
        clean();
        switch (mode)
        {
            case 2:
                x1=e.getX();
                y1=e.getY();
                line.setStartX(x1);
                line.setStartY(y1);
                line.setEndX(x1);
                line.setEndY(y1);
                line.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                Main.root.getChildren().add(line);
                break;
        }
    }

    /**
     * Drags end of line for create rectangle or polygon depending on current application mode.
     * @param e Stores information about cursor.
     */
    @FXML void handleMouseDragged(MouseEvent e)
    {
        switch (mode)
        {
            case 2:
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                break;
            case 3:
                if(flag)
                {
                    if(e.getX()<lineList.get(lineList.size() - 1).getStartX())
                        lineList.get(lineList.size() - 1).setEndX(e.getX()+1);
                    else
                        lineList.get(lineList.size() - 1).setEndX(e.getX()-1);
                    if(e.getY()<lineList.get(lineList.size() - 1).getStartY())
                        lineList.get(lineList.size() - 1).setEndY(e.getY()+1);
                    else
                        lineList.get(lineList.size() - 1).setEndY(e.getY()-1);
                }
                break;

        }
    }

    /**
     * Creates rectangles if proper application mode is active.
     * @param e Stores information about cursor.
     */
    @FXML void handleMouseReleased(MouseEvent e)
    {
        double x2, y2;
        switch (mode)
        {
            case 2:
                Main.root.getChildren().remove(line);
                x2=e.getX();
                y2=e.getY();
                if(x2<0 || 780<x2 || y2<0 || 600<y2) break;
                if(x1>x2)
                {
                    double a=x1;
                    x1=x2;
                    x2=a;
                }
                if(y1>y2)
                {
                    double a=y1;
                    y1=y2;
                    y2=a;
                }
                if(x2-x1<15) break;
                if(y2-y1<15) break;
                rectangleList.add(new Rectangle((int)x1, (int)y1, (int) (x2-x1), (int)(y2-y1)));
                rectangleList.get(rectangleList.size()-1).setStrokeWidth(5);
                rectangleList.get(rectangleList.size()-1).setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
                rectangleList.get(rectangleList.size()-1).setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                Main.root.getChildren().add(rectangleList.get(rectangleList.size()-1));
                rectangleData();
                fillLabelP.setText(rectangleList.get(rectangleList.size()-1).getFill().toString().substring(2));
                positionXLabelP.setText(Integer.toString((int)rectangleList.get(rectangleList.size()-1).getX()));
                positionYLabelP.setText(Integer.toString((int)rectangleList.get(rectangleList.size()-1).getY()));
                sideXLabelP.setText(Integer.toString((int)rectangleList.get(rectangleList.size()-1).getWidth()));
                sideYLabelP.setText(Integer.toString((int)rectangleList.get(rectangleList.size()-1).getHeight()));
                break;
        }
    }

    /**
     * Pulls end of line for create polygon when proper application mode is active and polygon is currently drawn (flag is true).
     * @param e Stores information about cursor.
     */
    @FXML void handleMouseMoved(MouseEvent e)
    {
        switch(mode)
        {
            case 3:
                if(flag)
                {
                    if(e.getX()<lineList.get(lineList.size() - 1).getStartX())
                        lineList.get(lineList.size() - 1).setEndX(e.getX()+1);
                    else
                        lineList.get(lineList.size() - 1).setEndX(e.getX()-1);
                    if(e.getY()<lineList.get(lineList.size() - 1).getStartY())
                        lineList.get(lineList.size() - 1).setEndY(e.getY()+1);
                    else
                        lineList.get(lineList.size() - 1).setEndY(e.getY()-1);
                }
                break;
        }

    }
}
