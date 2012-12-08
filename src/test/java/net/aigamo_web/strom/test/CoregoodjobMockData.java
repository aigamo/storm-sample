package net.aigamo_web.strom.test;

import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import backtype.storm.tuple.Values;

public class CoregoodjobMockData {

	public static Values[] getMockData() {

		ArrayList<Values> mockData = new ArrayList<Values>();

		try {
			URL url = new URL(
					"http://localhost:9000/coregoodjob/storm/api/v1.0/test.json");

			// JsonFactoryの生成
			JsonFactory factory = new JsonFactory();
			// JsonParserの取得
			JsonParser parser = factory.createJsonParser(url);
			// 各オブジェクトの処理
			if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String name = parser.getCurrentName();
					parser.nextToken();
					// "name"フィールド
					if ("samplewords".equals(name)) {
						while (parser.nextToken() != JsonToken.END_ARRAY) {
							if (parser.getCurrentToken() == JsonToken.VALUE_STRING) {
								System.out.println(parser.getText());
								mockData.add(new Values(parser.getText()));
							}
						}
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Values[] result = (Values[]) mockData.toArray(new Values[mockData
				.size()]);

		return result;
	}

}
