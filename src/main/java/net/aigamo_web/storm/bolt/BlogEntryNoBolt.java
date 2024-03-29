package net.aigamo_web.storm.bolt;

import static backtype.storm.utils.Utils.tuple;

import java.util.HashMap;
import java.util.Map;

import net.aigamo_web.storm.model.AnalyticObject;

import org.apache.log4j.Logger;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BlogEntryNoBolt extends BaseBasicBolt {
	public static Logger LOG = Logger.getLogger(BlogEntryNoBolt.class);

	Map<String, AnalyticObject> objectMap;

	private static final long serialVersionUID = 1L;

	public void prepare(Map stormConf, TopologyContext context) {
		objectMap = new HashMap<String, AnalyticObject>();
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// BlogEntryNoで集約する
		AnalyticObject object = (AnalyticObject) input.getValues().get(0);
		// 登録されてなければMapに追加してemmitする
		if (!objectMap.containsKey(object.getBlogEntryNo())) {
			objectMap.put(object.getBlogEntryNo(), object);
		}
		collector.emit(tuple(object.getBlogEntryNo(), object));

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("blogEntryNo", "AnalyticObject"));

	}

}
