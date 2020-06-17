package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import javafx.scene.Parent;

public interface IView {
    GraphEditorContainer getContainer();
    Parent getMain();
    void onDebugMessage(String message);
}
