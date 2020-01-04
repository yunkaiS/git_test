package cn.weli.production.author.service;

import cn.weli.common.constant.BizActionStatus;
import cn.weli.common.constant.Constant;
import cn.weli.production.author.dao.AuthorShipDao;
import cn.weli.production.author.dao.ExamineLogDao;
import cn.weli.production.author.enums.ExamineOpType;
import cn.weli.production.author.meta.AuthorShip;
import cn.weli.production.author.meta.ExamineLog;
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
    private AuthorShipDao authorShipDao;

    /**
     * 根据id查询审批日志
     * @param id
     * @return
     */
    private ExamineLog getById(long id) {
        return examineLogDao.getById(id);
    }

    /**
     * 申请审核
     */
      public boolean examine(long authorId,String opUser,String desc,int opRes,String loginIp) {
        AuthorShip authorShip=authorShipService.getById(authorId);
        if(authorShip.getStatus()!=0){
            throw new BusinessException(BizActionStatus.PRO_USER_HAS_EXAMINED);
        }
        long now=System.currentTimeMillis();
        authorShipDao.updateStatus(authorId,opRes);

        ExamineLog log=ExamineLog.builder().
                authorId(authorId).
                opType(ExamineOpType.examine.status()).
                opTime(now).
                opIp(loginIp).
                opUser(opUser).
                desc(desc).
                remark(Constant.BLANK).build();

        boolean isSuccess = examineLogDao.create(log);
        if (!isSuccess) {
            throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
        }
        log.setOpTime(now);
        return isSuccess;
    }

    /**
     * 记录作者注册
     * @param authorId
     * @param inputIp
     * @return
     */
    public ExamineLog inputRecord(long authorId,String inputIp){
          ExamineLog log=ExamineLog.builder().
                  authorId(authorId).
                  opType(ExamineOpType.input.status()).
                  opIp(inputIp).
                  opTime(System.currentTimeMillis()).
                  opUser(Long.toString(authorId)).
                  desc("input").build();
          boolean isSuccess=examineLogDao.create(log);
          if(!isSuccess){
              throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
          }
          return log;
    }

    /**
     * 记录作者登陆
     * @return
     */
    public ExamineLog loginRecord(long authorId,String inputIp){
        AuthorShip authorShip=authorShipService.getById(authorId);
        ExamineLog log = ExamineLog.builder().
                authorId(authorId).
                opType(ExamineOpType.login.status()).
                opIp(inputIp).
                opTime(System.currentTimeMillis()).
                opUser(authorShip.getEmail()).
                desc("login").build();
        boolean isSuccess=examineLogDao.create(log);
        if(!isSuccess){
            throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
        }
        return log;
    }

    /**
     * 记录作者修改信息
     * @return
     */
    public ExamineLog updateRecord(String email,String inputIp){
        AuthorShip authorShip=authorShipService.getByEmail(email);
        ExamineLog log = ExamineLog.builder().
                opType(ExamineOpType.update.status()).
                opIp(inputIp).
                opTime(System.currentTimeMillis()).
                opUser(authorShip.getEmail()).
                desc("updateInfo").build();
        boolean isSuccess=examineLogDao.create(log);
        if(!isSuccess){
            throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
        }
        return log;
    }
}
