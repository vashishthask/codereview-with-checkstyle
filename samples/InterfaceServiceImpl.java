import java.util.List;

public class InterfaceServiceImpl implements InterfaceSample{
	private static String sample = "john";
	public void method(String [] args) throws Exception{
		System.out.println("Hello World and interface");
		String x = null;
		x=new String();
		x.length();
		
		String name = "shrikant";
		
		String name2 = x;
		
		String nullString = null;
		nullString.length();
		
		List list1;
		
		List list2 = list1;
		list2.getSize();
		
		list2 = new ArrayList();
		
		//Sample of unnecessary initialization
		String sample = new String();
		
		sample.length();
		
		sample = sampleMethod();
	}
	
	private void sampleMethod() throws Exception{
		
	}
}