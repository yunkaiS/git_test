package cn.weli.biz.author.service;

import cn.weli.biz.author.dao.AuthorShipDao;
import cn.weli.biz.author.dto.AuthorCreateForm;
import cn.weli.biz.author.dto.AuthorQueryForm;
import cn.weli.biz.author.dto.AuthorQueryRespDTO;
import cn.weli.biz.author.dto.AuthorShipStatus;
import cn.weli.biz.author.meta.AuthorShip;
import cn.weli.biz.merchants.utils.AuthenticateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import suishen.libs.web.exception.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yunk.shen
 */
@Service
public class AuthorShipService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorShipService.class);

    @Resource
    private AuthorShipDao authorShipDao;

    /**
     * 创建作者
     *
     * @param form
     * @return (初始密码)
     */
    public String create(AuthorCreateForm form) {
        AuthorShip existAuthor = authorShipDao.getAuthorByAuthorId(form.getAuthorId());
        if (existAuthor != null) {
            throw new BusinessException("该id已有用户");
        }

        long now = System.currentTimeMillis();
        AuthorShip author = new AuthorShip();
        author.setAuthorId(form.getAuthorId());
        author.setEmail(form.getEmail());
        author.setAuthorName(StringUtils.trimToEmpty(form.getAuthorName()));
        author.setMobile(StringUtils.trimToEmpty(form.getMobile()));
        author.setCreateTime(now);
        author.setUpdateTime(now);
        author.setStatus(AuthorShipStatus.OK.status());

        String sourcePassword = AuthenticateUtils.generatePassword();

        boolean isSuccess = authorShipDao.create(author);
        if (!isSuccess) {
            throw new BusinessException("创建失败，请稍后重试！");
        }

        return sourcePassword;
    }

    /**
     * 删除作者
     * @param authorShip
     * @return
     */
    public boolean delete(AuthorShip authorShip) {
        return authorShipDao.delete(authorShip.getId());
    }

    /**
     * 更新作者信息
     * @param authorShip
     * @return
     */
    public boolean update(AuthorShip authorShip) {
        AuthorShip localAuthor = authorShipDao.getAuthorById(authorShip.getId());
        if (localAuthor == null) {
            throw new BusinessException("无效的作者id, id=" + authorShip.getAuthorId());
        }

        authorShip.setStatus(localAuthor.getStatus());
        authorShip.setUpdateTime(System.currentTimeMillis());
        return authorShipDao.update(authorShip);
    }

    /**
     * 更新作者状态
     * @param authorId 作者Id
     * @param status  作者状态
     */
    public boolean updateStatus(long authorId, Integer status) {
        AuthorShip localAuthor = authorShipDao.getAuthorByAuthorId(authorId);
        if (localAuthor == null) {
            throw new BusinessException("无效的作者id, id=" + authorId);
        }

        return authorShipDao.updateStatus(authorId,status);
    }

    /**
     * 根据作者id查询作者
     * @param authorId
     * @return
     */
    public AuthorShip getByAuthorId(long authorId) {
        return authorShipDao.getAuthorByAuthorId(authorId);
    }

    /**
     * 根据email查询作者
     * @param email
     * @return
     */
    public AuthorShip getByAuthorEmail(String email) {
        return authorShipDao.getAuthorByAuthorEmail(email);
    }

    /**
     * 后台查询
     */
    public AuthorQueryRespDTO<AuthorShip> adminQuery(AuthorQueryForm form) {
        return authorShipDao.getAuthorListByPage(form);
    }

    public List<AuthorShip> getAllList(){
        return authorShipDao.getAllList();
    }

}
