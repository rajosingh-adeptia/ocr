package com;

public enum SingletonEnum {
INSTANCE(1),INSTANCE2(4);
	private int intege;
	private String str;
	private SingletonEnum(final int intege){
		System.out.println("-----> ");
		this.intege=intege;
	}
	public int getIntege(){
		return intege;
	}
	public static void main(String[] args) {
		System.out.println();
		try {
			SingletonEnum singletonEnum=	SingletonEnum.valueOf("INSTANCE");
			System.out.println(singletonEnum.ordinal());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
