package thinkinjava.flow.limit;

import java.util.Date;

import thinkinjava.event.Event;

/**
 *
 * 限流异常导致的限流事件.
 */
public class LimitEvent implements Event {

    /** 限流时间 */
    private Date date;


    private LimitEvent(Builder builder) {
        setDate(builder.date);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public static final class Builder {

        private boolean syncRequest;
        private Date date;

        private Builder() {
        }


        public Builder syncRequest(boolean val) {
            syncRequest = val;
            return this;
        }

        public Builder date(Date val) {
            date = val;
            return this;
        }

        public LimitEvent build() {
            return new LimitEvent(this);
        }
    }
}

