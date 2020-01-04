package cn.weli.production.authorbook.meta;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import suishen.framework.dao.annotation.SuishenPrimary;
import suishen.framework.dao.annotation.SuishenTable;

@Data
@ApiModel
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@SuishenTable(columnNameUnderScore = true, alias = "author_books")
public class AuthorBook {
    @ApiModelProperty("书籍id")
    @SuishenPrimary
    private int id;

    @ApiModelProperty("作者ID")
    private long authorId;

    @ApiModelProperty("书名")
    private String name;

    @ApiModelProperty("章节总数")
    private int chapterCount;

    @ApiModelProperty("字数")
    private int wordCount;

    @ApiModelProperty("创建时间")
    private long createTime;

    @ApiModelProperty("最后更新时间")
    private long updateTime;

    @ApiModelProperty("最近一次更新内容时间")
    private long lastChapterUpdateTime;

    @ApiModelProperty("状态: 1上架 0下架")
    private int status;

    public AuthorBook() {
    }

    public static final class Builder {
        private int id;
        private long authorId;
        private String name;
        private int chapterCount;
        private int wordCount;
        private long createTime;
        private long updateTime;
        private long lastChapterUpdateTime;
        private int status;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder authorId(long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder chapterCount(int chapterCount) {
            this.chapterCount = chapterCount;
            return this;
        }

        public Builder wordCount(int wordCount) {
            this.wordCount = wordCount;
            return this;
        }

        public Builder createTime(long createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(long updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder lastChapterUpdateTime(long lastChapterUpdateTime) {
            this.lastChapterUpdateTime = lastChapterUpdateTime;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public AuthorBook build() {
            AuthorBook authorBook = new AuthorBook();
            authorBook.setId(id);
            authorBook.setAuthorId(authorId);
            authorBook.setName(name);
            authorBook.setChapterCount(chapterCount);
            authorBook.setWordCount(wordCount);
            authorBook.setCreateTime(createTime);
            authorBook.setUpdateTime(updateTime);
            authorBook.setLastChapterUpdateTime(lastChapterUpdateTime);
            authorBook.setStatus(status);
            return authorBook;
        }
    }
}
