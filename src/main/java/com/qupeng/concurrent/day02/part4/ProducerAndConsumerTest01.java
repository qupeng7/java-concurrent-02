package com.qupeng.concurrent.day02.part4;


/**
 * 生产者和消费者模式
 * 
 * 这里模拟炸油条和吃油条
 * @author qupeng
 */
public class ProducerAndConsumerTest01 {
	

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
		
		public synchronized void make(String name){
			while(flag){
				try {
					this.wait();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.name="第"+count+"号"+name;
			count++;
			System.out.println(Thread.currentThread().getName()+
					"：生产者====>我炸好了"+this.name);
			flag=true;
			this.notifyAll();
		}
		public synchronized void eat(){
			while(!flag){
				try {
					this.wait();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()+
					"：消费者===========>我吃掉了"+this.name);
			flag=false;
			this.notifyAll();
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
