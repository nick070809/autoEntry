Service Provider Interface，是一种服务发现机制。它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类。

实现原理

   java.util.ServiceLoader中定义了/META-INF/services/,把这些定义的service的qualifiedClassName找出来，然后通过反射实例化；
   
    private static final String PREFIX = "META-INF/services/";
    //loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
    Class c = Class.forName(qualifiedClassName, false, loader);
    S p = c.newInstance();
    providers.put(cn, p);
    retrun p;
    
在JDBC的应用

   打开mysql-connector-java.jar,/META-INF/services/java.sql.Driver
   
    com.mysql.jdbc.Driver
    com.mysql.fabric.jdbc.FabricMySQLDriver
    
   java.sql.DriverManager
   
    static {
             loadInitialDrivers();
             println("JDBC DriverManager initialized");
         }
   DriverManager类，它在静态代码块里面做了一件比较重要的事。很明显，它已经通过SPI机制， 把数据库驱动连接初始化了。
   
    loadInitialDrivers:
    ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        try{
            //查到之后创建对象
            while(driversIterator.hasNext()) {
                driversIterator.next();
            }
        } catch(Throwable t) {
            // Do nothing
        }
        return null;
    }
    
    
使用场景

   一个接口下多个实现，通过spi指定其中一个进行使用 
   参考 org.nixk.AppTest.spi
   
        public void spi()  {
            ServiceLoader<QueryInterface> serviceLoader = ServiceLoader.load(QueryInterface.class);//查找spi的实现,未实例化
            Iterator<QueryInterface> searchs = serviceLoader.iterator(); //重新了iterator ，进行实例化
            while(searchs.hasNext()) {
                QueryInterface queryInterface = searchs.next();
                System.out.println(queryInterface.getClass());
                System.out.println(queryInterface.query());
                System.out.println("=======");
            }
        }
   
   java.util.ServiceLoader.iterator
        
        public Iterator<S> iterator() {
            return new Iterator<S>() {
                Iterator<Map.Entry<String,S>> knownProviders  = providers.entrySet().iterator();
                public boolean hasNext() {
                    if (knownProviders.hasNext())
                        return true;
                    return lookupIterator.hasNext();
                }
                public S next() {
                    if (knownProviders.hasNext())
                        return knownProviders.next().getValue();
                    return lookupIterator.next();
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
    
            };
        }

 
        
    
    
    
    
    
    
    
    
    
    
     