package com.qupeng.concurrent.day02.part2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现同步
 * 
 * 使用lockInterruptibly对中断行为进行感知
 * @author Peter
 */
public class ReentrantLockTest04 {
	
	private static  int a=10;
	
	private static Lock myLock=new ReentrantLock();
	
	
	public static void main(String[] args) throws InterruptedException {
			
		Thread t1 = new Thread("线程1"){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName+"：开始执行……");
				System.out.println(threadName+"：我要永垂不朽了！！！！");
				myLock.lock();
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					System.out.println(threadName+"我被上帝拒绝进入天堂了！！！");
				}finally {
					myLock.unlock();
				}
				System.out.println(threadName+"：执行结束……");
			}
		};
		t1.start();
		
		
		Thread t2 = new Thread("线程2"){
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName+"：开始执行……");
				System.out.println(threadName+"：我也要永垂不朽！！！！");
				try {
//					myLock.lock();
					myLock.lockInterruptibly();
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					System.out.println(threadName+"：我被上帝告知：永垂不朽的名额只有一个，这个名额已经被线程1拿到了，我只能好好待在人间了！");
				}finally {
					if(myLock.tryLock()){
						myLock.unlock();
					}
				}
				System.out.println(threadName+"：执行结束……");
			}
		};
		t2.start();
		//休息一会儿，让线程1和线程2启动
		Thread.sleep(100);
		//将线程2退回人间
		t2.interrupt();
	}

}
