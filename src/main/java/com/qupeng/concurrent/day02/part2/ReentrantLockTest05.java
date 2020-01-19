package com.qupeng.concurrent.day02.part2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现同步
 * 
 * 实现公平锁
 * @author Peter
 */
public class ReentrantLockTest05 {
	
	private static  int a=10;
	
	private static Lock myLock=new ReentrantLock(true);
	
	public static  void decrement(){
		myLock.lock();
		try {
			a--;
			System.out.println(Thread.currentThread().getName()+"：a的值为："+a);
			Thread.sleep(1_000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			myLock.unlock();
		}
	}
	
	public static void main(String[] args)  {
			
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
