Service Provider Interface����һ�ַ����ֻ��ơ���ͨ����ClassPath·���µ�META-INF/services�ļ��в����ļ����Զ������ļ�����������ࡣ

ʵ��ԭ��

   java.util.ServiceLoader�ж�����/META-INF/services/,����Щ�����service��qualifiedClassName�ҳ�����Ȼ��ͨ������ʵ������
   
    private static final String PREFIX = "META-INF/services/";
    //loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
    Class c = Class.forName(qualifiedClassName, false, loader);
    S p = c.newInstance();
    providers.put(cn, p);
    retrun p;
    
��JDBC��Ӧ��

   ��mysql-connector-java.jar,/META-INF/services/java.sql.Driver
   
    com.mysql.jdbc.Driver
    com.mysql.fabric.jdbc.FabricMySQLDriver
    
   java.sql.DriverManager
   
    static {
             loadInitialDrivers();
             println("JDBC DriverManager initialized");
         }
   DriverManager�࣬���ھ�̬�������������һ���Ƚ���Ҫ���¡������ԣ����Ѿ�ͨ��SPI���ƣ� �����ݿ��������ӳ�ʼ���ˡ�
   
    loadInitialDrivers:
    ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        try{
            //�鵽֮�󴴽�����
            while(driversIterator.hasNext()) {
                driversIterator.next();
            }
        } catch(Throwable t) {
            // Do nothing
        }
        return null;
    }
    
    
ʹ�ó���

   һ���ӿ��¶��ʵ�֣�ͨ��spiָ������һ������ʹ�� 
   �ο� org.nixk.AppTest.spi
   
        public void spi()  {
            ServiceLoader<QueryInterface> serviceLoader = ServiceLoader.load(QueryInterface.class);//����spi��ʵ��,δʵ����
            Iterator<QueryInterface> searchs = serviceLoader.iterator(); //������iterator ������ʵ����
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

 
        
    
    
    
    
    
    
    
    
    
    
     