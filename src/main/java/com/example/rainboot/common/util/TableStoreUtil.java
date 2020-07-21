package com.example.rainboot.common.util;

import com.alicloud.openservices.tablestore.AsyncClient;
import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * TableStore配置
 *
 * @Author 小熊
 * @Created 2019-07-10 10:27 AM
 * @Version 1.0
 */
@Configuration
public class TableStoreUtil {

    @Value("${tableStore.accessKeyID}")
    private String accessKeyId;

    @Value("${tableStore.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 实例名称
     */
    @Value("${tableStore.instanceName}")
    private String instanceName;

    @Value("${tableStore.endPoint}")
    private String endPoint;

    /**
     * 连接超时以毫秒为单位
     */
    @Value("${tableStore.connectionTimeoutInMillisecond}")
    private int connectionTimeoutInMillisecond;

    /**
     * socket超时毫秒
     */
    @Value("${tableStore.socketTimeoutInMillisecond}")
    private int socketTimeoutInMillisecond;

    private static SyncClient syncClient;

    private static AsyncClient asyncClient;

    @Bean
    void init() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 设置建立连接的超时时间。
        clientConfiguration.setConnectionTimeoutInMillisecond(connectionTimeoutInMillisecond);
        // 设置socket超时时间。
        clientConfiguration.setSocketTimeoutInMillisecond(socketTimeoutInMillisecond);
        // 设置重试策略，若不设置，采用默认的重试策略。
        clientConfiguration.setRetryStrategy(new AlwaysRetryStrategy());
        // 最大连接数
        clientConfiguration.setMaxConnections(1000);
        syncClient = new SyncClient(endPoint, accessKeyId, accessKeySecret,
                instanceName, clientConfiguration);
        asyncClient = new AsyncClient(endPoint, accessKeyId, accessKeySecret,
                instanceName, clientConfiguration);
    }

    /**
     * 获取tableStore同步连接
     *
     * @return client
     */
    public static SyncClient getTableStoreSyncClient() {
        return syncClient;
    }

    /**
     * 获取tableStore异步连接
     *
     * @return client
     */
    public static AsyncClient getTableStoreAsyncClient() {
        return asyncClient;
    }

    public static ColumnValue getRow(SyncClient client, String tableName, String primaryKeyName, String pkValue, String columnsName) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(primaryKeyName, PrimaryKeyValue.fromLong(Long.valueOf(pkValue)));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(tableName, primaryKey);
        // 设置读取最新版本
        criteria.setMaxVersions(1);
        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
        Row row = getRowResponse.getRow();
        if (row == null) {
            return null;
        }
        return row.getColumn(columnsName).get(0).getValue();
    }

}
