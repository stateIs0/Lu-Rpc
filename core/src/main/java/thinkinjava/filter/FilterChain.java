package thinkinjava.filter;

import java.util.List;

import thinkinjava.core.Invoker;

/**
 *
 *
 * @author 莫那·鲁道
 * @date 2018/10/14-下午10:02
 */
public class FilterChain {

    private List<Filter> filters;

    private Invoker invoker;


    public FilterChain(List<Filter> filters, Invoker invoker) {
        this.filters = filters;
        this.invoker = invoker;
    }

    public FilterChain(Invoker invoker) {
        this(LoadFilters.create().getFilters(), invoker);
    }

    public Invoker buildFilterChain() {
        // 最后一个
        Invoker last = invoker;

        for (int i = filters.size() - 1; i >= 0; i--) {
            last = new FilterWrapper(filters.get(i), last);
        }
        // 第一个
        return last;

    }
}
