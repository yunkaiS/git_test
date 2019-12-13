package cn.weli.biz.author.service;

import cn.weli.biz.author.dao.ExamineLogDao;
import cn.weli.biz.author.meta.AuthorShip;
import cn.weli.biz.author.meta.ExamineLog;
import org.springframework.stereotype.Repository;
import suishen.libs.web.exception.BusinessException;

import javax.annotation.Resource;

/**
 * @author yunk.shen
 */
@Repository
public class ExamineLogService {

    @Resource
    private AuthorShipService authorShipService;

    @Resource
    private ExamineLogDao examineLogDao;

    @Resource
    private ExamineLogService examineLogService;

    /**
     * 作者申请
     */
    public void updateApply(long authorId){
        AuthorShip authorShip = authorShipService.getByAuthorId(authorId);
        if (authorShip == null) {
            throw new BusinessException("用户不存在，申请失败");
        }
        long now = System.currentTimeMillis();
        ExamineLog log=new ExamineLog();
        if(examineLogService.getByAuthorId(authorId)!=null){
            throw new BusinessException("用户已在申请中");
        }
        log.setAuthorId(authorId);
        log.setAuthorName(authorShip.getAuthorName());
        log.setApplyTime(now);
        log.setStatus(1);
        log.setOpUser(null);
        log.setExamineTime(0);
        log.setExamineContent(null);

        boolean isSuccess = examineLogDao.create(log);
        if (!isSuccess) {
            throw new BusinessException("用户申请失败，请稍后重试！");
        }
    }

    /**
     * 根据作者id查询审批日志
     * @param authorId
     * @return
     */
    private ExamineLog getByAuthorId(long authorId) {
        return examineLogDao.getByAuthorId(authorId);
    }

    /**
     * 申请审核
     */
      public void applyExamine(long examineId,int status,String opUser,String Content) {
        ExamineLog log = examineLogDao.getLogByLogId(examineId);
        if(log.getStatus()!=1){
            throw new BusinessException("作者已审核完成");
        }
        long now=System.currentTimeMillis();

        boolean isSuccess = examineLogService.updateExamineLog(examineId,opUser,status,Content);
        if (!isSuccess) {
            throw new BusinessException("创建失败，请稍后重试！");
        }
        log.setExamineTime(now);
    }

    /**
     * 删除日志
     * @param examineLog
     * @return
     */
    public boolean delete(ExamineLog examineLog) {
        return examineLogDao.delete(examineLog.getExamineId());
    }

    /**
     * 审批
     * @param examineId         审批日志Id
     * @param opUser         审批人
     * @param examineContent    审批意见
     * @return
     */
    public boolean updateExamineLog(long examineId, String opUser,int status,String examineContent) {
        return examineLogDao.update(examineId,opUser,status,examineContent);
    }

    /**
     * 根据id查询审批日志
     * @param examineId
     * @return
     */
    public ExamineLog getByExamineId(long examineId) {
        return examineLogDao.getLogByLogId(examineId);
    }
}
