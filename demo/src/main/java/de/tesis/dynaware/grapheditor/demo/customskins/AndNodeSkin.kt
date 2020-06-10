/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package de.tesis.dynaware.grapheditor.demo.customskins

import de.tesis.dynaware.grapheditor.GConnectorSkin
import de.tesis.dynaware.grapheditor.GNodeSkin
import de.tesis.dynaware.grapheditor.core.connectors.DefaultConnectorTypes
import de.tesis.dynaware.grapheditor.demo.GraphEditorDemoOld
import de.tesis.dynaware.grapheditor.demo.GraphEditorException
import de.tesis.dynaware.grapheditor.demo.NodeProperties
import de.tesis.dynaware.grapheditor.demo.Property
import de.tesis.dynaware.grapheditor.model.GConnector
import de.tesis.dynaware.grapheditor.model.GNode
import de.tesis.dynaware.grapheditor.utils.GeometryUtils
import javafx.css.PseudoClass
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.geometry.Point2D
import javafx.geometry.Side
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*

/**
 * The default node skin. Uses a [ResizableBox].
 *
 *
 *
 * If a node uses this skin its connectors must have one of the 8 types defined in [DefaultConnectorTypes]. If a
 * connector does not have one of these types, it will be set to **left-input**.
 *
 *
 *
 *
 * Connectors are evenly spaced along the sides of the node according to their type.
 *
 */
class AndNodeSkin(node: GNode) : GNodeSkin(node) {
    private val selectionHalo = Rectangle()
    private val topConnectorSkins: MutableList<GConnectorSkin> = ArrayList()
    private val rightConnectorSkins: MutableList<GConnectorSkin> = ArrayList()
    private val bottomConnectorSkins: MutableList<GConnectorSkin> = ArrayList()
    private val leftConnectorSkins: MutableList<GConnectorSkin> = ArrayList()

    // Border and background are separated into 2 rectangles so they can have different effects applied to them.
    private val border = Rectangle()
    private val background = Rectangle()
    private val pane = StackPane()
    private val iss = Property::class.java
            .getResourceAsStream("/de/tesis/dynaware/grapheditor/demo/and.png")
    private val and: Image = Image(iss, 80.0, 120.0, false, true)
//    private val and: Image = Image(`is`, 30, 40, false, true)
    private val imageView = ImageView(and)
    override fun setConnectorSkins(connectorSkins: List<GConnectorSkin>) {
        removeAllConnectors()
        topConnectorSkins.clear()
        rightConnectorSkins.clear()
        bottomConnectorSkins.clear()
        leftConnectorSkins.clear()
        if (connectorSkins != null) {
            for (connectorSkin in connectorSkins) {
                val type = connectorSkin.item.type
                if (DefaultConnectorTypes.isTop(type)) {
                    topConnectorSkins.add(connectorSkin)
                } else if (DefaultConnectorTypes.isRight(type)) {
                    rightConnectorSkins.add(connectorSkin)
                } else if (DefaultConnectorTypes.isBottom(type)) {
                    bottomConnectorSkins.add(connectorSkin)
                } else if (DefaultConnectorTypes.isLeft(type)) {
                    leftConnectorSkins.add(connectorSkin)
                }
                root.children.add(connectorSkin.root)
            }
        }
        layoutConnectors()
    }

    override fun layoutConnectors() {
        layoutAllConnectors()
        layoutSelectionHalo()
    }

    override fun getConnectorPosition(connectorSkin: GConnectorSkin): Point2D {
        val connectorRoot = connectorSkin.root
        val side = DefaultConnectorTypes.getSide(connectorSkin.item.type)

        // The following logic is required because the connectors are offset slightly from the node edges.
        val x: Double
        val y: Double
        if (side == Side.LEFT) {
            x = 0.0
            y = connectorRoot.layoutY + connectorSkin.height / 2
        } else if (side == Side.RIGHT) {
            x = root.width
            y = connectorRoot.layoutY + connectorSkin.height / 2
        } else if (side == Side.TOP) {
            x = connectorRoot.layoutX + connectorSkin.width / 2
            y = 0.0
        } else {
            x = connectorRoot.layoutX + connectorSkin.width / 2
            y = root.height
        }
        return Point2D(x, y)
    }

