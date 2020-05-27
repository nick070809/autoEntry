在 Mac OS X 上安装 Flink 是非常方便的。推荐通过 homebrew 来安装。

    brew install apache-flink

检查安装：
    
    flink --version
    Version: 1.10.1, Commit ID: c5915cf
    
启动 flink
    
    cd /usr/local/Cellar/apache-flink/1.10.1/libexec/bin 
    ./start-cluster.sh
    ex:  NoClassDefFoundError: org/apache/hadoop/yarn/exceptions/YarnException
    原因：没有将官方指定Pre-bundled Hadoop 2.7.5包放到flink的lib目录下,ref https://blog.csdn.net/Alex_Sheng_Sea/article/details/102607937
    fix: 下载flink-shaded-hadoop.jar
    cp /Users/xianguang/Downloads/flink-shaded-hadoop-2-uber-2.7.5-7.0.jar  /usr/local/Cellar/apache-flink/1.10.1/libexec/lib
    again
    ./start-cluster.sh
    Starting cluster.
    Starting standalonesession daemon on host MacBook-Pro-7.local.
    Starting taskexecutor daemon on host MacBook-Pro-7.local.
    
    see http://localhost:8081/
    
进入 flink 安装目录 bin 下执行以下命令跑程序：

    ref org.nixk.SocketTextStreamWordCount
    nc -l 9000
    
   
    右键项目打开Open Module Setting选择其中的Artifacts
    点击加号选择JAR
    选择From modules with dependency
    选择Main Class,然后点击Ok
    在工具栏Build中选择Build Artifacts即可
    out_directory:/Users/xianguang/IdeaProjects/nick070809/autoEntry/out/artifacts/flink_jar
    在本地运行的代码是, 先将Flink启动,bin下的start-cluster.sh
    bin下的Flink run -c 之前点击选择的Main Class jar包的全称.(这里注意,一定要确定jar包中Main Class的文件路径是否正确,如果Scala包含了多级包的话,那就是包名.MainClass名,这里的class是之前导出Jar包中选择的Main class)
    关闭Flink为,stop-cluster.sh
    
    flink run -c org.nixk.SocketTextStreamWordCount /Users/xianguang/IdeaProjects/nick070809/autoEntry/out/artifacts/flink_jar/flink.jar 127.0.0.1 9000
    Job has been submitted with JobID ce79c28205a1afac0d68e3daed156a59
    
    
    tail -200f flink-xianguang-taskexecutor-0-MacBook-Pro-7.local.out
    (nixk,1)
    (de,1)
    (mammd,1)
    (jadhfkjads,1)
    (nfjkdmncvjfiao,1)
    (hello,2)
    (nihao,2)
    
datasource 数据源/流式数据
dataset    数据集/批处理，一次性数据

