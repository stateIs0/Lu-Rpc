目前本人正在根据规范进行重构....

## 开发者须知
开发者在修改、扩展框架时，需要阅读开发规范：
1. [框架设计原则（梁飞）](http://thinkinjava.cn/2018/10/%E6%A1%86%E6%9E%B6%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99-%E6%A2%81%E9%A3%9E/)
2. [汇总梁飞博客设计文章](http://thinkinjava.cn/2018/10/汇总梁飞博客设计文章/)
3. [框架设计上的十点基本常识](http://thinkinjava.cn/2018/10/框架设计上的十点基本常识/)
4. [Java-并发编程常识-by-梁飞](http://thinkinjava.cn/2018/04/Java-并发编程常识-by-梁飞/)

## Lu-Rpc

Lu-Rpc 是个专为学习者准备的 RPC 框架, 初始架构非常简单, 可供初学者扩展和学习.

Lu 可以认为是中文世界的撸, 即撸 Rpc--- 造个 Rpc 轮子.

Lu-Rpc 架构图如下:

![](https://upload-images.jianshu.io/upload_images/4236553-a2bf8ddf71d1a993.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



Lu-Rpc 的领域模型设计借鉴 Dubbo, 服务域没有明显的界限. 核心域就是 Invoker, 非常合适作为核心领域模型的接口.

会话域可以是Request,也可以是 Invocation. 这个问题不大.

## 快速开始

进入 example 模块

先启动 ServerTest, 再启动 ClientTest. 


## 待开发功能
0. 自研基于 Raft 协议的注册中心
1. Lu-Service-mesh
2. 负载均衡
3. 异常处理
4. 异步调用，feature 调用。oneway 调用
5. 连接管理，心跳管理
6. 服务监控
7. 服务优雅下线
8. 无缝支持 SpringBoot
9. 服务故障转移
10. 服务链路追踪
11. 支持分布式配置中心（自研或使用第三方）
12. 自研网络通信框架。定义自己的 RPC 协议。
13. 支持零拷贝序列化。
14. 使用字节码增强，减少反射调用开销。
15. 增加扩展点机制。


欢迎提交 PR，issue。


