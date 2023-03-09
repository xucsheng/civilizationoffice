package org.jeecg.config.mybatis;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.DataBaseConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhtao
 * @descript 拦截修改语句，增加更新时间
 * @date 2023/1/29 17:04
 */
@Component
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class DBDeleteInterceptor extends AbstractSqlParserHandler implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()) {

            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            //获取已经构造好的SQL
            String sql = boundSql.getSql();
            //获取映射的参数
            List<ParameterMapping> mappings = new ArrayList(boundSql.getParameterMappings());
            //假如参数中不包含要构造的参数，手动写入
            if (sql.indexOf(DataBaseConstant.UPDATE_TIME_TABLE) == -1) {
                LoginUser sysUser = this.getLoginUser();
                if (sysUser != null) {
                    //写入更新时间和操作人
                    sql = sql.replace("SET", "SET " + DataBaseConstant.UPDATE_TIME_TABLE + " = '" + new Timestamp(System.currentTimeMillis()) + "', " + DataBaseConstant.UPDATE_BY_TABLE + " = '" + sysUser.getUsername() + "', ");
                } else {
                    //写入更新时间
                    sql = sql.replace("SET", "SET " + DataBaseConstant.UPDATE_TIME_TABLE + " = '" + new Timestamp(System.currentTimeMillis()) + "',");
                }
                metaObject.setValue("delegate.boundSql.sql", sql);
                metaObject.setValue("delegate.boundSql.parameterMappings", mappings);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    private LoginUser getLoginUser() {
        LoginUser sysUser = null;
        try {
            sysUser = SecurityUtils.getSubject().getPrincipal() != null ? (LoginUser) SecurityUtils.getSubject().getPrincipal() : null;
        } catch (Exception e) {
            //e.printStackTrace();
            sysUser = null;
        }
        return sysUser;
    }
}
