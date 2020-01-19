package com.qupeng.concurrent.day02.part4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者模式
 * 
 * 这里用Lock和Condition实现
 * @author Peter
 */
public class ProducerAndConsumerTest02 {
	

	public static void main(String[] args) throws InterruptedException {
			
			ShareData shareData=new ShareData();
			//创建3个生产者
			for(int i=1;i<=3;i++){
				Producer pdc = new Producer(shareData);
				pdc.setName("生产者"+i);
				pdc.start();
			}
			
			//创建3个消费者
			for(int i=1;i<=3;i++){
				Consumer csm = new Consumer(shareData);
				csm.setName("消费者"+i);
				csm.start();
			}
		
	}
	
	static class ShareData{
		
		private String name;
		
		private int count=1;
		
		private boolean flag;
		
		//创建一个锁对象
		Lock lock=new ReentrantLock();
		
		//通过已有的锁获取两组监视器，一组监视生产者，一组监视消费者。
		Condition prdcCdt=lock.newCondition();
		
		Condition cosmCdt=lock.newCondition();
		
		public  void make(String name){
			try {
				lock.lock();
				while(flag){
						prdcCdt.await();
						Thread.sleep(10);
				}
				this.name="第"+count+"号"+name;
				count++;
				System.out.println(Thread.currentThread().getName()+"：生产者====>我炸好了"+this.name);
				flag=true;
				cosmCdt.signal();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				lock.unlock();
			}
			
		}
		public  void eat(){
			try {
				lock.lock();
				while(!flag){
					cosmCdt.await();
					Thread.sleep(10);
				}
				System.out.println(Thread.currentThread().getName()+"：消费者===========>我吃掉了"+this.name);
				flag=false;
				prdcCdt.signal();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}
	
	
	static class Producer extends Thread{
		
		private   ShareData data;
		
		Producer(ShareData data){
			this.data=data;
		}
		
		@Override
		public void run(){
			while(true){
				data.make("油条");
			}
		}
		
	}
	
	
	static class Consumer extends Thread{
		
		private   ShareData data;
		
		Consumer(ShareData data){
			this.data=data;
		}
		
		public void run(){
			while(true){
				data.eat();
			}
		}
		
	}
	

}
