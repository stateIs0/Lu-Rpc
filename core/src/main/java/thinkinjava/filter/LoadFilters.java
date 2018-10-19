package thinkinjava.filter;

import java.util.ArrayList;
import java.util.List;

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
        return filterList;
    }

}
