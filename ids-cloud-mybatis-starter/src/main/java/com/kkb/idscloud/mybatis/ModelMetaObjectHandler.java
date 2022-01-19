package com.kkb.idscloud.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动填充模型数据
 *
 * @author zmc
 */
public class ModelMetaObjectHandler implements MetaObjectHandler {

    /**
     * metaObject是页面传递过来的参数的包装对象，不是从数据库取的持久化对象，因此页面传过来哪些值，metaObject里就有哪些值。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        Object createTime = this.getFieldValByName("createTime", metaObject);
        if (null == createTime) {
            this.setFieldValByName("createTime", now, metaObject);
        }
        Object updateTime = this.getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("updateTime", now, metaObject);
        }

        Object createdAt = this.getFieldValByName("createdAt", metaObject);
        if (null == createTime) {
            this.setFieldValByName("createdAt", now, metaObject);
        }
        Object updatedAt = this.getFieldValByName("updatedAt", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("updatedAt", now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        Object updateTime = this.getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("updateTime", now, metaObject);
        }

        Object updatedAt = this.getFieldValByName("updatedAt", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("updatedAt", now, metaObject);
        }
    }
}
