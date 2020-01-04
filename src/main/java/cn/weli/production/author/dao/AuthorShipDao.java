package cn.weli.production.author.dao;

import cn.weli.common.PageWrapper;
import cn.weli.production.author.dto.AuthorQueryForm;
import cn.weli.production.author.meta.AuthorShip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import suishen.framework.dao.DomainDaoSupport;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 原创作者数据
 * @author yunk.shen
 */
@Repository
@Slf4j
public class AuthorShipDao {
    private DomainDaoSupport<AuthorShip> domainDaoSupport;

    @Autowired
    public void setDomainDaoSupport(DomainDaoSupport<AuthorShip> domainDaoSupport) {
        this.domainDaoSupport = domainDaoSupport;
        this.domainDaoSupport.initConfig(AuthorShip.class);
    }

    /**
     * 添加
     * @param authorShip    原创作者
     * @return
     */
    public AuthorShip create(AuthorShip authorShip) {
        return domainDaoSupport.add(authorShip);
    }

    public boolean updatePwd(long id, String pwd, String pwdSalt){
        return domainDaoSupport.update("update author_ship set pwd=?, pwd_salt=?, update_time=? where id=?; ",
                new Object[]{pwd, pwdSalt, System.currentTimeMillis(), id});
    }

    public boolean updateBasicInfo(long id,String nickName,String email,String mobile,String qq){
        return domainDaoSupport.update("update author_ship set nick_name=?,email=?,mobile=?,qq=?,update_time=? where id=?",
                new Object[]{nickName, email,mobile, qq ,System.currentTimeMillis(), id});
    }

    public boolean updateStatus(long authorId, int status) {
        return domainDaoSupport.update("update author_ship set status=?, update_time=? where id=?; ",
                new Object[]{status, System.currentTimeMillis(), authorId});
    }

    public boolean updateBankInfo(long id, String bankName, String bankAccount) {
        return domainDaoSupport.update("update author_ship set bank_name=?, bank_account=?, update_time=? where id=?",
                new Object[]{bankName, bankAccount, System.currentTimeMillis(), id});
    }

    public boolean updateLastLoginIpAndTime(long id, String lastLoginIp, long lastLoginTime) {
        return domainDaoSupport.update("update author_ship set last_login_ip=?, last_login_time=? where id=?; ",
                new Object[]{lastLoginIp, lastLoginTime, id});
    }

    public boolean deleteByEmail(String email){
        return domainDaoSupport.deleteByWhere("email=?",
                new Object[]{email});
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public AuthorShip getAuthorById(long id) {
        return domainDaoSupport.getByWhere(" id = ?;", new Object[]{id});
    }

    /**
     * 根据用户email查询用户
     */
    public AuthorShip getAuthorByEmail(String email) {
        return domainDaoSupport.getByWhere(" email = ? limit 1;", new Object[]{email});
    }

    /**
     * 后台查询所有作者
     * @return
     */
    public List<AuthorShip> getAllList() {
        return domainDaoSupport.getListByWhere(" 1=1 order by id asc", new Object[]{});
    }

    /**
     * 后台查询作者
     * @return
     */
    public PageWrapper<AuthorShip> getAuthorListByPage(AuthorQueryForm form){
        PageWrapper<AuthorShip> pageWrapper =new PageWrapper<AuthorShip>();
        pageWrapper.setPage(form.getPage());
        pageWrapper.setPageSize(form.getPageSize());

        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();
        if (form.getId() != null && form.getId() > 0){
            sql.append("id=?");
            values.add(form.getId());
        }
        else{
            sql.append("id>?");
            values.add(0);
        }
        if (StringUtils.isNotBlank(form.getNickName())) {
            sql.append(" and nick_name like ? ");
            values.add("%" + form.getNickName() + "%");
        }
        if (form.getStatus() != null && form.getStatus() > -1) {
            sql.append(" and status = ? ");
            values.add(form.getStatus());
        }
        int total=domainDaoSupport.getCountByWhere(sql.toString(),values.toArray());
        pageWrapper.setTotalCount(total);
        if(total>0){
            sql.append(" order by id asc ");
            List<AuthorShip> authorShipList =
                    domainDaoSupport.getListByWhere(sql.toString(),form.getPageSize(),(form.getPage() - 1) * form.getPageSize(),  values.toArray());
            pageWrapper.setList(authorShipList);
        }
        return pageWrapper;
    }

}
