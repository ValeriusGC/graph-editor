package de.tesis.dynaware.grapheditor.demo.vvk;

import io.reactivex.disposables.Disposable;

import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.f;
import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.sayHelloSingle;

public class GraphEditorDemoPresenter implements IPresenter {
    private final GraphEditorDemoModel model;
    private GraphEditorDemoView view;
    final Model m = new Model();

    public GraphEditorDemoPresenter(GraphEditorDemoModel model) {
        this.model = model;
    }

    void setView(GraphEditorDemoView view) {
        this.view = view;
        model.ctrl.initialize(view.container);
    }


    @Override
    public void buttonPressed() {
        final Integer v1 = f(10);
        final String v2 = f("LALALA");
        final String[] s = {"-"};
        final Disposable d = m.getObs().subscribe(System.out::println);
        view.onDebugMessage("BEGIN");
        m.start();
        view.onDebugMessage(String.format("Hello World! %d, %s, %s\n", v1, v2,
                sayHelloSingle().blockingGet()));
        view.onDebugMessage("END");
        d.dispose();

//        btn.setOnAction(event -> {
//            System.out.println("BEGIN");
//            m.start();
//            System.out.printf("Hello World! %d, %s, %s\n", v1, v2,
//                    sayHelloSingle().blockingGet());
//            System.out.println("END");
//            d.dispose();
//        });
//        System.out.println("-----");
//        return btn;
//
//        view.onDebugMessage("OK");
    }
}

