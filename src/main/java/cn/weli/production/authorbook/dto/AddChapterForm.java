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
public class AddChapterForm {
    @ApiModelProperty(value = "章节名称", required = true)
    @NotBlank(message = "章节名称不能为空")
    private String chapter;

    @ApiModelProperty(value = "章节内容", required = true)
    @NotNull(message = "章节内容不能为空")
    private Integer content;

    @ApiModelProperty(value = "书籍id", required = true)
    @NotNull(message = "书籍id不能为空")
    private Integer bookId;
}
