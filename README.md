# express-query
A express query back end

使用方式：运行Java目录下的QueryImpl.java，按提示输入要调用的API，快递公司和单号。

一、Java文件夹里的 KdniaoQueryAPI.java 是对接快递鸟API，EBusinessID（用户ID）和AppKey在官网注册后即可获取，
有个免费的套餐只支持申通、圆通、中通，每天500次。

快递鸟官方demo：http://www.kdniao.com/documents-demo

快递公司编码：http://www.kdniao.com/documents

对官方demo的改动：

1.直接使用Java的类库进行base64编码

2.对输入的单号进行简单的中文检测，对返回的json字符串做了一点格式化输出。



二、Java文件夹里的 HuoBanQueryAPI.java 是对接伙伴数据资源的API，id可以找客服申请测试用的。

所支持的快递公司编码在官网首页：http://q.kdpt.net/index.html

官网也有给出其API的调用方式，比调用快递鸟的简单，请求参数不需要加密编码。
