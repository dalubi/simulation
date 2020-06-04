package com.ices.simulation.dao.mapper;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class cleanData {

    public static String DBDRIVER = "com.mysql.jdbc.Driver";

    public static String DB_URL = "jdbc:mysql://localhost:3306/elema?user=root&password=haohao123&useSSL=false";

    public static BasicDataSource bds = null;

    public void cleanActivitiData(){
        Statement stmt = null;
        Connection conn = null;

        try{
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.addBatch("delete from ACT_GE_BYTEARRAY");
            stmt.addBatch("delete from ACT_EVT_LOG");
            stmt.addBatch("delete from ACT_HI_ACTINST");
            stmt.addBatch("delete from ACT_HI_ATTACHMENT");
            stmt.addBatch("delete from ACT_HI_COMMENT");
            stmt.addBatch("delete from ACT_HI_DETAIL");
            stmt.addBatch("delete from ACT_HI_IDENTITYLINK");
            stmt.addBatch("delete from ACT_HI_PROCINST");
            stmt.addBatch("delete from ACT_HI_TASKINST");
            stmt.addBatch("delete from ACT_HI_VARINST");
            stmt.addBatch("delete from ACT_ID_GROUP");
            stmt.addBatch("delete from ACT_ID_INFO");
            stmt.addBatch("delete from ACT_ID_MEMBERSHIP");
            stmt.addBatch("delete from ACT_ID_USER");
            stmt.addBatch("delete from ACT_PROCDEF_INFO");
            stmt.addBatch("delete from ACT_RE_DEPLOYMENT");
            stmt.addBatch("delete from ACT_RE_MODEL");
            stmt.addBatch("delete from ACT_RU_DEADLETTER_JOB");
            stmt.addBatch("delete from ACT_RU_EVENT_SUBSCR");
            stmt.addBatch("delete from ACT_RU_IDENTITYLINK");
            stmt.addBatch("delete from ACT_RU_JOB");
            stmt.addBatch("delete from ACT_RU_SUSPENDED_JOB");
            stmt.addBatch("delete from ACT_RU_TIMER_JOB");
            stmt.addBatch("delete from ACT_RU_VARIABLE");
            stmt.addBatch("delete from ACT_RU_TASK");
            stmt.addBatch("delete from ACT_RU_EXECUTION");
            stmt.addBatch("delete from ACT_RE_PROCDEF");
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
