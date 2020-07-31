package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.Commands;
import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.core.skins.defaults.connection.SimpleConnectionSkin;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.GraphEditorDemoVvk;
import de.tesis.dynaware.grapheditor.demo.GraphEditorPersistence;
import de.tesis.dynaware.grapheditor.demo.customskins.DefaultSkinController;
import de.tesis.dynaware.grapheditor.demo.customskins.MySchemeSkinController;
import de.tesis.dynaware.grapheditor.demo.customskins.SkinController;
import de.tesis.dynaware.grapheditor.demo.selections.SelectionCopier;
import de.tesis.dynaware.grapheditor.demo.vvk.ulo.UloSkinConstants;
import de.tesis.dynaware.grapheditor.demo.vvk.ulo.UloSkinController;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;
import io.reactivex.disposables.Disposable;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.SetChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.WindowEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.f;
import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.sayHelloSingle;

public class GraphEditorDemoPresenter implements IPresenter {
    private final GraphEditorDemoModel model;
    private IView view;
    final Model m = new Model();

    //
    private DefaultSkinController defaultSkinController;
    private static final String STYLE_CLASS_TITLED_SKINS = "titled-skins"; //$NON-NLS-1$

    private final GraphEditor graphEditor = new DefaultGraphEditor();
    private UloSkinController uloSkinController;
    //private MySchemeSkinController mySchemeSkinController;
    private final SelectionCopier selectionCopier = new SelectionCopier(graphEditor.getSkinLookup(),
            graphEditor.getSelectionManager());
    private final GraphEditorPersistence graphEditorPersistence = new GraphEditorPersistence();
    private final ObjectProperty<SkinController> activeSkinController = new SimpleObjectProperty<>() {
        @Override
        protected void invalidated() {
            super.invalidated();
            if (get() != null) {
                get().activate();
            }
        }
    };
    public void setDetouredStyle() {
        final Map<String, String> customProperties = graphEditor.getProperties().getCustomProperties();
        customProperties.put(SimpleConnectionSkin.SHOW_DETOURS_KEY, Boolean.toString(true));
        graphEditor.reload();
    }

    @Override
    public GraphEditor getGraphEditor() {
        return graphEditor;
    }

    /**
     * Called by JavaFX when FXML is loaded.
     */
    public void initialize(GraphEditorContainer cnt) {

        graphEditor.modelProperty().addListener((w, o, n) -> Commands.getEditingDomain(n).getCommandStack().addCommandStackListener(eventObject -> {
            view.onCommandStackChanged(Commands.getEditingDomain(n).getCommandStack());
        }));

        final GModel model = GraphFactory.eINSTANCE.createGModel();
        graphEditor.getView().getStyleClass().add(STYLE_CLASS_TITLED_SKINS);

        graphEditor.setModel(model);
        cnt.setGraphEditor(graphEditor);

        setDetouredStyle();

//        defaultSkinController = new DefaultSkinController(graphEditor, view.getContainer());
        //mySchemeSkinController = new MySchemeSkinController(graphEditor, view.getContainer());
        uloSkinController = new UloSkinController(graphEditor, view.getContainer());
        //activeSkinController.set(mySchemeSkinController);
        activeSkinController.set(uloSkinController);
//        activeSkinController.set(defaultSkinController);
        activeSkinController.addListener((observable, oldValue, newValue) -> {
            checkConnectorButtonsToDisable();
        });

        graphEditor.modelProperty().addListener((w, o, n) -> selectionCopier.initialize(n));
        selectionCopier.initialize(model);

        final SetChangeListener<? super EObject> selectedNodesListener = change -> checkConnectorButtonsToDisable();
        graphEditor.getSelectionManager().getSelectedItems().addListener(selectedNodesListener);
        checkConnectorButtonsToDisable();

    }

    public void load() {
        graphEditorPersistence.loadFromFile(graphEditor);
    }

    public void save() {
        graphEditorPersistence.saveToFile(graphEditor);
    }

    private void checkConnectorButtonsToDisable() {
        final boolean nothingSelected = graphEditor.getSelectionManager().getSelectedItems().stream()
                .noneMatch(e -> e instanceof GNode);
        nodeSelectionChanged(!nothingSelected);

//
//        if (titledSkinActive || treeSkinActive) {
//            addConnectorButton.setDisable(true);
//            clearConnectorsButton.setDisable(true);
//            connectorTypeMenu.setDisable(true);
//            connectorPositionMenu.setDisable(true);
//        } else if (nothingSelected) {
//            addConnectorButton.setDisable(true);
//            clearConnectorsButton.setDisable(true);
//            connectorTypeMenu.setDisable(false);
//            connectorPositionMenu.setDisable(false);
//        } else {
//            addConnectorButton.setDisable(false);
//            clearConnectorsButton.setDisable(false);
//            connectorTypeMenu.setDisable(false);
//            connectorPositionMenu.setDisable(false);
//        }
//
//        intersectionStyle.setDisable(treeSkinActive);
    }


