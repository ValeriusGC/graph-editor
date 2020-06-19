package de.tesis.dynaware.grapheditor.core.vvk;

public abstract class TypedValue<T> {
    public final T value;
    protected TypedValue(T value) {
        this.value = value;
    }

    public static class Id extends TypedValue<String> {
        public Id(String value) {
            super(value);
        }
    }
}
