package com.wdd.myplatform.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-plus配置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置分页
     */
    @Bean
    @Order(0)
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser() {
            @Override
            public void processDelete(Delete delete) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                if ("user".equals(delete.getTable().getName())) {
                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
                    return;
                }
                super.processDelete(delete);
            }

            @Override
            public void processUpdate(Update update) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                if ("user".equals(update.getTable().getName())) {
                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
                    return;
                }
                super.processUpdate(update);
            }
        });
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}