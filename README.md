# express-query
A express query back end

Java文件夹的 ExpressQuery.java 是对接快递鸟API，EBusinessID（用户ID）和AppKey在官网注册后即可获取，
有个免费的套餐只支持申通、圆通、中通，每天500次。

官方demo：http://www.kdniao.com/documents-demo

对官方demo的改动：

1.直接使用Java的类库进行base64编码

2.对输入的单号进行简单的中文检测，对返回的json字符串做了一点格式化输出。
