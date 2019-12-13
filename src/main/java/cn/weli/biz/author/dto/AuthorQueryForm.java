package cn.weli.biz.author.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class AuthorQueryForm {
    @ApiModelProperty("作者id")
    private String authorId;

    @ApiModelProperty("作者名称")
    private String authorName;

    @ApiModelProperty("账号状态 0下架 1上架")
    private Integer status;

    @ApiModelProperty("页次 从1开始")
    @NotNull(message = "页次不能为空")
    @Min(value = 1, message = "无效的页次参数")
    private Integer page;

    @ApiModelProperty("页大小")
    @NotNull(message = "页大小不能为空")
    @Min(value = 1, message = "无效的页大小参数")
    private Integer pageSize;
}
