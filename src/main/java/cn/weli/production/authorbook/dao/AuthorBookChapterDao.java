package cn.weli.production.authorbook.dao;

import cn.weli.production.authorbook.meta.AuthorBookChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import suishen.framework.dao.DomainDaoSupport;

@Repository
public class AuthorBookChapterDao {

    private DomainDaoSupport<AuthorBookChapter> domainDaoSupport;


    @Autowired
    public void setDomainDaoSupport(DomainDaoSupport<AuthorBookChapter> domainDaoSupport) {
        this.domainDaoSupport = domainDaoSupport;
        this.domainDaoSupport.initConfig(AuthorBookChapter.class);
    }

    public AuthorBookChapter queryByBookId(int bookId) {
        return domainDaoSupport.getByWhere(" book_id = ?", new Object[]{bookId});
    }

    /**
     * 添加
     * @return
     */
    public boolean create(AuthorBookChapter authorBookChapter) {
        return domainDaoSupport.add(authorBookChapter) != null;
    }

    /**
     * 更新书籍内容
     * @return
     */
    public boolean updateContent(long chapterId,String content,int wordCount) {
        return domainDaoSupport.update("update author_book_chapter set content=?,word_count=?,update_time=? where id=?",
                new Object[]{content,wordCount,System.currentTimeMillis(),chapterId});
    }

    public AuthorBookChapter getWordCountByChapterId(long chapterId) {
        return domainDaoSupport.getByWhere("id=?",
                new Object[]{chapterId});
    }
}
