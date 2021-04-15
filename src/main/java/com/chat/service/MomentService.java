package com.chat.service;

import com.chat.common.CommonHeader;
import com.chat.common.PageView;
import com.chat.common.ResultException;
import com.chat.entity.article.CommentFavor;
import com.chat.entity.article.Moment;
import com.chat.entity.article.MomentComment;
import com.chat.entity.article.MomentFavor;
import com.chat.entity.user.UserBase;
import com.chat.entity.user.UserPic;
import com.chat.repository.article.CommentFavorRepository;
import com.chat.repository.article.MomentCommentRepository;
import com.chat.repository.article.MomentFavorRepository;
import com.chat.repository.article.MomentRepository;
import com.chat.repository.favor.InteractionRepository;
import com.chat.util.BeanUtil;
import com.chat.vo.article.*;
import com.chat.vo.user.UserShowVO;
import com.chat.vo.verify.MomentItemVerifyVO;
import com.chat.vo.verify.MomentVerifyVO;
import com.chat.vo.verify.UserPicItemVO;
import com.chat.vo.verify.VerifyBatchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chat.common.ResultCodeEnum.*;
import static com.chat.common.UserConstant.*;

@Slf4j
@Service
public class MomentService extends BaseService{
    @Autowired
    MomentRepository momentRepository;
    @Autowired
    MomentCommentRepository momentCommentRepository;
    @Autowired
    InteractionRepository interactionRepository;
    @Autowired
    MomentFavorRepository momentFavorRepository;
    @Autowired
    CommentFavorRepository commentFavorRepository;
    @Autowired
    UserService userService;

    public void save(MomentVO vo) {
        Moment moment = new Moment();
        BeanUtils.copyProperties(vo, moment);
        CommonHeader header = getCommonHeader();
        moment.setAuthorId(header.getUid());
        momentRepository.save(moment);
    }

    public void deleteById(Long id) {
        CommonHeader header = getCommonHeader();
        boolean moment = momentRepository.existsByIdAndAuthorIdAndIsDel(id,header.getUid(),NOT_DEL);
        if (!moment){
            throw new ResultException(MOMENT_COMMENT_NOT_EXIST_EX);
        }
        momentRepository.deleteMoment(id,header.getUid());
    }


    public MomentItemVO getMomentDetail(Long id) {
        Moment moment = momentRepository.findByIdAndIsDel(id,NOT_DEL);
        if (moment == null){
            throw new ResultException(MOMENT_NOT_EXIST_EX);
        }
        MomentItemVO itemVO = getMomentItemVO(moment);
        return itemVO;
    }

    private MomentItemVO getMomentItemVO(Moment moment) {
        MomentItemVO itemVO = new MomentItemVO();
        BeanUtils.copyProperties(moment, itemVO);
        UserBase author = userBaseRepository.findByUidAndIsDel(moment.getAuthorId(),NOT_DEL);
        UserShowVO userShowVO = new UserShowVO()
                .setUid(author.getUid())
                .setUsername(author.getUsername())
                .setAvatar(author.getAvatar());

        itemVO.setAuthor(userShowVO);

        //被赞次数,评论次数,被分享次数
        int lovedCnt = momentFavorRepository.countByMomentIdAndTypeAndIsDel(moment.getId(),MOMENT_LOVED,NOT_DEL);
        int sharedCnt = momentFavorRepository.countByMomentIdAndTypeAndIsDel(moment.getId(),MOMENT_SHARE,NOT_DEL);
        int commentCnt = momentCommentRepository.countByMomentIdAndIsDel(moment.getId(),NOT_DEL);
        //我是否喜欢了，我是否收藏了
        boolean meLoved = momentFavorRepository.existsByMomentIdAndTypeAndUidAndIsDel(moment.getId(),MOMENT_LOVED,getCommonHeader().getUid(),NOT_DEL);
        boolean meKeep = momentFavorRepository.existsByMomentIdAndTypeAndUidAndIsDel(moment.getId(),MOMENT_KEEP,getCommonHeader().getUid(),NOT_DEL);
        boolean meShared = momentFavorRepository.existsByMomentIdAndTypeAndUidAndIsDel(moment.getId(),MOMENT_SHARE,getCommonHeader().getUid(),NOT_DEL);

        //查询点赞的用户
//        List<MomentFavor> favors = momentFavorRepository.findByMomentIdAndTypeAndIsDel(moment.getId(), MOMENT_LOVED, NOT_DEL);
//        List<Long> praiseUserIds = favors.stream().map(MomentFavor::getUid).collect(Collectors.toList());
//        List<UserBase> praiseUsers = userBaseRepository.findByUidInAndIsDel(praiseUserIds,NOT_DEL);
//        List<UserShowVO> favorVOS = praiseUsers.stream().map(p-> new UserShowVO()
//                .setUsername(p.getUsername())
//                .setUid(p.getUid())
//                .setAvatar(p.getAvatar()))
//                .collect(Collectors.toList());
//        itemVO.setPraiseUserList(favorVOS);

        itemVO.setPraiseCnt(lovedCnt);
        itemVO.setCommentCnt(commentCnt);
        itemVO.setShareCnt(sharedCnt);
        itemVO.setPraised(meLoved?1:0);
        itemVO.setKeeped(meKeep?1:0);
        itemVO.setShared(meShared?1:0);
        return itemVO;
    }

