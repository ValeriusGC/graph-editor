/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.customskins;

import de.tesis.dynaware.grapheditor.GConnectorSkin;
import de.tesis.dynaware.grapheditor.GNodeSkin;
import de.tesis.dynaware.grapheditor.core.connectors.DefaultConnectorTypes;
import de.tesis.dynaware.grapheditor.demo.Property;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.utils.GeometryUtils;
import de.tesis.dynaware.grapheditor.utils.ResizableBox;
import javafx.beans.Observable;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The default node skin. Uses a {@link ResizableBox}.
 *
 * <p>
 * If a node uses this skin its connectors must have one of the 8 types defined in {@link DefaultConnectorTypes}. If a
 * connector does not have one of these types, it will be set to <b>left-input</b>.
 * </p>
 *
 * <p>
 * Connectors are evenly spaced along the sides of the node according to their type.
 * </p>
 */
public class AndWith3InputNodeSkin extends GNodeSkin {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndWith3InputNodeSkin.class);

    public static final String AND_NODE_TYPE = "and-node";

    private static final String STYLE_CLASS_BORDER = "default-node-border";
    private static final String STYLE_CLASS_SELECTION_HALO = "default-node-selection-halo";

    private static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");

    private static final double HALO_OFFSET = 5;
    private static final double HALO_CORNER_SIZE = 10;

    private static final double MINOR_POSITIVE_OFFSET = 2;
    private static final double MINOR_NEGATIVE_OFFSET = -3;

    private static final double MIN_WIDTH = 101.0;
    private static final double MIN_HEIGHT = 101.0;

    private static final double SIZE_AROUND_BORDER = 358;

    private final Rectangle selectionHalo = new Rectangle();

    private final List<GConnectorSkin> topConnectorSkins = new ArrayList<>();
    private final List<GConnectorSkin> rightConnectorSkins = new ArrayList<>();
    private final List<GConnectorSkin> bottomConnectorSkins = new ArrayList<>();
    private final List<GConnectorSkin> leftConnectorSkins = new ArrayList<>();

    // Border and background are separated into 2 rectangles so they can have different effects applied to them.
    private final Rectangle border = new Rectangle();
    private final StackPane borderPane = new StackPane();
    private final Rectangle body = new Rectangle();
    private final StackPane bodyPane = new StackPane();
