
/*******************************************************************************

 FILE NAME      : retry_statpack_realtime.java
 DESCRIPTION    : Display statpack in realtime
 AUTHOR         : Antonio NAVARRO - /\/\/
 CREATE         : 22.10.15
 LAST MODIFIED  : 06.01.18
 USAGE          : Display statpack in realtime
 CALL SYNTAXIS  :
 [NOTES]        :

  This program as based in statpack_rt view, for get the data in real time

           create view statpack_rt AS
           select
              Z.VALUE col0,
              A.VALUE col1,
              B.VALUE col2,
              C.VALUE col3,
              D.VALUE col4,
              E.VALUE col5,
              F.VALUE col6,
              G.VALUE col7,
              H.VALUE col8,
              I.VALUE col9,
              J.VALUE col10,
              K.VALUE col11,
              L.VALUE col12,
              M.VALUE col13
           from
              (select value from v$sysstat where STATISTIC# = 115) Z,
              (select value from v$sysstat where STATISTIC# = 9) A,
              (select value from v$sysstat where STATISTIC# = 43) B,
              (select value from v$sysstat where STATISTIC# = 42) C,
              (select value from v$sysstat where STATISTIC# = 46) D,
              (select value from v$sysstat where STATISTIC# = 6) E,
              (select value from v$sysstat where STATISTIC# = 232) F,
              (select value from v$sysstat where STATISTIC# = 233) G,
              (select value from v$sysstat where STATISTIC# = 242) H,
              (select value from v$sysstat where STATISTIC# = 243) I,
              (select value from v$sysstat where STATISTIC# = 0) J,
              (select value from v$sysstat where STATISTIC# = 235) K,
              (select value from v$sysstat where STATISTIC# = 5) L,
              (select value from v$sysstat where STATISTIC# = 4) M
           /


****************************************************************************/

import java.io.*;
import java.sql.*;

