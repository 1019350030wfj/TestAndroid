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

## JVM

### Java内存区域划分
1. 方法区： 线程共享的，存储常量和静态变量
2. Java堆： 线程共享的， 存储类的成员变量和类的实例，比如 new 数组，new 字符串，new 类实例。 常说的内存回收就是针对这块区域
3. 虚拟机栈： 线程私有的， 存储编译时期可知的各种局部变量和对象引用
4. 本地方法栈： native方法
5. 程序计数器： 线程私有，记录每个虚拟机字节码指令的位置。 比如多线程编程，线程切换且获得cpu时间片执行，切换后，如何能够继续执行，就是靠它来记录位置的。

### Java 内存模型
1. 每个线程有自己的工作内存，存放方法体里面的局部变量
2. 共享变量存在主存中，每个线程含有共享变量的副本
3. 对共享变量的修改是先在自己线程内修改副本，然后更新到主存，以便其他线程获得最新值
4. 每个线程是不能互相访问对方的私有工作内存。这也解释了为什么方法间不能互相访问局部变量的原因


### JVM 重排序机制

    通常程序都是顺序执行的，但是有时候为了不让内存操作速度慢于CPU运行速度所带来的CPU闲置的影响。虚拟机会按照一定的规则让后面的程序优于前面的程序执行。只要不影响程序逻辑结果。
    
### JVM 原子性 可见性 有序性
1. 原子性： 要么执行完毕要么不执行。 对基本数据类型的读取和赋值是原子操作
2. 可见性： 多个线程访问同一个变量，一个线程对变量修改的值，在另一个线程能够立即看到改变后的值。 通过volatile、synchronized、lock能够保证共享变量的可见性
3. 有序性： 即程序执行是按照代码先后顺序执行。可以通过synchronized、lock来保证程序执行的有序性
4. volatile修饰的共享变量，线程访问都会到主存去获取最新值，它的工作内存暂时失效。






