package jcgroup;

import cn.com.jcgroup.service.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsTest {

    @Test
    public void testDate() throws ParseException {
//        String format = "yyyy.MM.dd";
//        String formatDate = DateUtil.formatDate(format, new Date());
//        System.out.println(formatDate);
//
//        String dateForm = "yyyy-MM-dd";
//        Date startDate = DateUtil.covertToDate(dateForm, "2017-02-01");
//        String en = DateUtil.formatDate(dateForm, startDate);
//        System.out.println(en);
//        Date endDate = DateUtil.covertToDate(dateForm, "2013.12" + ".01");

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        System.out.print(dateFormater.format(date));
    }
}
