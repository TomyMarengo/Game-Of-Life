import java.util.List;
import java.util.function.Function;

public enum Rule {
    CONWAY((parameters) -> {
        if (parameters.alive) {
            return (parameters.neighbors.size() == (2 * parameters.radius)) || (parameters.neighbors.size() == (3 * parameters.radius));
        }
        return parameters.neighbors.size() == 3 * parameters.radius;
    }),

    EMPTY((parameters) -> {
        if (parameters.alive) {
            return parameters.neighbors.isEmpty();
        }
        return !parameters.neighbors.isEmpty();
    }),

    ;

    public static class RuleParameters {
        public List<Coordinates> neighbors;
        public boolean alive;
        public int radius;

        public RuleParameters(List<Coordinates> neighbors, boolean alive, int radius) {
            this.neighbors = neighbors;
            this.alive = alive;
            this.radius = radius;
        }
    }

    private final Function<RuleParameters, Boolean> applyFunction;

    Rule(Function<RuleParameters, Boolean> applyFunction) {
        this.applyFunction = applyFunction;
    }

    public boolean apply(RuleParameters parameters) {
        return applyFunction.apply(parameters);
    }
}
