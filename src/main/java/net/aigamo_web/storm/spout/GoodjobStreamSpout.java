package net.aigamo_web.storm.spout;

import static backtype.storm.utils.Utils.tuple;

import java.net.URL;
import java.util.List;
import java.util.Map;

import net.aigamo_web.storm.model.AnalyticObject;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class GoodjobStreamSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	public static final String baseUri = "http://localhost:9000/coregoodjob/storm/api/v1.0/goodjobstream.json";
	URL url;
	SpoutOutputCollector collector;

	public GoodjobStreamSpout() {
		try {
			url = new URL(baseUri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;

	}

	@Override
	public void nextTuple() {
		Utils.sleep(1000);
		try {
			URL url = new URL(
					"http://localhost:9000/coregoodjob/storm/api/v1.0/goodjobstream.json");
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(url);
			for (int i = 0; i < jsonNode.size(); i++) {
				AnalyticObject object = objectMapper.readValue(jsonNode.get(i),
						new TypeReference<AnalyticObject>() {
						});
				collector.emit(new Values(object));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("goodjobStream"));

	}

}
