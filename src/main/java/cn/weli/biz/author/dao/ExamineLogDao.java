package cn.weli.biz.author.dao;

import cn.weli.biz.author.meta.ExamineLog;
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
     * @return  添加操作是否成功
     */
    public boolean create(ExamineLog log) {
        return domainDaoSupport.add(log) != null;
    }

    /**
     * 删除
     * @param examineId  审批日志Id
     * @return      删除操作是否成功
     */
    public boolean delete(Long examineId){return domainDaoSupport.deleteById(examineId);}


    /**
     * 审批：输入审批日志ID，审批状态,审批人姓名与审批意见
     * @param examineId         审批日志Id
     * @param opUser            审批人
     * @param status            审批状态（1.未审批；2.审批通过；3.审批未通过）
     * @param examineContent    审批意见
     * @return            审批操作是否成功
     */
    public boolean update(long examineId, String opUser,int status,String examineContent) {
        return domainDaoSupport.update(
                "update examine_log set examine_id=?, op_user=?, examine_time=?, examine_content=?, status=? where examine_id=? ;",
                examineId, opUser, System.currentTimeMillis(),examineContent, status,examineId);
    }

    /**
     * 刷新日志信息
     * @param examineLog  日志记录
     * @return      刷新是否成功
     */
    public boolean updateLog(ExamineLog examineLog){
        return domainDaoSupport.update(examineLog.getExamineId(),new String[]{"author_id","apply_time",
                        "status","op_user","examine_time","examine_content"}
                ,new Object[]{examineLog.getAuthorId(),examineLog.getApplyTime(),examineLog.getStatus(),examineLog.getOpUser(),
                        examineLog.getExamineTime(),examineLog.getExamineContent()});
    }

    /**
     * 根据审批日志id查日志记录
     * @param examineId     审批日志id
     * @return              id对应的审批记录
     */
    public ExamineLog getLogByLogId(long examineId) {
        return domainDaoSupport.getByWhere(" examine_id = ? limit 1;", new Object[]{examineId});
    }

    /**
     * 根据作者id查审批日志记录
     * @param authorId  作者id
     * @return          id查询到的审批日志记录
     */
    public ExamineLog getByAuthorId(long authorId) {
        return domainDaoSupport.getByWhere(" author_id = ? limit 1;", new Object[]{authorId});
    }
}
