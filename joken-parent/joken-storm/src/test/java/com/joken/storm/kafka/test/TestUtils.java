package com.joken.storm.kafka.test;

import junit.framework.TestCase;

public class TestUtils extends TestCase {

	public void name() {
		String regex = "(\\s+|\")";
		String[] arr = "[2016-08-05 11:31:24.109] [main] DEBUG - CartMarketingModel: {\"deductMoney\":0,\"exraMarketingSeq\":[{\"desc\":\"已满12元，免运费\",\"marketingId\":\"44\",\"type\":0}],\"marketingGoodsModelSeq\":[{\"currentPrice\":2,\"desc\":\"\",\"goodsId\":\"0382C7F8-DCD5-6371-32A9-35CCEA33F2E3\",\"goodsNum\":6,\"goodsPropertyId\":\"0CC54CC0-1F13-461E-9C2A-76ACC78118F3\",\"marketingId\":\"\",\"salePrice\":2,\"type\":0}],\"totalPrice\":12}"
				.split(regex);
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}