    public GraphEditorDemoPresenter(GraphEditorDemoModel model) {
        this.model = model;
//        ctrl = new GraphEditorDemoControllerVvk();
    }

    @Override
    public void setView(IView view) {
        this.view = view;
        initialize(view.getContainer());

    }

    @Override
    public void buttonPressed() {
        final Integer v1 = f(10);
        final String v2 = f("LALALA");
        final Disposable d = m.getObs().subscribe(System.out::println);
        view.onDebugMessage("BEGIN");
        m.start();
        view.onDebugMessage(String.format("Hello World! %d, %s, %s\n", v1, v2,
                sayHelloSingle().blockingGet()));
        view.onDebugMessage("END");
        d.dispose();
    }

    @Override
    public void openScheme() {
        System.out.println("GraphEditorDemoPresenter.openScheme");
        load();
    }

    @Override
    public void saveScheme() {
        System.out.println("GraphEditorDemoPresenter.saveScheme");
        save();
    }

    @Override
    public void clearScheme() {
        System.out.println("GraphEditorDemoPresenter.clearScheme");
    }

    private boolean handleUnsavedSchema(WindowEvent event) {
        boolean itCanBePersisted = Optional.ofNullable(graphEditor.getModel())
                .map(v -> v.getNodes())
                .map(nodes -> !nodes.isEmpty())
                .orElse(false);

        boolean updated = Optional.ofNullable(graphEditor.getModel())
                .map(GModel::isUpdated)
                .orElse(false);

        System.out.println("GraphEditorDemoPresenter.handleUnsavedSchema " + itCanBePersisted + ", " + updated);
        if (itCanBePersisted && updated) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("You have one unsaved schema");
            alert.setContentText("Do you want to save the open schema before leaving?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yes, no, cancel);

            ButtonType result = alert.showAndWait().orElse(null);
            if (result == yes) {
                graphEditorPersistence.saveToFile(graphEditor);
            } else if (result == no) {
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
            return true;
        }
        return false;
    }

    @Override
    public void exit() {
        System.out.println("GraphEditorDemoPresenter.exit");
        boolean saved = handleUnsavedSchema(new WindowEvent(
                GraphEditorDemoVvk.primary,
                WindowEvent.WINDOW_CLOSE_REQUEST));

        if (!saved) {
            Platform.exit();
        }

    }

    @Override
    public void undo() {
        Commands.undo(graphEditor.getModel());
    }

    @Override
    public void redo() {
        Commands.redo(graphEditor.getModel());
    }

    @Override
    public void addNode() {
        final Region v = graphEditor.getView();
//        activeSkinController.get().addNode(v.getLocalToSceneTransform().getMxx());
        final UloSkinController c = (UloSkinController) activeSkinController.get();
        c.setNodeType(UloSkinConstants.ULO_NODE_NEWNEW);
        c.addNode(graphEditor.getView().getLocalToSceneTransform().getMxx());
    }

    @Override
    public void addNodeAnd() {
        final Region v = graphEditor.getView();
//        activeSkinController.get().addNode(v.getLocalToSceneTransform().getMxx());
        final UloSkinController c = (UloSkinController) activeSkinController.get();
        c.setNodeType(UloSkinConstants.ULO_NODE_AND);
        c.addNode(graphEditor.getView().getLocalToSceneTransform().getMxx());
    }

    @Override
    public void addNodeOr() {
        final Region v = graphEditor.getView();
//        activeSkinController.get().addNode(v.getLocalToSceneTransform().getMxx());
        final UloSkinController c = (UloSkinController) activeSkinController.get();
        c.setNodeType(UloSkinConstants.ULO_NODE_OR);
        c.addNode(graphEditor.getView().getLocalToSceneTransform().getMxx());

    }

    @Override
    public void addNodeTr() {
        final Region v = graphEditor.getView();
//        activeSkinController.get().addNode(v.getLocalToSceneTransform().getMxx());
        final UloSkinController c = (UloSkinController) activeSkinController.get();
        c.setNodeType(UloSkinConstants.ULO_NODE_TR);
        c.addNode(graphEditor.getView().getLocalToSceneTransform().getMxx());
    }

    @Override
    public void addInputConnector() {
        activeSkinController.get().addConnector(Side.LEFT, true);
//        ctrl.addConnector();
    }

    @Override
    public void addOutputConnector() {
        activeSkinController.get().addConnector(Side.RIGHT, false);
    }

    @Override
    public void showGrid() {
        //graphEditor.getProperties().gridVisibleProperty().bind();
    }

    @Override
    public void delete() {
        final List<EObject> selection = new ArrayList<>(graphEditor.getSelectionManager().getSelectedItems());
        graphEditor.delete(selection);
    }

    @Override
    public void nodeSelectionChanged(boolean isSelected) {
        view.onNodeSelectionChanged(isSelected);
    }
}

