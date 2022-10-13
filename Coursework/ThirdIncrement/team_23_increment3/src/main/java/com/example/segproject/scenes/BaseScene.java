package com.example.segproject.scenes;

import static com.example.segproject.App.shutdown;

import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javax.imageio.ImageIO;
import javax.swing.DesktopManager;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.segproject.Calculations;
import com.example.segproject.SceneController;
import com.example.segproject.components.CalculationInput;
import com.example.segproject.components.CalculationOutput;
import com.example.segproject.components.DistanceIndicator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Base class from which all scene classes will inherit
 * Provides basic functionality required by the SceneController
 * Provides a setupDefaultScene method to be used by all non-menu scenes
 * Provides functionality for setting the style sheet of a scene
 */
public abstract class BaseScene {

    protected SceneController controller;
    protected Scene scene;

    protected BorderPane root;
    protected Pane runwayPane;
    protected HBox io;
    protected CalculationInput inputs;
    protected CalculationOutput outputs;
    protected MenuBar toolbar;

    protected Calculations cal;
    protected Double runwayPaneCenterX;
    protected Double runwayPaneCenterY;
    protected Integer runwayLength;

    protected ImageView runway;
    protected Rectangle obstacle;

    protected DistanceIndicator toraIndicator;
    protected DistanceIndicator asdaIndicator;
    protected DistanceIndicator todaIndicator;
    protected DistanceIndicator ldaIndicator;
    protected DistanceIndicator distanceFromThresholdIndicator;
    protected DistanceIndicator displacementThresholdIndicator;
    protected DistanceIndicator resaIndicator;
    protected DistanceIndicator stripEndIndicator;
    protected DistanceIndicator blastProtectionIndicator;
    protected DistanceIndicator slopeCalculationIndicator;

    protected Boolean rotationEnabled = false;

    public BaseScene(SceneController controller) {
        this.controller = controller;
    }

    /**
     * Instantiates the foundational ui elements needed for all non-menu scenes
     */
    protected void setupDefaultScene() {
        root = new BorderPane();
        runwayPane = new Pane();
        io = new HBox();

        toolbar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(h -> shutdown());
        fileMenu.getItems().addAll(exitMenuItem);

        Menu viewMenu = new Menu("View");
        MenuItem topMenuItem = new MenuItem("Top View");
        MenuItem sideMenuItem = new MenuItem("Side View");
        // MenuItem bothMenuItem = new MenuItem("Top & Side View");
        topMenuItem.setOnAction(h -> controller.openTopScene());
        sideMenuItem.setOnAction(h -> controller.openSideScene());
        // bothMenuItem.setOnAction(h -> controller.openDoubleScene());
        viewMenu.getItems().addAll(topMenuItem, sideMenuItem);
        // viewMenu.getItems().add(bothMenuItem);

        Menu settingsMenu = new Menu("Settings");
        MenuItem rotationMenuItem = new MenuItem("Enable View Rotation");
        StringProperty rotationMenuItemText = rotationMenuItem.textProperty();
        rotationMenuItem.setOnAction(h -> {
            if (rotationEnabled) {
                rotationEnabled = false;
                rotationMenuItemText.set("Enable View Rotation");
            } else {
                rotationEnabled = true;
                rotationMenuItemText.set("Disable View Rotation");
            }
        });
        settingsMenu.getItems().addAll(rotationMenuItem);

        Menu importMenu = new Menu("Import");
        MenuItem importXML = new MenuItem("Import XML");
        importMenu.getItems().addAll(importXML);
        importXML.setOnAction(h -> importAsXML());
        Menu exportMenu = new Menu("Export");
        MenuItem exportAsXML = new MenuItem("Export as XML");
        exportAsXML.setOnAction(h -> exportAsXML());
        MenuItem exportAsJPEG = new MenuItem("Export as JPEG");
        exportAsJPEG.setOnAction(h -> takeScreenshot(root, "jpg"));
        MenuItem exportAsPNG = new MenuItem("Export as PNG");
        exportAsPNG.setOnAction(h -> takeScreenshot(root, "png"));
        MenuItem exportAsGIF = new MenuItem("Export as GIF");
        exportAsGIF.setOnAction(h -> takeScreenshot(root, "gif"));
        exportMenu.getItems().addAll(exportAsXML, exportAsJPEG, exportAsPNG, exportAsGIF);

        toolbar.getMenus().addAll(fileMenu, viewMenu, settingsMenu, importMenu, exportMenu);
        root.setTop(toolbar);

        root.setCenter(runwayPane);
        root.setRight(io);

        root.setMaxWidth(controller.getWidth());
        root.setMaxHeight(controller.getHeight());

        runwayPane.setMinWidth(controller.getWidth() * 0.66);
        io.setMinWidth(controller.getWidth() * 0.33);

        inputs = new CalculationInput(controller);
        outputs = new CalculationOutput();

        io.getChildren().add(inputs);
        io.getChildren().add(outputs);

        inputs.setOnButtonClicked(this::newValues);

        runwayPaneCenterX = controller.getWidth() * 0.66 * 0.5;
        runwayPaneCenterY = controller.getHeight() * 0.5;

    }

