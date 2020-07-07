/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.GConnectorSkin;
import de.tesis.dynaware.grapheditor.GNodeSkin;
import de.tesis.dynaware.grapheditor.core.connectors.DefaultConnectorTypes;
import de.tesis.dynaware.grapheditor.demo.customskins.titled.TitledSkinConstants;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphPackage;
import de.tesis.dynaware.grapheditor.utils.GeometryUtils;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import org.eclipse.emf.ecore.EReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Node skin for a 'tree-like' graph.
 */
public abstract class UloNodeSkin extends GNodeSkin {

    protected static final String STYLE_CLASS_BORDER = "ulo-node-border"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_INNER = "ulo-node-inner"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_INNER2 = "ulo-node-inner2"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_BACKGROUND = "ulo-node-background"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_SELECTION_HALO = "ulo-node-selection-halo"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_BUTTON = "ulo-node-button"; //$NON-NLS-1$

    protected static final String STYLE_CLASS_HEADER = "ulo-node-header"; //$NON-NLS-1$
    protected static final String STYLE_CLASS_TITLE = "ulo-node-title"; //$NON-NLS-1$

    protected static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected"); //$NON-NLS-1$

    protected static final double HALO_OFFSET = 5;
    protected static final double HALO_CORNER_SIZE = 10;

//    protected static final double ULO_MASTER_SZ = 99.0;
//    protected static final double ULO_MASTER_PART_SZ = ULO_MASTER_SZ / 3.0;

    private static final double KOEF = 3;
    protected static final double MIN_WIDTH = 3 * (5 + 10) * KOEF;
    protected static final double MIN_HEIGHT = 20.0 * KOEF;

    protected static final double MINOR_POSITIVE_OFFSET = 2;
    protected static final double MINOR_NEGATIVE_OFFSET = -3;

    // Child nodes will be added this far below their parent.
    protected static final double CHILD_Y_OFFSET = 180;

    protected static final EReference NODES = GraphPackage.Literals.GMODEL__NODES;
    protected static final EReference CONNECTIONS = GraphPackage.Literals.GMODEL__CONNECTIONS;
    protected static final EReference CONNECTOR_CONNECTIONS = GraphPackage.Literals.GCONNECTOR__CONNECTIONS;

    protected static final double VIEW_PADDING = 15;

    protected final Rectangle selectionHalo = new Rectangle();
    protected final Button addChildButton = new Button();

//    private GConnectorSkin inputConnectorSkin;
//    private GConnectorSkin outputConnectorSkin;
//    private final List<GConnectorSkin> topConnectorSkins = new ArrayList<>();
    public final List<GConnectorSkin> rightConnectorSkins = new ArrayList<>();
//    private final List<GConnectorSkin> bottomConnectorSkins = new ArrayList<>();
    protected final List<GConnectorSkin> leftConnectorSkins = new ArrayList<>();

    protected final List<Line> inputConnectorTails = new ArrayList<>();
    protected final List<Line> outputConnectorTails = new ArrayList<>();

    // Border and background are separated into 2 rectangles so they can have different effects applied to them.
//    protected final Rectangle border = new Rectangle();
//    protected final Pane inner = makeInner();
    protected final Rectangle background = new Rectangle();

//    protected final Text txt = makeText(inner);

    protected final Font titleFont = Font.font("Verdana", FontWeight.BOLD, 18);
    protected final Font subtitleFont = Font.font("Verdana", FontWeight.BOLD, 14);
    protected final Font connectFont = Font.font("Verdana", FontWeight.BOLD, 10 );

    final protected Pane border = new StackPane();

    final protected HBox inner = new HBox();

    abstract void buildInner(HBox inner);
    abstract void buildText(HBox inner);

    public final String type;

