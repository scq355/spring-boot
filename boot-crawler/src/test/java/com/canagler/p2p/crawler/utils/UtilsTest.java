package com.canagler.p2p.crawler.utils;

import com.canagler.p2p.crawler.util.NumberUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scq on 2018-04-19 13:46:13
 */
public class UtilsTest {

	@Test
	public void testPaging() {
		String paging_str_1 = "12345678910... 350 / 350 页下一页";
		String paging_str_2 = "首页 上一页 1 2 3 4 5 ... 下一页 末页 共312条数据 1/16";
		String paging_str_3 = "2 3 4 5 6 7 8 9 10 ... 38 下一页";
		List<String> pagingList = new ArrayList<>();
		pagingList.add(paging_str_1);
		pagingList.add(paging_str_2);
		pagingList.add(paging_str_3);

		for (String enu : pagingList) {
			Integer bounds = NumberUtil.getPageBounds(enu);
			System.out.println(bounds);
		}
	}

}