//    private final Rectangle background = new Rectangle();
    //private final StackPane pane = new StackPane();
    private final InputStream is = Property.class
            .getResourceAsStream("/de/tesis/dynaware/grapheditor/demo/and.png");
    private final Image and = new Image(is,30, 40, false, true);
    private final ImageView imageView = new ImageView(and);

    private List<AndInputNodeSkin> skins = new ArrayList<>();

    /**
     * Creates a new default node skin instance.
     *
     * @param node the {@link GNode} the skin is being created for
     */
    public AndWith3InputNodeSkin(final GNode node) {

        super(node);

        performChecks();

        node.setType(AND_NODE_TYPE);

        getRoot().getChildren().add(borderPane);
        borderPane.setStyle("" +
                "-fx-border-color: black;" +
//                "-fx-stroke: darkslategrey;" +
//                "-fx-stroke-dash-array: 8 4;" +
//                "-fx-stroke-dash-offset: 4;" +
                "");
        borderPane.setOnMousePressed(event ->{
            if (event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                final double w = borderPane.widthProperty().get();
                final Alert alert = new Alert(AlertType.CONFIRMATION, "AndNodeSkin.BORDER-PANE: "+w, ButtonType.OK);
                alert.showAndWait();
//                if (alert.getResult() == ButtonType.YES) {
//                    //do stuff
//                }
            }
        });

        //
//        borderPane.getChildren().add(border);
//        border.widthProperty().bind(borderPane.widthProperty());
//        border.heightProperty().bind(borderPane.heightProperty());
//        border.getStrokeDashArray().addAll(7.0);
//        border.getStyleClass().setAll(STYLE_CLASS_BORDER);

        borderPane.getChildren().add(bodyPane);
        bodyPane.prefWidthProperty().bind(borderPane.widthProperty().divide(2.2));
        bodyPane.maxWidthProperty().bind(borderPane.widthProperty().divide(2.2));
        bodyPane.setStyle("" +
                "-fx-border-color: red;"
//                + "-fx-fill: null;"
//                + "-fx-stroke: green;"
//                + "-fx-stroke-width: 2;"
        );
//        final Rectangle rect = new Rectangle();
//        rect.setStyle(""
//                + "-fx-fill: null;"
//                + "-fx-stroke: green;"
//                + "-fx-stroke-width: 2;"
//        );
//        rect.widthProperty().bind(bodyPane.widthProperty().subtract(1.0));
//        rect.heightProperty().bind(bodyPane.heightProperty().subtract(1.0));
//        bodyPane.getChildren().add(rect);

        final VBox box = new VBox();
        bodyPane.getChildren().add(box);
        final double [] arr = {2,4,4};
        for(int i=0; i<3; ++i){
            final StackPane pane = new StackPane();
            double k = (i+1) * 2D;
            pane.prefHeightProperty().bind(bodyPane.heightProperty().divide(arr[i]));
            pane.maxHeightProperty().bind(bodyPane.heightProperty().divide(arr[i]));
//            pane.prefWidthProperty().bind(border.widthProperty().divide(2.2));
//            pane.maxWidthProperty().bind(border.widthProperty().divide(2.2));


            pane.setStyle("" +
                            "-fx-stroke: darkslategrey;" +
                            "-fx-stroke-type: inside;" +
                            "-fx-border-color: red;" +
                            "-fx-background-color: #2f4f4f;" +
                            "-fx-border-width: 14;" +
                    ""
            );
            box.getChildren().add(pane);
//            final AndInputNodeSkin skin = new AndInputNodeSkin(node, i, pane);

//            final Rectangle r = new Rectangle();
//            r.heightProperty().bind(pane.heightProperty());
//            r.widthProperty().bind(pane.widthProperty());
//            r.setStyle(""
//                    + "-fx-fill: red;"
//                    + "-fx-stroke: green;"
//                    + "-fx-stroke-width: 2;"
//            );
//            pane.getChildren().add(r);

            final Font titleFont = Font.font("Verdana", FontWeight.BOLD, 24);
            final Label l = new Label(""+i);
            l.prefWidthProperty().bind(pane.widthProperty());
            l.maxWidthProperty().bind(pane.widthProperty());
            l.prefHeightProperty().bind(pane.heightProperty());
            l.maxHeightProperty().bind(pane.heightProperty());
            l.setFont(titleFont);
            l.setAlignment(Pos.CENTER);
            pane.getChildren().add(l);

        }

        getRoot().setMinSize(MIN_WIDTH, MIN_HEIGHT);

        addSelectionHalo();
    }

    @Override
    public void setConnectorSkins(final List<GConnectorSkin> connectorSkins) {

//        removeAllConnectors();
//
//        topConnectorSkins.clear();
//        rightConnectorSkins.clear();
//        bottomConnectorSkins.clear();
//        leftConnectorSkins.clear();
//
//        if (connectorSkins != null) {
//            for (final GConnectorSkin connectorSkin : connectorSkins) {
//
//                final String type = connectorSkin.getItem().getType();
//
//                if (DefaultConnectorTypes.isTop(type)) {
//                    topConnectorSkins.add(connectorSkin);
//                } else if (DefaultConnectorTypes.isRight(type)) {
//                    rightConnectorSkins.add(connectorSkin);
//                } else if (DefaultConnectorTypes.isBottom(type)) {
//                    bottomConnectorSkins.add(connectorSkin);
//                } else if (DefaultConnectorTypes.isLeft(type)) {
//                    leftConnectorSkins.add(connectorSkin);
//                }
//
//                getRoot().getChildren().add(connectorSkin.getRoot());
//            }
//        }
//
//        layoutConnectors();
    }

    @Override
    public void layoutConnectors() {
        layoutAllConnectors();
        layoutSelectionHalo();
    }

    @Override
    public Point2D getConnectorPosition(final GConnectorSkin connectorSkin) {

        final Node connectorRoot = connectorSkin.getRoot();

        final Side side = DefaultConnectorTypes.getSide(connectorSkin.getItem().getType());

        // The following logic is required because the connectors are offset slightly from the node edges.
        final double x, y;
        if (side.equals(Side.LEFT)) {
            x = 0;
            y = connectorRoot.getLayoutY() + connectorSkin.getHeight() / 2;
        } else if (side.equals(Side.RIGHT)) {
            x = getRoot().getWidth();
            y = connectorRoot.getLayoutY() + connectorSkin.getHeight() / 2;
        } else if (side.equals(Side.TOP)) {
            x = connectorRoot.getLayoutX() + connectorSkin.getWidth() / 2;
            y = 0;
        } else {
            x = connectorRoot.getLayoutX() + connectorSkin.getWidth() / 2;
            y = getRoot().getHeight();
        }

        return new Point2D(x, y);
    }

    /**
     * Checks that the node and its connectors have the correct values to be displayed using this skin.
     */
    private void performChecks()
    {
        for (final GConnector connector : getItem().getConnectors())
        {
            if (!DefaultConnectorTypes.isValid(connector.getType()))
            {
                LOGGER.error("Connector type '{}' not recognized, setting to 'left-input'.", connector.getType());
                connector.setType(DefaultConnectorTypes.LEFT_INPUT);
            }
        }
    }

    /**
     * Lays out the node's connectors.
     */
    private void layoutAllConnectors() {

        layoutConnectors(topConnectorSkins, false, 0);
        layoutConnectors(rightConnectorSkins, true, getRoot().getWidth());
        layoutConnectors(bottomConnectorSkins, false, getRoot().getHeight());
        layoutConnectors(leftConnectorSkins, true, 0);
    }

    /**
     * Lays out the given connector skins in a horizontal or vertical direction at the given offset.
     *
     * @param connectorSkins the skins to lay out
     * @param vertical {@code true} to lay out vertically, {@code false} to lay out horizontally
     * @param offset the offset in the other dimension that the skins are layed out in
     */
    private void layoutConnectors(final List<GConnectorSkin> connectorSkins, final boolean vertical, final double offset) {

        final int count = connectorSkins.size();

        for (int i = 0; i < count; i++) {

            final GConnectorSkin skin = connectorSkins.get(i);
            final Node root = skin.getRoot();

            if (vertical) {

                final double offsetY = getRoot().getHeight() / (count + 1);
                final double offsetX = getMinorOffsetX(skin.getItem());

                root.setLayoutX(GeometryUtils.moveOnPixel(offset - skin.getWidth() / 2 + offsetX));
                root.setLayoutY(GeometryUtils.moveOnPixel((i + 1) * offsetY - skin.getHeight() / 2));

            } else {

                final double offsetX = getRoot().getWidth() / (count + 1);
                final double offsetY = getMinorOffsetY(skin.getItem());

                root.setLayoutX(GeometryUtils.moveOnPixel((i + 1) * offsetX - skin.getWidth() / 2));
                root.setLayoutY(GeometryUtils.moveOnPixel(offset - skin.getHeight() / 2 + offsetY));
            }
        }
    }

    /**
     * Adds the selection halo and initializes some of its values.
     */
    private void addSelectionHalo() {

        getRoot().getChildren().add(selectionHalo);

        selectionHalo.setManaged(false);
        selectionHalo.setMouseTransparent(false);
        selectionHalo.setVisible(false);

        selectionHalo.setLayoutX(-HALO_OFFSET);
        selectionHalo.setLayoutY(-HALO_OFFSET);

        selectionHalo.getStyleClass().add(STYLE_CLASS_SELECTION_HALO);
    }

    /**
     * Lays out the selection halo based on the current width and height of the node skin region.
     */
    private void layoutSelectionHalo() {

        if (selectionHalo.isVisible()) {

            selectionHalo.setWidth(borderPane.getWidth() + 2 * HALO_OFFSET);
            selectionHalo.setHeight(borderPane.getHeight() + 2 * HALO_OFFSET);

            final double cornerLength = 2 * HALO_CORNER_SIZE;
            final double xGap = borderPane.getWidth() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;
            final double yGap = borderPane.getHeight() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;

            selectionHalo.setStrokeDashOffset(HALO_CORNER_SIZE);
            selectionHalo.getStrokeDashArray().setAll(cornerLength, yGap, cornerLength, xGap);
        }
    }

    @Override
    protected void selectionChanged(boolean isSelected) {
        if (isSelected) {
            selectionHalo.setVisible(true);
            layoutSelectionHalo();
            border.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true);
            getRoot().toFront();
        } else {
            selectionHalo.setVisible(false);
            border.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, false);
        }
    }

    /**
     * Removes all connectors from the list of children.
     */
    private void removeAllConnectors() {

        topConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
        rightConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
        bottomConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
        leftConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
    }

    /**
     * Gets a minor x-offset of a few pixels in order that the connector's area is distributed more evenly on either
     * side of the node border.
     *
     * @param connector the connector to be positioned
     * @return an x-offset of a few pixels
     */
    private double getMinorOffsetX(final GConnector connector) {

        final String type = connector.getType();

        if (type.equals(DefaultConnectorTypes.LEFT_INPUT) || type.equals(DefaultConnectorTypes.RIGHT_OUTPUT)) {
            return MINOR_POSITIVE_OFFSET;
        } else {
            return MINOR_NEGATIVE_OFFSET;
        }
    }

    /**
     * Gets a minor y-offset of a few pixels in order that the connector's area is distributed more evenly on either
     * side of the node border.
     *
     * @param connector the connector to be positioned
     * @return a y-offset of a few pixels
     */
    private double getMinorOffsetY(final GConnector connector) {

        final String type = connector.getType();

        if (type.equals(DefaultConnectorTypes.TOP_INPUT) || type.equals(DefaultConnectorTypes.BOTTOM_OUTPUT)) {
            return MINOR_POSITIVE_OFFSET;
        } else {
            return MINOR_NEGATIVE_OFFSET;
        }
    }

    /**
     * Stops the node being dragged if it isn't selected.
     *
     * @param event a mouse-dragged event on the node
     */
    private void filterMouseDragged(final MouseEvent event) {
        if (event.isPrimaryButtonDown() && !isSelected()) {
            event.consume();
        }
    }
}
