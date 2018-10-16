
## Lu-Rpc

Lu-Rpc 是个专为学习者准备的 RPC 框架, 初始架构非常简单, 可供初学者扩展和学习.

Lu 可以认为是中文世界的撸, 即撸 Rpc--- 造个 Rpc 轮子.

Lu-Rpc 架构图如下:


u-Rpc 的领域模型设计借鉴 Dubbo, 服务域没有明显的界限. 核心域就是 Invoker, 非常合适作为核心领域模型的接口.

会话域可以是Request,也可以是 Invocation. 这个问题不大.

## 快速开始

在 test 目录下: cn.thinkinjava.example

先启动 ServerTest, 再启动 ClientTest. 




