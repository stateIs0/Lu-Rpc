package thinkinjava.filter;

import java.util.ArrayList;
import java.util.List;

import thinkinjava.breaker.CircuitBreakerFilter;
import thinkinjava.breaker.CircuitBreakerStatFilter;
import thinkinjava.flow.limit.LimitFilter;

/**
 * TODO 自动加载过滤器
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:43
 */
public final class LoadFilters {

    private List<Filter> filterList = new ArrayList<Filter>();

    private LoadFilters() {
    }

    public static LoadFilters create() {

        return new LoadFilters();
    }

    public List<Filter> getFilters() {
        this.filterList.add(new ExampleFilter());
        this.filterList.add(new ExampleFilter2());
        this.filterList.add(CircuitBreakerFilter.getInstance());
        this.filterList.add(LimitFilter.getInstance());
        this.filterList.add(CircuitBreakerStatFilter.getInstance());
        return filterList;
    }

}
