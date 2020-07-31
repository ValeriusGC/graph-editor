package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.GraphEditor;
import org.eclipse.emf.common.command.CommandStack;

public interface IPresenter {
    void setView(IView view);
    GraphEditor getGraphEditor();

    void buttonPressed();
    void openScheme();
    void saveScheme();
    void clearScheme();
    void exit();
    //
    void undo();
    void redo();
    //
    void addNode();
    void addNodeAnd();
    void addNodeOr();
    void addNodeTr();
    void addInputConnector();
    void addOutputConnector();
    //
    void showGrid();
    //
    void delete();
    void nodeSelectionChanged(boolean isSelected);

}