    /**
     * Will be used by children to declare all ui elements in the scene
     */
    public abstract void build();

    /**
     * Used by the SceneController to declare an instance of this class as
     * the current scene to be displayed
     * 
     * @return scene
     */
    public Scene setScene() {
        Scene currentScene = controller.getScene();
        Scene scene = new Scene(root, currentScene.getWidth(), currentScene.getHeight());
        this.scene = scene;
        return scene;
    }

    /**
     * Called when calculate button is clicked
     * 
     * @param cal   new calculated values
     * @param event the button click event, shouldn't be needed for anything
     */
    private void newValues(Calculations cal, ActionEvent event) {
        this.cal = cal;
        outputs.updateValues(cal);
    }

    private void newValues(Calculations cal) {
        this.cal = cal;
        outputs.updateValues(cal);
    }

    /**
     * Maybe won't be used at all, idk
     * 
     * @return scene
     */
    public Scene getScene() {
        return scene;
    }

    public Rectangle createObstable(Double x, Double y) {
        Rectangle obstacle = new Rectangle(x, y, 50, 50);
        obstacle.setFill(Color.ORANGE);
        return obstacle;
    }

    /**
     * Set the stylesheet of the scene.
     * <p>
     * File name should be the full name with file type e.g. "menu.css".
     * Will commonly be used in the build method.
     * 
     * @param fileName Name of the stylesheet file in the resources folder
     */
    protected void setStylesheet(String fileName) {
        root.getStylesheets().add(this.getClass().getResource("/".concat(fileName)).toExternalForm());
    }

    protected void rotate(int bearing) {
        int rotateVal = 0;

        if (bearing == 36 || bearing == 18) {
            rotateVal = 90;
        } else if (bearing == 27 || bearing == 9) {
            rotateVal = 0;
        } else if (bearing == 1 || bearing == 17 || bearing == 35 || bearing == 19) {
            rotateVal = 80;
        } else if (bearing == 2 || bearing == 16 || bearing == 34 || bearing == 20) {
            rotateVal = 70;
        } else if (bearing == 3 || bearing == 15 || bearing == 33 || bearing == 21) {
            rotateVal = 60;
        } else if (bearing == 4 || bearing == 14 || bearing == 32 || bearing == 22) {
            rotateVal = 50;
        } else if (bearing == 5 || bearing == 13 || bearing == 31 || bearing == 23) {
            rotateVal = 40;
        } else if (bearing == 6 || bearing == 12 || bearing == 30 || bearing == 24) {
            rotateVal = 30;
        } else if (bearing == 7 || bearing == 11 || bearing == 29 || bearing == 25) {
            rotateVal = 20;
        } else if (bearing == 8 || bearing == 10 || bearing == 28 || bearing == 26) {
            rotateVal = 10;
        }

        if ((19 <= bearing && bearing <= 27) || (1 <= bearing && bearing <= 8)) {
            rotateVal = rotateVal * (-1);
        }

        runwayPane.setRotate(rotateVal);
    }

    protected void setIndicatorsLabel(DistanceIndicator[] indicators) {
        for (DistanceIndicator indicator : indicators) {
            indicator.setLabelX();
        }
    }

    protected void disableIndicators(DistanceIndicator[] indicators) {
        for (DistanceIndicator indicator : indicators) {
            indicator.disable();
        }
    }

    protected void setIndicatorsToDarkMode(DistanceIndicator[] indicators) {
        for (DistanceIndicator indicator : indicators) {
            indicator.setColor(Color.WHITE);
        }
    }

