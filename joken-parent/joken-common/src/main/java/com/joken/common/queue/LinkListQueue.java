package com.joken.common.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 队列实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class LinkListQueue {
	/**
	 * 
	 * 队列节点类
	 * 
	 * @version V1.0.0, 2016-3-15
	 * @author 欧阳增高
	 * @since V1.0.0
	 */
	static class Node {
		volatile Object item;
		Node next;

		Node(Object x) {
			item = x;
		}
	}

	/**
	 * 队列数
	 */
	private final AtomicInteger count = new AtomicInteger(0);
	/**
	 * 队列首节点
	 */
	private transient Node head;
	/**
	 * 队列尾节点
	 */
	private transient Node last;
	/**
	 * 队列读锁
	 */
	private final ReentrantLock takeLock = new ReentrantLock();
	/**
	 * 条件锁对象
	 */
	private final Condition notEmpty;
	/**
	 * 队列put锁
	 */
	private final ReentrantLock putLock = new ReentrantLock();
	static final boolean $assertionsDisabled = !LinkListQueue.class
			.desiredAssertionStatus();

	private void signalNotEmpty() {
		ReentrantLock takeLock;
		takeLock = this.takeLock;
		try {
			takeLock.lock();
			notEmpty.signal();
		} catch (Exception e) {
		}
		if (takeLock != null)
			takeLock.unlock();
	}

	/**
	 * 插入一对象到队列中
	 * 
	 * @param item
	 *            需要插入到队列的处理业务对象
	 */
	private void insert(Object item) {
		last = last.next = new Node(item);
	}

	/**
	 * 队列中提取一个处理业务对象
	 * 
	 * @return 处理业务对象
	 */
	private Object extract() {
		Node first = head.next;
		head = null;
		head = first;
		Object x = first.item;
		first.item = null;
		head.item = null;
		first = null;
		return x;
	}

	/**
	 * 开启所有锁
	 */
	private void fullyLock() {
		putLock.lock();
		takeLock.lock();
	}

	/**
	 * 释放所有锁
	 */
	private void fullyUnlock() {
		takeLock.unlock();
		putLock.unlock();
	}

	/**
	 * 构造
	 */
	public LinkListQueue() {
		notEmpty = takeLock.newCondition();
		last = head = new Node(null);
	}

	/**
	 * 获取队列长度
	 * 
	 * @return 队列长度
	 */
	public int size() {
		return count.get();
	}

	/**
	 * 向队列提供指定业务处理对象
	 * 
	 * @param item
	 *            业务处理对象
	 * @return boolean
	 */
	public boolean offer(Object item) {
		int c = 0;
		ReentrantLock putLock = null;
		AtomicInteger count;
		try {
			if (item == null)
				throw new NullPointerException();
			c = -1;
			putLock = this.putLock;
			count = this.count;
			putLock.lock();
			insert(item);
			c = count.getAndIncrement();
		} catch (Exception e) {
		}
		if (putLock != null)
			putLock.unlock();
		if (c == 0)
			signalNotEmpty();
		return c >= 0;
	}

	/**
	 * 获取一个队列处理对象
	 * 
	 * @return Object
	 * @throws InterruptedException
	 */
	public Object take() throws InterruptedException {
		AtomicInteger count;
		ReentrantLock takeLock;
		int c = -1;
		count = this.count;
		takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		Object x = null;
		try {
			for (; count.get() == 0; notEmpty.await())
				;
		} catch (InterruptedException ie) {
			notEmpty.signal();
			throw ie;
		}
		try {
			x = extract();
			c = count.getAndDecrement();
			if (c > 1)
				notEmpty.signal();
		} catch (Exception e) {

		}
		if (takeLock != null)
			takeLock.unlock();
		return x;
	}

	/**
	 * 清空
	 */
	public void clear() {
		fullyLock();
		head.next = null;
		if (!$assertionsDisabled && head.item != null)
			throw new AssertionError();
		last = head;
		fullyUnlock();
		return;
	}
}
