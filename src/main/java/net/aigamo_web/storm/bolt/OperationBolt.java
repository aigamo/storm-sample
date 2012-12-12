package net.aigamo_web.storm.bolt;

import static backtype.storm.utils.Utils.tuple;

import java.util.HashMap;
import java.util.Map;

import net.aigamo_web.storm.model.AnalyticObject;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class OperationBolt extends BaseBasicBolt {

	Map<String, Integer> objectMap;

	private static final long serialVersionUID = 1L;

	public void prepare(Map stormConf, TopologyContext context) {
		objectMap = new HashMap<String, Integer>();

	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// Operationで集約する
		// get(1)なのはAnalyticObjectが2カラム目だから。Objectをとっている
		AnalyticObject object = (AnalyticObject) input.getValues().get(1);
		// 登録されてなければMapに追加してemmitする
		Integer count = objectMap.get(object.getOperation());
		if (count == null) {
			count = 0;
		}
		count++;
		objectMap.put(object.getOperation(), count);

		collector.emit(tuple(object.getBlogEntryNo(), object.getOperation(),
				object.getName(), object.getDate(), count));

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("blogEntryNo", "operation", "name", "date",
				"count"));

	}

}
