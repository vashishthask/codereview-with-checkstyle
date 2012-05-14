public class SingletonSample {

	private static final SingletonSample singleton = new SingletonSample();
	
	String sample;
	
	private SingletonSample(){
		 int x = 4 + 3 * 5 + (30 + 26) / 4 + 5 % 4 + (1<<3);
		 sample = "shrikant";
	}
	
	public SingletonSample getInstance(){
		return singleton;
	}
	
	public void sampleMethod(String [] args){
		while(args != null && "shrikant".length() > 0)
			System.out.println("Sample");
		
		for(i=0;i<"sample".length()-3;i++)
			System.out.println("Sample");
	}
	
	public void sampleMethod2(String [] args){
		try{
		while(args != null && ((args.length() > 0||"shri".length()>4) || "anothercondtion".eqauls("shri")|| mainMethod(null)!= null))
			System.out.println("Sample");
		}catch(Exception ex){
		}
		InerfaceSample sample = new InterfaceSample(){
			public void method(String [] args){
			}
		};
	}
	
	public void sampleMethod3(String [] args){
		try{
		while(args != null && args.length() > 0)
			System.out.println("Sample");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String [] mainMethod(String [] args){
		return null;
	}
	
	public static void main(String args[]){
		
	}


}
