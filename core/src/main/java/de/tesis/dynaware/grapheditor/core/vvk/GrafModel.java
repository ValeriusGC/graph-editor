package de.tesis.dynaware.grapheditor.core.vvk;

/*
https://dzone.com/articles/singleton-making-more-effective
SingletonClassMono pattern.
Создается объект уровня приложения, который содержит всю актуальную логику
текущей схемы.
 */
public class GrafModel {
    public static class State {
        public static final String appVersion;
        public static final String stateName;
        public static final NodeModels nodeModels;
        public static final ModelNodes modelNodes;
        static {
            appVersion = String.format("%d.%d.%d", 0,1,236);
            stateName = String.format("%d", System.currentTimeMillis());
            nodeModels = new NodeModels();
            modelNodes = new ModelNodes();
        }
    }
}
