package com.chat.service;

import com.chat.common.CommonHeader;
import com.chat.common.PageView;
import com.chat.common.ResultException;
import com.chat.entity.suggest.Suggest;
import com.chat.repository.suggest.SuggestRepository;
import com.chat.vo.suggest.SuggestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.chat.common.ResultCodeEnum.SUGGEST_NOT_EXIST_EX;
import static com.chat.common.UserConstant.NOT_DEL;

@Slf4j
@Service
public class SuggestService extends BaseService{
    @Autowired
    SuggestRepository suggestRepository;

    public PageView<SuggestVO> getListByPage(Integer page, Integer pageSize, Long uid) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0),pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<Suggest> moments = suggestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            if (uid != null && uid > 0) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("uid"), uid),
                        criteriaBuilder.equal(root.get("isDel"), NOT_DEL)
                );
            }else {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isDel"), NOT_DEL)
                );
            }
        },pageable);

        List<SuggestVO> itemVOS = moments.getContent().stream().map(
                vo -> {
                    SuggestVO suggestVO = new SuggestVO()
                            .setId(vo.getId())
                            .setUid(vo.getUid())
                            .setTitle(vo.getTitle())
                            .setContent(vo.getContent())
                            .setCreateTime(vo.getCreateTime());
                    return suggestVO;
                }
        ).collect(Collectors.toList());
        return new PageView<>(moments.getTotalElements(),itemVOS);
    }

    public SuggestVO getDetailById(Long id) {
        Suggest vo = suggestRepository.findByIdAndIsDel(id,NOT_DEL);
        if (vo == null){
            throw new ResultException(SUGGEST_NOT_EXIST_EX);
        }
        SuggestVO suggestVO = new SuggestVO()
                .setId(vo.getId())
                .setUid(vo.getUid())
                .setContent(vo.getContent())
                .setTitle(vo.getTitle())
                .setCreateTime(vo.getCreateTime());
        return suggestVO;
    }

    public void delById(Long id) {
        boolean f = suggestRepository.existsById(id);
        if (f){
            throw new ResultException(SUGGEST_NOT_EXIST_EX);
        }
        suggestRepository.delById(id);
    }

    public void save(SuggestVO vo) {
        CommonHeader header = getCommonHeader();
        Suggest suggest = new Suggest();
        suggest.setUid(header.getUid());
        suggest.setTitle(vo.getTitle());
        suggest.setContent(vo.getContent());
        suggest.setUrl(vo.getUrl());
        suggestRepository.save(suggest);
    }
}
