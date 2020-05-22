如何自定义类加载器：

　　1、继承java.lang.ClassLoader

　　2、重写父类的findClass方法

　　父类有那么多方法，为什么偏偏只重写findClass方法？

　　因为JDK已经在loadClass方法中帮我们实现了ClassLoader搜索类的算法，当在loadClass方法中搜索不到类时，loadClass方法就会调用findClass方法来搜索类，所以我们只需重写该方法即可。如没有特殊的要求，一般不建议重写loadClass搜索类的算法。

   ref : org.nixk.clazz.loader.TestUnit.testClassLoader
    
双亲委托模型 

　　1、当前ClassLoader首先从自己已经加载的类中查询是否此类已经加载，如果已经加载则直接返回原来已经加载的类
    
　　2、当前classLoader的缓存中没有找到被加载的类的时候，委托父类加载器去加载，父类加载器采用同样的策略，首先查看自己的缓存，然后委托父类的父类去加载，一直到bootstrp ClassLoader.
    
　　3、当所有的父类加载器都没有加载的时候，再由当前的类加载器加载，并将其放入它自己的缓存中，以便下次有加载请求的时候直接返回。


　　说到这里大家可能会想，Java为什么要采用这样的委托机制？理解这个问题，我们引入另外一个关于Classloader的概念“命名空间”， 它是指要确定某一个类，需要类的全限定名以及加载此类的ClassLoader来共同确定。也就是说即使两个类的全限定名是相同的，但是因为不同的 ClassLoader加载了此类，那么在JVM中它是不同的类。明白了命名空间以后，我们再来看看委托模型。采用了委托模型以后加大了不同的 ClassLoader的交互能力，比如上面说的，我们JDK本生提供的类库，比如hashmap,linkedlist等等，这些类由bootstrp 类加载器加载了以后，无论你程序中有多少个类加载器，那么这些类其实都是可以共享的，这样就避免了不同的类加载器加载了同样名字的不同类以后造成混乱。

　　Class查找的位置和顺序依次是：Cache、parent、self

为什么要使用双亲委托这种模型呢？

   因为这样可以避免重复加载，当父亲已经加载了该类的时候，就没有必要子ClassLoader再加载一次。考虑到安全因素，我们试想一下，如果不使用这种委托模式，那我们就可以随时使用自定义的String来动态替代java核心api中定义的类型，这样会存在非常大的安全隐患，而双亲委托的方式，就可以避免这种情况，因为String已经在启动时就被引导类加载器（Bootstrcp ClassLoader）加载，所以用户自定义的ClassLoader永远也无法加载一个自己写的String，除非你改变JDK中ClassLoader搜索类的默认算法。


不遵循“双亲委托机制”的场景

   上面说了双亲委托机制主要是为了实现不同的ClassLoader之间加载的类的交互问题，被大家公用的类就交由父加载器去加载，但是Java中确实也存在父类加载器加载的类需要用到子加载器加载的类的情况。下面我们就来说说这种情况的发生。
    
   Java中有一个SPI(Service Provider Interface)标准,使用了SPI的库，比如JDBC，JNDI等，我们都知道JDBC需要第三方提供的驱动才可以，而驱动的jar包是放在我们应用程序本身的classpath的，而jdbc 本身的api是jdk提供的一部分，它已经被bootstrp加载了，那第三方厂商提供的实现类怎么加载呢？这里面JAVA引入了线程上下文类加载的概念，线程类加载器默认会从父线程继承，如果没有指定的话，默认就是系统类加载器（AppClassLoader）,这样的话当加载第三方驱动的时候，就可 以通过线程的上下文类加载器来加载。
   
   另外为了实现更灵活的类加载器OSGI以及一些Java app server也打破了双亲委托机制。