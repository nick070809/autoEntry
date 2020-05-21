com.mysql.jdbc.Driver

    public class Driver extends NonRegisteringDriver implements java.sql.Driver {
        public Driver() throws SQLException {
        }
    
        static {
            try {
                //java.sql.DriverManager  jdk/src/
                DriverManager.registerDriver(new Driver());
                //CopyOnWriteArrayList<DriverInfo> registeredDrivers.add(this)
            } catch (SQLException var1) {
                throw new RuntimeException("Can't register driver!");
            }
        }
    }
    
Á´½Ó
    
    public  static Connection getConnection(){
            try {
                 Class.forName(DRIVER); 
                 //URL="jdbc:mysql:***";
                 return DriverManager.getConnection(URL,USERNAME,PASSWORD);
                 //com.mysql.jdbc.NonRegisteringDriver.connect(url,property)
                 //return   ConnectionImpl.getInstance(this.host(props), this.port(props), props, this.database(props), url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    
    for(DriverInfo aDriver : registeredDrivers) { 
         return aDriver.driver.connect(url, info);           
    }
    
DML
    
    statement=connection.createStatement();
    String sql="insert into user(loginName,userName,password,sex)values('tom123','tom','123456',1)";
    statement.executeUpdate(sql);
    /*
        public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            this.checkClosed();
            StatementImpl stmt = new StatementImpl(this.getLoadBalanceSafeProxy(), this.database);
            stmt.setResultSetType(resultSetType);
            stmt.setResultSetConcurrency(resultSetConcurrency);
            return stmt;
        }
    */