package life.midorin.info.lifers.command.spec;

import life.midorin.info.lifers.util.ImmutableCollectors;

import java.util.Arrays;
import java.util.List;

public enum CommandSpec {

    /*PROTECT("/%s protect <args>");

    private final String usage;
    private final List<Argument> args;

    CommandSpec(String usage, PartialArgument... args) {
        this.usage = usage;
        this.args = args.length == 0 ? null : Arrays.stream(args)
                .map(builder -> {
                    String key = builder.id.replace(".", "").replace(' ', '-');
                    String description = ""; //Component.translatable.usage." + this.key() + ".description)
                    return new Argument(builder.name, builder.required, description);
                })
                .collect(ImmutableCollectors.toList());
    }

    CommandSpec(PartialArgument... args) {
        this(null, args);
    }

    public String description() {
        return "Component.translatable(.usage." + this.key() + ".description)";
    }

    public String usage() {
        return this.usage;
    }

    //public List<Argument> args() {
        return this.args;
    }

    public String key() {
        return name().toLowerCase().replace('_', '-');
    }

    private static PartialArgument arg(String id, String name, boolean required) {
        return new PartialArgument(id, name, required);
    }

    private static PartialArgument arg(String name, boolean required) {
        return new PartialArgument(name, name, required);
    }

    private static final class PartialArgument {
        private final String id;
        private final String name;
        private final boolean required;

        private PartialArgument(String id, String name, boolean required) {
            this.id = id;
            this.name = name;
            this.required = required;
        }
    }*/

}
