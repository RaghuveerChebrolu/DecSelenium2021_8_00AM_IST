package com.java.basics;

public class staticBlock {
	static int a;
	static {
		a=10;
		System.out.println("static block is invoked");
	}

	public static void method1() {
		System.out.println("inside static method");
	}

	public static void main(String[] args) {
		System.out.println("i am inside main");
		System.out.println(a);
		method1();
	}

	/*
	 * Note-> uses of staticBlock: 1. Is used to initialize the static data
	 * member. 2. It is executed before the main method at the time of
	 * classloading.
	 */

}
