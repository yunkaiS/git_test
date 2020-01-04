package cn.weli.production.authorbook.service;

import cn.weli.common.exception.BusinessException;
import cn.weli.production.authorbook.dao.AuthorBookChapterDao;
import cn.weli.production.authorbook.dao.AuthorBookDao;
import cn.weli.production.authorbook.dto.AdminAddBookChapterForm;
import cn.weli.production.authorbook.dto.AuthorBookChapterContent;
import cn.weli.production.authorbook.meta.AuthorBook;
import cn.weli.production.authorbook.meta.AuthorBookChapter;
import org.springframework.stereotype.Service;
import suishen.redis.SuishenRedisTemplate;

import javax.annotation.Resource;

@Service
public class AuthorBookChapterService {

    @Resource
    private AuthorBookDao authorBookDao;

    @Resource
    private AuthorBookChapterDao authorBookChapterDao;

    @Resource
    private SuishenRedisTemplate redisTemplate;


    /**
     * 添加章节
     * @param form
     * @return
     */
    public boolean addChapterContent(AdminAddBookChapterForm form) {
        long now=System.currentTimeMillis();

        //添加章节信息
        int wordCount=form.getContent().replaceAll("\n|\r\n|","").length();
        AuthorBookChapter chapter=AuthorBookChapter.Builder.builder().bookId(form.getBookId()).title(form.getTitle())
                .chapterOrder(form.getLastChapterOrder()+1).content(form.getContent()).createTime(now)
                .updateTime(now).wordCount(wordCount).build();

        //更新书籍信息
        AuthorBook authorBook=authorBookDao.getBookByBookId(form.getBookId());
        authorBook.setChapterCount(chapter.getChapterOrder());
        authorBook.setLastChapterUpdateTime(now);
        authorBook.setWordCount(authorBook.getWordCount()+wordCount);
        boolean isSuccess=authorBookDao.updateBookInfo(form.getBookId(),authorBook.getWordCount(),authorBook.getChapterCount(),now);
        if(!isSuccess){
            throw new BusinessException("更新书籍信息失败");
        }
        return authorBookChapterDao.create(chapter);
    }

    /**
     * 更新章节内容
     * @param chapterContent
     * @return
     */
    public boolean updateChapterContent(AuthorBookChapterContent chapterContent) {
        AuthorBookChapter authorBookChapter=authorBookChapterDao.getWordCountByChapterId(chapterContent.getChapterId());
        int wordCountOld=authorBookChapter.getWordCount();
        int wordCountNew=chapterContent.getContent().replaceAll("\n|\r\n|","").length();
        boolean isSuccess=authorBookChapterDao.updateContent(chapterContent.getChapterId(),chapterContent.getContent(),wordCountNew);
        if(!isSuccess){
            throw new BusinessException("更新书籍内容失败");
        }
        AuthorBook book=authorBookDao.getBookByBookId(chapterContent.getBookId());
        book.setWordCount(book.getWordCount()-wordCountOld+wordCountNew);
        book.setLastChapterUpdateTime(System.currentTimeMillis());
        authorBookDao.updateWordCount(book.getId(),book.getWordCount());
        return isSuccess;
    }
}
