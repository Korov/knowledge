将一个对象请求封装成一个对象，从而让你使用不同的请求吧客户端参数化，对请求排队或者记录请求日志，可以提供命令的撤销和恢复功能。

三个角色：
1. Receive接收者：该角色就是干活的角色，命令传递到这里是应该被执行的
2. Command命令角色：需要执行的所有命令都在这里
3. Invoker：调用者角色：接收到命令，并执行命令。