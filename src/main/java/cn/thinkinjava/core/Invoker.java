package cn.thinkinjava.core;

import cn.thinkinjava.rpc.Request;
import cn.thinkinjava.rpc.Response;

/**
 * @author 莫那·鲁道
 * @date 2018/10/15-下午3:59
 */
public interface Invoker {

    /**
     * Remote  Calls
     * @param req 请求: 会话域
     * @return 结果:包装真正结果.
     */
    Response invoke(Request req);

    /**
     * 获取该服务的配置
     *
     * @return 获取该服务的配置
     */
    ServiceConfig getConfig();
}
