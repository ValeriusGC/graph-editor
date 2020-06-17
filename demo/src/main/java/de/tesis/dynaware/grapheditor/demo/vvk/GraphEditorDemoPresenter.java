package de.tesis.dynaware.grapheditor.demo.vvk;

import io.reactivex.disposables.Disposable;

import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.f;
import static de.tesis.dynaware.grapheditor.demo.vvk.My_kotKt.sayHelloSingle;

public class GraphEditorDemoPresenter implements IPresenter {
    private final GraphEditorDemoModel model;
    private IView view;
    final Model m = new Model();

    public GraphEditorDemoPresenter(GraphEditorDemoModel model) {
        this.model = model;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
        model.ctrl.initialize(view.getContainer());
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
}

