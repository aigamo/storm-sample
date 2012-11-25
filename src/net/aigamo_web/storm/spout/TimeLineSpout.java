package net.aigamo_web.storm.spout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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

public class TimeLineSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(TimeLineSpout.class);
	boolean isDistributed;
	SpoutOutputCollector collector;

	public TimeLineSpout() {
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void nextTuple() {

		try {
			URL url = new URL(
					"http://localhost:9000/coregoodjob/strom/api/1.0/test");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));
			String contents = IOUtils.toString(reader);

			// JsonFactoryの生成
			JsonFactory factory = new JsonFactory();
			// JsonParserの取得
			JsonParser parser = factory.createJsonParser(contents);
			// 各オブジェクトの処理
			log.debug("start Timeline Soput");
			if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String name = parser.getCurrentName();
					parser.nextToken();
					// "name"フィールド
					if ("samplewords".equals(name)) {
						log.info(name);

						while (parser.nextToken() != JsonToken.END_ARRAY) {
							if (parser.getCurrentToken() == JsonToken.VALUE_STRING) {
								System.out.println(parser.getText());
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