public class anr_probe1 {
  public static void main(String[] args) {

  int  Retardo 	    = 1        ;  /*** Value for the interval    ***/
  int  Iteraciones  = 86400    ;  /*** Iteractions for 24 hours  ***/
  int  i            = 0        ;  /*** counter                   ***/

  long RedoSize                ;
  long LogicalReads            ;
  long BlockChanges            ;
  long PhysicalReads           ;
  long PhysicalWrites          ;
  long UserCalls               ;
  long Parses                  ;
  long HardParses              ;
  long SortsMemory             ;
  long SortsDisk               ;
  long Logons                  ;
  long Executes                ;
  long Urol                    ;
  long Ucom                    ;

  long OldRedoSize       = 0   ;
  long OldLogicalReads   = 0   ;
  long OldBlockChanges   = 0   ;
  long OldPhysicalReads  = 0   ;
  long OldPhysicalWrites = 0   ;
  long OldUserCalls      = 0   ;
  long OldParses         = 0   ;
  long OldHardParses     = 0   ;
  long OldSortsMemory    = 0   ;
  long OldSortsDisk      = 0   ;
  long OldLogons         = 0   ;
  long OldExecutes       = 0   ;
  long OldUrol           = 0   ;
  long OldUcom           = 0   ;


    /*** from string to int ***/
    try {
      Retardo     = Integer.parseInt(args [0]);
    }
    catch (NumberFormatException nx) {
      System.out.println  (">>> Error getting the delay (Seconds) ... ");
    }


    try {
      Connection con=null;
      Class.forName("oracle.jdbc.driver.OracleDriver");
      con=DriverManager.getConnection("jdbc:oracle:thin:@bbddnet:1521:prod","anavarro","secret");

      /*** A first read for descart peaks ***/
      Statement stmtPrev = con.createStatement();
      ResultSet rsetPrev = stmtPrev.executeQuery("select col0, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13 from statpack_rt");

      rsetPrev.next();
      OldRedoSize       = rsetPrev.getLong("col0");
      OldLogicalReads   = rsetPrev.getLong("col1");
      OldBlockChanges   = rsetPrev.getLong("col2");
      OldPhysicalReads  = rsetPrev.getLong("col3");
      OldPhysicalWrites = rsetPrev.getLong("col4");
      OldUserCalls      = rsetPrev.getLong("col5");
      OldParses         = rsetPrev.getLong("col6");
      OldHardParses     = rsetPrev.getLong("col7");
      OldSortsMemory    = rsetPrev.getLong("col8");
      OldSortsDisk      = rsetPrev.getLong("col9");
      OldLogons         = rsetPrev.getLong("col10");
      OldExecutes       = rsetPrev.getLong("col11");
      OldUrol           = rsetPrev.getLong("col12");
      OldUcom           = rsetPrev.getLong("col13");

      stmtPrev.close();              /*** close cursor ***/



while (i <= Iteraciones) {
      Statement stmt = con.createStatement();
      ResultSet rset = stmt.executeQuery("select col0, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13 from statpack_rt");
      while (rset.next())
      {

      	    RedoSize       = rset.getLong("col0");
      	    LogicalReads   = rset.getLong("col1");
            BlockChanges   = rset.getLong("col2");
            PhysicalReads  = rset.getLong("col3");
            PhysicalWrites = rset.getLong("col4");
            UserCalls      = rset.getLong("col5");
            Parses         = rset.getLong("col6");
            HardParses     = rset.getLong("col7");
            SortsMemory    = rset.getLong("col8");
            SortsDisk      = rset.getLong("col9");
            Logons         = rset.getLong("col10");
            Executes       = rset.getLong("col11");
            Urol           = rset.getLong("col12");
            Ucom           = rset.getLong("col13");

            System.out.print    (" " + ((RedoSize       - OldRedoSize       ) / Retardo)   );
      	    System.out.print    (" " + ((LogicalReads   - OldLogicalReads   ) / Retardo)   );
            System.out.print    (" " + ((BlockChanges   - OldBlockChanges   ) / Retardo)   );
            System.out.print    (" " + ((PhysicalReads  - OldPhysicalReads  ) / Retardo)   );
            System.out.print    (" " + ((PhysicalWrites - OldPhysicalWrites ) / Retardo)   );
            System.out.print    (" " + ((UserCalls      - OldUserCalls      ) / Retardo)   );
            System.out.print    (" " + ((Parses         - OldParses         ) / Retardo)   );
            System.out.print    (" " + ((HardParses     - OldHardParses     ) / Retardo)   );
            System.out.print    (" " + ((SortsMemory    - OldSortsMemory    ) / Retardo)   );
            System.out.print    (" " + ((SortsDisk      - OldSortsDisk      ) / Retardo)   );
            System.out.print    (" " + ((Logons         - OldLogons         ) / Retardo)   );
            System.out.print    (" " + ((Executes       - OldExecutes       ) / Retardo)   );
            System.out.print    (" " + ((Urol           - OldUrol           ) / Retardo)   );
            System.out.println  (" " + ((Ucom            - OldUcom          ) / Retardo)   );

            OldRedoSize       = RedoSize        ;
            OldLogicalReads   = LogicalReads    ;
            OldBlockChanges   = BlockChanges    ;
            OldPhysicalReads  = PhysicalReads   ;
            OldPhysicalWrites = PhysicalWrites  ;
            OldUserCalls      = UserCalls       ;
            OldParses         = Parses          ;
            OldHardParses     = HardParses      ;
            OldSortsMemory    = SortsMemory     ;
            OldSortsDisk      = SortsDisk       ;
            OldLogons         = Logons          ;
            OldExecutes       = Executes        ;
            OldUrol           = Urol            ;
            OldUcom           = Ucom            ;



      }
      stmt.close();              /*** Close the cursor ***/

      i = i + 1;
      Thread.sleep(1000 * Retardo);        /*** delay of 5 seconds ***/
}


      con.close();
   } catch(Exception e){e.printStackTrace();}
 }
}

                                                                  
                                                                                                                                                                   
                                                                                                                                                                   
                                                                                                                                                                   
                                                                                                                                                                   