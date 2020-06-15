/**
 * Sample graph editor implementation
 */
module fx.graph.editor.demo
{
    requires javafx.fxml;
    requires fx.graph.editor.api;
    requires fx.graph.editor.core;
    requires org.eclipse.emf.ecore;
    requires org.eclipse.emf.common;
    requires org.eclipse.emf.ecore.xmi;
    requires org.eclipse.emf.edit;
    requires org.slf4j;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires io.reactivex.rxjava2;
    requires kotlinx.coroutines.rx2;

    exports de.tesis.dynaware.grapheditor.demo to javafx.graphics, javafx.fxml;
    opens de.tesis.dynaware.grapheditor.demo to javafx.fxml, javafx.base;
}
