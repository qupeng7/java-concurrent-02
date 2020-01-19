package com.qupeng.concurrent.day02.part4;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程内部的局部变量： 可以实现共享数据在线程范围内的线程安全 自己实现一个ThreadLocal的功能
 * 
 * @author Peter
 */
public class ThreadLocalTest02 {

	public static void main(String[] args) throws InterruptedException {

		final MyThreadLocal<String> shareData = new MyThreadLocal<>();
		System.out.println("财主：我必须把黄金埋在一个隐蔽的地方，别被其他人发现了！！！");
		shareData.set("黄金");

		new Thread("冯锡范") {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + "：听说财主在鹿鼎山埋下了宝藏里面放了很多黄金，我把它挖出来瞧瞧！！！" + shareData.get());
				System.out.println(threadName + "：打开看到里面放的是：" + shareData.get());
			}

		}.start();

		new Thread("吴三桂") {

			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + "：听说财主在鹿鼎山埋下了宝藏里面放了很多黄金，我把它挖出来瞧瞧！！！" + shareData.get());
				System.out.println(threadName + "：打开看到里面放的是：" + shareData.get());
			}

		}.start();

		// 休息一下，让两个线程启动
		Thread.sleep(100);
		System.out.println("过了几天……");
		System.out.println("财主：我来看看，我埋在鹿鼎山的黄金还在不？");
		System.out.println("财主：打开看到里面放的是：" + shareData.get());
	}

	static class MyThreadLocal<T> {

		private final Map<Thread, T> myThreadLocal;

		public MyThreadLocal() {
			myThreadLocal = new HashMap<>();
		}

		public synchronized void set(T t) {
			myThreadLocal.put(Thread.currentThread(), t);
		}

		public synchronized T get() {
			Thread currThread = Thread.currentThread();
			if(currThread!=null){
				return myThreadLocal.get(Thread.currentThread());
			}
			return initValue();
		}

		protected T initValue() {
			return null;
		}

	}

}
