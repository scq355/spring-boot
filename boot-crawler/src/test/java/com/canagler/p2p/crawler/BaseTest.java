package com.canagler.p2p.crawler;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.repository.WebInfoRepository;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by scq on 2018-04-12 09:48:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

	@Autowired
	private WebInfoRepository webInfoRepository;

	@Test
	public void testListIsEmpty() {
	 	List<WebInfo> webInfoList = webInfoRepository.findAllByProcessStatus(5); // webInfoList不为空，size>=0
		System.out.println(webInfoList.toString());
		for (WebInfo info : webInfoList) {
			System.out.println(info.toString());
		}
	}

	@Test
	public void testDate() {
		System.out.println(DateFormatUtils.format(new Date(), Constants.DATE_TIME_PATTERN));
	}


	@Test
	public void getHost() throws MalformedURLException {
		java.net.URL  url = new  java.net.URL("https://admin.qianshiwang.com/finance/refund");

		String host = url.getHost();// 获取主机名

		System.out.println("host:"+host);// 结果 blog.csdn.net
	}

	@Test
	public void testListMap() {
		Map<String, String> map = new HashMap<>();
		map.put("1231", "asda3");
		map.put("1244", "asda1");
		map.put("1256", "asda2");
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			System.out.println(key);
		}

	}

	@Test
	public void testTime() {
		try {
			while (true) {
				System.out.println(getSecondTimestamp(new Date()));
				Thread.sleep(2000L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取精确到秒的时间戳
	 * @return
	 */
	public int getSecondTimestamp(Date date){
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0,length-3));
		} else {
			return 0;
		}
	}

	@Test
	public void testAdd() {
		BigDecimal num1 = new BigDecimal(23261653.81);
		BigDecimal num2 = new BigDecimal(13803997.43);
		BigDecimal num3 = new BigDecimal(16836724.2);
		BigDecimal num4 = new BigDecimal(14770536.79);
		BigDecimal num5 = new BigDecimal(15639756.62);
		BigDecimal num6 = new BigDecimal(3070901.26);

		BigDecimal num7 = new BigDecimal(940161.56);
		BigDecimal num8 = new BigDecimal(7244.97);
		BigDecimal num9 = new BigDecimal(10132.11);
		BigDecimal num10 = new BigDecimal(5216.5);

		BigDecimal res = num1.add(num2).add(num3).add(num4).add(num5).add(num6).add(num7).add(num8).add(num9).add(num10).setScale(2, BigDecimal.ROUND_HALF_UP);

		System.out.println(res);

	}
}
