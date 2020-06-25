package de.tesis.dynaware.grapheditor.core.vvk;

/*
https://dzone.com/articles/singleton-making-more-effective
SingletonClassMono pattern.
Создается объект уровня приложения, который содержит всю актуальную логику
текущей схемы.
 */
public class GrafModel {
    public static class State {
        public static final String stateName;
        public static final NodeModels nodeModels;
        public static final ModelNodes modelNodes;
        static {
            stateName = String.format("%d", System.currentTimeMillis());
            nodeModels = new NodeModels();
            modelNodes = new ModelNodes();
        }
    }
}