    public PageView<MomentItemVO> getMomentByPage(Long authorId, Integer page, Integer pageSize) {
        //查询我不喜欢的文章列表
        List<Long> notLoveMomentIds = momentFavorRepository.findMomentIdByUidAndTypeAndIsDel(getCommonHeader().getUid(),MOMENT_NOT_LOVED,NOT_DEL);

        Pageable pageable = PageRequest.of(Math.max(page - 1, 0),pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<Moment> moments = momentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("isDel"), NOT_DEL));
            if (notLoveMomentIds != null && notLoveMomentIds.size() > 0){
                predicates.add(criteriaBuilder.not(root.get("id").in(notLoveMomentIds)));
            }

            if (authorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("authorId"), authorId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        },pageable);

        List<MomentItemVO> itemVOS = moments.getContent().stream().map(this::getMomentItemVO).collect(Collectors.toList());
        return new PageView<>(moments.getTotalElements(),itemVOS);
    }

    public void saveComment(CommentFormVO vo) {
        switch (vo.getType()){
            case MOMENT_COMMENT:
            case MOMENT_REPORT:
                handleComment(vo);
                break;
            case MOMENT_VIEW:
            case MOMENT_LOVED:
            case MOMENT_NOT_LOVED:
            case MOMENT_KEEP:
            case MOMENT_SHARE:
                handleFavor(vo);
                break;
            default:throw new ResultException(MOMENT_COMMENT_TYPE_INVALID_EX);
        }
    }



    private void handleFavor(CommentFormVO vo) {
        boolean f = momentFavorRepository.existsByMomentIdAndTypeAndUidAndIsDel(vo.getMomentId(),vo.getType(),getCommonHeader().getUid(),NOT_DEL);
        if (f){
            throw new ResultException(MOMENT_FAVOR_EX);
        }
        //对文章的非评论操作都写入一张表
        MomentFavor momentFavor = new MomentFavor();
        BeanUtils.copyProperties(vo, momentFavor);
        momentFavor.setUid(getCommonHeader().getUid());
        momentFavorRepository.save(momentFavor);
    }

    private void handleComment(CommentFormVO vo) {
        //如果是评论和举报，内容不能为空
        if (StringUtils.isEmpty(vo.getContent())){
            throw new ResultException(MOMENT_CONTENT_EMPTY_EX);
        }

        MomentComment comment = new MomentComment();
        BeanUtils.copyProperties(vo, comment);
        comment.setUid(getCommonHeader().getUid());
        momentCommentRepository.save(comment);
    }

    public void saveReply(ReplyFormVO vo) {
        if (StringUtils.isEmpty(vo.getContent())){
            throw new ResultException(MOMENT_REPLY_CONTENT_EMPTY_EX);
        }

        MomentComment comment = new MomentComment();
        BeanUtils.copyProperties(vo, comment);
        comment.setUid(getCommonHeader().getUid());
        momentCommentRepository.save(comment);
    }

    public void deletePraiseById(Long id) {
        CommonHeader header = getCommonHeader();
        MomentFavor comment = momentFavorRepository.findByMomentIdAndTypeAndUidAndIsDel(id, MOMENT_LOVED,header.getUid(), NOT_DEL);
        if (comment == null){
            throw new ResultException(MOMENT_FAVOR_NOT_EX);
        }
        comment.setIsDel(IS_DEL);
        momentFavorRepository.save(comment);
    }

    public PageView<MomentCommentVO> getCommentDetailByPage(Long id, Integer page, Integer pageSize) {
        //查询文章的评论
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0),pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<MomentComment> comments = momentCommentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("momentId"),id),
                    criteriaBuilder.equal(root.get("isDel"),NOT_DEL)
            );
        },pageable);
        long count = comments.getTotalElements();
        List<MomentComment> commentList = comments.getContent();
        List<MomentCommentVO> commentVOS = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            MomentCommentVO commentVO = getCommentDetail(commentList.get(i));
            commentVO.setLevel(i+1);
            commentVOS.add(commentVO);
        }

        return new PageView<>(count, commentVOS);
    }

    public MomentCommentVO getCommentDetail(MomentComment comment){
        MomentCommentVO commentVO = new MomentCommentVO();
        BeanUtil.copyProperties(commentVO,comment);
        //查询评论人信息
        UserBase commentUser = userBaseRepository.findByUidAndIsDel(comment.getUid(),NOT_DEL);
        UserShowVO userShowVO = new UserShowVO()
                .setUid(commentUser.getUid())
                .setUsername(commentUser.getUsername())
                .setNickname(commentUser.getNickname())
                .setAvatar(commentUser.getAvatar());
        commentVO.setUser(userShowVO);

        if (comment.getToUid() != 0) {
            //查询被评论人信息
            UserBase toUser = userBaseRepository.findByUidAndIsDel(comment.getToUid(), NOT_DEL);
            UserShowVO toUserShowVO = new UserShowVO()
                    .setUid(toUser.getUid())
                    .setUsername(toUser.getUsername())
                    .setNickname(toUser.getNickname())
                    .setAvatar(toUser.getAvatar());
            commentVO.setToUser(toUserShowVO);
            commentVO.setType(REPLY);
        }
        //我是否点赞了此评论
        boolean commentFavor = commentFavorRepository.existsByCommentIdAndTypeAndUidAndIsDel(comment.getCommentId(),MOMENT_LOVED,getCommonHeader().getUid(),NOT_DEL);
        commentVO.setFavorType(commentFavor?2:0);

        return commentVO;
    }

    public void saveCommentFavor(Long commentId, Integer type) {
        CommentFavor favor = new CommentFavor();
        favor.setCommentId(commentId);
        favor.setType(type);
        favor.setUid(getCommonHeader().getUid());
        commentFavorRepository.save(favor);
    }

    @Transactional
    public void verifyMoment(MomentVerifyVO vo) {
        //todo 如果用户是管理员才能审核
        if (!userService.isAdmin(getCommonHeader().getUid())){
            throw new ResultException(USER_NOT_ADMIN_EX);
        }
        switch (vo.getType()){
            case VERIFY_TYPE_MOMENT:
                verifyMomentHandle(vo);
                break;
            case VERIFY_TYPE_COMMENT:
                verifyCommentHandle(vo);
                break;
            default:throw new ResultException(USER_VERIFY_TYPE_EX);
        }
    }

    private void verifyCommentHandle(MomentVerifyVO vo) {
        List<VerifyBatchVO> batchVOList = vo.getMomentList();

        if (batchVOList != null && batchVOList.size() > 0){
            List<Long> commentIdsPass = batchVOList.stream().filter(s->s.getStatus().equals(VERIFY_PASS)).map(VerifyBatchVO::getId).collect(Collectors.toList());
            List<Long> commentsNotPass = batchVOList.stream().filter(s->s.getStatus().equals(VERIFY_NOT_PASS)).map(VerifyBatchVO::getId).collect(Collectors.toList());

            if (commentIdsPass.size()>0) {
                momentCommentRepository.updateStatus(commentIdsPass, VERIFY_PASS_STATUS);
            }
            if (commentsNotPass.size()>0) {
                momentCommentRepository.updateStatus(commentsNotPass, VERIFY_NOT_PASS_STATUS);
            }

            //TODO 审核不通过的需要通知用户

        }
    }

    private void verifyMomentHandle(MomentVerifyVO vo) {
        List<VerifyBatchVO> batchVOList = vo.getMomentList();

        if (batchVOList != null && batchVOList.size() > 0){
            List<Long> momentIdsPass = batchVOList.stream().filter(s->s.getStatus().equals(VERIFY_PASS)).map(VerifyBatchVO::getId).collect(Collectors.toList());
            List<Long> momentIdsNotPass = batchVOList.stream().filter(s->s.getStatus().equals(VERIFY_NOT_PASS)).map(VerifyBatchVO::getId).collect(Collectors.toList());
            if (momentIdsPass.size()>0) {
                momentRepository.updateStatus(momentIdsPass, VERIFY_PASS_STATUS);
            }
            if (momentIdsNotPass.size()>0) {
                momentRepository.updateStatus(momentIdsNotPass, VERIFY_NOT_PASS_STATUS);
            }

            //TODO 审核不通过的需要通知用户


        }
    }

    public PageView<MomentItemVerifyVO> getVerifyMomentList(Integer page, Integer pageSize, Integer type) {
        Pageable pageable = PageRequest.of(page - 1,pageSize);
        if (type == 1) {
            Page<Moment> userBases = momentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                //审核中的头像
                predicates.add(criteriaBuilder.equal(root.get("verifyStatus"), VERIFY_PASS_IN));
                predicates.add(criteriaBuilder.equal(root.get("isDel"), 0));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }, pageable);

            long total = userBases.getTotalElements();
            List<MomentItemVerifyVO> itemVOS = userBases.getContent().stream().map(s -> new MomentItemVerifyVO(s.getId(),s.getContent(), s.getPicUrl())).collect(Collectors.toList());
            return new PageView<>(total, itemVOS);
        }else {
            Page<MomentComment> userBases = momentCommentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                //审核中的头像
                predicates.add(criteriaBuilder.equal(root.get("verifyStatus"), VERIFY_PASS_IN));
                predicates.add(criteriaBuilder.equal(root.get("isDel"), 0));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }, pageable);

            long total = userBases.getTotalElements();
            List<MomentItemVerifyVO> itemVOS = userBases.getContent().stream().map(s -> new MomentItemVerifyVO(s.getId(),s.getContent(), s.getCommentUrl())).collect(Collectors.toList());
            return new PageView<>(total, itemVOS);
        }
    }
}
