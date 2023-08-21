import java.util.List;
import java.util.function.Function;

public enum Rule {

    // A PARTICLE SURVIVES IF IT HAS 2 OR 3 NEIGHBORS
    // A PARTICLE IS BORN IF IT HAS 3 NEIGHBORS
    // Original rule
    CONWAY((parameters) -> {
        if (parameters.alive) {
            return (parameters.neighbors.size() == (2 * parameters.radius)) || (parameters.neighbors.size() == (3 * parameters.radius));
        }
        return parameters.neighbors.size() == (3 * parameters.radius);
    }),

    // A PARTICLE SURVIVES IF IT HAS 1, 2 OR 3 NEIGHBORS
    // A PARTICLE IS BORN IF IT HAS 3 NEIGHBORS
    // It tends to create maze-like patterns and interesting structures.
    MAZE((parameters) -> {
        if (parameters.alive) {
            return (parameters.neighbors.size() >= (parameters.radius)) && (parameters.neighbors.size() <= (3 * parameters.radius));
        }
        return parameters.neighbors.size() == (3 * parameters.radius);
    }),

    // A PARTICLE SURVIVES IF IT HAS 4, 5, 6, 7 OR 8 NEIGHBORS
    // A PARTICLE IS BORN IF IT HAS 3 NEIGHBORS
    // Can generate branching structures reminiscent of coral reefs
    CORAL((parameters) -> {
        if (parameters.alive) {
            return (parameters.neighbors.size() >= (4 * parameters.radius)) && (parameters.neighbors.size() <= (8 * parameters.radius));
        }
        return (parameters.neighbors.size() == (3 * parameters.radius));
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
