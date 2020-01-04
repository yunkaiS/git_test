package cn.weli.production.authorbook.meta;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import suishen.framework.dao.annotation.SuishenPrimary;
import suishen.framework.dao.annotation.SuishenTable;

/**
 * 章节表
 */
@Data
@ApiModel
@SuishenTable(columnNameUnderScore = true, alias = "author_book_chapter")
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AuthorBookChapter {
    @ApiModelProperty("章节id")
    @SuishenPrimary
    private long id;

    @ApiModelProperty("书籍id")
    private int bookId;

    @ApiModelProperty("章节名")
    private String title;

    @ApiModelProperty("创建时间")
    private long createTime;

    @ApiModelProperty("上次更新时间")
    private long updateTime;

    @ApiModelProperty("章节序号")
    private int chapterOrder;

    @ApiModelProperty("总字数")
    private int wordCount;

    @ApiModelProperty("章节内容")
    private String content;

    public static final class Builder {
        private long id;
        private int bookId;
        private String title;
        private long createTime;
        private long updateTime;
        private int chapterOrder;
        private int wordCount;
        private String content;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder bookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
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

        public Builder chapterOrder(int chapterOrder) {
            this.chapterOrder = chapterOrder;
            return this;
        }

        public Builder wordCount(int wordCount) {
            this.wordCount = wordCount;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public AuthorBookChapter build() {
            AuthorBookChapter authorBookChapter = new AuthorBookChapter();
            authorBookChapter.setId(id);
            authorBookChapter.setBookId(bookId);
            authorBookChapter.setTitle(title);
            authorBookChapter.setCreateTime(createTime);
            authorBookChapter.setUpdateTime(updateTime);
            authorBookChapter.setChapterOrder(chapterOrder);
            authorBookChapter.setWordCount(wordCount);
            authorBookChapter.setContent(content);
            return authorBookChapter;
        }
    }
}