    /**
     * Checks that the node and its connectors have the correct values to be displayed using this skin.
     */
    private fun performChecks() {
        for (connector in item.connectors) {
            if (!DefaultConnectorTypes.isValid(connector.type)) {
                LOGGER.error("Connector type '{}' not recognized, setting to 'left-input'.", connector.type)
                connector.type = DefaultConnectorTypes.LEFT_INPUT
            }
        }
    }

    /**
     * Lays out the node's connectors.
     */
    private fun layoutAllConnectors() {
        layoutConnectors(topConnectorSkins, false, 0.0)
        layoutConnectors(rightConnectorSkins, true, root.width)
        layoutConnectors(bottomConnectorSkins, false, root.height)
        layoutConnectors(leftConnectorSkins, true, 0.0)
    }

    /**
     * Lays out the given connector skins in a horizontal or vertical direction at the given offset.
     *
     * @param connectorSkins the skins to lay out
     * @param vertical `true` to lay out vertically, `false` to lay out horizontally
     * @param offset the offset in the other dimension that the skins are layed out in
     */
    private fun layoutConnectors(connectorSkins: List<GConnectorSkin>, vertical: Boolean, offset: Double) {
        val count = connectorSkins.size
        for (i in 0 until count) {
            val skin = connectorSkins[i]
            val root = skin.root
            if (vertical) {
                val offsetY = getRoot().height / (count + 1)
                val offsetX = getMinorOffsetX(skin.item)
                root.layoutX = GeometryUtils.moveOnPixel(offset - skin.width / 2 + offsetX)
                root.layoutY = GeometryUtils.moveOnPixel((i + 1) * offsetY - skin.height / 2)
            } else {
                val offsetX = getRoot().width / (count + 1)
                val offsetY = getMinorOffsetY(skin.item)
                root.layoutX = GeometryUtils.moveOnPixel((i + 1) * offsetX - skin.width / 2)
                root.layoutY = GeometryUtils.moveOnPixel(offset - skin.height / 2 + offsetY)
            }
        }
    }

    /**
     * Adds the selection halo and initializes some of its values.
     */
    private fun addSelectionHalo() {
        root.children.add(selectionHalo)
        selectionHalo.isManaged = false
        selectionHalo.isMouseTransparent = false
        selectionHalo.isVisible = false
        selectionHalo.layoutX = -HALO_OFFSET
        selectionHalo.layoutY = -HALO_OFFSET
        selectionHalo.styleClass.add(STYLE_CLASS_SELECTION_HALO)
    }

    /**
     * Lays out the selection halo based on the current width and height of the node skin region.
     */
    private fun layoutSelectionHalo() {
        if (selectionHalo.isVisible) {
            selectionHalo.width = border.width + 2 * HALO_OFFSET
            selectionHalo.height = border.height + 2 * HALO_OFFSET
            val cornerLength = 2 * HALO_CORNER_SIZE
            val xGap = border.width - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET
            val yGap = border.height - 2 * HALO_CORNER_SIZE + 2 * HALO_OFFSET
            selectionHalo.strokeDashOffset = HALO_CORNER_SIZE
            selectionHalo.strokeDashArray.setAll(cornerLength, yGap, cornerLength, xGap)
        }
    }

