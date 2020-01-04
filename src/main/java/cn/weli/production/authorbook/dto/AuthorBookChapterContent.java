package cn.weli.production.authorbook.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 原创章节内容
 */
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AuthorBookChapterContent {
    @ApiModelProperty("书籍id")
    private int bookId;

    @ApiModelProperty("章节id")
    private long chapterId;

    @ApiModelProperty("章节内容")
    private String content;
}
