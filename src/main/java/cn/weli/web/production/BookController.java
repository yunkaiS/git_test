package cn.weli.web.production;

import cn.weli.common.PageWrapper;
import cn.weli.production.authorbook.dto.AdminAddBookChapterForm;
import cn.weli.production.authorbook.dto.AuthorBookChapterContent;
import cn.weli.production.authorbook.dto.AuthorBookUpdateForm;
import cn.weli.production.authorbook.meta.AuthorBook;
import cn.weli.production.authorbook.service.AuthorBookChapterService;
import cn.weli.production.authorbook.service.AuthorBookService;
import cn.weli.biz.book.dto.ClientBookBasicDto;
import cn.weli.util.ValidationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import suishen.libs.meta.JSONResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Api(value = "authorbook", description = "原创作者书籍平台")
@RestController
@RequestMapping("/authorbook")
public class BookController {
    @Resource
    private AuthorBookService authorBookService;

    @Resource
    private AuthorBookChapterService authorBookChapterService;

    @ApiOperation(value = "书籍录入")
    @PostMapping(value = "input", headers = "Accept=application/json")
    public JSONResult input(@ApiParam("书名") @RequestParam(required = true,name="name",defaultValue = "") String name,
                            @ApiParam("作者Id") @RequestParam(required = true,name="author_id",defaultValue = "0") Long authorId){
        boolean isSuccess =authorBookService.create(name,authorId);
        return isSuccess?JSONResult.okResult():JSONResult.failureResult("抱歉，操作失败，请刷新后重试");
    }

    @ApiOperation(value = "查询书籍列表", response = ClientBookBasicDto.class, responseContainer = "List")
    @GetMapping(value = "query", headers = "Accept=application/json")
    @Deprecated
    public JSONResult queryBook(
            @ApiParam("书籍名称") @RequestParam(name = "name", required = false) String name,
            @ApiParam("作者Id") @RequestParam(name="author_id",required = false) Long authorId,
            @ApiParam("书籍状态") @RequestParam(name="status",required = false) Integer status,
            @ApiParam("页数")
            @Pattern(regexp = ValidationUtils.POSITIVE_NUMBER, message = "分页参数有误(页数)")
            @RequestParam(required = false, defaultValue = "1") String page,
            @ApiParam("每页多少条")
            @Pattern(regexp = ValidationUtils.POSITIVE_NUMBER, message = "分页参数有误(页大小)")
            @RequestParam(required = false, defaultValue = "10", name = "page_size") String pageSize) {
        PageWrapper<AuthorBook> wrapper = authorBookService.adminQuery(name, authorId,status,Integer.parseInt(page),Integer.parseInt(pageSize));
        return JSONResult.okResult(wrapper);
    }

    @ApiOperation(value = "编辑书籍基本信息", response = Boolean.class)
    @PostMapping(value = "/update/info", headers = "Accept=application/json")
    public JSONResult updateBook(@ApiParam("请求参数") @Valid @RequestBody AuthorBookUpdateForm form) {
        boolean isSuccess = authorBookService.updateBookInfo(form);
        return isSuccess ? JSONResult.okResult() : JSONResult.failureResult("抱歉，操作失败，请刷新后重试");
    }

    @ApiOperation(value = "新增章节")
    @PostMapping(value = "chapter/input", headers = "Accept=application/json")
    public JSONResult chapterInput(@ApiParam @RequestBody @Valid AdminAddBookChapterForm form){
        authorBookChapterService.addChapterContent(form);
        return JSONResult.okResult(form);
    }

    @ApiOperation(value = "编辑章节内容", response = Boolean.class, responseContainer = "Map")
    @PostMapping(value = "/chapter/edit", headers = "Accept=application/json")
    public JSONResult chapterEdit(@ApiParam("章节内容") @RequestBody AuthorBookChapterContent chapterContent) {
        if (StringUtils.isBlank(chapterContent.getContent())) {
            return JSONResult.paramErrorResult("内容不可为空");
        }
        return JSONResult.okResult(authorBookChapterService.updateChapterContent(chapterContent));
    }
}
