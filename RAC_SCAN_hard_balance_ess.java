

/*******************************************************************************

 FILE NAME      : RAC_SCAN_hard_balance_ess.sql
 DESCRIPTION    : Example of connection using SCAN
 AUTHOR         : Antonio NAVARRO - /\/\/
 CREATE         : 22.10.15
 LAST MODIFIED  : 06.01.18
 USAGE          : Example of connection using SCAN, explicit IPs
 CALL SYNTAXIS  : java balanceo2
 [NOTES]        :    


********************************************************************************/

import java.sql.*;

public class balanceo2 {

  public static void main(String[] s)throws Exception {

    Class.forName("oracle.jdbc.OracleDriver");

    String url="jdbc:oracle:thin:@(DESCRIPTION= (LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=10.501.182.94) (PORT=4580))(ADDRESS=(PROTOCOL=TCP)(HOST=10.501.182.95) (PORT=4580))(ADDRESS=(PROTOCOL=TCP)(HOST=10.501.182.96) (PORT=4580))(CONNECT_DATA=(SERVICE_NAME=dbtest.company.com)))"; 

    /*** String url ="jdbc:oracle:thin:@abc.us.oracle.com:23001:xyz";   ***/
   

    for (int i=0; i<5000; i++) {
      try {
        long x= System.currentTimeMillis () ;
        Connection conn = DriverManager.getConnection(url,"USERDBA","SECRET");
        long y= System.currentTimeMillis ();

        System.out.println("Connection Succesful "+conn);
        System.out.println("Connection time is "+(y-x)/1000+" ms");
        Statement stmt =conn.createStatement();
        ResultSet res= stmt.executeQuery(" select host_name from v$instance");

        while(res.next()) {
          System.out.println(res.getString(1));
        }
        stmt.close();
        conn.close();
      }
      catch(Exception e) { 
        e.printStackTrace();
      }

    }
    
  }
  
}

