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

## 数据结构

### HashMap和HashTable的区别
1. HashMap是非线程安全的，HashTable是线程安全的。所以在多线程环境可以使用HashTable
2. HashMap允许key和value为null的值，而HashTable不允许key和value为null
3. 单线程环境HashMap执行速度快于HashTable
4. 数组初始化大小和扩容方式不同：HashTable默认初始化大小是11，而HashMap是16. 扩容为2*HashTable.size+1，2*HashMap.size
5. key-value放在数组位置的计算方式不一样。 HashTable是直接以key的hashcode与数组大小取模。而HashMap是以key的hashcode计算新的hash值，然后再以这个hash与数组大小取模。

## JVM
### 结构
1. 类加载系统：负责加载文件系统或来自网络的class文件
2. 5个Java内存区域
3. 垃圾回收系统： 负责内存回收
4. 执行引擎 ： 负责执行虚拟机的字节码

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

    通常程序都是顺序执行的，但是有时候为了不让内存操作速度慢于CPU运行速度所带来的CPU闲置的影响。
    虚拟机会按照一定的规则让后面的程序优于前面的程序执行。只要不影响程序逻辑结果。
    必须遵循happen-before原则， 也就是全面的操作结果对后面操作是可见的（有影响的），就必须遵循happen-before。 如果全面的操作对后面不影响，不可见，就无需遵循happen-before，也就是可以重排序

### JVM 原子性 可见性 有序性
1. 原子性： 要么执行完毕要么不执行。 对基本数据类型的读取和赋值是原子操作
2. 可见性： 多个线程访问同一个变量，一个线程对变量修改的值，在另一个线程能够立即看到改变后的值。 通过volatile、synchronized、lock能够保证共享变量的可见性
3. 有序性： 即程序执行是按照代码先后顺序执行。可以通过synchronized、lock来保证程序执行的有序性
4. volatile修饰的共享变量，线程访问都会到主存去获取最新值，它的工作内存暂时失效。

### 什么是GC

    是一种自动存储管理机制。 简单的说，就是一块被占用的内存区域在不需要使用的时候，就应该被释放，让出空间。
    
    Java堆分成三个部分： 年轻代（Eden、Survivor）、老年代、永久代
    
    Java内存分配和回收的机制概况的说就是：分代分配，分代回收
    
### GC会有什么影响

    不管哪种算法，都会造成 stop-the-world。 也就是当在执行GC的过程，会导致所有线程都会被停止。

### GC执行的时机
1. 当应用程序空闲的时候，因为GC在优先级最低的线程中执行，所以应用忙时，GC线程不会被调用
2. Java堆内存不足时，GC被调用。

### GC的执行步骤
1. 新创建的对象分配在年轻代的Eden中，执行一次Minor GC会把Eden区的没有被引用的对象清掉，有被引用的放入Servivor 0 中；
2. 在执行一次Minor GC会把Eden区和Servivor 0 区的没有被引用的对象清除掉，有被引用的放入Servivor 1中，且Servivor 0放入Servivor 1中的对象年龄域增加；
3. 在下一次Minor GC会把Eden区和Servivor 1区的没有被引用的对象清除掉，有被引用的放入Servivor 0中，且Servivor 1放入Servivor 0中的对象年龄域增加；
4. 循环上面几次操作后（有的说是8次，有的说是16次），还保留的对象将被移入老年代中
5. 老年代执行Major GC
6. 也就是说一开始先在年轻代反复的清理，顽强不死的被移入老年代清理

### 加快年轻代Eden区的内存分配
1. bump-the-pointer： 因为Eden区内存是连续的，所以只要检测最后分配对象的末尾是否有足够的内存空间，大大加快了内存分配速度
2. TLAB（Thread-Local Allocation Buffers），本地线程分配缓存区，对于多线程而言，将Eden区域分为若干段，每个线程拥有独立一段，互不影响；

### 类加载机制ClassLoader
**类加载器分类：**
1. BootStrap 启动类加载器 -》 主要是加载JVM虚拟机内部的类， 应用是无法引用的
2. Extension 扩展类加载器 -》 对JVM虚拟机扩展类的加载
3. Application 应用类加载器 -》 java应用程序默认的加载器， 继承自Extention
4. 自定义类加载器-> Android的DexClassLoader和PathClassLoader， 继承自ClassLoader，重写findClass方法（主要加载类的二进制字节流），然后由defineClass生成类对象

**什么时候加载，如何加载**
1. jvm是按需加载的， 只有当我们要用到的时候才去加载
2. 通过双亲委派模式，也就是都是先让父亲类加载器（Application-》Extention ， 当为null就用Bootstrap类加载器），当都没有报异常ClassNotFoundException
3. 双亲委派模式的优势：避免重复加载类；安全性，防止核心api库被篡改（比如我们从网络加载了java.lang.Integer，而这个类已经在启动类加载器加载过了） 

**原理**
1. 实际理解了java文件到为什么能够被jvm运行这个过程，就可以很好的理解类加载机制；
2. 首先java会被编译成class文件，然后再由类加载器按需加载的方式和利用双亲委派模式加载到jvm内存中
3. 类加载器又分为四类，分别是Bootstrap启动加载器、Extension扩展类加载器、Application应用类加载器、自定义加载器
4. 类加载器又是经过几个步骤才把类加载到jvm内存，分别是：加载、验证、准备、解析、初始化、使用、卸载