    override fun selectionChanged(isSelected: Boolean) {
        if (isSelected) {
            selectionHalo.isVisible = true
            layoutSelectionHalo()
            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true)
            root.toFront()
        } else {
            selectionHalo.isVisible = false
            background.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, false)
        }
    }

    /**
     * Removes all connectors from the list of children.
     */
    private fun removeAllConnectors() {
        topConnectorSkins.stream().forEach { skin: GConnectorSkin -> root.children.remove(skin.root) }
        rightConnectorSkins.stream().forEach { skin: GConnectorSkin -> root.children.remove(skin.root) }
        bottomConnectorSkins.stream().forEach { skin: GConnectorSkin -> root.children.remove(skin.root) }
        leftConnectorSkins.stream().forEach { skin: GConnectorSkin -> root.children.remove(skin.root) }
    }

    /**
     * Gets a minor x-offset of a few pixels in order that the connector's area is distributed more evenly on either
     * side of the node border.
     *
     * @param connector the connector to be positioned
     * @return an x-offset of a few pixels
     */
    private fun getMinorOffsetX(connector: GConnector): Double {
        val type = connector.type
        return if (type == DefaultConnectorTypes.LEFT_INPUT || type == DefaultConnectorTypes.RIGHT_OUTPUT) {
            MINOR_POSITIVE_OFFSET
        } else {
            MINOR_NEGATIVE_OFFSET
        }
    }

    /**
     * Gets a minor y-offset of a few pixels in order that the connector's area is distributed more evenly on either
     * side of the node border.
     *
     * @param connector the connector to be positioned
     * @return a y-offset of a few pixels
     */
    private fun getMinorOffsetY(connector: GConnector): Double {
        val type = connector.type
        return if (type == DefaultConnectorTypes.TOP_INPUT || type == DefaultConnectorTypes.BOTTOM_OUTPUT) {
            MINOR_POSITIVE_OFFSET
        } else {
            MINOR_NEGATIVE_OFFSET
        }
    }

    /**
     * Stops the node being dragged if it isn't selected.
     *
     * @param event a mouse-dragged event on the node
     */
    private fun filterMouseDragged(event: MouseEvent) {
        if (event.isPrimaryButtonDown && !isSelected) {
            event.consume()
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AndNodeSkin::class.java)
        const val AND_NODE_TYPE = "and-node"
        private const val STYLE_CLASS_BORDER = "default-node-border"
        private const val STYLE_CLASS_SELECTION_HALO = "default-node-selection-halo"
        private val PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected")
        private const val HALO_OFFSET = 5.0
        private const val HALO_CORNER_SIZE = 10.0
        private const val MINOR_POSITIVE_OFFSET = 2.0
        private const val MINOR_NEGATIVE_OFFSET = -3.0
        private const val MIN_WIDTH = 41.0
        private const val MIN_HEIGHT = 41.0
        private const val SIZE_AROUND_BORDER = 358.0
    }

    /**
     * Creates a new default node skin instance.
     *
     * @param node the [GNode] the skin is being created for
     */
    init {
        performChecks()
        node.type = AND_NODE_TYPE
        background.heightProperty().bind(border.heightProperty().subtract(border.strokeWidthProperty().multiply(2)))
        border.widthProperty().bind(root.widthProperty())
        border.heightProperty().bind(root.heightProperty())
        background.width = border.width + SIZE_AROUND_BORDER
        background.strokeDashArray.addAll(3.0)
        border.styleClass.setAll(STYLE_CLASS_BORDER)
        background.styleClass.setAll(STYLE_CLASS_BORDER)
        border.addEventFilter(MouseEvent.MOUSE_DRAGGED) { event: MouseEvent -> filterMouseDragged(event) }
        border.onMouseDragEntered = EventHandler { mouseDragEvent: MouseDragEvent? ->
            if (border.isResizable) {
                background.width = border.width + SIZE_AROUND_BORDER
            }
        }
        root.children.addAll(border, pane)
        root.setMinSize(MIN_WIDTH, MIN_HEIGHT)
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED) { event: MouseEvent -> filterMouseDragged(event) }
        pane.onMouseClicked = EventHandler { event: MouseEvent ->
            if (event.button == MouseButton.SECONDARY) {
                try {
                    val loader = FXMLLoader()
                    val controller = NodeProperties()
                    controller.setSkin(this)
                    loader.setController(controller)
                    val `is` = Property::class.java
                            .getResourceAsStream("/de/tesis/dynaware/grapheditor/demo/NodeProperties.fxml")
                    val root = loader.load<Parent>(`is`)
                    val stage = Stage()
                    stage.title = "Node properties"
                    stage.scene = Scene(root,
                            Property.PROPERTIES_NODE_WIDTH.int.toDouble(),
                            Property.PROPERTIES_NODE_HEIGHT.int.toDouble())
                    stage.isResizable = false
                    stage.initOwner(GraphEditorDemoOld.primary)
                    stage.show()
                } catch (e: IOException) {
                    LOGGER.error("could nod node properties", e)
                    throw GraphEditorException(e)
                }
            }
        }
        pane.children.addAll(background, imageView)
        addSelectionHalo()
    }
}