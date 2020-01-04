package cn.weli.production.author.service;

import cn.weli.biz.merchants.service.MerchantAccountService;
import cn.weli.common.PageWrapper;
import cn.weli.common.constant.Constant;
import cn.weli.production.author.dao.AuthorShipDao;
import cn.weli.production.author.dto.*;
import cn.weli.production.author.enums.AuthorStatusEnum;
import cn.weli.production.author.meta.AuthorShip;
import cn.weli.biz.merchants.utils.AuthenticateUtils;
import cn.weli.common.constant.BizActionStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import suishen.libs.lock.annotation.RequestId;
import suishen.libs.web.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yunk.shen
 */
@Service
@Slf4j
public class AuthorShipService {
    private static final Logger logger = LoggerFactory.getLogger(MerchantAccountService.class);

    @Value("${merchant_password_salt}")
    private String merchantPasswordSalt;

    @Resource
    private AuthorShipDao authorShipDao;

    /**
     * 登陆
     */
    public ClientAuthorLoginResp login(@RequestId String email, String inputPassword, String loginIp){
        AuthorShip authorShip = authorShipDao.getAuthorByEmail(email);
        if (authorShip == null) {
            logger.warn("用户登录失败 用户不存在 email={}, loginIp={}", new Object[]{email, loginIp});
            throw new BusinessException(BizActionStatus.PRO_USER_NOT_EXIST);
        }

        if(authorShip.getStatus()!=1){
            logger.warn("用户禁止登陆");
            throw new BusinessException(BizActionStatus.PRO_USER_FOBBIDEN_LOGIN);
        }

        String passwordSalt = authorShip.getPwdSalt();
        String hashedPassword = AuthenticateUtils.hashedPassword(inputPassword, passwordSalt, merchantPasswordSalt);
        if (!StringUtils.equals(authorShip.getPwd(), hashedPassword)) {
            logger.warn("用户登录失败 密码错误 email={}, loginIp={}", new Object[]{email, loginIp});
            logger.error("inputPassword={}, passwordSalt={}, rightPass={}, hashedPass={}",
                    new Object[]{inputPassword, passwordSalt, authorShip.getPwd(), hashedPassword});
            throw new BusinessException(BizActionStatus.PRO_LOGIN_FAILURE);
        }

        if (authorShip.getStatus() != AuthorStatusEnum.valid.status()) {
            throw new BusinessException(BizActionStatus.PRO_INVALID_CERTIFICATE);
        }

        long now = System.currentTimeMillis();

        ClientAuthorDTO clientAuthorDTO = ClientAuthorDTO.builder()
                .id(authorShip.getId())
                .nickName(authorShip.getNickName())
                .email(authorShip.getEmail())
                .lastLoginIp(authorShip.getLastLoginIp())
                .lastLoginTime(now)
                .build();

        authorShipDao.updateLastLoginIpAndTime(authorShip.getId(), loginIp, now);

        String token = AuthenticateUtils.getJwtToken(email, authorShip.getId());

        logger.warn("用户登录成功, 用户id={}, nickname={}, email={}, fromIp={}",
                new Object[]{authorShip.getId(), authorShip.getNickName(), authorShip.getEmail(), loginIp});

        ClientAuthorLoginResp resp = new ClientAuthorLoginResp();
        resp.setAccount(clientAuthorDTO);
        resp.setToken(token);

        return resp;
    }

    /**
     * 修改密码
     * @param email
     * @param new_pwd
     * @return
     */
    public boolean resetPassword(String email,String new_pwd){
        AuthorShip authorShip=authorShipDao.getAuthorByEmail(email);
        if(authorShip==null){
            logger.warn("修改失败 用户不存在 email={}", new Object[]{email});
            throw new BusinessException(BizActionStatus.PRO_USER_NOT_EXIST);
        }

        String pwdSalt = AuthenticateUtils.generatePasswordSalt();

        String pwd = AuthenticateUtils.hashedPassword(new_pwd,pwdSalt,merchantPasswordSalt);
        authorShip.setPwd(pwd);
        authorShip.setPwdSalt(pwdSalt);

        boolean isSuccess = authorShipDao.updatePwd(authorShip.getId(), pwd,pwdSalt);
        if(!isSuccess){
            throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
        }
        return isSuccess;
    }

