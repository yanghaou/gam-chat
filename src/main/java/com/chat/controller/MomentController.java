package com.chat.controller;

import com.chat.common.CommonResult;
import com.chat.common.PageView;
import com.chat.service.MomentService;
import com.chat.vo.article.*;
import com.chat.vo.verify.MomentVerifyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "文章相关接口")
@RestController
public class MomentController extends BaseController{
    @Autowired
    MomentService momentService;

    @ApiOperation(value = "发布文章")
    @PostMapping("/moment/save")
    public CommonResult save(@Valid @RequestBody MomentVO vo, BindingResult bindingResult) {
        checkParam(bindingResult);
        momentService.save(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除文章")
    @GetMapping("/moment/delete")
    public CommonResult delete(@ApiParam("文章id") @RequestParam Long id) {
        momentService.deleteById(id);
        return CommonResult.success();
    }

    @ApiOperation(value = "查看文章详情")
    @GetMapping("/moment/detail")
    public CommonResult<MomentItemVO> detail(@ApiParam("文章id") @RequestParam Long id) {
        MomentItemVO itemVO = momentService.getMomentDetail(id);
        return CommonResult.success(itemVO);
    }

    @ApiOperation(value = "分页查看文章的所有评论")
    @GetMapping("/moment/comment")
    public CommonResult<PageView<MomentCommentVO>> commentDetail(@ApiParam("文章id") @RequestParam Long id,
                                                                 @ApiParam("分页") @RequestParam(defaultValue = "1") Integer page,
                                                                 @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize
                                                       ) {
        PageView<MomentCommentVO> itemVO = momentService.getCommentDetailByPage(id,page,pageSize);
        return CommonResult.success(itemVO);
    }

    @ApiOperation(value = "查看文章列表")
    @GetMapping("/moment/list")
    public CommonResult<PageView<MomentItemVO>> list(
                                       @ApiParam("作者id") @RequestParam(required = false) Long authorId,
                                       @ApiParam("关注类型:1=关注，2=推荐，3=最新") @RequestParam(required = false) Integer searchType,
                                       @ApiParam("分页") @RequestParam(defaultValue = "1") Integer page,
                                       @ApiParam("总数量") @RequestParam(defaultValue = "5") Integer pageSize) {
        PageView<MomentItemVO> pageView = momentService.getMomentByPage(authorId,page,pageSize);
        return CommonResult.success(pageView);
    }


    @ApiOperation(value = "用户对文章的操作(点赞,评论,转发等)")
    @PostMapping("/moment/comment/save")
    public CommonResult comment(@Valid @RequestBody CommentFormVO vo, BindingResult bindingResult) {
        checkParam(bindingResult);
        momentService.saveComment(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "对评论的回复")
    @PostMapping("/moment/comment/reply/save")
    public CommonResult replyComment(@Valid @RequestBody ReplyFormVO vo, BindingResult bindingResult) {
        checkParam(bindingResult);
        momentService.saveReply(vo);
        return CommonResult.success();
    }

    @ApiOperation(value = "对评论的点赞和举报")
    @GetMapping("/moment/comment/favor")
    public CommonResult commentFavor(@ApiParam("评论id") @RequestParam Long commentId,
                                     @ApiParam("2=点赞喜欢,7=举报") @RequestParam Integer type
                                     ) {

        momentService.saveCommentFavor(commentId,type);
        return CommonResult.success();
    }


    @ApiOperation(value = "撤销点赞")
    @GetMapping("/moment/praise/revoke")
    public CommonResult praiseRevoke(@ApiParam("文章id") @RequestParam Long id) {
        momentService.deletePraiseById(id);
        return CommonResult.success();
    }
}
