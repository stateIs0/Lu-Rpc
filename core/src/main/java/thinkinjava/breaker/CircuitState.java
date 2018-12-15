package thinkinjava.breaker;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class CircuitState implements Serializable {

    private static final long serialVersionUID = 3911162672779172131L;

    private String unique;

    private final AtomicReference<CircuitBreaker.State> state = new AtomicReference<>(CircuitBreaker.State.CLOSE);

    private AtomicLong startTime;

    public CircuitState(String unique) {
        this.unique = unique;
    }

    private CircuitState(Builder builder) {
        setUnique(builder.unique);
        startTime = builder.startTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public AtomicReference<CircuitBreaker.State> getState() {
        return state;
    }

    public void setState(CircuitBreaker.State state) {
        this.state.set(state);
    }

    public long getStartTime() {
        return startTime.get();
    }

    public void setStartTime(long startTime) {
        this.startTime.set(startTime);
    }

    @Override
    public String toString() {
        return "CircuitState{" +
            "unique='" + unique + '\'' +
            ", state=" + state +
            '}';
    }


    public static final class Builder {

        private String unique;
        private AtomicLong startTime;

        private Builder() {
        }

        public Builder unique(String val) {
            unique = val;
            return this;
        }

        public Builder startTime(AtomicLong val) {
            startTime = val;
            return this;
        }

        public CircuitState build() {
            return new CircuitState(this);
        }
    }
}


