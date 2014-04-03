package edu.alexu.dmlab;
//Global constants
public class Global {
	public static String alg="";
	public static boolean prune=true;
	public static boolean length_prune=true;
	public  static boolean delete_not_needed_activenodes = true;
	static public String str(){
		String x=alg+"\t"+prune+"\t"+delete_not_needed_activenodes;
		return x;
	}
}
