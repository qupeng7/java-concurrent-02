package com.qupeng.concurrent.day02.part2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现同步
 * 
 * 可以使用进行tryLock尝试加锁。
 * 注意：不管tryLock返回值如何都会继续往下执行。
 * @author Peter
 */
public class ReentrantLockTest03 {
	
	private static  int a=10;
	
	private static Lock myLock=new ReentrantLock();
	
	public static  void decrement(){
		myLock.lock();
		try {
			a--;
			System.out.println(Thread.currentThread().getName()+"：a的值为："+a);
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
//			myLock.unlock();
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
				String threadName = Thread.currentThread().getName();
				try {
					//线程2也对共享变量a进行“--” 操作
					for(int i=1;i<=5;i++){
						boolean locked = myLock.tryLock();
						System.out.println(threadName+"：我尝试加锁之后，发现这把锁是被我持有的吗？"+locked);
						
//						boolean locked=false;
						System.out.println(threadName+"：开始尝试获取锁……");
						locked = myLock.tryLock(2, TimeUnit.SECONDS);
						System.out.println(threadName+"：我已经等了2秒了，我不等了，我要去翻跟斗了！");
						//如果我不能获取这把锁，那么我就做别的事，这里是跳出循环去翻跟斗
						if(!locked){
							break;
						}
						decrement();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					//判断如果是我持有着这把锁就必需要在这里进行释放
					//注意；如果这里不做判断直接释放锁，那么可能会出现异常
					if(myLock.tryLock()){
						myLock.unlock();
					}
				}
				System.out.println(threadName+"：我现在已经翻了一个跟斗了！");
			}
		}.start();
	}

}