原理学习
   
   1、数据定义
   
    无边界： 有开始无结尾 ， 系统日志/信用卡账单
    有边界： 有开始和结尾 ， 静态文本
   
   2、计算资源  
      
    可运行在hadoop yarn / apache mesos / kubernetes / stand-alone

   3、JobManager
   
    JobManager：主要负责调度task，协调checkpoint已经错误恢复等。当客户端将打包好的任务提交到JobManager之后，JobManager就会根据注册的TaskManager资源信息将任务分配给有资源的TaskManager，然后启动运行任务。TaskManger从JobManager获取task信息，然后使用slot资源运行task；
    TaskManager：执行数据流的task，一个task通过设置并行度，可能会有多个subtask。 每个TaskManager都是作为一个独立的JVM进程运行的。他主要负责在独立的线程执行的operator。其中能执行多少个operator取决于每个taskManager指定的slots数量。Task slot是Flink中最小的资源单位。假如一个taskManager有3个slot，他就会给每个slot分配1/3的内存资源，目前slot不会对cpu进行隔离。同一个taskManager中的slot会共享网络资源和心跳信息。
    当然在Flink中并不是一个slot只可以执行一个task，在某些情况下，一个slot中也可能执行多个task.一般情况下，flink都是默认允许共用slot的，即便不是相同的task，只要都是来同一个job即可。共享slot的好处有以下两点： 1. 当Job的最高并行度正好和flink集群的slot数量相等时，则不需要计算总的task数量。例如，最高并行度是6时，则只需要6个slot，各个subtask都可以共享这6个slot； 2. 共享slot可以优化资源管理。 非资源密集型subtask source/map在不共享slot时会占用6个slot，而在共享的情况下，可以保证其他的资源密集型subtask也能使用这6个slot，保证了资源分配。

   4、编程
    1）、sql
    2）、table api
    3) 、dataStream / dataSet api
    4) 、stateful stream processing

   5、什么是 Window
    
    在流处理应用中，数据是连续不断的，因此我们不可能等到所有数据都到了才开始处理。当然我们可以每来一个消息就处理一次，但是有时我们需要做一些聚合类的处理，例如：在过去的1分钟内有多少用户点击了我们的网页。在这种情况下，我们必须定义一个窗口，用来收集最近一分钟内的数据，并对这个窗口内的数据进行计算。
    
    窗口可以是时间驱动的（Time Window，例如：每30秒钟），也可以是数据驱动的（Count Window，例如：每一百个元素）。一种经典的窗口分类可以分成：翻滚窗口（Tumbling Window，无重叠），滚动窗口（Sliding Window，有重叠），和会话窗口（Session Window，活动间隙）。
    
   6、Time Window
   
     就如名字所说的，Time Window 是根据时间对数据流进行分组的。这里我们涉及到了流处理中的时间问题，时间问题和消息乱序问题是紧密关联的，这是流处理中现存的难题之一，我们将在后续的 EventTime 和消息乱序处理中对这部分问题进行深入探讨。这里我们只需要知道 Flink 提出了三种时间的概念，分别是event time（事件时间：事件发生时的时间），ingestion time（摄取时间：事件进入流处理系统的时间），processing time（处理时间：消息被计算处理的时间）。Flink 中窗口机制和时间类型是完全解耦的，也就是说当需要改变时间类型时不需要更改窗口逻辑相关的代码。     

   6.1 、Tumbling Time Window翻滚时间窗口
   
    我们需要统计每一分钟中用户购买的商品的总数，需要将用户的行为事件按每一分钟进行切分，这种切分被成为翻滚时间窗口（Tumbling Time Window）。翻滚窗口能将数据流切分成不重叠的窗口，每一个事件只能属于一个窗口。通过使用 DataStream API 
    val tumblingCnts: DataStream[(Int, Int)] = buyCnts
      // key stream by userId
      .keyBy(0) 
      // tumbling time window of 1 minute length
      .timeWindow(Time.minutes(1))
      // compute sum over buyCnt
      .sum(1)
    
   6.2、Sliding Time Window滑动时间窗口
    
    但是对于某些应用，它们需要的窗口是不间断的，需要平滑地进行窗口聚合。比如，我们可以每30秒计算一次最近一分钟用户购买的商品总数。这种窗口我们称为滑动时间窗口（Sliding Time Window）。在滑窗中，一个元素可以对应多个窗口。通过使用 DataStream API    
    
   7、Count Window
   Count Window 是根据元素个数对数据流进行分组的。
   
   7.1、Tumbling Count Window
   
    当我们想要每100个用户购买行为事件统计购买总数，那么每当窗口中填满100个元素了，就会对窗口进行计算，这种窗口我们称之为翻滚计数窗口（Tumbling Count Window） 。通过使用 DataStream API 
    
   7.2、Sliding Count Window
   
    当然Count Window 也支持 Sliding Window， 和Sliding Time Window含义是类似的，例如计算每10个元素计算一次最近100个元素的总和 
    
   8、Session Window
    
    在这种用户交互事件流中，我们首先想到的是将事件聚合到会话窗口中（一段用户持续活跃的周期），由非活跃的间隙分隔开。如上图所示，就是需要计算每个用户在活跃期间总共购买的商品数量，如果用户30秒没有活动则视为会话断开（假设raw data stream是单个用户的购买行为流）
    
   Flink 中定义一个窗口主要需要以下三个组件。
   
   WindowAssigner：用来决定某个元素被分配到哪个/哪些窗口中去。 
   
   Trigger：触发器。决定了一个窗口何时能够被计算或清除，每个窗口都会拥有一个自己的Trigger。
   
   Evictor：可以译为“驱逐者”。在Trigger触发之后，在窗口被处理之前，Evictor（如果有Evictor的话）会用来剔除窗口中不需要的元素，相当于一个filter。
   
   这三个组件位于一个算子（window operator）中，数据流源源不断地进入算子，每一个到达的元素都会被交给 WindowAssigner。WindowAssigner 会决定元素被放到哪个或哪些窗口（window），可能会创建新窗口。因为一个元素可以被放入多个窗口中，所以同时存在多个窗口是可能的。注意，Window本身只是一个ID标识符，其内部可能存储了一些元数据，如TimeWindow中有开始和结束时间，但是并不会存储窗口中的元素。窗口中的元素实际存储在 Key/Value State 中，key为Window，value为元素集合（或聚合值）。为了保证窗口的容错性，该实现依赖了 Flink 的 State 机制。
   
   每一个窗口都拥有一个属于自己的 Trigger，Trigger上会有定时器，用来决定一个窗口何时能够被计算或清除。每当有元素加入到该窗口，或者之前注册的定时器超时了，那么Trigger都会被调用。Trigger的返回结果可以是 continue（不做任何操作），fire（处理窗口数据），purge（移除窗口和窗口中的数据），或者 fire + purge。一个Trigger的调用结果只是fire的话，那么会计算窗口并保留窗口原样，也就是说窗口中的数据仍然保留不变，等待下次Trigger fire的时候再次执行计算。一个窗口可以被重复计算多次直到它被 purge 了。在purge之前，窗口会一直占用着内存。
   
   当Trigger fire了，窗口中的元素集合就会交给Evictor（如果指定了的话）。Evictor 主要用来遍历窗口中的元素列表，并决定最先进入窗口的多少个元素需要被移除。剩余的元素会交给用户指定的函数进行窗口的计算。如果没有 Evictor 的话，窗口中的所有元素会一起交给函数进行计算。
   
   计算函数收到了窗口的元素（可能经过了 Evictor 的过滤），并计算出窗口的结果值，并发送给下游。窗口的结果值可以是一个也可以是多个。DataStream API 上可以接收不同类型的计算函数，包括预定义的sum(),min(),max()，还有 ReduceFunction，FoldFunction，还有WindowFunction。WindowFunction 是最通用的计算函数，其他的预定义的函数基本都是基于该函数实现的。
   
   Flink 对于一些聚合类的窗口计算（如sum,min）做了优化，因为聚合类的计算不需要将窗口中的所有数据都保存下来，只需要保存一个result值就可以了。每个进入窗口的元素都会执行一次聚合函数并修改result值。这样可以大大降低内存的消耗并提升性能。但是如果用户定义了 Evictor，则不会启用对聚合窗口的优化，因为 Evictor 需要遍历窗口中的所有元素，必须要将窗口中所有元素都存下来。
   
   在 KeyedStream 上创建 Count Window，其源码如下所示：
    
    // tumbling count window
    public WindowedStream<T, KEY, GlobalWindow> countWindow(long size) {
      return window(GlobalWindows.create())  // create window stream using GlobalWindows
          .trigger(PurgingTrigger.of(CountTrigger.of(size))); // trigger is window size
    }
    
    // sliding count window
    public WindowedStream<T, KEY, GlobalWindow> countWindow(long size, long slide) {
      return window(GlobalWindows.create())
        .evictor(CountEvictor.of(size))  // evictor is window size
        .trigger(CountTrigger.of(slide)); // trigger is slide size
    }
    
   第一个函数是申请翻滚计数窗口，参数为窗口大小。第二个函数是申请滑动计数窗口，参数分别为窗口大小和滑动大小。它们都是基于 GlobalWindows 这个 WindowAssigner 来创建的窗口，该assigner会将所有元素都分配到同一个global window中，所有GlobalWindows的返回值一直是 GlobalWindow 单例。基本上自定义的窗口都会基于该assigner实现。
   
   翻滚计数窗口并不带evictor，只注册了一个trigger。该trigger是带purge功能的 CountTrigger。也就是说每当窗口中的元素数量达到了 window-size，trigger就会返回fire+purge，窗口就会执行计算并清空窗口中的所有元素，再接着储备新的元素。从而实现了tumbling的窗口之间无重叠。
   
   滑动计数窗口的各窗口之间是有重叠的，但我们用的 GlobalWindows assinger 从始至终只有一个窗口，不像 sliding time assigner 可以同时存在多个窗口。所以trigger结果不能带purge，也就是说计算完窗口后窗口中的数据要保留下来（供下个滑窗使用）。另外，trigger的间隔是slide-size，evictor的保留的元素个数是window-size。也就是说，每个滑动间隔就触发一次窗口计算，并保留下最新进入窗口的window-size个元素，剔除旧元素。
   
   同样的，我们也可以在 KeyedStream 上申请 Time Window，其源码如下所示：
   
    // tumbling time window
    public WindowedStream<T, KEY, TimeWindow> timeWindow(Time size) {
      if (environment.getStreamTimeCharacteristic() == TimeCharacteristic.ProcessingTime) {
        return window(TumblingProcessingTimeWindows.of(size));
      } else {
        return window(TumblingEventTimeWindows.of(size));
      }
    }
    // sliding time window
    public WindowedStream<T, KEY, TimeWindow> timeWindow(Time size, Time slide) {
      if (environment.getStreamTimeCharacteristic() == TimeCharacteristic.ProcessingTime) {
        return window(SlidingProcessingTimeWindows.of(size, slide));
      } else {
        return window(SlidingEventTimeWindows.of(size, slide));
      }
    }
    
   在方法体内部会根据当前环境注册的时间类型，使用不同的WindowAssigner创建window。可以看到，EventTime和IngestTime都使用了XXXEventTimeWindows这个assigner，因为EventTime和IngestTime在底层的实现上只是在Source处为Record打时间戳的实现不同，在window operator中的处理逻辑是一样的。
   
SlidingProcessingTimeWindows会对每个进入窗口的元素根据系统时间分配到(size / slide)个不同的窗口，并会在每个窗口上根据窗口结束时间注册一个定时器（相同窗口只会注册一份），当定时器超时时意味着该窗口完成了，这时会回调对应窗口的Trigger的onProcessingTime方法，返回FIRE_AND_PURGE，也就是会执行窗口计算并清空窗口。

为简化问题，时间戳从0开始，第一条record会被分配到[-5,5)和[0,10)两个窗口中，当系统时间到5时，就会计算[-5,5)窗口中的数据，并将结果发送出去，最后清空窗口中的数据，释放该窗口资源。