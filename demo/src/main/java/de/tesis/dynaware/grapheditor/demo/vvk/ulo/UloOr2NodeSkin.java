/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.model.GNode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Node skin for a 'tree-like' graph.
 */
public class UloOr2NodeSkin extends UloNodeSkin {


    /**
     * FIXME стили грида  https://stackoverflow.com/questions/32892646/adding-borders-to-gridpane-javafx
     * Чутка про
     * @param inner
     */
    @Override
    void buildInner(HBox inner) {

        final VBox left = new VBox();
        left.prefHeightProperty().bind(inner.heightProperty());
        left.maxHeightProperty().bind(inner.heightProperty());
        left.prefWidthProperty().bind(inner.widthProperty().divide(3.0));
        left.minWidthProperty().bind(inner.widthProperty().divide(3.0));
        left.maxWidthProperty().bind(inner.widthProperty().divide(3.0));
//        left.setStyle("" +
//                "-fx-padding: 0;" +
//                "");

        final GridPane grid = new GridPane();
        grid.prefHeightProperty().bind(left.heightProperty());
        grid.maxHeightProperty().bind(left.heightProperty());
        grid.prefWidthProperty().bind(left.widthProperty());
        grid.minWidthProperty().bind(left.widthProperty());
        grid.maxWidthProperty().bind(left.widthProperty());
        grid.getStyleClass().add("game-grid");
//        grid.setStyle("" +
//                "-fx-background-color: white ; " +
//                "");

        left.getChildren().addAll(grid);

        ColumnConstraints cc = new ColumnConstraints();
        cc.prefWidthProperty().bind(left.widthProperty());
        grid.getColumnConstraints().add(cc);

        for(int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints();
            row.prefHeightProperty().bind(left.heightProperty().divide(2.0));
            grid.getRowConstraints().add(row);
        }

        for(int i = 0; i < 2; i++) {
            Pane pane = new Pane();
            pane.getStyleClass().add("game-grid-cell");

            final Label lTop = new Label("&\n(1)");
            lTop.setMaxWidth(Double.MAX_VALUE);
            lTop.setAlignment(Pos.CENTER);
            pane.getChildren().add(lTop);

//
//            pane.setStyle("" +
//                    "-fx-background-fill: blue, green; " +
//                    "-fx-background-insets: 2, 3; " +
//                    "");
            grid.add(pane, 0, i);
        }
//        grid.setStyle("" +
//                "-fx-background-fill: black, white; " +
//                "-fx-background-insets: 0, 0 1 1 0; " +
//                "");
//        grid.setStyle("" +
//                "-fx-background-color: lightgray; " +
//                "    -fx-vgap: 1; " +
//                "    -fx-hgap: 1; " +
//                "    -fx-padding: 1;");

        final Label lTop = new Label("A");
        lTop.setMaxWidth(Double.MAX_VALUE);
        lTop.setAlignment(Pos.CENTER);

//        grid.add(new Label("A"), 0, 0);
//        grid.add(new Label("B"), 0, 1);
//        grid.add(new Label("C"), 0, 2);
//
//        final VBox leftAtoB = new VBox();
//        leftAtoB.prefHeightProperty().bind(inner.heightProperty());
//        left.maxHeightProperty().bind(inner.heightProperty());
//        left.prefWidthProperty().bind(inner.widthProperty().divide(3.0));
//        left.minWidthProperty().bind(inner.widthProperty().divide(3.0));
//        left.maxWidthProperty().bind(inner.widthProperty().divide(3.0));
//        left.setStyle("" +
//                "-fx-padding: 4;" +
//                "");
//
//        final Label lCenter = new Label("B");
//        lCenter.setMaxWidth(Double.MAX_VALUE);
//        lCenter.setAlignment(Pos.CENTER);
//
//        final Label lBot = new Label("C");
//        lBot.setMaxWidth(Double.MAX_VALUE);
//        lBot.setAlignment(Pos.CENTER);
//
//        final Region lFillA = new Region();
//        VBox.setVgrow(lFillA, Priority.ALWAYS);
//        final Region lFillB = new Region();
//        VBox.setVgrow(lFillB, Priority.ALWAYS);
//        left.getChildren().addAll(lTop, lFillA, lCenter, lFillB, lBot);
//
        final VBox divider = new VBox();
        divider.prefHeightProperty().bind(inner.heightProperty());
        divider.minWidth(1.0);
        divider.maxWidth(1.0);
        divider.prefWidth(1.0);
        divider.setStyle("" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: blue;");

        final VBox right = new VBox();
        right.prefHeightProperty().bind(inner.heightProperty());
        right.prefWidthProperty().bind(left.widthProperty().multiply(2.0));
        right.minWidthProperty().bind(left.widthProperty().multiply(2.0));
        right.setStyle("" +
                "-fx-padding: 4;" +
                "");

//        final Label rTop = new Label("Tr");
//        rTop.setMaxWidth(Double.MAX_VALUE);
//        rTop.setAlignment(Pos.CENTER);
//        right.getChildren().addAll(rTop);
//
        inner.getChildren().addAll(left, divider, right);
    }

    @Override
    void buildText(HBox inner) {

    }

    /**
     * Creates a new {@link de.tesis.dynaware.grapheditor.demo.customskins.ulo.UloOr2NodeSkin} instance.
     *
     * @param node the {link GNode} this skin is representing
     */
    public UloOr2NodeSkin(final GNode node) {
        super(node, "OR");

    }

}
