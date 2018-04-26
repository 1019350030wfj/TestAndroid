package com.jayden.officialandroiddemo.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/moongeek/p/7631447.html
 */
public class Storage implements AbsStorage {

    private static final int MAX_SIZE = 100; //池子最大容量
    private List mContainer = new ArrayList();//池子

    @Override
    public void produce(int num) {
        // 生产，当池子满了，就等待
        synchronized (mContainer) {//多线程操作，数据安全性
            while (mContainer.size() + num > MAX_SIZE) {
                //当 池子满了，等待
                System.out.println("【要生产的产品数量】:" + num + "\t【库存量】:" + mContainer.size() + "\t暂时不能执行生产任务!");
                try {
                    mContainer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //一直生产，就是个循环
            for (int i = 0; i < num; i++) {//生产指定数量
                mContainer.add(new Object());
            }
            mContainer.notifyAll();//生产完， 通知大家来取
        }
    }

    @Override
    public void consume(int num) {
        synchronized (mContainer){
            while (mContainer.size() < num) {
                //当池子为空了， 等待
                System.out.println("【要消费的产品数量】:" + num + "\t【库存量】:" + mContainer.size() + "\t暂时不能执行消费任务!");
                try {
                    mContainer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {//消费资源
                mContainer.remove(0);
            }
            mContainer.notifyAll();//池子有空间了，可以生产了
        }

    }
}
