package daa.bench;

public enum InputType {
    RANDOM("random"),
    SORTED("sorted"),
    DUPLICATES("duplicates");

    private final String label;

    InputType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}