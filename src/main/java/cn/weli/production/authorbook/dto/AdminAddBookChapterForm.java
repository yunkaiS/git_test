package cn.weli.production.authorbook.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 新增书籍章节表单
 * @author syk
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@ApiModel
public class AdminAddBookChapterForm {

    @ApiModelProperty(value = "书籍id", required = true)
    @NotNull(message = "书籍id不能为空")
    private int bookId;

    @ApiModelProperty(value = "章节标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "章节内容", required = true)
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "上一章章节序号 当前为第一章传0", required = true)
    @NotNull(message = "上一章章节序号不能为空")
    private Integer lastChapterOrder;
}
