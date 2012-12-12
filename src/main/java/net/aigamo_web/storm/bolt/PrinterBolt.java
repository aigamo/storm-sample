package net.aigamo_web.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class PrinterBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
		System.out.println("たぷたぷ：" + tuple.getValues().get(0)
				+ tuple.getValues().get(1) + tuple.getValues().get(2)
				+ tuple.getValues().get(3) + "こたえ！" +tuple.getValues().get(4));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer ofd) {
	}

}
