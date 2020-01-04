package cn.weli.production.authorbook.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class AuthorBookUpdateForm {
    @ApiModelProperty("书籍id")
    @NotNull(message = "书籍id不可为空")
    private Integer bookId;

    @ApiModelProperty("书籍名称")
    @NotEmpty(message = "书籍名称不可为空")
    private String name;

    @ApiModelProperty("作者id")
    private long authorId;

    @ApiModelProperty("书籍状态：1、上架  0、下架")
    private int status;
}
