/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo;

import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.utils.AwesomeIcon;
import de.tesis.dynaware.grapheditor.demo.vvk.Model;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.f;
import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.sayHelloSingle;

/**
 * A VVK application to show uses of the {@link GraphEditor} library.
 */
public class GraphEditorDemoVvk extends Application {

    private static final String APPLICATION_TITLE = "Graph Editor Demo"; //$NON-NLS-1$
    private static final String DEMO_STYLESHEET = "demo.css"; //$NON-NLS-1$
    private static final String TREE_SKIN_STYLESHEET = "treeskins.css"; //$NON-NLS-1$
    private static final String TITLED_SKIN_STYLESHEET = "titledskins.css"; //$NON-NLS-1$
    private static final String FONT_AWESOME = "fontawesome.ttf"; //$NON-NLS-1$

    GraphEditorDemoControllerVvk ctrl;
    GraphEditorContainer cnt;

    @Override
    public void start(final Stage primaryStage) throws Exception {

        cnt = new GraphEditorContainer();
        cnt.setMaxHeight(Integer.MAX_VALUE);
        cnt.setMaxWidth(Integer.MAX_VALUE);
        ctrl = new GraphEditorDemoControllerVvk();
        ctrl.initialize(cnt);

        Scene scene = new Scene(getAnchorPane(), 500, 400);
        final String f = getClass().getResource(DEMO_STYLESHEET).toExternalForm();
        scene.getStylesheets().add(f);
        scene.getStylesheets().add(getClass().getResource(TREE_SKIN_STYLESHEET).toExternalForm());
        scene.getStylesheets().add(getClass().getResource(TITLED_SKIN_STYLESHEET).toExternalForm());
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        Font.loadFont(getClass().getResource(FONT_AWESOME).toExternalForm(), 12);

        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    public AnchorPane getAnchorPane() {
        final AnchorPane ap = new AnchorPane();
        final Node b = vbox();
        AnchorPane.setRightAnchor(b, 0.0);
        AnchorPane.setTopAnchor(b, 0.0);
        AnchorPane.setLeftAnchor(b, 0.0);
        AnchorPane.setBottomAnchor(b, 0.0);
        final ToggleButton btn = btn();
//        btn.getStyleClass().add("custom-button");
        btn.getStyleClass().add("minimap-button");
        btn.setGraphic(AwesomeIcon.MAP.node());
//        btn.getStyleClass().add("button1");
        btn.setFocusTraversable(false);
        AnchorPane.setRightAnchor(btn, 10.0);
        AnchorPane.setTopAnchor(btn, 4.0);
//        btn.setGraphic(AwesomeIcon.MAP.node());
        ap.getChildren().addAll(b, btn);
        return ap;
    }

    public Parent vbox() {
        final VBox b = new VBox();
        b.fillWidthProperty().setValue(true);
        b.getChildren().addAll(menuBar(), cnt);
        VBox.setVgrow(cnt, Priority.ALWAYS);
        return b;
    }

    public Node menuBar() {
        Menu menu = new Menu("Файл");
        MenuItem item1 = new MenuItem("Открыть схему");
        item1.setOnAction(a -> ctrl.load());
        MenuItem item2 = new MenuItem("Item 2");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        menu.getItems().add(item1);
        menu.getItems().add(separator);
        menu.getItems().add(item2);


        MenuBar node = new MenuBar();
        node.getMenus().add(menu);
        return node;
    }

    public ToggleButton btn() {
        ToggleButton btn = new ToggleButton();
//        InputStream is = Property.class
//                .getResourceAsStream("/de/tesis/dynaware/grapheditor/demo/and.png");
//        Image and = new Image(is,30, 40, false, true);
//        ImageView imageView = new ImageView(and);
//        btn.setGraphic(imageView);
//
//        final URL res = Property.class
//                .getResource("/de/tesis/dynaware/grapheditor/demo/demo.css");
//        btn.getStylesheets().add(res.toExternalForm());
//
//        //btn.setGraphic(AwesomeIcon.MAP.node());
//        btn.getStyleClass().add("minimapButton");
//        btn.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

//        btn.setStyle("minimapButton");
//        btn.setText("Say 'Hello World'");
        final Integer v1 = f(10);
        final String v2 = f("LALALA");
        final String[] s = {"-"};
        m.observe().collect((a,b)-> {
            return System.out.println("");
        });
        btn.setOnAction(event -> System.out.printf("Hello World! %d, %s, %s\n", v1, v2,
                sayHelloSingle().blockingGet()));
        return btn;
    }

    final Model m = new Model();

}

