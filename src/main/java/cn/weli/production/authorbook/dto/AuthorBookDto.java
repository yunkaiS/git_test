package cn.weli.production.authorbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "返回给客户端的书籍基本信息")
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AuthorBookDto<T> {
    @ApiModelProperty("id")
    @JsonIgnore
    private int id;

    @ApiModelProperty("书籍Id")
    private Integer bookId;

    @ApiModelProperty("书名")
    private String name;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("作者Id")
    private Long authorId;

    @ApiModelProperty("当前页")
    private int page;

    @ApiModelProperty("总页数")
    private int totalPage;

    @ApiModelProperty("总个数，必填")
    private int totalCount;

    @ApiModelProperty("每页应该有的条目数,必填")
    private int pageSize;

    @ApiModelProperty("list结果集")
    private List<T> list;
}
