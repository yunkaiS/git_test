package cn.weli.production.authorbook.dao;

import cn.weli.common.PageWrapper;
import cn.weli.production.authorbook.meta.AuthorBook;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import suishen.framework.dao.DomainDaoSupport;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorBookDao {
    private DomainDaoSupport<AuthorBook> domainDaoSupport;

    @Autowired
    public void setDomainDaoSupport(DomainDaoSupport<AuthorBook> domainDaoSupport) {
        this.domainDaoSupport = domainDaoSupport;
        this.domainDaoSupport.initConfig(AuthorBook.class);
    }

    /**
     * 添加
     * @param authorBook    添加书籍
     * @return
     */
    public boolean create(AuthorBook authorBook) {
        return domainDaoSupport.add(authorBook) != null;
    }

    /**
     * 根据书籍id查询书籍
     * @param id
     * @return
     */
    public AuthorBook getBookByBookId(int id) {
        return domainDaoSupport.getByWhere(" id = ?;", new Object[]{id});
    }

    /**
     * 根据作者Id查询书籍
     * @param authorId
     * @return
     */
    public AuthorBook getBookByAuthorId(long authorId) {
        return domainDaoSupport.getByWhere(" author_id = ?",new Object[]{authorId});
    }

    public boolean updateBookInfo(int bookId, int wordCount, int chapterCount, long lastChapterTime) {
        return domainDaoSupport.update("update author_books set word_count=?,chapter_count=?,last_chapter_update_time=?,update_time=? where id =?",
                new Object[]{wordCount, chapterCount, lastChapterTime, System.currentTimeMillis(), bookId});
    }

    public boolean updateBookBasicInfo(int bookId, String name, long authorId, int status) {
        return domainDaoSupport.update("update author_books set name=?,author_id=?,status=?,update_time=? where id =?",
                new Object[]{name, authorId, status, System.currentTimeMillis(), bookId});
    }

    public boolean updateWordCount(int bookId, int wordCount) {
        return domainDaoSupport.update("update author_books set word_count=?,update_time=? where id=?",
                new Object[]{wordCount, System.currentTimeMillis(), bookId});
    }

    /**
     * 书籍后台查询
     * @param name
     * @param authorId
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    public PageWrapper<AuthorBook> getAuthorListByPage(String name, Long authorId, Integer status, int page, int pageSize) {
        PageWrapper<AuthorBook> pageWrapper = new PageWrapper<>();
        pageWrapper.setPage(page);
        pageWrapper.setPageSize(pageSize);

        StringBuilder sql = new StringBuilder();
        List<Object> values = new ArrayList<>();
        if (authorId != null && authorId > 0){
            sql.append("author_id=?");
            values.add(authorId);
        }
        else{
            sql.append("author_id>?");
            values.add(0);
        }
        if (StringUtils.isNotBlank(name)){
            sql.append(" and name like ? ");
            values.add("%" + name + "%");
        }
        if (status != null && status > -1) {
            sql.append(" and status = ? ");
            values.add(status);
        }
        int total=domainDaoSupport.getCountByWhere(sql.toString(),values.toArray());
        pageWrapper.setTotalCount(total);
        if(total>0){
            sql.append(" order by id asc ");
            List<AuthorBook> authorShipList =
                    domainDaoSupport.getListByWhere(sql.toString(),pageSize,(page - 1) * pageSize,  values.toArray());
            pageWrapper.setList(authorShipList);
        }
        return pageWrapper;
    }

}
