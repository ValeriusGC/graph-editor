package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.utils.AwesomeIcon;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class GraphEditorDemoView implements IView {

    IPresenter presenter;
    GraphEditorContainer container;


    private AnchorPane main;

    public GraphEditorDemoView(IPresenter presenter) {
        this.presenter = presenter;
        container = new GraphEditorContainer();
        container.setMaxHeight(Integer.MAX_VALUE);
        container.setMaxWidth(Integer.MAX_VALUE);
        presenter.setView(this);
    }

    @Override
    public GraphEditorContainer getContainer() {
        return container;
    }

    @Override
    public Parent getMain() {
        main = new AnchorPane();
        //final AnchorPane ap = new AnchorPane();
        final Node b = vbox();
        AnchorPane.setRightAnchor(b, 0.0);
        AnchorPane.setTopAnchor(b, 0.0);
        AnchorPane.setLeftAnchor(b, 0.0);
        AnchorPane.setBottomAnchor(b, 0.0);
        final ToggleButton btn = btn();
//        btn.getStyleClass().add("custom-button");
        btn.getStyleClass().add("minimap-button");
        btn.setGraphic(AwesomeIcon.MAP.node());
//        btn.getStyleClass().add("button1");
        btn.setFocusTraversable(false);
        AnchorPane.setRightAnchor(btn, 10.0);
        AnchorPane.setTopAnchor(btn, 4.0);
//        btn.setGraphic(AwesomeIcon.MAP.node());
        main.getChildren().addAll(b, btn);
//        return ap;
        return main;
    }

    public Parent vbox() {
        final VBox b = new VBox();
        b.fillWidthProperty().setValue(true);
        b.getChildren().addAll(menuBar(), container);
        VBox.setVgrow(container, Priority.ALWAYS);
        return b;
    }


    MenuItem addConnector;

    public Node menuBar() {
        Menu file = new Menu("Файл");
        MenuItem open = new MenuItem("Открыть схему");
        open.setOnAction(v -> presenter.openScheme());
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        file.getItems().add(open);

        MenuItem save = new MenuItem("Сохранить схему");
        save.setOnAction(v -> presenter.saveScheme());
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        //save.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        file.getItems().add(save);

        MenuItem clear = new MenuItem("Очистить все");
        clear.setOnAction(v -> presenter.clearScheme());
        file.getItems().add(clear);
//        item1.setOnAction(a -> ctrl.load());

        SeparatorMenuItem separator = new SeparatorMenuItem();
        file.getItems().add(separator);

        MenuItem exit = new MenuItem("Выход");
        exit.setOnAction(v -> presenter.exit());
        exit.setAccelerator(KeyCombination.keyCombination("Alt+X"));
        file.getItems().add(exit);

        //
        Menu editor = new Menu("Редактор");
        MenuItem undo = new MenuItem("Отмена");
        undo.setOnAction(v -> presenter.undo());
        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        editor.getItems().add(undo);
        MenuItem redo = new MenuItem("Повтор");
        redo.setOnAction(v -> presenter.redo());
        redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
        editor.getItems().add(redo);

        MenuItem delete = new MenuItem("Удалить");
        delete.setOnAction(v -> presenter.delete());
        delete.setAccelerator(KeyCombination.keyCombination("Delete"));
        editor.getItems().add(delete);

        //
        Menu actions = new Menu("Действия");
        MenuItem addNode = new MenuItem("+ Узел");
        addNode.setOnAction(v -> presenter.addNode());
        addNode.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        actions.getItems().add(addNode);

        addConnector = new MenuItem("Добавить коннектор");
        addConnector.setOnAction(v -> presenter.addConnector());
        addConnector.setDisable(true);
//        addConnector.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        actions.getItems().add(addConnector);

        MenuBar node = new MenuBar();
        node.getMenus().addAll(file, editor, actions);
        return node;
    }

    public ToggleButton btn() {
        ToggleButton btn = new ToggleButton();
//        InputStream is = Property.class
//                .getResourceAsStream("/de/tesis/dynaware/grapheditor/demo/and.png");
//        Image and = new Image(is,30, 40, false, true);
//        ImageView imageView = new ImageView(and);
//        btn.setGraphic(imageView);
//
//        final URL res = Property.class
//                .getResource("/de/tesis/dynaware/grapheditor/demo/demo.css");
//        btn.getStylesheets().add(res.toExternalForm());
//
//        //btn.setGraphic(AwesomeIcon.MAP.node());
//        btn.getStyleClass().add("minimapButton");
//        btn.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

//        btn.setStyle("minimapButton");
//        btn.setText("Say 'Hello World'");

//        final Integer v1 = f(10);
//        final String v2 = f("LALALA");
//        final String[] s = {"-"};
//        final Disposable d = m.getObs().subscribe(System.out::println);
//        btn.setOnAction(event -> {
//            System.out.println("BEGIN");
//            m.start();
//            System.out.printf("Hello World! %d, %s, %s\n", v1, v2,
//                    sayHelloSingle().blockingGet());
//            System.out.println("END");
//            d.dispose();
//        });
//        System.out.println("-----");

        btn.setOnAction(event -> presenter.buttonPressed());
        return btn;
    }

    @Override
    public void onDebugMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onNodeSelectionChanged(boolean isSelected) {
        System.out.println("GraphEditorDemoView.onNodeSelectionChanged: " + isSelected);
        if(addConnector != null){
            addConnector.setDisable(!isSelected);
        }
    }
}
