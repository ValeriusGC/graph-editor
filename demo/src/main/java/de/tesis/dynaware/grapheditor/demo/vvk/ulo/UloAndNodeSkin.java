/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.model.GNode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Node skin for a 'tree-like' graph.
 */
public class UloAndNodeSkin extends UloNodeSkin {

//    private static final String STYLE_CLASS_BORDER = "ulo-node-border"; //$NON-NLS-1$
//    private static final String STYLE_CLASS_INNER = "ulo-node-inner"; //$NON-NLS-1$
//    private static final String STYLE_CLASS_BACKGROUND = "ulo-node-background"; //$NON-NLS-1$
//    private static final String STYLE_CLASS_SELECTION_HALO = "ulo-node-selection-halo"; //$NON-NLS-1$
//    private static final String STYLE_CLASS_BUTTON = "ulo-node-button"; //$NON-NLS-1$
//
//    private static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected"); //$NON-NLS-1$
//
//    private static final double HALO_OFFSET = 5;
//    private static final double HALO_CORNER_SIZE = 10;
//
//    private static final double MIN_WIDTH = 161;
//    private static final double MIN_HEIGHT = 161;
//
//    // Child nodes will be added this far below their parent.
//    private static final double CHILD_Y_OFFSET = 80;
//
//    private static final EReference NODES = GraphPackage.Literals.GMODEL__NODES;
//    private static final EReference CONNECTIONS = GraphPackage.Literals.GMODEL__CONNECTIONS;
//    private static final EReference CONNECTOR_CONNECTIONS = GraphPackage.Literals.GCONNECTOR__CONNECTIONS;
//
//    private static final double VIEW_PADDING = 15;
//
//    private final Rectangle selectionHalo = new Rectangle();
//    private final Button addChildButton = new Button();
//
//    private GConnectorSkin inputConnectorSkin;
//    private GConnectorSkin outputConnectorSkin;
//
//    // Border and background are separated into 2 rectangles so they can have different effects applied to them.
//    private final Rectangle border = new Rectangle();
//    private final Rectangle inner = new Rectangle();
//    private final Rectangle background = new Rectangle();
//    private final Text txt = new Text();

