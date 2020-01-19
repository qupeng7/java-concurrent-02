package com.qupeng.concurrent.day02.part2;

/**
 * 使用传统的同步技术synchronized关键字实现同步
 * @author qupeng
 */
public class ReentrantLockTest01 {
	
	private static  int a=10;
	
	public static synchronized void decrement(){
		try {
			a--;
			System.out.println(Thread.currentThread().getName()+"：a的值为："+a);
//			Thread.sleep(1_000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
			
		new Thread("线程1"){
			@Override
			public void run() {
				for(int i=1;i<=5;i++){
					decrement();
				}
			}
		}.start();
		
		
		new Thread("线程2"){
			@Override
			public void run() {
				for(int i=1;i<=5;i++){
					decrement();
				}
			}
		}.start();
	}

}
