package com.qupeng.concurrent.day02.part3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 写一个固定容量的同步容器，拥有put和get方法，
 * 以及getCount方法，要求支持能够支持两个生产者
 * 线程以及10个消费者线程的阻塞调用
 * 
 * 这里使用Lock和Condition实现
 * @author qupeng
 */
public class Examination08 {
	

	public static void main(String[] args) {
		
		final Box_8 box=new Box_8();
		
		//创建生产者线程
		for(int i=1;i<=2;i++){
			new Thread("生产者线程"+i){
				@Override
				public void run() {
					try {
						while(true){
							Thread.sleep(200);
							box.put(new Object());
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		
		//创建消费者线程
		for(int i=1;i<=10;i++){
			new Thread("消费者线程"+i){
				@Override
				public void run() {
					try {
						while(true){
							Thread.sleep(1_000);
							box.get();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
		

}

class Box_8{
	
	private final List<Object> box=new LinkedList<>();
	
	private final int max=10;
	
	private int count=0;
	
	private Lock myLock=new ReentrantLock();
	
	private Condition producer=myLock.newCondition();
	
	private Condition consumer=myLock.newCondition();
	
	public  void put(Object element) throws InterruptedException{
		try {
			myLock.lock();
			while(count==max){
				producer.await();
			}
			box.add(element);
			count++;
			System.out.println(Thread.currentThread().getName()+"：put  an element……count="+count);
			consumer.signal();
		} finally{
			myLock.unlock();
		}
	}
	
	public  Object get() throws InterruptedException{
		try {
			myLock.lock();
			if(count==0){
				consumer.await();
			}
			Object element=box.remove(0);
			count--;
			System.out.println(Thread.currentThread().getName()+"：get  an element……count="+count);
			producer.signal();
			return element;
		} finally{
			myLock.unlock();
		}
	}
	
	public  int getCount(){
		try {
			myLock.lock();
			return count;
		} finally{
			myLock.unlock();
		}
	}
	
}
