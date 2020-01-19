package com.qupeng.concurrent.day02.part2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现同步
 * 
 * 需要注意：使用ReentrantLock需要手动释放锁
 * 一般会将释放锁的过程放在finally中进行
 * （这样就可以避免死锁）
 * 
 * 因为synchronized实现同步的过程中，如果出现异常
 * 那么会自动释放锁，但是使用ReentrantLock却必须手动
 * 释放锁。
 * @author qupeng
 */
public class ReentrantLockTest02 {
	
	private static  int a=10;
	
	private static Lock myLock=new ReentrantLock();
	
	public static  void decrement(){
		myLock.lock();
		try {
			a--;
			System.out.println(Thread.currentThread().getName()+"：a的值为："+a);
//			Thread.sleep(1_000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			myLock.unlock();
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
