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









