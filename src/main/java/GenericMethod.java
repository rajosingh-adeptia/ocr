
public class GenericMethod<T extends Number> {
	
	public  void display(T[] arr){
		for(T e: arr){
			System.out.println(e);
		}
		
	}

	public static void main(String[] args) {
		
   String[] str=new String[3];
   GenericMethod<Integer> genericMethod=new GenericMethod<Integer>();
   str[0]="A"; str[1]="B";str[2]="C";
  // genericMethod.display(str);
   Integer [] integer={1,2,3};
  genericMethod.display(integer);
   
	}

}
