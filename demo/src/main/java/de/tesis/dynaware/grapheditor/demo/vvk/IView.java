package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import javafx.scene.Parent;
import org.eclipse.emf.common.command.CommandStack;

public interface IView {
    GraphEditorContainer getContainer();
    Parent getMain();
    void onDebugMessage(String message);
    void onNodeSelectionChanged(boolean isSelected);

    void onCommandStackChanged(CommandStack stack);
}
