package de.tesis.dynaware.grapheditor.demo.vvk;

public interface IPresenter {
    void setView(IView view);
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
    void addConnector();
    //
    void delete();
    void nodeSelectionChanged(boolean isSelected);
}
