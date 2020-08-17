package com;

public class EnumSingleton {

	public static void main(String[] args) {
		
		SingletonTest singletonTest1=SingletonTest.INSTANCE;
		SingletonTest singletonTest2=SingletonTest.INSTANCE;
		System.out.println(singletonTest1.hashCode()+" "+singletonTest2.hashCode());
		System.out.println(SingletonTest.INSTANCE.show());;
		
		
	}
	
	enum SingletonTest{
		INSTANCE;
		private SingletonTest(){
			System.out.println("constructor called..");
		}
		 final private Integer obj=new Integer(10);
		public Integer show(){
			return obj;
		}
		
	}

}
