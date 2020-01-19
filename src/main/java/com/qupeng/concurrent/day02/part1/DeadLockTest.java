package com.qupeng.concurrent.day02.part1;

/**
 * 演示死锁现象
 * @author Peter
 */
public class DeadLockTest {
	
	private static Object myLock_1=new Object();
	
	private static Object myLock_2=new Object();
	

	public static void main(String[] args) {

		new Thread("线程1"){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				synchronized (myLock_1) {
					System.out.println(threadName+"：进入myLock_1的同步代码块……");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					synchronized (myLock_2) {
						System.out.println(threadName+"：进入myLock_2的同步代码块……");
						for(int i=0;i<5;i++){
							System.out.println(threadName+"我叫你一声，你敢答应吗？");
						}
						System.out.println(threadName+"：离开myLock_2的同步代码块……");
					}
					System.out.println(threadName+"：离开myLock_1的同步代码块……");
				}
				
			}
		}.start();
		
		
		new Thread("线程2"){
			
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				synchronized (myLock_2) {
					System.out.println(threadName+"：进入myLock_1的同步代码块……");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					synchronized (myLock_1) {
						System.out.println(threadName+"：进入myLock_2的同步代码块……");
						for(int i=0;i<5;i++){
							System.out.println(threadName+"：心想：不管你叫几声我都不答应！沉默中……");
						}
						System.out.println(threadName+"：离开myLock_2的同步代码块……");
					}
					System.out.println(threadName+"：离开myLock_1的同步代码块……");
				}
				
			}
		}.start();
		
	}

}
