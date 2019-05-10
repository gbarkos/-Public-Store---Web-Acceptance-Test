package utils;

public class StringManipulation{
	
	public static double preparePrice(String str){
		
		String rmvEuro = str.replaceAll("[^\\d.]", "");//remove all non-numeric chars from the String
		int tmpInt = Integer.valueOf(rmvEuro);//convert String to int
		double left = tmpInt/100; //get the left part
		double tmpRight = tmpInt%100; //get the right part
		double right = tmpRight/100;//convert the right part to #.(right)
		
		return left+right;
	}
}