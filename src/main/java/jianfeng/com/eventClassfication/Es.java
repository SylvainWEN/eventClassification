package jianfeng.com.eventClassfication;
/*
 * this call is used to call the elasticsearch module.
 * wenjf@act.buaa.edu.cn
 * */

import java.util.ArrayList;

import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.hadoop.io.serializer.Serializer;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.Serializable;
public class Es implements Serializable{
	static TransportClient client = null;
	private Settings settings = null;
	private String dataset = null;
	//constructor
	public Es(){
		settings = ImmutableSettings.settingsBuilder()
			.put("client.transport.sniff", true)
			.put("cluster.name", "elasticsearch").build();
		
		this.dataset = "crawler";
		this.buildClient();
	}
	private void buildClient(){
		client = new TransportClient(this.settings)	
		.addTransportAddress(new InetSocketTransportAddress("10.1.1.33", 9300));
	}
	public ArrayList<String> cherche(String name){
		ArrayList<String> weibos = new ArrayList<String>();;
		QueryBuilder aQuery = QueryBuilders.queryString(name).field("username");//field should be modifier
		SearchRequestBuilder search = client.prepareSearch(this.dataset);
		search.setTypes("msg");
		search.setQuery(aQuery);
		search.setSize(5000);
		SearchResponse response = search.execute().actionGet();
		int count=0;
		for(int j=0; j<response.getHits().getTotalHits()&&j<1000&&count<500; j++){
			//System.out.println(response.getHits().getAt(j).sourceAsString());
//			this.weibos.add(response.getHits().getAt(j).sourceAsString());
			String content = (String) response.getHits().getAt(j).getSource().get("content");
			if(content.length()>30){
				weibos.add(content);
				count++;
			}
		}
		return weibos;
	}
}

