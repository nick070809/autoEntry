在 java.sql.DriverManager.loadInitialDrivers 中有2处使用了AccessController.doPrivileged();不太明白这个方法的作用；

当运行未知的Java程序的时候，该程序可能有恶意代码（删除系统文件、重启系统等），为了防止运行恶意代码对系统产生影响，需要对运行的代码的权限进行控制，这时候就要启用Java安全管理器。