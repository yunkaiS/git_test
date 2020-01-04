package cn.weli.production.authorbook.service;

import cn.weli.common.PageWrapper;
import cn.weli.production.authorbook.dao.AuthorBookDao;
import cn.weli.production.authorbook.dto.AuthorBookUpdateForm;
import cn.weli.production.authorbook.meta.AuthorBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import suishen.libs.web.exception.BusinessException;

import javax.annotation.Resource;

@Service
@Slf4j
public class AuthorBookService {

    @Resource
    private AuthorBookDao authorBookDao;

    public AuthorBook getById(int id){
        return authorBookDao.getBookByBookId(id);
    }

    /**
     * 录入书籍
     *
     */
    public boolean create(String name,long authorId) {
        long now = System.currentTimeMillis();
        AuthorBook book = new AuthorBook();
        book.setName(name);
        book.setAuthorId(authorId);
        book.setCreateTime(now);
        book.setUpdateTime(now);

        boolean isSuccess = authorBookDao.create(book);
        if (!isSuccess) {
            throw new BusinessException("创建失败，请稍后重试！");
        }

        return isSuccess;
    }

    /**
     * 书籍后台查询
     * @return
     */
    public PageWrapper<AuthorBook> adminQuery(String name, Long authorId, Integer status, int page, int pageSize) {
        return authorBookDao.getAuthorListByPage(name,authorId,status,page,pageSize);
    }

    /**
     * 更新书籍信息
     * @param form
     * @return
     */
    public boolean updateBookInfo(AuthorBookUpdateForm form) {
        boolean isSuccess = authorBookDao.updateBookBasicInfo(form.getBookId(),form.getName(),form.getAuthorId(),form.getStatus());
        if(!isSuccess){
            throw new BusinessException("更新书籍信息失败");
        }
        return isSuccess;
    }
}
