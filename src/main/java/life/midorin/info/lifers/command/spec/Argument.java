package life.midorin.info.lifers.command.spec;

public class Argument {
    private final String name;
    private final boolean required;
    private final String description;

    Argument(String name, boolean required, String description) {
        this.name = name;
        this.required = required;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public boolean isRequired() {
        return this.required;
    }

    public String getDescription() {
        return this.description;
    }

    public String asPrettyString() {
        return (this.required ? "Message.REQUIRED_ARGUMENT" : "Message.OPTIONAL_ARGUMENT");
    }
}
