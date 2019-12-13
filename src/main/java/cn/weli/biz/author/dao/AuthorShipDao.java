package cn.weli.biz.author.dao;

import cn.weli.biz.author.meta.AuthorShip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import suishen.framework.dao.DomainDaoSupport;

import java.util.List;

/**
 * 原创作者数据
 * @author yunk.shen
 */
@Repository
public class AuthorShipDao {
    private DomainDaoSupport<AuthorShip> domainDaoSupport;

//    @Resource
//    private MySQLSqlConnectionManager sqlSqlConnectionManager;

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
    public boolean create(AuthorShip authorShip) {
        return domainDaoSupport.add(authorShip) != null;
    }

    /**
     * 删除
     * @return
     */
    public boolean delete(long id){return domainDaoSupport.deleteById(id);}

    /**
     * 修改作者信息
     * @param authorShip
     * @return
     */
    public boolean update(AuthorShip authorShip){
        return domainDaoSupport.update(authorShip.getId(),new String[]{"author_id","authorName","email",
                "mobile","status","createTime","updateTime"}
           ,new Object[]{authorShip.getAuthorId(),authorShip.getAuthorName(),authorShip.getEmail(),authorShip.getMobile(),authorShip.getCreateTime(),
                authorShip.getUpdateTime()});
    }

    public List<AuthorShip> getAllList() {
        return domainDaoSupport.getListByWhere(" 1=1 order by id desc", new Object[]{});
    }

    public boolean updateStatus(String authorId, int status) {
        return domainDaoSupport.update("update author_ship set status=?, update_time=? where id=?; ",
                new Object[]{status, System.currentTimeMillis(), authorId});
    }

    public boolean updateNameAndMobile(long id, String username, String mobile) {
        return domainDaoSupport.update("update author_ship set username=?, mobile=?, update_time=? where id=?",
                new Object[]{username, mobile, System.currentTimeMillis(), id});
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public AuthorShip getAuthorById(long id) {
        return domainDaoSupport.getByWhere(" id = ? limit 1;", new Object[]{id});
    }

    /**
     * 根据用户email查询用户
     */
    public AuthorShip getAuthorByAuthorEmail(String email) {
        return domainDaoSupport.getByWhere(" email = ? limit 1;", new Object[]{email});
    }

    public AuthorShip getAuthorByAuthorId(String authorId) {
        return domainDaoSupport.getByWhere(" author_id = ? limit 1;", new Object[]{authorId});
    }

    /**
     * 后台查询作者
     * @return
     */
//    public AuthorQueryRespDTO getAuthorListByPage(AuthorQueryForm form){
//        AuthorQueryRespDTO<AuthorShip> pageWrapper = new AuthorQueryRespDTO<>();
//        pageWrapper.setPage(form.getPage());
//        pageWrapper.setPageSize(form.getPageSize());
//
//        StringBuilder sql = new StringBuilder();
//        List<Object> values = new ArrayList<>();
//        if (form.getAuthorId() != null && form.getAuthorId() > 0){
//            sql.append("id=?");
//            values.add(form.getAuthorId());
//        }else{
//            sql.append("id>?");
//            values.add(0);
//        }
//        if (StringUtils.isNotBlank(form.getAuthorName())) {
//            sql.append(" and name like ? ");
//            values.add("%" + form.getAuthorName() + "%");
//        }
//        if (form.getStatus() != null && form.getStatus() > -1) {
//            sql.append(" and status = ? ");
//            values.add(form.getStatus());
//        }
//        int total=domainDaoSupport.getCountByWhere(sql.toString(),values.toArray());
//        pageWrapper.setTotalCount(total);
//        if(total>0){
//            sql.append(" order by id desc ");
//            List<AuthorShip> authorShipList =
//                    domainDaoSupport.getListByWhere(sql.toString(),form.getPageSize(),(form.getPage() - 1) * form.getPageSize(),  values.toArray());
//            pageWrapper.setList(authorShipList);
//        }
//        return pageWrapper;
//    }
}
