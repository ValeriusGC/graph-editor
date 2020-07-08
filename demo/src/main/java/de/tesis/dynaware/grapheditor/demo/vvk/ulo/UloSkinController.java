package de.tesis.dynaware.grapheditor.demo.vvk.ulo;

import de.tesis.dynaware.grapheditor.*;
import de.tesis.dynaware.grapheditor.core.connectors.DefaultConnectorTypes;
import de.tesis.dynaware.grapheditor.core.skins.defaults.DefaultConnectionSkin;
import de.tesis.dynaware.grapheditor.core.skins.defaults.DefaultConnectorSkin;
import de.tesis.dynaware.grapheditor.core.skins.defaults.DefaultNodeSkin;
import de.tesis.dynaware.grapheditor.core.skins.defaults.DefaultTailSkin;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.customskins.AndNodeSkin;
import de.tesis.dynaware.grapheditor.demo.customskins.AndWith3InputNodeSkin;
import de.tesis.dynaware.grapheditor.demo.customskins.DefaultSkinController;
import de.tesis.dynaware.grapheditor.demo.customskins.titled.TitledSkinConstants;
import de.tesis.dynaware.grapheditor.demo.customskins.tree.TreeConnectionSkin;
import de.tesis.dynaware.grapheditor.demo.customskins.tree.TreeSkinConstants;
import de.tesis.dynaware.grapheditor.demo.customskins.tree.TreeTailSkin;
import de.tesis.dynaware.grapheditor.demo.selections.SelectionCopier;
import de.tesis.dynaware.grapheditor.model.*;
import javafx.geometry.Side;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.List;


/**
 * Responsible for ulo-skin specific logic in the graph editor demo.
 */
public class UloSkinController extends DefaultSkinController {

    protected static final int TREE_NODE_INITIAL_Y = 19;

    private static final int MAX_CONNECTOR_COUNT = 8;

//    private final GraphEditor graphEditor;
//    private final GraphEditorContainer graphEditorContainer;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    private String nodeType;

    /**
     * Creates a new {@link UloSkinController} instance.
     *
     * @param graphEditor the graph editor on display in this demo
     * @param graphEditorContainer the graph editor container on display in this demo
     */
    public UloSkinController(final GraphEditor graphEditor, final GraphEditorContainer graphEditorContainer) {
        super(graphEditor, graphEditorContainer);
//        this.graphEditor = graphEditor;
//        this.graphEditorContainer = graphEditorContainer;
    }

    @Override
    public void activate()
    {
        graphEditorContainer.getMinimap().setConnectionFilter(c -> true);
        graphEditor.setNodeSkinFactory(this::createSkin);
        graphEditor.setConnectorSkinFactory(this::createSkin);
//        graphEditor.setConnectionSkinFactory(this::createSkin);
//        graphEditor.setTailSkinFactory(this::createTailSkin);
//        graphEditorContainer.getMinimap().setConnectionFilter(c -> false);
    }

    private GTailSkin createTailSkin(final GConnector connector) {
        return TreeSkinConstants.TREE_INPUT_CONNECTOR.equals(connector.getType()) || TreeSkinConstants.TREE_OUTPUT_CONNECTOR.equals(connector.getType()) ?
                new TreeTailSkin(connector) : new DefaultTailSkin(connector);
    }

    @Override
    public void addNode(final double currentZoomFactor) {

//        graphEditor.getSkinLookup().getNodeSkins().size()

        final double windowXOffset = graphEditorContainer.getContentX() / currentZoomFactor;
        final double windowYOffset = graphEditorContainer.getContentY() / currentZoomFactor;

        final GNode node = GraphFactory.eINSTANCE.createGNode();
        node.setY(TREE_NODE_INITIAL_Y + windowYOffset);

        node.setId(String.format("%08X", System.currentTimeMillis()));

        final GConnector output = GraphFactory.eINSTANCE.createGConnector();
        node.getConnectors().add(output);

        final GConnector input = GraphFactory.eINSTANCE.createGConnector();
        node.getConnectors().add(input);

        final double initialX = graphEditorContainer.getWidth() / (2 * currentZoomFactor) - node.getWidth() / 2;
        node.setX(Math.floor(initialX) + windowXOffset);

        node.setType(getNodeType());
        input.setType(TitledSkinConstants.TITLED_INPUT_CONNECTOR);
        output.setType(TitledSkinConstants.TITLED_OUTPUT_CONNECTOR);
//        output.setType(UloSkinConstants.ULO_OUTPUT_CONNECTOR);
//        input.setType(UloSkinConstants.ULO_INPUT_CONNECTOR);

        Commands.addNode(graphEditor.getModel(), node);
    }

    private GNodeSkin fromFactory(final GNode node) {
        final String type = node.getType();
        GNodeSkin skin;
        switch (type) {
            case UloSkinConstants.ULO_NODE_OR:
                skin = new UloOrNodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_OR_3:
                skin = new UloOr3NodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_OR_2:
                skin = new UloOr2NodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_AND:
                skin = new UloAndNodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_TR:
                skin = new UloTrNodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_TR_S:
                skin = new UloTrSNodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_TR_Q:
                skin = new UloTrQNodeSkin(node);
                break;
            case UloSkinConstants.ULO_NODE_NEWNEW:
                //skin = new AndNodeSkin(node);
                skin = new AndWith3InputNodeSkin(node);
                break;
            default:
                skin = new DefaultNodeSkin(node);
        }
        return skin;
    }

    private GNodeSkin createSkin(final GNode node) {
        return fromFactory(node);
    }

