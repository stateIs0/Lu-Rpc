package thinkinjava.breaker;

import java.util.Date;

import thinkinjava.event.Event;
import thinkinjava.util.SimpleDateFormatUtil;

/**
 *
 * 熔断事件.
 *
 */
public class CircuitBreakerEvent implements Event {

    private String unique;

    private Date startTime;

    private CircuitBreakerEvent(Builder builder) {
        setUnique(builder.unique);
        setStartTime(builder.startTime);
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "CircuitBreakerEvent{" +
            "unique='" + unique + '\'' +
            ", startTime=" + SimpleDateFormatUtil.format1(startTime) +
            '}';
    }

    public static final class Builder {

        private String unique;
        private Date startTime;

        private Builder() {
        }

        public Builder unique(String val) {
            unique = val;
            return this;
        }

        public Builder startTime(Date val) {
            startTime = val;
            return this;
        }

        public CircuitBreakerEvent build() {
            return new CircuitBreakerEvent(this);
        }
    }
}
