package com.qupeng.concurrent.day02.part3;

import java.util.LinkedList;
import java.util.List;


/**
 * 写一个固定容量的同步容器，拥有put和get方法，
 * 以及getCount方法，要求支持能够支持两个生产者
 * 线程以及10个消费者线程的阻塞调用
 * 
 * 这里使用wait和nodify/nodifyAll实现
 * @author qupeng
 */
public class Examination07 {
	

	public static void main(String[] args) {
		
		final Box_7 box=new Box_7();
		
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

class Box_7{
	
	private final List<Object> box=new LinkedList<>();
	
	private final int max=10;
	
	private int count=0;
	
	public synchronized void put(Object element) throws InterruptedException{
		//这里用if会有什么问题？
		if(count==max){
			this.wait();
		}
		box.add(element);
		count++;
		System.out.println(Thread.currentThread().getName()+"：put  an element……count="+count);
		//这里用nodify合适么？
		this.notify();
//		this.notifyAll();
	}
	
	public synchronized Object get() throws InterruptedException{
		if(count==0){
			this.wait();
		}
		Object element=box.remove(0);
		count--;
		System.out.println(Thread.currentThread().getName()+"：get  an element……count="+count);
		this.notify();
//		this.notifyAll();
		return element;
	}
	
	public synchronized int getCount(){
		return count;
	}
	
}