**类加载的步骤**
1. 加载： 通过类的全限定名加载类的二进制字节流；将静态数据结构的存储到jvm的方法区；在堆区生成class对象（二进制字节流-》class对象，由defineClass）;
2. 验证： 文件格式验证（是否是class文件格式的规范）；元数据验证（语义分析是否符合java语言规范）；字节码验证；符号验证
3. 准备： 正式为类变量分配内存和初始化默认值，比如 public static int value = 3。这时候value默认初始值为0
4. 解析： 类或接口的解析；字段解析；方法解析；接口方法解析；
5. 初始化： 真正执行类中的java程序代码或者说是执行类构造器（）方法的过程

### 参考资料
[一看你就懂，超详细java中的ClassLoader详解](https://blog.csdn.net/briblue/article/details/54973413)

### String StringBuffer 和 StringBuilder
1. String是字符串常量，StringBuffer和StringBuilder是字符串变量。对String变量的操作，实际都是在创建一个新的String对象，然后把指针指向新的对象。
2. StringBuffer是线程安全的，StringBuilder是非线程安全的。因此单线程下尽量用StringBuilder，因为它的效率比StringBuffer高。
3. String适用于少量的字符串操作，StringBuilder适用于在单线程下在字符缓冲区对字符串进行大量操作。

### ==、 equals和hashcode的区别
1. == 是运算符，通常用于比较基本数据类型是否相等。若是引用类型比较，则是比较他们的内存地址是否相等；
2. equals()方法是Object类的一个方法，默认实现也是比较两个对象的内存地址是否相等，等同于“==”
3. String 类是有对equals和hashcode进行重写，所以String类的equals方法比较的是字符串内容。
4. hashcode默认是用对象的内存地址计算hash值

## 多线程

**生产者与消费者**

```
生产者：生产者往池子（内存缓冲区）添加资源，若池子满的话，就需要等待，只有当自己生产的东西能够放入池子
消费者：消费者不断从池子获取资源，若池子空了，就需要等待，只有当池子的资源满足自己的需求
```

**并发与并行**
```
并行：多核多CPU或多处理器在同一时刻多个执行流共同执行
并发：主要利用了CPU的调度算法，让用户感觉好像在执行多个任务，但同一时刻只有一个执行流占用cpu
调度算法：1、分时调度（各个线程轮流使用cpu时间片，）；2、抢占式调度（利用线程优先级，优先调度优先级高的，优先级相等的随机调度）
```

**Thread与Runnable、Callable的区别**
1. Thread是线程，线程是用来执行任务的，而任务就是Runnale
2. Thread实现Runnale接口， Runnale是接口，Thread是类不支持多继承
3. Callable也是一个任务，不过它可以返回结果和当无法计算结果的时候会抛出异常

**提高锁性能**
1. 减少锁的持有时间，比如单利类，为什么不把synchronized放在方法上
2. 减小锁的力度，
3. 读写锁分离
4. 锁分离，比如比如ConcurrentHashMap，利用的是锁分段技术（每一段实际）

    synchronized关键字加锁的原理，其实是对对象加锁，不论你是在方法前加synchronized还是语句块前加，锁住的都是对象整体，但是ConcurrentHashMap的同步机制和这个不同，它不是加synchronized关键字，而是基于lock操作的，这样的目的是保证同步的时候，锁住的不是整个对象。

## NDK
1. Java->JNI->C/C++:  Java文件定义native本地方法，然后编写cpp或者c文件，方法名称为Java_包名_类名_方法名;
 JNI的数据转C/C++(jstring->std::string: 先从jstring中通过getBytes（）获取jbyteArray； 然后将jbyteArray设置给std::string)
 (jbyteArray->jbyte* ,通过GetByteArrayElements)
2. jni里面获取java对象， 可以通过msgData = env->FindClass("com/onesoft/MsgData")获取类,env->GetMethodID(类名msgData,"方法名/构造函数<init>","方法签名如：（ZLjava/lang/String;[I）J") ->long fun (boolean n, String str, int[] arr);

## 设计模式
1. 单例模式： [简述DCL失效原因，解决方法](https://blog.csdn.net/zhaojw_420/article/details/70477921)

1. RecyclerView的缓存机制优于ListView，屏外缓存无需重新绑定（缓存的是ViewHolder，flag），flag标志的是否需要重新绑定view
2. 如果需要动画、频繁刷新、局部刷新，就可以用RecyclerView
 
# Android

## View相关
 
 **自定义view如何保证高度和宽度自适应**
 1. view的获取默认大小，是没有区分EXACTLY和AT_MOST模式的，默认都是一样的大小
 2. 自定义view，可以设置一个期望的大小，然后判断是否是wrap_content属性值，是的话，取期望值与建议值的最小值，最大不能超过期望值；

## Listview缓存
1. Adapter的作用：listview是为了交互和展示数据，而数据来源就是由Adapter提供。避免了listview太重了，且Adapter是接口便于扩展（子类可以根据自己的逻辑去完成特定的功能、数据类型）
2. listview只会创建一屏的数据，当view不在屏幕的时候会被缓存到scrapview中，然后通过getScrapView方法获取缓存
3. 第一次layout通过LayoutInflate的inflate加载一屏的view；第二次layout将view添加到ActiveViews缓存；当滑动的时候，将view缓存到scrapViews中



