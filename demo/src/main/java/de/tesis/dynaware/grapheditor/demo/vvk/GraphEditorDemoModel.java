package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.GraphEditorDemoControllerVvk;

public class GraphEditorDemoModel {
    final GraphEditorDemoControllerVvk ctrl;
//    private final GraphEditorContainer cnt;


    public GraphEditorDemoModel() {
//        cnt = new GraphEditorContainer();
        ctrl = new GraphEditorDemoControllerVvk();
//        ctrl.initialize(cnt);
    }
}
