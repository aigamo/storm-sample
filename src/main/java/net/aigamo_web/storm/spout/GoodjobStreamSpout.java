package net.aigamo_web.storm.spout;

import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

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
		// TODO Auto-generated method stub

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
				collector.emit("goodjobStream", new Values(jsonNode));
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
