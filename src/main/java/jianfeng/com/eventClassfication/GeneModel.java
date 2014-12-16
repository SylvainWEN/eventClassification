package jianfeng.com.eventClassfication;

import java.io.*;
import java.util.*;

public class GeneModel implements Serializable{
	public static List<String> Label1;
	public static List<String> Label2;
	public static List<String> Label3;
	public static List<String> testList1;
	public static List<String> testList2;
	public static List<String> testList3;
	public static Es myEs;
	public GeneModel(){
		Label1 = new ArrayList<String>();
		Label2 = new ArrayList<String>();
		Label3 = new ArrayList<String>();
		testList1 = new ArrayList<String>();
		testList2 = new ArrayList<String>();
		testList3 = new ArrayList<String>();
		myEs = new Es();
	}
	public static void writeData() throws IOException{
		File dataTrain = new File("dataTrain.txt");
		FileWriter writer = new FileWriter(dataTrain,true);
		for(String L:Label1){
			writer.write(L+"\t"+"1"+"\n");
		}
		for(String L:Label2){
			writer.write(L+"\t"+"2"+"\n");
		}
		for(String L:Label3){
			writer.write(L+"\t"+"0"+"\n");//事件类label为0
		}
		File dataTest = new File("dataTest.txt");
		writer = new FileWriter(dataTest,true);
		for(String L:testList1){
			writer.write(L+"\t"+"1"+"\n");
		}
		for(String L:testList2){
			writer.write(L+"\t"+"2"+"\n");
		}
		for(String L:testList3){
			writer.write(L+"\t"+"0"+"\n");//事件类label为0
		}
		writer.close();
	}
	public static void getDataSet(){
		//心灵鸡汤
		ArrayList<String> result1 = myEs.cherche("心灵鸡汤");
		for(int j = 0; j<250;j++){
			Label1.add(result1.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList1.add(result1.get(2*j+1));
		}
		//星座八卦
		result1 = myEs.cherche("星座秘语");
		for(int j = 0; j<250;j++){
			Label1.add(result1.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList1.add(result1.get(2*j+1));
		}
		//休闲养生
		ArrayList<String> result2 = myEs.cherche("老中医健康减肥养生堂");
		for(int j = 0; j<250;j++){
			Label2.add(result2.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList2.add(result2.get(2*j+1));
		}
		//美容健康
		result2 = myEs.cherche("美容健康");
		for(int j = 0; j<250;j++){
			Label2.add(result2.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList2.add(result2.get(2*j+1));
		}
		//新浪新闻
		ArrayList<String> result3 = myEs.cherche("央视新闻");
		for(int j = 0; j<250;j++){
			Label3.add(result3.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList3.add(result3.get(2*j+1));
		}
		//人民日报
		result3 = myEs.cherche("青年时报");
		for(int j = 0; j<250;j++){
			Label3.add(result3.get(2*j));
		}
		for(int j = 0; j<250;j++){
			testList3.add(result3.get(2*j+1));
		}
	}
}
