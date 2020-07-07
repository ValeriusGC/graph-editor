/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.model.GNode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Node skin for a 'tree-like' graph.
 */
public class UloOrNodeSkin extends UloNodeSkin {

    @Override
    void buildInner(HBox inner) {

        final Label l = new Label("I");
        l.prefWidthProperty().bind(inner.widthProperty());
        l.maxWidthProperty().bind(inner.widthProperty());
        l.prefHeightProperty().bind(inner.heightProperty());
        l.maxHeightProperty().bind(inner.heightProperty());
        l.setFont(titleFont);
        l.setAlignment(Pos.CENTER);
        inner.getChildren().add(l);

    }

    @Override
    void buildText(HBox inner) {

    }

    /**
     * Creates a new {@link de.tesis.dynaware.grapheditor.demo.customskins.ulo.UloOrNodeSkin} instance.
     *
     * @param node the {link GNode} this skin is representing
     */
    public UloOrNodeSkin(final GNode node) {
        super(node, "OR");

    }

}
