package de.tesis.dynaware.grapheditor.demo.vvk;

import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.demo.utils.AwesomeIcon;
import io.reactivex.disposables.Disposable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.f;
import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.sayHelloSingle;

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

    public Node menuBar() {
        Menu menu = new Menu("Файл");
        MenuItem item1 = new MenuItem("Открыть схему");
//        item1.setOnAction(a -> ctrl.load());
        MenuItem item2 = new MenuItem("Item 2");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        menu.getItems().add(item1);
        menu.getItems().add(separator);
        menu.getItems().add(item2);


        MenuBar node = new MenuBar();
        node.getMenus().add(menu);
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
}