    protected void setIndicatorsToLightMode(DistanceIndicator[] indicators) {
        for (DistanceIndicator indicator : indicators) {
            indicator.setColor(Color.BLACK);
        }
    }

    protected void takeScreenshot(Node node, String format) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Bounds bounds = node.getLayoutBounds();
        int imageWidth = (int) Math.round(bounds.getWidth());
        int imageHeight = (int) Math.round(bounds.getHeight());

        WritableImage screenshot = new WritableImage(imageWidth, imageHeight);
        screenshot = node.snapshot(params, screenshot);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Screenshot");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(format.toUpperCase(), "*." + format),
                new FileChooser.ExtensionFilter("All Images", "*.*"));

        File file = fileChooser.showSaveDialog(controller.getStage());

        try {
            if (file != null) {
                if (format.equals("jpg")) {
                    BufferedImage bImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
                    ImageIO.write(SwingFXUtils.fromFXImage(screenshot, bImage), format, file);
                } else {
                    ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), format, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void exportAsXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save XML");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File f = fileChooser.showSaveDialog(controller.getStage());
        String path = f.getAbsolutePath();

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element root = document.createElement("Runway designator output");
            document.appendChild(root);

            Element employee = document.createElement(cal.getRunwayName());

            root.appendChild(employee);

            Element tora = document.createElement("Original TORA");
            tora.appendChild(document.createTextNode(Integer.toString(cal.getTORA())));
            employee.appendChild(tora);

            Element toda = document.createElement("Original TODA");
            toda.appendChild(document.createTextNode(Integer.toString(cal.getToda())));
            employee.appendChild(toda);

            Element asda = document.createElement("ASDA");
            asda.appendChild(document.createTextNode(Integer.toString(cal.getAsda())));
            employee.appendChild(asda);

            Element lda = document.createElement("LDA");
            lda.appendChild(document.createTextNode(Integer.toString(cal.getLda())));
            employee.appendChild(lda);

            Element obstacleHeight = document.createElement("obstacleHeight");
            obstacleHeight.appendChild(document.createTextNode(Integer.toString(cal.getObstacleHeight())));
            employee.appendChild(obstacleHeight);

            Element obstacleDistanceFromThreshold = document.createElement("obstacleDistanceFromThreshold");
            obstacleDistanceFromThreshold
                    .appendChild(document.createTextNode(Integer.toString(cal.getObstacleDistanceFromThreshold())));
            employee.appendChild(obstacleDistanceFromThreshold);

            Element obstacleDistanceFromCenter = document.createElement("obstacleDistanceFromCenter");
            obstacleDistanceFromCenter
                    .appendChild(document.createTextNode(Integer.toString(cal.getObstacleDistanceFromCenter())));
            employee.appendChild(obstacleDistanceFromCenter);

            Element obstacleDirection = document.createElement("getObstacleDirection");
            obstacleDirection
                    .appendChild(document.createTextNode(cal.getObstacleDirection()));
            employee.appendChild(obstacleDirection);

            Element displacementThreshold = document.createElement("displacementThreshold");
            displacementThreshold
                    .appendChild(document.createTextNode(Integer.toString(cal.getDisplacementThreshold())));
            employee.appendChild(displacementThreshold);

            Element resa = document.createElement("resa");
            resa
                    .appendChild(document.createTextNode(Integer.toString(cal.getResa())));
            employee.appendChild(resa);

            Element stripEnd = document.createElement("stripEnd");
            stripEnd
                    .appendChild(document.createTextNode(Integer.toString(cal.getStripEnd())));
            employee.appendChild(stripEnd);

            Element blastProtection = document.createElement("blastProtection");
            blastProtection
                    .appendChild(document.createTextNode(Integer.toString(cal.getBlastProtection())));
            employee.appendChild(blastProtection);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    protected void importAsXML() {
        FileChooser chooser = new FileChooser();
        File selectedFile = chooser.showOpenDialog(controller.getStage());
        if (selectedFile != null) {
            if (!selectedFile.getName().endsWith(".xml")) {
                new Alert(AlertType.NONE, "The File should be in XML format", ButtonType.OK).showAndWait();
            } else {
                try {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    XMLDecoder decoder = new XMLDecoder(fis);
                    newValues((Calculations) decoder.readObject());
                    decoder.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
