import cn.com.jcgroup.service.util.NumberUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: sunchangqing
 * Date: 2017-06-30
 * Time: 上午10:35
 */
public class NumberTest {

    public static Logger logger = LoggerFactory.getLogger(NumberTest.class);

    @Test
    public void randomNumber() {
        String randomNumber = getStringRandom(7);
        logger.info(randomNumber);
        for (int i = 0; i < 3; i++) {
            logger.info(i + "");
        }
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Test
    public void testNumberUtils() {
        Long resNumber = NumberUtil.convertToPointWithUnit("12.23", 1L);
        logger.info(resNumber + "");
    }
}
