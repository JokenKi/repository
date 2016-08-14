package com.joken.storm.kafka;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class TestKafka {

	public static class ConsoleBolt extends BaseRichBolt {
		private static final long serialVersionUID = 886149197481637894L;
		private OutputCollector collector;

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word", "count"));
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			this.collector = collector;
		}

		@Override
		public void execute(Tuple input) {
			String line = input.getString(0);
			String[] words = line.split("(\\s+|\")");
			for (String word : words) {
				// System.out.println("word:" + word);
				collector.emit(input, new Values(word, 1));
			}
			collector.ack(input);
		}

	}

	public static class WordCounter extends BaseRichBolt {
		private static final long serialVersionUID = 5143600050807549977L;

		private OutputCollector collector;
		private Map<String, AtomicInteger> counterMap;

		@SuppressWarnings("rawtypes")
		@Override
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			this.collector = collector;
			this.counterMap = new HashMap<String, AtomicInteger>();
		}

		@Override
		public void execute(Tuple input) {
			String word = input.getString(0);
			int count = input.getInteger(1);
			AtomicInteger ai = this.counterMap.get(word);
			if (ai == null) {
				ai = new AtomicInteger();
				this.counterMap.put(word, ai);
			}
			ai.addAndGet(count);
			System.out.println("word:" + word + " count:" + ai.longValue());
			collector.ack(input);
		}

		@Override
		public void cleanup() {
			Iterator<Entry<String, AtomicInteger>> iter = this.counterMap
					.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, AtomicInteger> entry = iter.next();
				System.out.println(entry.getKey() + "\t:\t"
						+ entry.getValue().get());
			}

		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word", "count"));
		}

	}

	public static void main(String[] args) {
		BrokerHosts hosts = new ZkHosts("192.168.1.118:2181");
		SpoutConfig config = new SpoutConfig(hosts, "mytopic", "/kafka", "id");
		config.scheme = new SchemeAsMultiScheme(new StringScheme());
		// config.scheme = new SchemeAsMultiScheme(new StringScheme());
		// config.zkServers = Arrays.asList(new String[] { "192.168.1.118" });
		// config.zkPort = 2181;
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("kafka-spout", new KafkaSpout(config), 3);
		builder.setBolt("word-splitter", new ConsoleBolt(), 2).shuffleGrouping(
				"kafka-spout");
		builder.setBolt("word-counter", new WordCounter()).fieldsGrouping(
				"word-splitter", new Fields("word"));
		Config conf = new Config();
		conf.setDebug(false);
		conf.setMaxTaskParallelism(3);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test-kafka", conf, builder.createTopology());
		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cluster.shutdown();
	}

	public void test() {
	}
}
