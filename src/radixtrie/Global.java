package radixtrie;
//Global constants
public class Global {
	public static String alg="";
	public static String file="";
	public static boolean prune=true;
	public static long time;
	public static int t;
	public static long activenodes;
	public static long r;
	public static int limit;
	public static boolean hist_prune=true;
	public  static boolean delete_not_needed_activenodes = true;
	static public String header(){
		return "file"+"\t"+"limit"+"\t"+"t"+"\t"+"alg"+"\t"+"prune"+"\t"+"hist_prune"+"\t"+"delete_not_needed_activenodes"+"\t"+"active node"+"\t"+"result"+"\t" +"time";
	
	}
	static public String str(){
		String x=file+"\t"+limit+"\t"+t+"\t"+alg+"\t"+prune+"\t"+hist_prune+"\t"+delete_not_needed_activenodes+"\t"+activenodes+"\t"+r +"\t" +time;
		return x;
	}
}
