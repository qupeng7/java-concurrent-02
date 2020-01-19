package com.qupeng.concurrent.day02.part1;


/**
 * 演示脏读
 * 对修改、删除、添加操作加锁，
 * 对查询操作不加锁时就会产生脏读
 * @author qupeng
 */
public class DirtyRead {
	
	static class Person{
		
		private String name;
		
		private boolean hitedSuccess=false;
		
		public  /*synchronized*/ void hited(String name,boolean hitedSuccess) throws InterruptedException{
			this.name=name;
			Thread.sleep(2000);
			this.hitedSuccess=hitedSuccess;
		}
		
		public /*synchronized*/ boolean see(String name){
			return this.hitedSuccess;
		}
		
	}
	

	public static void main(String[] args) throws InterruptedException {

		final Person p=new Person();
		new Thread("无相皇"){
			@Override
			public void run() {
				try {
					p.hited("幽灵密探",true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		Thread.sleep(1_000);
		
		System.out.println("幽灵密探被打中了么："+p.see("幽灵密探"));
		
		Thread.sleep(1_000);
		
		System.out.println("幽灵密探被打中了么："+p.see("幽灵密探"));
	}

}
