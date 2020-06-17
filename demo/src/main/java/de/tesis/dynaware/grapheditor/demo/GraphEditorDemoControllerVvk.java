///*
// * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
// */
//package de.tesis.dynaware.grapheditor.demo;
//
//import de.tesis.dynaware.grapheditor.Commands;
//import de.tesis.dynaware.grapheditor.GraphEditor;
//import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
//import de.tesis.dynaware.grapheditor.core.skins.defaults.connection.SimpleConnectionSkin;
//import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
//import de.tesis.dynaware.grapheditor.demo.customskins.*;
//import de.tesis.dynaware.grapheditor.demo.customskins.titled.TitledSkinConstants;
//import de.tesis.dynaware.grapheditor.demo.customskins.tree.TreeConnectorValidator;
//import de.tesis.dynaware.grapheditor.demo.customskins.tree.TreeSkinConstants;
//import de.tesis.dynaware.grapheditor.demo.selections.SelectionCopier;
//import de.tesis.dynaware.grapheditor.demo.utils.AwesomeIcon;
//import de.tesis.dynaware.grapheditor.model.GModel;
//import de.tesis.dynaware.grapheditor.model.GNode;
//import de.tesis.dynaware.grapheditor.model.GraphFactory;
//import javafx.application.Platform;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.SetChangeListener;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Side;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
//import org.eclipse.emf.edit.domain.EditingDomain;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//public class GraphEditorDemoControllerVvk {
//
//    private static final String STYLE_CLASS_TITLED_SKINS = "titled-skins"; //$NON-NLS-1$
//    private final GraphEditor graphEditor = new DefaultGraphEditor();
//    private final SelectionCopier selectionCopier = new SelectionCopier(graphEditor.getSkinLookup(),
//            graphEditor.getSelectionManager());
//    private final GraphEditorPersistence graphEditorPersistence = new GraphEditorPersistence();
//    private final ObjectProperty<SkinController> activeSkinController = new SimpleObjectProperty<>() {
//        @Override
//        protected void invalidated() {
//            super.invalidated();
//            if (get() != null) {
//                get().activate();
//            }
//        }
//    };
//
//    /**
//     * Called by JavaFX when FXML is loaded.
//     */
//    public void initialize(GraphEditorContainer cnt) {
//        final GModel model = GraphFactory.eINSTANCE.createGModel();
//        graphEditor.getView().getStyleClass().add(STYLE_CLASS_TITLED_SKINS);
//        graphEditor.setModel(model);
//        cnt.setGraphEditor(graphEditor);
//        activeSkinController.addListener((observable, oldValue, newValue) -> {
//            checkConnectorButtonsToDisable();
//        });
//    }
//
//    public void load() {
//        graphEditorPersistence.loadFromFile(graphEditor);
//    }
//
//    public void addConnector() {
//        activeSkinController.get().addConnector(Side.LEFT, true);
//    }
//
//    private void checkConnectorButtonsToDisable() {
//        final boolean nothingSelected = graphEditor.getSelectionManager().getSelectedItems().stream()
//                .noneMatch(e -> e instanceof GNode);
////
////        if (titledSkinActive || treeSkinActive) {
////            addConnectorButton.setDisable(true);
////            clearConnectorsButton.setDisable(true);
////            connectorTypeMenu.setDisable(true);
////            connectorPositionMenu.setDisable(true);
////        } else if (nothingSelected) {
////            addConnectorButton.setDisable(true);
////            clearConnectorsButton.setDisable(true);
////            connectorTypeMenu.setDisable(false);
////            connectorPositionMenu.setDisable(false);
////        } else {
////            addConnectorButton.setDisable(false);
////            clearConnectorsButton.setDisable(false);
////            connectorTypeMenu.setDisable(false);
////            connectorPositionMenu.setDisable(false);
////        }
////
////        intersectionStyle.setDisable(treeSkinActive);
//    }
//
//}
