/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.model.GNode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Node skin for a 'tree-like' graph.
 */
public class UloTrSNodeSkin extends UloNodeSkin {


    @Override
    void buildInner(HBox inner) {

        final VBox left = new VBox();
        left.prefHeightProperty().bind(inner.heightProperty());
        left.maxHeightProperty().bind(inner.heightProperty());
        left.prefWidthProperty().bind(inner.widthProperty().divide(3.0));
        left.minWidthProperty().bind(inner.widthProperty().divide(3.0));
        left.maxWidthProperty().bind(inner.widthProperty().divide(3.0));
        left.setStyle("" +
                "-fx-padding: 4;" +
                "");

        final Label lTop = new Label("S");
        lTop.setMaxWidth(Double.MAX_VALUE);
        lTop.setAlignment(Pos.CENTER);

        final Label lBot = new Label("R");
        lBot.setMaxWidth(Double.MAX_VALUE);
        lBot.setAlignment(Pos.CENTER);

        final Region lFill = new Region();
        VBox.setVgrow(lFill, Priority.ALWAYS);
        left.getChildren().addAll(lTop, lFill, lBot);

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
        final Label rTop = new Label("Ts");
        rTop.setMaxWidth(Double.MAX_VALUE);
        rTop.setAlignment(Pos.CENTER);
        right.getChildren().addAll(rTop);

        inner.getChildren().addAll(left, divider, right);
    }

    @Override
    void buildText(HBox inner) {
        new Text("I");
    }

    /**
     * Creates a new {@link de.tesis.dynaware.grapheditor.demo.customskins.ulo.UloTrSNodeSkin} instance.
     *
     * @param node the {link GNode} this skin is representing
     */
    public UloTrSNodeSkin(final GNode node) {
        super(node, "TR-S");
    }

}
