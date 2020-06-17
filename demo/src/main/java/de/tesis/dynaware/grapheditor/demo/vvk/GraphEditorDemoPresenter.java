package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.core.skins.defaults.connection.SimpleConnectionSkin;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.GraphEditorPersistence;
import de.tesis.dynaware.grapheditor.demo.customskins.DefaultSkinController;
import de.tesis.dynaware.grapheditor.demo.customskins.SkinController;
import de.tesis.dynaware.grapheditor.demo.selections.SelectionCopier;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;
import io.reactivex.disposables.Disposable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.SetChangeListener;
import javafx.geometry.Side;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;

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


    /**
     * Called by JavaFX when FXML is loaded.
     */
    public void initialize(GraphEditorContainer cnt) {

        final GModel model = GraphFactory.eINSTANCE.createGModel();
        graphEditor.getView().getStyleClass().add(STYLE_CLASS_TITLED_SKINS);

        graphEditor.setModel(model);
        cnt.setGraphEditor(graphEditor);

        setDetouredStyle();

        defaultSkinController = new DefaultSkinController(graphEditor, view.getContainer());

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
    }

    @Override
    public void clearScheme() {
        System.out.println("GraphEditorDemoPresenter.clearScheme");
    }

    @Override
    public void exit() {
        System.out.println("GraphEditorDemoPresenter.exit");
    }

    @Override
    public void undo() {
        System.out.println("GraphEditorDemoPresenter.undo");
    }

    @Override
    public void redo() {
        System.out.println("GraphEditorDemoPresenter.redo");
    }

    @Override
    public void addConnector() {
        activeSkinController.get().addConnector(Side.LEFT, true);
//        ctrl.addConnector();
    }

    @Override
    public void nodeSelectionChanged(boolean isSelected) {
        view.onNodeSelectionChanged(isSelected);
    }
}

