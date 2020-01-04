package cn.weli.production.authorbook.cache;

import cn.weli.production.authorbook.meta.AuthorBook;
import cn.weli.common.constant.Constant;
import cn.weli.config.redis.MsRedisTemplate;
import cn.weli.common.RedisKey;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import suishen.libs.util.JSONUtil;

import javax.annotation.Resource;

@Repository
public class AuthorBookCache {
    @Resource
    private MsRedisTemplate redisTemplate;

    private static final int BOOK_EXPIRE_SECONDS = Constant.ONE_DAY_SECONDS * 5;

    public AuthorBook get(int bookId) {
        String cacheKey = RedisKey.keyOfBook(bookId);
        String cacheValue = redisTemplate.get(cacheKey);
        return StringUtils.isBlank(cacheValue) ? null : JSONUtil.getObject(cacheValue, AuthorBook.class);
    }

    public boolean set(AuthorBook book) {
        String cacheKey = RedisKey.keyOfBook(book.getId());
        redisTemplate.setex(cacheKey, BOOK_EXPIRE_SECONDS, JSONUtil.getJsonString(book));
        return true;
    }
}
