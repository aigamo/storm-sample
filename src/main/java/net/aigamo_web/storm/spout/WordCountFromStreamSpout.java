package net.aigamo_web.storm.spout;

import java.io.BufferedReader;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class WordCountFromStreamSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(WordCountFromStreamSpout.class);
	boolean isDistributed;
	SpoutOutputCollector collector;
	BufferedReader reader;
	URL url;
	public static final String baseUri = "http://localhost:9000/coregoodjob/storm/api/v1.0/test.json";

	public WordCountFromStreamSpout() {
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

			// String contents = IOUtils.toString(reader);

			// JsonFactoryの生成
			JsonFactory factory = new JsonFactory();

			// JsonParserの取得
			JsonParser parser = factory.createJsonParser(url);
			parser.nextValue();

			
			// 各オブジェクトの処理
			if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String name = parser.getCurrentName();
					parser.nextToken();
					// "name"フィールド
					if ("samplewords".equals(name)) {
						log.info(name);
						while (parser.nextToken() != JsonToken.END_ARRAY) {
							if (parser.getCurrentToken() == JsonToken.VALUE_STRING) {
								collector.emit(new Values(parser.getText()));
							}
						}
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));

	}
}
