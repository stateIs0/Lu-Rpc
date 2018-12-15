package thinkinjava.breaker;

import java.util.Date;

import thinkinjava.event.Event;
import thinkinjava.util.SimpleDateFormatUtil;

/**
 *
 * 电路恢复事件.
 */
public class CircuitRenewEvent implements Event {

    private String unique;

    private Date endTime;


    public CircuitRenewEvent(String unique, Date endTime) {
        this.unique = unique;
        this.endTime = endTime;
    }

    private CircuitRenewEvent(Builder builder) {
        setUnique(builder.unique);
        setEndTime(builder.endTime);
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CircuitRenewEvent{" +
            "unique='" + unique + '\'' +
            ", endTime=" + SimpleDateFormatUtil.format1(endTime) +
            '}';
    }

    public static final class Builder {

        private String unique;
        private Date endTime;

        private Builder() {
        }

        public Builder unique(String val) {
            unique = val;
            return this;
        }

        public Builder endTime(Date val) {
            endTime = val;
            return this;
        }

        public CircuitRenewEvent build() {
            return new CircuitRenewEvent(this);
        }
    }
}