    public UloNodeSkin(final GNode node, final String type) {
        super(node);
        this.type = type;

        //this.getItem().setId(uid);

        getRoot().getChildren().add(border);

        //  НЕ НАЗНАЧАТЬ: *.minHeightProperty().bind(*.heightProperty());
        border.prefHeightProperty().bind(getRoot().heightProperty());
        border.maxHeightProperty().bind(getRoot().heightProperty());
        //  НЕ НАЗНАЧАТЬ: .minWidthProperty().bind(root.widthProperty().divide(3.0));
        border.prefWidthProperty().bind(getRoot().widthProperty());
        border.maxWidthProperty().bind(getRoot().widthProperty());

        border.getStyleClass().setAll(STYLE_CLASS_BORDER);

        inner.prefHeightProperty().bind(border.heightProperty().subtract(2.0));
        inner.maxHeightProperty().bind(border.heightProperty().subtract(2.0));
        inner.prefWidthProperty().bind(border.widthProperty().divide(3.0));
        inner.minWidthProperty().bind(border.widthProperty().divide(3.0));
        inner.maxWidthProperty().bind(border.widthProperty().divide(3.0));
        inner.setStyle(""
                + "-fx-border-style: solid outside;"
                + "-fx-border-width: 2;"
                + "-fx-border-color: blue;");

        border.getChildren().add(inner);

        buildInner(inner);
        buildText(inner);


        getRoot().setMinWidth(MIN_WIDTH);
        getRoot().setMinHeight(MIN_HEIGHT);
        getRoot().setPrefWidth(MIN_WIDTH);
        getRoot().setPrefHeight(MIN_HEIGHT);

        System.out.println("UloNodeSkin.UloNodeSkin: getRoot().height " + getRoot().getPrefHeight());

        addSelectionHalo();
        addButton();

        addInputs();
        background.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::filterMouseDragged);