    private GConnectionSkin createSkin(final GConnection connection) {
        return TreeSkinConstants.TREE_CONNECTION.equals(connection.getType()) ? new TreeConnectionSkin(connection) : new DefaultConnectionSkin(connection);
    }

    private GConnectorSkin createSkin(final GConnector connector) {
        return TitledSkinConstants.TITLED_INPUT_CONNECTOR.equals(connector.getType()) || TitledSkinConstants.TITLED_OUTPUT_CONNECTOR.equals(connector.getType()) ?
                new UloConnectorSkin(connector) : new DefaultConnectorSkin(connector);
//        return UloSkinConstants.ULO_INPUT_CONNECTOR.equals(connector.getType())
//                || UloSkinConstants.ULO_OUTPUT_CONNECTOR.equals(connector.getType()) ?
//                new UloConnectorSkin(connector) : new DefaultConnectorSkin(connector);
    }


//    @Override
//    public void addConnector(final Side position, final boolean input) {
//        // Not implemented for tree nodes.
//    }
//
//    @Override
//    public void clearConnectors() {
//        // Not implemented for tree nodes.
//    }

    public void addInput() {
        addConnector(TitledSkinConstants.TITLED_INPUT_CONNECTOR);
        //addConnector(UloSkinConstants.ULO_INPUT_CONNECTOR);
    }

    public void addOutput() {
        addConnector(TitledSkinConstants.TITLED_OUTPUT_CONNECTOR);
        //addConnector(UloSkinConstants.ULO_OUTPUT_CONNECTOR);
    }

    public void addConnector(String type) {
        System.out.println("UloSkinController.addConnector");
        final GModel model = graphEditor.getModel();
        final SkinLookup skinLookup = graphEditor.getSkinLookup();
        final CompoundCommand command = new CompoundCommand();
        final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(model);

        for (final GNode node : model.getNodes()) {

            String s = "" + node.getId() + ": ";
            node.getConnectors().forEach(e -> {
                e.getConnections().forEach(e1 -> {
                    String s1 = s + e1.getTarget().getParent().getId();
                    System.out.println("UloSkinController.addConnector::: " + s1);
                });
            });

            if (skinLookup.lookupNode(node).isSelected()) {
//                if (countConnectors(node, position) < MAX_CONNECTOR_COUNT) {
                final GConnector connector = GraphFactory.eINSTANCE.createGConnector();
                connector.setType(type);
                connector.setId("gcntr_" + System.nanoTime());
                final EReference connectors = GraphPackage.Literals.GNODE__CONNECTORS;
                command.append(AddCommand.create(editingDomain, node, connectors, connector));
//                }
            }
        }

        if (command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    @Override
    public void addConnector(final Side position, final boolean input) {

        System.out.println("UloSkinController.addConnector: " + input);

        final String type = getType(position, input);

        final GModel model = graphEditor.getModel();
        final SkinLookup skinLookup = graphEditor.getSkinLookup();
        final CompoundCommand command = new CompoundCommand();
        final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(model);

        for (final GNode node : model.getNodes()) {

            if (skinLookup.lookupNode(node).isSelected()) {
                if (countConnectors(node, position) < MAX_CONNECTOR_COUNT) {

                    final GConnector connector = GraphFactory.eINSTANCE.createGConnector();
                    connector.setType(type);

                    final EReference connectors = GraphPackage.Literals.GNODE__CONNECTORS;
                    command.append(AddCommand.create(editingDomain, node, connectors, connector));
                }
            }
        }

        if (command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    @Override
    public void clearConnectors() {
        Commands.clearConnectors(graphEditor.getModel(), graphEditor.getSelectionManager().getSelectedNodes());
    }

    private int countConnectors(final GNode node, final Side side) {

        int count = 0;

        for (final GConnector connector : node.getConnectors()) {
            if (side.equals(DefaultConnectorTypes.getSide(connector.getType()))) {
                count++;
            }
        }

        return count;
    }

    private String getType(final Side position, final boolean input)
    {
        switch (position)
        {
            case TOP:
                if (input)
                {
                    return DefaultConnectorTypes.TOP_INPUT;
                }
                return DefaultConnectorTypes.TOP_OUTPUT;
            case RIGHT:
                if (input)
                {
                    return DefaultConnectorTypes.RIGHT_INPUT;
                }
                return DefaultConnectorTypes.RIGHT_OUTPUT;
            case BOTTOM:
                if (input)
                {
                    return DefaultConnectorTypes.BOTTOM_INPUT;
                }
                return DefaultConnectorTypes.BOTTOM_OUTPUT;
            case LEFT:
                if (input)
                {
                    return DefaultConnectorTypes.LEFT_INPUT;
                }
                return DefaultConnectorTypes.LEFT_OUTPUT;
        }
        return null;
    }

    @Override
    public void handlePaste(final SelectionCopier selectionCopier) {
        selectionCopier.paste((nodes, command) -> selectReferencedConnections(nodes));
    }

    @Override
    public void handleSelectAll() {
    	graphEditor.getSelectionManager().selectAll();
    }

    /**
     * Selects all connections that are referenced (i.e. connected to) the given nodes.
     *
     * @param nodes a list of graph nodes
     */
	private void selectReferencedConnections(final List<GNode> nodes) {

		nodes.stream()
			.flatMap(node -> node.getConnectors().stream())
			.flatMap(connector -> connector.getConnections().stream())
			.forEach(graphEditor.getSelectionManager()::select);
	}
}
