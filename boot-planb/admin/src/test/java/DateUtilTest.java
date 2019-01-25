import cn.com.jcgroup.AdminApplication;
import cn.com.jcgroup.service.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: 日期工具类
 * User: sunchangqing
 * Date: 2017-06-23
 * Time: 上午11:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DateUtilTest {

    public static final Logger log = LoggerFactory.getLogger(DateUtilTest.class);

    @Test
    public void testDateI() {

        String formatDateStr = "2017.02";
        String year = StringUtils.substringBefore(formatDateStr, ".");
        String month = StringUtils.substringAfter(formatDateStr,".");
        String formatDate = year + "-" + month + "-01 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Date resDate = DateUtil.parseDate(formatDate, format);
        log.info("日期： " + resDate);
    }

    @Test
    public void testDateIII() {
        Date now = new Date();
        String format = "yyyy.MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateStr = simpleDateFormat.format(now);
        log.info(dateStr);
    }

    @Test
    public void testDateII() {
        String date = "2017.01.01";
        String[] time = date.split("\\.");
        String year = time[0];
        String month = time[1];
        String day = time[2];
        String formatDay = year + "-" + month + "-" + day + " 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Date days = DateUtil.parseDate(formatDay, format);
        log.info("日期： " + days);
    }
}