        inner.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    System.out.println("Double clicked");
                }
            }
        });

    }

    @Override
    public void setConnectorSkins(final List<GConnectorSkin> connectorSkins) {
        System.out.println("UloNodeSkin.setConnectorSkins");

        removeAllConnectors();
        rightConnectorSkins.clear();
        leftConnectorSkins.clear();
        inputConnectorTails.clear();
        outputConnectorTails.clear();

        if (connectorSkins == null || connectorSkins.isEmpty()) {
            return;
        }

        for (final GConnectorSkin skin : connectorSkins) {
            System.out.println("UloNodeSkin.setConnectorSkins: skin="+skin);
            System.out.println("UloNodeSkin.setConnectorSkins: skin.getRoot()="+skin.getRoot());
            System.out.println("UloNodeSkin.setConnectorSkins: skin.getItem().getType()="+skin.getItem().getType());
            if (TitledSkinConstants.TITLED_INPUT_CONNECTOR.equals(skin.getItem().getType())) {
                leftConnectorSkins.add(skin);
                final Line line = new Line(0, 0, inner.getWidth(), 0);
                inputConnectorTails.add(line);
                getRoot().getChildren().add(line);
            } else if (TitledSkinConstants.TITLED_OUTPUT_CONNECTOR.equals(skin.getItem().getType())) {
                rightConnectorSkins.add(skin);
                final Line line = new Line(0, 0, 22.0, 0);
                outputConnectorTails.add(line);
                getRoot().getChildren().add(line);
            }
            getRoot().getChildren().add(skin.getRoot());

        }


        layoutConnectors();
    }



    @Override
    public void layoutConnectors() {
        layoutAllConnectors();
        layoutSelectionHalo();
    }

    @Override
    public Point2D getConnectorPosition(final GConnectorSkin connectorSkin) {

        final Node connectorRoot = connectorSkin.getRoot();

        final double x = connectorRoot.getLayoutX() + connectorSkin.getWidth() / 2;
        final double y = connectorRoot.getLayoutY() + connectorSkin.getHeight() / 2;

        if (leftConnectorSkins.contains(connectorSkin)) {
            return new Point2D(x, y);
        }
        // ELSE:
        // Subtract 1 to align start-of-connection correctly. Compensation for rounding errors?
        return new Point2D(x - 1, y);
    }


    private void performChecks()
    {
        for (final GConnector connector : getItem().getConnectors())
        {
            if (!DefaultConnectorTypes.isValid(connector.getType()))
            {
//                LOGGER.error("Connector type '{}' not recognized, setting to 'left-input'.", connector.getType());
                connector.setType(DefaultConnectorTypes.LEFT_INPUT);
            }
        }
    }

    private void layoutAllConnectors() {
//        layoutConnectors(topConnectorSkins, false, 0);
//        layoutConnectors(rightConnectorSkins, true, getRoot().getWidth());
//        layoutConnectors(bottomConnectorSkins, false, getRoot().getHeight());
//        layoutConnectors(leftConnectorSkins, true, 0);

        layoutInputs();
        layoutOutputs();
    }

    private void layoutOutputs() {
        final int count = rightConnectorSkins.size();
        for (int i = 0; i < count; i++) {
            final GConnectorSkin skin = rightConnectorSkins.get(i);

            final Node root = skin.getRoot();

            final double skinOffsetY = getRoot().getHeight() / (count + 1);
            final double skinOffsetX = getRoot().getWidth() - skin.getWidth() / 2.0;

            root.setLayoutX(/*GeometryUtils.moveOnPixel(0.0)*/skinOffsetX);
            root.setLayoutY(GeometryUtils.moveOnPixel((i + 1) * skinOffsetY - skin.getHeight() / 2));

            final Line line = outputConnectorTails.get(i);
            final double lineOffsetX = ((getRoot().getLayoutBounds().getWidth() - inner.getWidth()) / 2) + inner.getWidth();
            final double lineOffsetY = skinOffsetX;
            line.setLayoutX(lineOffsetX);
            line.setLayoutY(GeometryUtils.moveOnPixel(skin.getRoot().getLayoutY() + skin.getHeight()/2));
            line.setEndX((getRoot().getLayoutBounds().getWidth()) - lineOffsetX);
        }
    }

    private void layoutInputs() {
        final int count = leftConnectorSkins.size();
        for (int i = 0; i < count; i++) {

            final GConnectorSkin skin = leftConnectorSkins.get(i);

            final Node root = skin.getRoot();

            final double skinOffsetY = getRoot().getHeight() / (count + 1);
            final double skinOffsetX = -skin.getWidth() / 2.0;

//            System.out.println("UloNodeSkin.layoutInputs: skin.getWidth()="+skin.getWidth());
//            System.out.println("UloNodeSkin.layoutInputs: skinOffsetX="+skinOffsetX);

            root.setLayoutX(/*GeometryUtils.moveOnPixel(0.0)*/skinOffsetX);
            root.setLayoutY(GeometryUtils.moveOnPixel((i + 1) * skinOffsetY - skin.getHeight() / 2));

            final Line line = inputConnectorTails.get(i);
            final double lineOffsetY = skinOffsetY;
            final double lineOffsetX = skinOffsetX;
            line.setLayoutX(GeometryUtils.moveOnPixel(0));
            line.setLayoutY(GeometryUtils.moveOnPixel(skin.getRoot().getLayoutY() + skin.getHeight()/2));
            line.setEndX((getRoot().getLayoutBounds().getWidth() - inner.getWidth()) / 2);
        }
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

            //final Line line = inputConnectorTails.get(i);

            if (vertical) {

                final double offsetY = getRoot().getHeight() / (count + 1);
                final double offsetX = getMinorOffsetX(skin.getItem());

                System.out.println("UloOrNodeSkin.layoutConnectors x:y = " + offsetX + ":" + offsetY);

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

            selectionHalo.setWidth(border.getWidth() + 2 * HALO_OFFSET);
            selectionHalo.setHeight(border.getHeight() + 2 * HALO_OFFSET);

            final double cornerLength = 2 * HALO_CORNER_SIZE;
            final double xGap = border.getWidth() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;
            final double yGap = border.getHeight() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;

            selectionHalo.setStrokeDashOffset(HALO_CORNER_SIZE);
            selectionHalo.getStrokeDashArray().setAll(cornerLength, yGap, cornerLength, xGap);
        }
    }

    @Override
    protected void selectionChanged(boolean isSelected) {
        if (isSelected) {
            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true);
            selectionHalo.setVisible(true);
            layoutSelectionHalo();
            getRoot().toFront();
        } else {
            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, false);
            selectionHalo.setVisible(false);
        }
    }

    /**
     * Removes any input and output connectors from the list of children, if they exist.
     */
    private void removeAllConnectors() {
        System.out.println("UloNodeSkin.removeAllConnectors");

//        topConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
        rightConnectorSkins.forEach(skin -> {
            getRoot().getChildren().remove(skin.getRoot());
        });
//        bottomConnectorSkins.stream().forEach(skin -> getRoot().getChildren().remove(skin.getRoot()));
        leftConnectorSkins.forEach(skin -> {
            getRoot().getChildren().remove(skin.getRoot());
        });

        inputConnectorTails.forEach(tail -> getRoot().getChildren().remove(tail));
        outputConnectorTails.forEach(tail -> getRoot().getChildren().remove(tail));

        getRoot().getChildren().forEach(e->{
            System.out.println("UloNodeSkin.removeAllConnectors: " +e);
        });

    }

    /**
     * Adds a button to the node skin that will add a child node when pressed.
     */
    private void addButton() {

        StackPane.setAlignment(addChildButton, Pos.BOTTOM_CENTER);

        addChildButton.getStyleClass().setAll(STYLE_CLASS_BUTTON);
        addChildButton.setCursor(Cursor.DEFAULT);
        addChildButton.setPickOnBounds(false);

        //addChildButton.setGraphic(AwesomeIcon.ELLIPSIS.node());
        addChildButton.setText(getItem().getId());
        //addChildButton.setOnAction(event -> addChildNode());
        getRoot().getChildren().add(addChildButton);
    }

    private void addInputs() {

    }

    /**
     * Adds a child node with one input and one output connector, placed directly underneath its parent.
     */
    private void addChildNode() {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("A");
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return "OOO";
                    //return new PhoneBook(text1.getText(), text2.getText());
                }
                return "UUU";
            }
        });
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            System.out.println("UloNodeSkin.addChildNode: result="+ result.get());
        }

