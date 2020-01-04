package cn.weli.production.author.dao;

import cn.weli.production.author.meta.ExamineLog;
import org.springframework.stereotype.Repository;
import suishen.framework.dao.AbstractNoCacheDao;

/**
 * 原创作者审核日志数据
 * @author yunk.shen
 */
@Repository
public class ExamineLogDao extends AbstractNoCacheDao<ExamineLog> {
    @Override
    public Class<ExamineLog> getDomainClass() {
        return ExamineLog.class;
    }

    /**
     * 添加
     * @param log 审批记录
     * @return
     */
    public boolean create(ExamineLog log) {
        return domainDaoSupport.add(log) != null;
    }


    /**
     * 根据审批日志id查日志记录
     * @param examineId     审批日志id
     * @return              id对应的审批记录
     */
    public ExamineLog getLogByLogId(long examineId) {
        return domainDaoSupport.getByWhere(" id = ?;", new Object[]{examineId});
    }

    /**
     * 根据作者id查审批日志记录
     * @param authorId  作者id
     * @return          id查询到的审批日志记录
     */
    public ExamineLog getByAuthorId(long authorId) {
        return domainDaoSupport.getByWhere(" author_id =?;", new Object[]{authorId});
    }
}
