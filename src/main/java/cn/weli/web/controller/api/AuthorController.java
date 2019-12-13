package cn.weli.web.controller.api;

import cn.weli.biz.author.dto.AuthorCreateForm;
import cn.weli.biz.author.dto.ExamineApplyForm;
import cn.weli.biz.author.service.AuthorShipService;
import cn.weli.biz.author.service.ExamineLogService;
import cn.weli.common.helper.BaseNovelController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import suishen.libs.meta.JSONResult;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "Author", description = "原创作者平台")
@RestController
@RequestMapping("/api")
public class AuthorController extends BaseNovelController {
    @Resource
    private AuthorShipService authorShipService;

    @Resource
    private ExamineLogService examineLogService;

    @ApiOperation(value = "测试接口")
    @GetMapping(value = "/test", headers = "Accept=application/json")
    public JSONResult test(){
        AuthorCreateForm form = new AuthorCreateForm();
        form.setAuthorId("1");
        form.setEmail("mail");
        return JSONResult.okResult(form);
    }

    @ApiOperation(value = "作者注册")
    @PostMapping(value = "/login", headers = "Accept=application/json")
    public JSONResult addAuthor(@ApiParam @RequestBody @Valid AuthorCreateForm form){
        authorShipService.create(form);
        return JSONResult.okResult(form);
    }

    @ApiOperation(value = "作者申请")
    @PostMapping("/apply")
    @ResponseBody
    public JSONResult authorApply(@ApiParam("申请作者id") @RequestParam(name = "author_id", required = false, defaultValue = "")  String authorId){
        examineLogService.updateApply(authorId);
        return JSONResult.okResult(authorId);
    }

    @ApiOperation(value = "申请审批")
    @RequestMapping("/examine")
    @ResponseBody
    public JSONResult applyExamine(@ApiParam @RequestBody @Valid ExamineApplyForm form){
        if(form.getStatus()==2||form.getStatus()==3){
            examineLogService.applyExamine(form.getExamineId(),form.getStatus(),form.getOpUser(),form.getContent());
            return JSONResult.okResult(form);
        }
        return JSONResult.NO_RESULT;
    }

}
