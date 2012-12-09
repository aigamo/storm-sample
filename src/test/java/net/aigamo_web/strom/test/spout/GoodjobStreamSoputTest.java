package net.aigamo_web.strom.test.spout;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.aigamo_web.storm.model.AnalyticObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import backtype.storm.tuple.Values;

public class GoodjobStreamSoputTest {

	public static List<Values> getMockData() {

		List<Values> mockData = new ArrayList<Values>();

		try {
			URL url = new URL(
					"http://localhost:9000/coregoodjob/storm/api/v1.0/goodjobstream.json");

			ObjectMapper objectMapper = new ObjectMapper();
			// JsonNode jsonNode = objectMapper.readTree(url);
			// for (int i = 0; i < jsonNode.size(); i++) {
			// mockData.add(new Values(jsonNode.get(i)));
			// }\
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy MMM dd HH:mm:ss");
			objectMapper.setDateFormat(dateFormat);
			List<AnalyticObject> jsonNode2 = objectMapper.readValue(new File(
					"src/test/resources/mockStreamData.json"),
					new TypeReference<AnalyticObject>() {
					});
			System.out.println(jsonNode2.get(0));
			// for (int i = 0; i < jsonNode2.size(); i++) {
			// AnalyticObject jsonNode3 = objectMapper.readValue(jsonNode2,
			// AnalyticObject.class);
			// System.out.println(jsonNode3.name);
			// //mockData.add(new Values(jsonNode2.get(i)));
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Values[] result = (Values[]) mockData.toArray(new
		// Values[mockData.get(index)]);

		return mockData;
	}

	public static void main(String args[]) {
		List<Values> values = GoodjobStreamSoputTest.getMockData();
		System.out.println(values.size());
		for (Values val : values) {
			System.out.println(val.toString());

		}
	}
}