//        final GNode childNode = GraphFactory.eINSTANCE.createGNode();
//
//        childNode.setType(TreeSkinConstants.TREE_NODE);
//        childNode.setX(getItem().getX() + (getItem().getWidth() - childNode.getWidth()) / 2);
//        childNode.setY(getItem().getY() + getItem().getHeight() + CHILD_Y_OFFSET);
//
//        final GModel model = getGraphEditor().getModel();
//        final double maxAllowedY = model.getContentHeight() - VIEW_PADDING;
//
//        if (childNode.getY() + childNode.getHeight() > maxAllowedY) {
//            childNode.setY(maxAllowedY - childNode.getHeight());
//        }
//
//        final GConnector input = GraphFactory.eINSTANCE.createGConnector();
//        final GConnector output = GraphFactory.eINSTANCE.createGConnector();
//
//        input.setType(TreeSkinConstants.TREE_INPUT_CONNECTOR);
//        output.setType(TreeSkinConstants.TREE_OUTPUT_CONNECTOR);
//
//        childNode.getConnectors().add(input);
//        childNode.getConnectors().add(output);
//
//        // This allows multiple connections to be created from the output.
//        output.setConnectionDetachedOnDrag(false);
//
//        final GConnector parentOutput = findOutput();
//        final GConnection connection = GraphFactory.eINSTANCE.createGConnection();
//
//        connection.setType(TreeSkinConstants.TREE_CONNECTION);
//        connection.setSource(parentOutput);
//        connection.setTarget(input);
//
//        input.getConnections().add(connection);
//
//        // Set the rest of the values via EMF commands because they touch the currently-edited model.
//        final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(model);
//        final CompoundCommand command = new CompoundCommand();
//
//        command.append(AddCommand.create(editingDomain, model, NODES, childNode));
//        command.append(AddCommand.create(editingDomain, model, CONNECTIONS, connection));
//        command.append(AddCommand.create(editingDomain, parentOutput, CONNECTOR_CONNECTIONS, connection));
//
//        if (command.canExecute()) {
//            editingDomain.getCommandStack().execute(command);
//        }
    }

    /**
     * Finds the output connector of this skin's node.
     *
     * <p>
     * Assumes the node has 1 or 2 connectors, and if there are 2 connectors the second is the output. Bit dodgy but
     * only used in the demo.
     * </p>
     */
    private GConnector findOutput() {

        if (getItem().getConnectors().size() == 1) {
            return getItem().getConnectors().get(0);
        } else if (getItem().getConnectors().size() == 2) {
            return getItem().getConnectors().get(1);
        } else {
            return null;
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
