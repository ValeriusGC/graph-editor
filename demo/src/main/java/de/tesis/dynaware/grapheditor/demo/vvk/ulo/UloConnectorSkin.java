package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.GConnectorSkin;
import de.tesis.dynaware.grapheditor.GConnectorStyle;
import de.tesis.dynaware.grapheditor.demo.customskins.titled.TitledSkinConstants;
import de.tesis.dynaware.grapheditor.model.GConnector;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class UloConnectorSkin extends GConnectorSkin {

    private static final String STYLE_CLASS_INPUT = "ulo-input-connector"; //$NON-NLS-1$
    private static final String STYLE_CLASS_OUTPUT = "ulo-output-connector"; //$NON-NLS-1$

    private static final PseudoClass PSEUDO_CLASS_ALLOWED = PseudoClass.getPseudoClass("allowed"); //$NON-NLS-1$
    private static final PseudoClass PSEUDO_CLASS_FORBIDDEN = PseudoClass.getPseudoClass("forbidden"); //$NON-NLS-1$

    private static final double RADIUS = 3.0;

    private final Pane root = new Pane();
    private final Circle circle = new Circle(RADIUS);

    public UloConnectorSkin(final GConnector connector) {

        super(connector);

        root.setMinSize(2 * RADIUS, 2 * RADIUS);
        root.setPrefSize(2 * RADIUS, 2 * RADIUS);
        root.setMaxSize(2 * RADIUS, 2 * RADIUS);

        root.setPickOnBounds(false);

        circle.setManaged(false);
        circle.resizeRelocate(0, 0, 2 * RADIUS, 2 * RADIUS);

        if (TitledSkinConstants.TITLED_INPUT_CONNECTOR.equals(connector.getType())) {
            circle.getStyleClass().setAll(STYLE_CLASS_INPUT);
        } else {
            circle.getStyleClass().setAll(STYLE_CLASS_OUTPUT);
        }
//        if (UloSkinConstants.ULO_INPUT_CONNECTOR.equals(connector.getType())) {
//            circle.getStyleClass().setAll(STYLE_CLASS_INPUT);
//        } else {
//            circle.getStyleClass().setAll(STYLE_CLASS_OUTPUT);
//        }

        root.getChildren().add(circle);
    }

    @Override
    protected void selectionChanged(boolean isSelected) {
        // Not implemented
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public double getWidth() {
        return 2 * RADIUS;
    }

    @Override
    public double getHeight() {
        return 2 * RADIUS;
    }

    @Override
    public void applyStyle(final GConnectorStyle style) {

        switch (style) {

        case DEFAULT:
            circle.pseudoClassStateChanged(PSEUDO_CLASS_FORBIDDEN, false);
            circle.pseudoClassStateChanged(PSEUDO_CLASS_ALLOWED, false);
            break;

        case DRAG_OVER_ALLOWED:
            circle.pseudoClassStateChanged(PSEUDO_CLASS_FORBIDDEN, false);
            circle.pseudoClassStateChanged(PSEUDO_CLASS_ALLOWED, true);
            break;

        case DRAG_OVER_FORBIDDEN:
            circle.pseudoClassStateChanged(PSEUDO_CLASS_FORBIDDEN, true);
            circle.pseudoClassStateChanged(PSEUDO_CLASS_ALLOWED, false);
            break;
        }
    }
}