    @Override
    void buildInner(HBox inner) {
        final Label l = new Label("&");
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
     * Creates a new {@link de.tesis.dynaware.grapheditor.demo.customskins.ulo.UloAndNodeSkin} instance.
     *
     * @param node the {link GNode} this skin is representing
     */
    public UloAndNodeSkin(final GNode node) {
        super(node, "AND");

//        inner.widthProperty().bind(border.widthProperty().subtract(121.0));
//        inner.heightProperty().bind(border.heightProperty().subtract(10.0));
//
//        background.widthProperty().bind(inner.widthProperty().subtract(inner.strokeWidthProperty().multiply(2)));
//        background.heightProperty().bind(inner.heightProperty().subtract(inner.strokeWidthProperty().multiply(2)));
//
//        border.widthProperty().bind(getRoot().widthProperty());
//        border.heightProperty().bind(getRoot().heightProperty());
//
//        border.getStyleClass().setAll(STYLE_CLASS_BORDER);
//        inner.getStyleClass().setAll(STYLE_CLASS_INNER);
//        background.getStyleClass().setAll(STYLE_CLASS_BACKGROUND);
//
//        txt.setText("&");
//        txt.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
//
//        getRoot().getChildren().addAll(border, inner, background, txt);
//        getRoot().setMinSize(MIN_WIDTH, MIN_HEIGHT);
//
//        addSelectionHalo();
//        addButton();
//
//        System.out.println("UloAndNodeSkin.UloAndNodeSkin");
//
//        background.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::filterMouseDragged);
    }

//    @Override
//    public void setConnectorSkins(final List<GConnectorSkin> connectorSkins) {
//        System.out.println("UloAndNodeSkin.setConnectorSkins : " + connectorSkins.size());
////        removeConnectors();
////
////        if (connectorSkins == null || connectorSkins.isEmpty() || connectorSkins.size() > 2) {
////            return;
////        }
////
////        for (final GConnectorSkin skin : connectorSkins) {
////            if (TreeSkinConstants.TREE_OUTPUT_CONNECTOR.equals(skin.getItem().getType())) {
////                outputConnectorSkin = skin;
////                getRoot().getChildren().add(skin.getRoot());
////            } else if (TreeSkinConstants.TREE_INPUT_CONNECTOR.equals(skin.getItem().getType())) {
////                inputConnectorSkin = skin;
////                getRoot().getChildren().add(skin.getRoot());
////            }
////        }
//    }

//    @Override
//    public void layoutConnectors() {
//        layoutTopAndBottomConnectors();
//        layoutSelectionHalo();
//    }

//    @Override
//    public Point2D getConnectorPosition(final GConnectorSkin connectorSkin) {
//
//        System.out.println("UloAndNodeSkin.getConnectorPosition");
//
//        final Node connectorRoot = connectorSkin.getRoot();
//
//        final double x = connectorRoot.getLayoutX() + connectorSkin.getWidth() / 2;
//        final double y = connectorRoot.getLayoutY() + connectorSkin.getHeight() / 2;
//
//        return new Point2D(x, y);
//    }

    /**
     * Lays out the connectors. Inputs on top, outputs on the bottom.
     */
    private void layoutTopAndBottomConnectors() {
        System.out.println("UloAndNodeSkin.layoutTopAndBottomConnectors");
//        if (inputConnectorSkin != null) {
//
//            final double inputX = (getRoot().getWidth() - inputConnectorSkin.getWidth()) / 2;
//            final double inputY = -inputConnectorSkin.getHeight() / 2;
//
//            inputConnectorSkin.getRoot().setLayoutX(inputX);
//            inputConnectorSkin.getRoot().setLayoutY(inputY);
//        }
//
//        if (outputConnectorSkin != null) {
//
//            final double outputX = (getRoot().getWidth() - outputConnectorSkin.getWidth()) / 2;
//            final double outputY = getRoot().getHeight() - outputConnectorSkin.getHeight() / 2;
//
//            outputConnectorSkin.getRoot().setLayoutX(outputX);
//            outputConnectorSkin.getRoot().setLayoutY(outputY);
//        }
    }

//    /**
//     * Adds the selection halo and initializes some of its values.
//     */
//    private void addSelectionHalo() {
//
//        getRoot().getChildren().add(selectionHalo);
//
//        selectionHalo.setManaged(false);
//        selectionHalo.setMouseTransparent(false);
//        selectionHalo.setVisible(false);
//
//        selectionHalo.setLayoutX(-HALO_OFFSET);
//        selectionHalo.setLayoutY(-HALO_OFFSET);
//
//        selectionHalo.getStyleClass().add(STYLE_CLASS_SELECTION_HALO);
//    }

//    /**
//     * Lays out the selection halo based on the current width and height of the node skin region.
//     */
//    private void layoutSelectionHalo() {
//
//        if (selectionHalo.isVisible()) {
//
//            selectionHalo.setWidth(border.getWidth() + 2 * HALO_OFFSET);
//            selectionHalo.setHeight(border.getHeight() + 2 * HALO_OFFSET);
//
//            final double cornerLength = 2 * HALO_CORNER_SIZE;
//            final double xGap = border.getWidth() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;
//            final double yGap = border.getHeight() - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET;
//
//            selectionHalo.setStrokeDashOffset(HALO_CORNER_SIZE);
//            selectionHalo.getStrokeDashArray().setAll(cornerLength, yGap, cornerLength, xGap);
//        }
//    }

//    @Override
//    protected void selectionChanged(boolean isSelected) {
//        if (isSelected) {
//            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true);
//            selectionHalo.setVisible(true);
//            layoutSelectionHalo();
//            getRoot().toFront();
//        } else {
//            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, false);
//            selectionHalo.setVisible(false);
//        }
//    }

    /**
     * Removes any input and output connectors from the list of children, if they exist.
     */
    private void removeConnectors() {
//
//        if (inputConnectorSkin != null) {
//            getRoot().getChildren().remove(inputConnectorSkin.getRoot());
//        }
//
//        if (outputConnectorSkin != null) {
//            getRoot().getChildren().remove(outputConnectorSkin.getRoot());
//        }
    }

    /**
     * Adds a button to the node skin that will add a child node when pressed.
     */
//    private void addButton() {
//
//        StackPane.setAlignment(addChildButton, Pos.BOTTOM_CENTER);
//
//        addChildButton.getStyleClass().setAll(STYLE_CLASS_BUTTON);
//        addChildButton.setCursor(Cursor.DEFAULT);
//        addChildButton.setPickOnBounds(false);
//
//        addChildButton.setGraphic(AwesomeIcon.PLUS.node());
//        addChildButton.setOnAction(event -> addChildNode());
//
//        getRoot().getChildren().add(addChildButton);
//    }

//    /**
//     * Adds a child node with one input and one output connector, placed directly underneath its parent.
//     */
//    private void addChildNode() {
//
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
//    }

    /**
     * Finds the output connector of this skin's node.
     *
     * <p>
     * Assumes the node has 1 or 2 connectors, and if there are 2 connectors the second is the output. Bit dodgy but
     * only used in the demo.
     * </p>
     */
//    private GConnector findOutput() {
//
//        if (getItem().getConnectors().size() == 1) {
//            return getItem().getConnectors().get(0);
//        } else if (getItem().getConnectors().size() == 2) {
//            return getItem().getConnectors().get(1);
//        } else {
//            return null;
//        }
//    }

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
