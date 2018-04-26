## Android内存溢出和内存泄漏的区别

**概念：**

内存溢出是指程序在申请内存的时候，没有足够的内存可以分配，导致Out Of Memory错误，也就是OOM

内存泄漏对象都有生命周期，当生命周期结束时本来应该释放和回收，却没有得到释放一直存在于内存中，造成内存泄漏。随着内存泄漏的堆积，可用内存空间越来越少，最后导致内存溢出。



**怎么检测：**

1. Android Studio：Android Profiler 包含 CPU、MEMORY、NETWORK
2. MAT
3. LeakCanary

步骤： Heap Dump -》对生成的hprof文件进行分析 -》进行结果展示



**使用场景和如何解决：**

1. 内部类持有外部类的引用，将内部类设置为static，不隐式持有外部类的实例
2. 慎重使用Handler，当new 一个Handler的时候就会绑定一个Looper。消息处理晚于Activity的destroy，那么即使Activity销毁了，Activity还是没有释放和回收。因为消息被Looper持有，消息里面的view又持有Activity的context。 解决方法： 在Activity或者fragment的onDestroy方法使用handler.removeCallbacksAndMessages（null），来清空消息队列。
3. 注册和反注册成对存在，比如事件总线（EventBus）的使用
4. 单利模式持有Context
5. 线程造成内存泄漏，当线程执行的任务比Activity的生命周期长。 创建一个静态类，实现Runnable方法



**根源：**

对象实例被生命周期更长的对象持有，无法释放

**工具使用：**

1. 可以通过AS的Android Device Monitor或者Eclipse生成hprof文件
2. 在sdk目录下的platform-tools文件中运行命令：

```
hprof-conv 1.hprof 2.hprof
```

3. 利用Eclipse打开2.hprof文件
4. 可以点击Histogram，然后输入觉得会内存泄漏的对象，比如 MainActivity
5. 右键List Objects-》with incoming reference 列出所有持有该对象（MainActivity）的外部对象，即哪些对象持有了MainActivity
6. 在上面的外部对象中右键 Path To GC Roots， 可以列出对象的所有引用链



**参考资料：**

[Android 内存泄露和性能检测](http://blog.csdn.net/u012482178/article/details/78988176)

[MAT使用教程](https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.mat.ui.help%2Fwelcome.html)


## DP和SP的区别

**概念：**
1. DP=DIP（Density-Independent Pixel）密度无关像素，在Android中通常作为长度单位
2. SP（Scale-Independent Pixel）缩放像素， 在Android中通常作为字体大小单位
3. DPI（Dot Per Inch）每英寸上面的点
4. PPI（Pixel Per Inch）每英寸上面的像素

**计算：**
1. 计算公式 dp = （dpi/160）px
2. PPI= 屏幕对角线像素数/对角线长度 
3. 160dpi的设备，1dp = 1px
4. ldpi mdpi hdpi xhdpi xxhdpi 对应的dp和px比值 = 0.75：1：1.5：2：3

**核心区别：**
1. 主要区别在density和scaledDensity,可以通过源码TypedValue的applyDimension方法
2. sp可以在运行时去改变，比如在手机端的设置中去改变字体大小


## 多线程

**生产者与消费者**

```
生产者：生产者往池子（内存缓冲区）添加资源，若池子满的话，就需要等待，只有当自己生产的东西能够放入池子
消费者：消费者不断从池子获取资源，若池子空了，就需要等待，只有当池子的资源满足自己的需求
```


## 数据结构

### HashMap和HashTable的区别
1. HashMap是非线程安全的，HashTable是线程安全的。所以在多线程环境可以使用HashTable
2. HashMap允许key和value为null的值，而HashTable不允许key和value为null
3. 单线程环境HashMap执行速度快于HashTable
4. 数组初始化大小和扩容方式不同：HashTable默认初始化大小是11，而HashMap是16. 扩容为2*HashTable.size+1，2*HashMap.size
5. key-value放在数组位置的计算方式不一样。 HashTable是直接以key的hashcode与数组大小取模。而HashMap是以key的hashcode计算新的hash值，然后再以这个hash与数组大小取模。