    /**
     * 创建作者（注册方式）
     *
     * @param form
     * @return (初始密码)
     */
    public AuthorShip create(AuthorCreateForm form) {
        AuthorShip existAuthor = authorShipDao.getAuthorByEmail(form.getEmail());
        if (existAuthor != null) {
            switch (existAuthor.getStatus()) {
                case 0:
                    logger.warn("申请失败 用户当前已在申请中 email={}", new Object[]{form.getEmail()});
                    throw new BusinessException(BizActionStatus.PRO_USER_IS_PENDING);
                case 1:
                    logger.warn("申请失败 用户已经注册成功 email={}", new Object[]{form.getEmail()});
                    throw new BusinessException(BizActionStatus.PRO_USER_ALREADY_EXITST);
                default:
                    break;
            }
            authorShipDao.deleteByEmail(form.getEmail());
        }

        long now = System.currentTimeMillis();

        AuthorShip author = AuthorShip.builder().email(form.getEmail()).nickName(StringUtils.trimToEmpty(form.getNickName()))
                .mobile(StringUtils.trimToEmpty(form.getMobile())).lastLoginIp(Constant.BLANK).createTime(now)
                .updateTime(now).status(AuthorStatusEnum.pending_check.status()).qq(form.getQq()).bankName(form.getBankName())
                .bankAccount(form.getBankAccount()).build();

        String sourcePassword = form.getPwd();
        String passwordSalt = AuthenticateUtils.generatePasswordSalt();

        String password = AuthenticateUtils.hashedPassword(sourcePassword,passwordSalt,merchantPasswordSalt);
        author.setPwd(password);
        author.setPwdSalt(passwordSalt);

        AuthorShip authorShip=authorShipDao.create(author);
        boolean isSuccess = authorShip!=null;
        if (!isSuccess) {
            throw new BusinessException(BizActionStatus.PRO_CREAT_FAILURE);
        }

        return authorShip;
    }

    /**
     * 管理员创建作者
     */
    public AuthorShip createByAdmin(AuthorCreateForm form){
        AuthorShip existAuthor = authorShipDao.getAuthorByEmail(form.getEmail());
        if (existAuthor != null ) {
            if(existAuthor.getStatus()==1){throw new BusinessException(BizActionStatus.PRO_USER_ALREADY_EXITST);}
            else{authorShipDao.deleteByEmail(form.getEmail());}
        }

        long now = System.currentTimeMillis();
        AuthorShip author = AuthorShip.builder().email(form.getEmail()).nickName(StringUtils.trimToEmpty(form.getNickName()))
                .mobile(StringUtils.trimToEmpty(form.getMobile())).lastLoginIp(Constant.BLANK).createTime(now)
                .updateTime(now).status(AuthorStatusEnum.valid.status()).qq(form.getQq()).bankName(form.getBankName())
                .bankAccount(form.getBankAccount()).build();

        String sourcePassword = form.getPwd();
        String passwordSalt = AuthenticateUtils.generatePasswordSalt();

        String password = AuthenticateUtils.hashedPassword(sourcePassword,passwordSalt,merchantPasswordSalt);
        author.setPwd(password);
        author.setPwdSalt(passwordSalt);

        AuthorShip authorShip = authorShipDao.create(author);
        return authorShip;
    }

    /**
     * 更新作者基本信息
     */
    public boolean updateBasicInfo(long id,String nickName,String email,String mobile,String qq) {
        AuthorShip localAuthor = authorShipDao.getAuthorById(id);
        if (localAuthor == null) {
            logger.warn("更新信息失败 用户不存在 email={}", new Object[]{email});
            throw new BusinessException(BizActionStatus.PRO_USER_NOT_EXIST);
        }

        return authorShipDao.updateBasicInfo(id,nickName,email,mobile,qq);
    }

    /**
     * 更新作者状态
     * @param id 作者Id
     * @param authorStatus  作者状态
     */
    public boolean updateStatus(long id, AuthorStatusEnum authorStatus) {
        AuthorShip localAuthor = authorShipDao.getAuthorById(id);
        if (localAuthor == null) {
            throw new BusinessException(BizActionStatus.PRO_USER_NOT_EXIST);
        }

        return authorShipDao.updateStatus(id,authorStatus.getStatus());
    }

    /**
     * 根据作者id查询作者
     * @param id
     * @return
     */
    public AuthorShip getById(long id) {
        return authorShipDao.getAuthorById(id);
    }

    /**
     * 根据作者email查询作者
     * @param email
     * @return
     */
    public AuthorShip getByEmail(String email) {
        return authorShipDao.getAuthorByEmail(email);
    }

    /**
     * 后台查询
     */
    public PageWrapper<AuthorShip> adminQuery(AuthorQueryForm form) {
        return authorShipDao.getAuthorListByPage(form);
    }

    public List<AuthorShip> getAllList(){
        return authorShipDao.getAllList();
    }

}
