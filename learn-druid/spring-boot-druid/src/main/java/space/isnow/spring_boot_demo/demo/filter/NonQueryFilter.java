/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package space.isnow.spring_boot_demo.demo.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

/**
 * @author wenshao [szujobs@hotmail.com]
 */
public class NonQueryFilter extends FilterEventAdapter {

    private final static Log          LOG                        = LogFactory.getLog(NonQueryFilter.class);


    private String                    dbType;
    
    private boolean           statementExecutableSqlLogEnable      = true;

    public NonQueryFilter(){
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }


    @Override
    public synchronized void init(DataSourceProxy dataSource) {
        if (this.dbType == null || this.dbType.trim().length() == 0) {
            this.dbType = dataSource.getDbType();
        }

        configFromProperties(dataSource.getConnectProperties());
        configFromProperties(System.getProperties());
    }

    public void configFromProperties(Properties properties) {
        if (properties == null) {
            return;
        }
        {
            String prop = properties.getProperty("druid.log.stmt.executableSql");
            if ("true".equals(prop)) {
                statementExecutableSqlLogEnable = true;
            } else if ("false".equals(prop)) {
                statementExecutableSqlLogEnable = false;
            }
        }
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        internalAfterStatementExecute(statement, false, updateCount);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        internalAfterStatementExecute(statement, true);
    }

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        internalAfterStatementExecute(statement, firstResult);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        internalAfterStatementExecute(statement, false, result);

    }

    private final void internalAfterStatementExecute(StatementProxy statement, boolean firstResult,
                                                     int... updateCountArray) {
        String sql = statement.getLastExecuteSql();
        logExecutableSql(statement, sql);
    }
    
    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
    	// sql执行出错时，是否记录
    }
    
    private void logExecutableSql(StatementProxy statement, String sql) {
        if (!isStatementExecutableSqlLogEnable()) {
        	LOG.info(sql);
            return;
        }

        int parametersSize = statement.getParametersSize();
        if (parametersSize == 0) {
            statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n"
                         + sql);
            return;
        }

        List<Object> parameters = new ArrayList<Object>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            JdbcParameter jdbcParam = statement.getParameter(i);
            parameters.add(jdbcParam.getValue());
        }

        String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
        String formattedSql = SQLUtils.format(sql, dbType, parameters);
        statementLog(formattedSql);
//        statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n" + formattedSql);
    }
    
    private String stmtId(StatementProxy statement) {
        StringBuffer buf = new StringBuffer();
        if (statement instanceof CallableStatementProxy) {
            buf.append("cstmt-");
        } else if (statement instanceof PreparedStatementProxy) {
            buf.append("pstmt-");
        } else {
            buf.append("stmt-");
        }
        buf.append(statement.getId());

        return buf.toString();
    }
    
    protected void statementLog(String message) {
    	LOG.info(message);
    }
    
    public boolean isStatementExecutableSqlLogEnable() {
        return statementExecutableSqlLogEnable;
    }

    public void setStatementExecutableSqlLogEnable(boolean statementExecutableSqlLogEnable) {
        this.statementExecutableSqlLogEnable = statementExecutableSqlLogEnable;
    }
}
