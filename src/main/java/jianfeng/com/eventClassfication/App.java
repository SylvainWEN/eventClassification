package jianfeng.com.eventClassfication;

import java.io.*;
import java.util.*;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * Hello world!
 *
 */
public class App 
{
	static JavaSparkContext sparkContest;
	static List<String> wordList;
	static NaiveBayesModel myNB;
	static int dim=0;
	static int step=0;
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" ); 
       
//        GeneModel toGene = new GeneModel();
//        toGene.getDataSet();
//        toGene.writeData();
//        System.out.println(System.getProperty("user.dir"));当前路径
//        readerTXT("carid.txt");
//        Es testEs = new Es();
//        ArrayList<String> testList = testEs.cherche("青年时报");
//        for(String L:testList){
//        	System.out.print(L+"\n");
//        }
//        System.out.print(testList.size());
//        

        sparkContest = new JavaSparkContext("local","App",System.getenv("SPARK_HOME"),new String[]{System.getenv("SPARK_TEST_JAR")});
        JavaRDD<String> dic = sparkContest.textFile("hdfs://10.1.1.33:9000/user/root/mydic.txt");
        wordList = dic.collect();
        for(int i = 0;i<wordList.size();i++){
        	if(wordList.get(i).length()==1){
        		wordList.remove(wordList.get(i));
        	}	
        }
        dim=wordList.size();
      System.out.print(dim);
      myNB = getSVM();
      doTest();
//        System.out.print(dim);
    }
    
    public static NaiveBayesModel getSVM(){
    	JavaRDD<String> trainData = sparkContest.textFile("hdfs://10.1.1.33:9000/user/root/dataTrain.txt");
    	JavaRDD<LabeledPoint> trainDataRDD = trainData.map(
    			new Function<String, LabeledPoint>(){
					public LabeledPoint call(String v1) throws Exception {
						// TODO Auto-generated method stub
						return stringPrepare(v1);
					}	
    			}
    			);
    	NaiveBayesModel myModel = NaiveBayes.train(trainDataRDD.rdd());
    	return myModel;
    }
    public static void doTest(){
    	JavaRDD<String> testData = sparkContest.textFile("hdfs://10.1.1.33:9000/user/root/dataTest.txt");
    	JavaPairRDD<String,LabeledPoint> testDataRDD = testData.mapToPair(
    			new PairFunction<Tuple2<String,LabeledPoint>,String>(){
    				
    			}
    			);
    	JavaRDD<String> res = testDataRDD.map(
    			new Function<LabeledPoint, String>(){

					public String call(LabeledPoint v1) throws Exception {
						// TODO Auto-generated method stub
//						if(v1.label()==myNB.predict(v1.features()))
//							return true;
//						else
						double result = myNB.predict(v1.features());
						
							return null;
					}
    			}
    			);
    	List<String> resFinal = res.collect();
    	int numRight=0;
//    	for(int j = 0;j<resFinal.size();j++){
//    		if(resFinal.get(j)==true)
//    			numRight++;
//    	}
    	System.out.print(numRight);
    }
    public static LabeledPoint stringPrepare(String withLabel) throws IOException{
    	String[] oneDoc = withLabel.split("\t");
    	double[] vect = new double[dim];
    	StringReader re = new StringReader(oneDoc[0]);
		IKSegmenter ik = new IKSegmenter(re,true);
    	Lexeme lex = null;
    	while((lex=ik.next())!=null){
    		String word = lex.getLexemeText();
    		if(word.length()>1){
    			int index = wordList.indexOf(word);
    			if(index!=-1){
    				vect[index] = 1;
    			}
    		}
		}
    	double label = Double.parseDouble(oneDoc[1]);
    	System.out.println(step+++"\n");
		return new LabeledPoint(label,Vectors.dense(vect));
    }
    
    
    
//    public static void readerTXT(String Path) throws IOException{
//       	int i = 0;
//    	System.out.print(i);
//    	File file = new File(Path);
////    	BufferedReader reader = new BufferedReader(new FileReader(file));
//    	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
//    	String temp = null;
//    	System.out.print(i);
//    	List<String> res = new ArrayList<String>();
//    	while((temp = in.readLine())!=null){
//    		res.add(temp);
//    		System.out.print(temp+" "+i+"\n");
//    		i++;
//    	}
//    	System.out.print(i);
//    	return;
//    }
}
