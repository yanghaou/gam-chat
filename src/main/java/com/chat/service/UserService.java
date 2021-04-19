package com.chat.service;


import cn.hutool.core.util.RandomUtil;
import com.chat.common.CommonHeader;
import com.chat.common.PageView;
import com.chat.common.ResultException;
import com.chat.entity.auth.Permission;
import com.chat.entity.auth.UserRole;
import com.chat.entity.favor.Interaction;
import com.chat.entity.user.*;
import com.chat.repository.auth.RoleRepository;
import com.chat.repository.auth.UserRoleRepository;
import com.chat.repository.favor.InteractionRepository;
import com.chat.repository.favor.ViewMeRepository;
import com.chat.repository.user.UserBaseRepository;
import com.chat.repository.user.UserExtraRepository;
import com.chat.repository.user.UserLocationRepository;
import com.chat.repository.user.UserPicRepository;
import com.chat.util.*;
import com.chat.vo.common.IdAndNameVO;
import com.chat.vo.file.PicVO;
import com.chat.vo.user.*;
import com.chat.vo.verify.*;
import com.github.wujun234.uid.impl.CachedUidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.chat.common.ResultCodeEnum.*;
import static com.chat.common.UserConstant.*;

@Slf4j
@Service
public class UserService extends BaseService{
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value(("${jwt.tokenHead}"))
    private String tokenHead;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Value("${verify.code.validTime:10}")
    Integer verifyCodeValidTime;
    @Value("${verify.code.prefix}")
    String verifyCodePrefix;
    @Autowired
    JMailUtil jMailUtil;
    @Autowired
    UserPicRepository userPicRepository;
    @Value("${im.expire}")
    long imExpireTime;
    @Autowired
    TLSSigAPIv2 tlsSigAPIv2;
    @Autowired
    ViewMeRepository viewMeRepository;
    @Autowired
    InteractionRepository interactionRepository;
    @Resource
    CachedUidGenerator uidGenerator;
    @Autowired
    UserBaseRepository userBaseRepository;
    @Autowired
    UserExtraRepository userExtraRepository;
    @Autowired
    UserLocationRepository userLocationRepository;
    @Value("${qiniu.domain}")
    String qiNiuDomain;
    @Value("${adminRoleId:1}")
    Long adminRoleId;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    NoticeService noticeService;

    public Set<Permission> getPermissionList(Long uid) {
        Set<Permission> permissions = new HashSet<>();

        return permissions;
    }

    public List<Long> getUserRoleIdList(Long uid){
        List<UserRole> userRoles = userRoleRepository.findByUidAndIsDel(uid,NOT_DEL);
        if (userRoles != null && userRoles.size() > 0){
            return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional
    public void register(UserRegisterVO vo) {
        StringUtil.checkUsername(vo.getUsername());
        StringUtil.checkPassword(vo.getPassword());
        StringUtil.checkEmail(vo.getEmail());

        CommonHeader c = getCommonHeader();

        //查询邮箱和用户名对应的账号是否存在
        boolean emailExist = userAccountRepository.existsByAppTypeAndClientTypeAndEmailAndIsDel(c.getAppType(),c.getClientType(),vo.getEmail(),NOT_DEL);
        boolean usernameExist = userAccountRepository.existsByAppTypeAndClientTypeAndUsernameAndIsDel(c.getAppType(),c.getClientType(),vo.getUsername(),NOT_DEL);
        if (emailExist||usernameExist){
            throw new ResultException(USER_EXIST_EX);
        }

        UserAccount user = new UserAccount();
        BeanUtils.copyProperties(vo, user);
        //客户端类型
        user.setAppType(c.getAppType());
        user.setClientType(c.getClientType());
        //获取唯一UID
        long uid = uidGenerator.getUID();
        user.setUid(uid);
        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        userAccountRepository.save(user);

        //插入用户基本信息表
        UserBase userBase = new UserBase();
        userBase.setUid(uid);
        userBase.setUsername(user.getUsername());
        userBaseRepository.save(userBase);
    }

    public UserLoginResVO login(UserLoginVO vo) {
        CommonHeader header = getCommonHeader();
        if (StringUtils.isEmpty(header.getAppType()) || header.getAppType() == null)  {
            throw new ResultException(APP_TYPE_CLIENT_TYPE_BLANK_EX);
        }
        UserAccount user = userAccountRepository.findByAppTypeAndClientTypeAndEmailAndIsDel(header.getAppType(), header.getClientType(), vo.getEmail(),NOT_DEL);
        if (user == null){
            throw new ResultException(USER_EMAIL_NOT_EXIST_EX);
        }

        if (!passwordEncoder.matches(vo.getPassword(),user.getPassword())){
//            throw new ResultException(PASSWORD_ERROR);
            throw new ResultException(PASSWORD_INCORRECT_EX);
        }
        String token = jwtTokenUtil.generateToken(user.getUid().toString());

        if (token == null){
//            throw new ResultException(TOKEN_GENERATE_ERROR);
            throw new ResultException(TOKEN_GENERATE_EX);
        }

        return new UserLoginResVO(user.getUid(),tokenHead+" "+token, user.getAccountStatus());
    }

    public UserDetailVO getDetail() {
        //账号信息
        UserAccount user = getUserByHeader();
        //查询基本信息
        UserBase userBase = userBaseRepository.findByUidAndIsDel(user.getUid(),NOT_DEL);
        //查询扩展信息
        UserExtra userExtra = userExtraRepository.findByUidAndIsDel(user.getUid(),NOT_DEL);
        //查询位置信息
        UserLocation userLocation = userLocationRepository.findByUidAndIsDel(user.getUid(),NOT_DEL);
        //图片信息
        List<UserPic> userPics = userPicRepository.findByUidAndIsDel(user.getUid(),NOT_DEL);
        UserDetailVO userVO = new UserDetailVO();
        userVO.setUserAccount(user);
        userVO.setUserBase(userBase);
        userVO.setUserExtra(userExtra);
        userVO.setUserLocation(userLocation);

        UserOther other = new UserOther();
        other.setUserSign(getUserSig());
        other.setQiNiuDomain(qiNiuDomain);
        //im_uid_用户名
        other.setImId(new StringBuilder().append("im_").append(user.getUid()).append("_").append(user.getUsername()).toString());
        userVO.setOther(other);

        userVO.setUserPics(userPics);

        //用户权限角色
//        userVO.setUserRoles(null);

        return userVO;
    }

    public UserShowVO getShowDetail(Long uid) {
        UserBase userBase = userBaseRepository.findByUidAndIsDel(uid,NOT_DEL);

        UserShowVO userVO = new UserShowVO();
        BeanUtils.copyProperties(userBase, userVO);

        //查询被别人关注数量
        int followed = interactionRepository.countByToUserIdAndTypeAndIsDel(uid,OTHERS_FAVOR_ME,NOT_DEL);
        //查询我关注别人的数量
        int favorCnt = interactionRepository.countByFromUserIdAndTypeAndIsDel(uid,ME_FAVOR_OTHERS,NOT_DEL);
        //我是否关注了此用户
        CommonHeader header = getCommonHeader();
        if (!header.getUid().equals(uid)){
            boolean favor = interactionRepository.existsByFromUserIdAndToUserIdAndTypeAndIsDel(header.getUid(), uid, FAVOR,NOT_DEL);
            if (favor){
                //已关注此用户
                userVO.setFavored(1);
            }
        }
        //我是否关注了此用户
        userVO.setFollowedCnt(followed);
        userVO.setFavorCnt(favorCnt);
        return userVO;
    }

    public void updatePassword(UserPassUpdateVO vo) {
        StringUtil.checkPassword(vo.getNewPassword());
        UserAccount user = getUserByHeader();
        if (!passwordEncoder.matches(vo.getOldPassword(),user.getPassword())){
            throw new ResultException(OLD_PASSWORD_ERROR_EX);
        }

        user.setPassword(passwordEncoder.encode(vo.getNewPassword()));

        userAccountRepository.save(user);
    }

    @Transactional
    public void save(UserDetailVO vo) {
        UserAccount account = getUserByHeader();

        //保存用户的详情信息
        if (account.getAccountStatus() == ACCOUNT_STATUS_CREATE){
            //账号是新建状态时，用户基本信息和位置信息必填
            if (vo.getUserBase() == null || vo.getUserLocation() == null){
                throw new ResultException(USER_BASE_AND_LOCATION_EX);
            }
        }
        UserBase userBase = vo.getUserBase();
        if (userBase != null){
            userBase.setUid(account.getUid());
            userBase.setUsername(account.getUsername());
            userBaseRepository.save(userBase);
        }
        UserLocation userLocation = vo.getUserLocation();
        if (userLocation != null){
            userLocation.setUid(account.getUid());
            userLocationRepository.save(userLocation);
        }

        if (account.getAccountStatus() == ACCOUNT_STATUS_CREATE){
            //账号是新建状态时，保存基本信息后需要修改账号状态为审核中
            userAccountRepository.updateStatus(account.getUid(),NOT_DEL,ACCOUNT_STATUS_VERIFY,"");
        }

        UserExtra userExtra = vo.getUserExtra();
        if (userExtra != null){
            userExtra.setUid(account.getUid());
            userExtraRepository.save(userExtra);
        }

        // 用户信息保存完成后，给管理员发送审核信息
        List<Long> adminUid = userRoleRepository.findByRoleIdAndIsDel(adminRoleId, NOT_DEL);
        sendImUserMsg(getImIdsByUid(adminUid),"uid:"+account.getUid()+" update user detail , please verify");
    }

    public void sendCode(String email) {
        CommonHeader header = getCommonHeader();
        UserAccount user = userAccountRepository.findByAppTypeAndClientTypeAndEmailAndIsDel(header.getAppType(), header.getClientType(), email,NOT_DEL);
        if (user == null){
            throw new ResultException(USER_EMAIL_NOT_EXIST_EX);
        }
        //随机验证码
        String veryCode = RandomUtil.randomString(4);
        String key = verifyCodePrefix + email;
        //存入redis，有效期
        stringRedisTemplate.opsForValue().set(key,veryCode,verifyCodeValidTime, TimeUnit.MINUTES);
        jMailUtil.sendMail1(email,"password find back","verify code:"+veryCode);
    }

    public void forgetPassword(UserForgetPassVO vo) {
        String key = verifyCodePrefix + vo.getEmail();
        //存入redis，有效期5分钟
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null){
            throw new ResultException(FOUND_PASSWORD_VERIFY_CODE_NOT_EXIST_EX);
        }
        //验证码不正确
        if (!code.equals(vo.getVerifyCode())){
            throw new ResultException(FOUND_PASSWORD_VERIFY_CODE_ERROR_EX);
        }
        //密码格式不正确
        StringUtil.checkPassword(vo.getPassword());

        //重新保存新密码
        CommonHeader header = getCommonHeader();
        UserAccount user = userAccountRepository.findByAppTypeAndClientTypeAndEmailAndIsDel(header.getAppType(), header.getClientType(), vo.getEmail(),NOT_DEL);
        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        userAccountRepository.save(user);
        //密码更新后删除验证码
        stringRedisTemplate.delete(key);

    }



    public IdAndNameVO savePicture(PicVO vo) {
        UserPic userPic = new UserPic();
        userPic.setPicType(vo.getPicType());
        userPic.setPicUrl(vo.getPicUrl());
        userPic.setPicOrder(vo.getPicOrder());
        userPic.setUid(getUid());

        userPicRepository.save(userPic);

        return new IdAndNameVO(userPic.getId());
    }

    public void deletePic(Long picId) {
        userPicRepository.delPicByIdAndUserId(picId,getUid());
    }

    /**
     * 获取腾讯七牛云签名
     * @return
     */
    public String getUserSig() {
        //账号信息
        UserAccount user = getUserByHeader();
        StringBuilder imId = new StringBuilder().append("im_").append(user.getUid()).append("_").append(user.getUsername());
        return tlsSigAPIv2.genUserSig(imId.toString(), imExpireTime);
    }

    @Transactional
    public void saveFavor(Long userId, Integer type) {
        CommonHeader header = getCommonHeader();
        //查询被关注用户是否存在
        boolean account = userAccountRepository.existsByUidAndIsDel(userId,NOT_DEL);
        if (!account){
            throw new ResultException(FAVOR_USER_NOT_EXIST_EX);
        }
        //查询是否已关注
        Interaction exist = interactionRepository.findInteractionByFromUserIdAndToUserIdAndTypeAndIsDel(header.getUid(), userId,type,NOT_DEL);

        Interaction interaction = new Interaction();
        if (type == FAVOR){
            //关注某个用户
            if (exist != null){
                throw new ResultException(USER_HAVE_FAVORED_ERROR_EX);
            }
            interaction.setFromUserId(header.getUid());
            interaction.setToUserId(userId);
            interaction.setType(INTERACTION_TYPE_FAVOR);
            interactionRepository.save(interaction);
        }else {
            if (exist == null){
                throw new ResultException(USER_HAVE_NOT_FAVORED_ERROR_EX);
            }
            //删除关注关系
            exist.setIsDel(1);
            interactionRepository.save(exist);
        }
    }

    public PageView<UserShowVO> getByFavorType(Integer page, Integer pageSize, Integer favorType) {
        CommonHeader header = getCommonHeader();
        UserBase user = userBaseRepository.findByUidAndIsDel(header.getUid(),NOT_DEL);
        Pageable pageable = PageRequest.of(Math.max(page-1,page),pageSize, Sort.by(Sort.Direction.DESC,"id"));

        Page<Interaction> interactions = interactionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("isDel"),0));
            predicates.add(criteriaBuilder.equal(root.get("type"),favorType));
            if (favorType == ME_FAVOR_OTHERS){
                //我关注别人
                predicates.add(criteriaBuilder.equal(root.get("fromUserId"),user.getUid()));
            }else if (favorType == OTHERS_FAVOR_ME){
                //别人关注我
                predicates.add(criteriaBuilder.equal(root.get("toUserId"),user.getUid()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        },pageable);

        List<Long> userIds = null;
        long total = interactions.getTotalElements();
        if (favorType == ME_FAVOR_OTHERS) {
            userIds = interactions.stream().map(Interaction::getToUserId).collect(Collectors.toList());
        }else {
            userIds = interactions.stream().map(Interaction::getFromUserId).collect(Collectors.toList());
        }

        List<UserBase> userList = userBaseRepository.findByUidInAndIsDel(userIds,NOT_DEL);
        List<UserShowVO> userShowVOS = userList.stream().map(u->{
            UserShowVO showVO = new UserShowVO();
            BeanUtils.copyProperties(u,showVO);
            return showVO;
        }).collect(Collectors.toList());

        return new PageView<>(total, userShowVOS);
    }

    public PageView<UserShowVO> searchUserList(UserSearchVO searchVO) {
        CommonHeader header = getCommonHeader();
        UserBase userBase = userBaseRepository.findByUidAndIsDel(header.getUid(), NOT_DEL);
        Integer page = searchVO.getPage();
        Integer pageSize = searchVO.getPageSize();


        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<UserBase> userList = userBaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("isDel"),0));
            if (searchVO.getGender() != null){
                predicates.add(criteriaBuilder.equal(root.get("gender"),searchVO.getGender()));
            }
            if (StringUtils.isNotEmpty(searchVO.getSignature())){
                predicates.add(criteriaBuilder.like(root.get("signature"),"%"+searchVO.getSignature()+"%"));
            }
            if (StringUtils.isNotEmpty(searchVO.getUsername())){
                predicates.add(criteriaBuilder.like(root.get("username"),"%"+searchVO.getUsername()+"%"));
            }
            if (StringUtils.isNotEmpty(searchVO.getAboutMe())){
                predicates.add(criteriaBuilder.like(root.get("aboutMe"),"%"+searchVO.getAboutMe()+"%"));
            }
            if (StringUtils.isNotEmpty(searchVO.getAboutMatch())){
                predicates.add(criteriaBuilder.like(root.get("aboutMatch"),"%"+searchVO.getAboutMatch()+"%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        },pageable);

        List<UserShowVO> userShowVOS = userList.getContent().stream().map(u->{
            UserShowVO showVO = new UserShowVO();
            BeanUtils.copyProperties(u,showVO);
            return showVO;
        }).collect(Collectors.toList());

        return new PageView<>(userList.getTotalElements(), userShowVOS);
    }

    @Transactional
    public void verifyUserDetail(UserDetailVerifyVO vo) {
        if (!isAdmin(getCommonHeader().getUid())){
            throw new ResultException(USER_NOT_ADMIN_EX);
        }
        List<UserDetailVerifyBaseVO> baseVOList = vo.getVerifyDetail();
        if (baseVOList == null || baseVOList.size() == 0){
            throw new ResultException(VERIFY_CONTENT_EMPTY_EX);
        }
        for (UserDetailVerifyBaseVO baseVO : baseVOList) {
            switch (vo.getType()){
                case VERIFY_TYPE_AVATAR:
                    verifyAvatar(baseVO);
                    break;
                case VERIFY_TYPE_ABOUT_ME:
                    verifyAboutMe(baseVO);
                    break;
                case VERIFY_TYPE_ABOUT_MATCH:
                    verifyAboutMatch(baseVO);
                    break;
                case VERIFY_TYPE_ACCOUNT:
                    verifyAccount(baseVO);
                    break;
                default:throw new ResultException(USER_VERIFY_TYPE_EX);
            }
        }
    }

    private void verifyAccount(UserDetailVerifyBaseVO vo) {
//        UserDetailVerifyBaseVO vo = verifyVO.getVerifyDetail();
        Integer accountStatus = vo.getStatus() == VERIFY_PASS?ACCOUNT_STATUS_PASS:ACCOUNT_STATUS_NOT_PASS;
        userAccountRepository.updateStatus(vo.getVerifyUid(),NOT_DEL,accountStatus, vo.getReason());
        if (vo.getStatus() == VERIFY_NOT_PASS){
            UserAccount userAccount = userAccountRepository.findByUidAndIsDel(vo.getVerifyUid(),NOT_DEL);
            StringBuilder toAccount = new StringBuilder().append("im_").append(userAccount.getUid()).append("_").append(userAccount.getUsername());
            sendImUserMsg(Arrays.asList(toAccount),"Your account is frozen, Please contact the administrator to unfreeze");
        }
    }

    private void verifyAboutMatch(UserDetailVerifyBaseVO vo) {
//        UserDetailVerifyBaseVO vo = verifyVO.getVerifyDetail();
        UserExtra userExtra = userExtraRepository.findByUidAndIsDel(vo.getVerifyUid(), NOT_DEL);
        if (vo.getStatus() == VERIFY_PASS) {
            //修改了aboutMe
            userExtra.setAboutMatch(userExtra.getAboutMatchTemp());
            userExtra.setAboutMatchTemp("");
            userExtra.setAboutMatchStatus(VERIFY_PASS_STATUS);
        }else {
            userExtra.setAboutMatchStatus(VERIFY_NOT_PASS_STATUS);
            UserAccount userAccount = userAccountRepository.findByUidAndIsDel(vo.getVerifyUid(),NOT_DEL);
            StringBuilder toAccount = new StringBuilder().append("im_").append(userAccount.getUid()).append("_").append(userAccount.getUsername());
            sendImUserMsg(Arrays.asList(toAccount),"Your AboutMatch is illegal, please re-write");

        }
        userExtra.setAboutMeReason(vo.getReason());
        userExtraRepository.save(userExtra);
    }

    private void verifyAboutMe(UserDetailVerifyBaseVO vo) {
//        UserDetailVerifyBaseVO vo = verifyVO.getVerifyDetail();
        UserExtra userExtra = userExtraRepository.findByUidAndIsDel(vo.getVerifyUid(), NOT_DEL);
        if (vo.getStatus() == VERIFY_PASS) {
            userExtra.setAboutMe(userExtra.getAboutMeTemp());
            userExtra.setAboutMeTemp("");
            userExtra.setAboutMeStatus(VERIFY_PASS_STATUS);
        }else {
            userExtra.setAboutMeStatus(VERIFY_NOT_PASS_STATUS);

            UserAccount userAccount = userAccountRepository.findByUidAndIsDel(vo.getVerifyUid(),NOT_DEL);
            StringBuilder toAccount = new StringBuilder().append("im_").append(userAccount.getUid()).append("_").append(userAccount.getUsername());
            sendImUserMsg(Arrays.asList(toAccount),"Your AboutMe is illegal, please re-write");
        }
        userExtra.setAboutMeReason(vo.getReason());
        userExtraRepository.save(userExtra);
    }

    private void verifyAvatar(UserDetailVerifyBaseVO vo) {
//        UserDetailVerifyBaseVO vo = verifyVO.getVerifyDetail();
        UserBase userBase = userBaseRepository.findByUidAndIsDel(vo.getVerifyUid(), NOT_DEL);
        if (userBase == null){
            throw new ResultException(USER_ID_NOT_FOUND);
        }
        if (vo.getStatus() == VERIFY_PASS) {
            userBase.setAvatar(userBase.getAvatarTemp());
            userBase.setAvatarTemp("");
            userBase.setAvatarStatus(VERIFY_PASS_STATUS);

            //头像审核通过，需要更新账号状态为审批通过
            userAccountRepository.updateStatus(vo.getVerifyUid(),NOT_DEL,ACCOUNT_STATUS_PASS,"");
        }else {
            userBase.setAvatarStatus(VERIFY_NOT_PASS_STATUS);
            //头像审核不通过，需要更新账号状态为审批不通过
            userAccountRepository.updateStatus(vo.getVerifyUid(),NOT_DEL,ACCOUNT_STATUS_NOT_PASS,"");
            StringBuilder toAccount = new StringBuilder().append("im_").append(userBase.getUid()).append("_").append(userBase.getUsername());
            sendImUserMsg(Arrays.asList(toAccount),"Your avatar is illegal, please re-upload");
        }
        userBaseRepository.save(userBase);

    }

    /**
     * [
     *         {
     *             "MsgType": "TIMTextElem",
     *             "MsgContent": {
     *                 "Text": "hi, beauty"
     *             }
     *         }
     *     ]
     * @param msg
     */
    public void sendImUserMsg(List toAccount,String msg){
        List list = Arrays.asList(getNoticeMsg(msg));
        Callable<Integer> callable = () -> noticeService.adminSendVerifyNotice(toAccount,list);
        AsyncTaskExecutor.submit(callable);
    }

    public Map getNoticeMsg(String msg){
        Map map = new HashMap();
        map.put("MsgType","TIMTextElem");
        Map mg = new HashMap();
        mg.put("Text",msg);
        map.put("MsgContent",mg);
        return map;
    }

    @Transactional
    public void verifyUserPic(UserPicVerifyVO vo) {
        if (!isAdmin(getCommonHeader().getUid())){
            throw new ResultException(USER_NOT_ADMIN_EX);
        }
        List<VerifyBatchVO> pics = vo.getPics();
        if (pics == null || pics.size() == 0) {
            throw new ResultException(USER_VERIFY_CONTENT_EMPTY_EX);
        }

        List<Long> passIds = pics.stream().filter(s -> s.getStatus() == VERIFY_PASS).map(VerifyBatchVO::getId).collect(Collectors.toList());
        List<Long> notPassIds = pics.stream().filter(s -> s.getStatus() == VERIFY_NOT_PASS).map(VerifyBatchVO::getId).collect(Collectors.toList());

        //相册
        //批量更新图片状态
        userPicRepository.updateStatusByIdsIn(passIds, VERIFY_PASS_STATUS);
        userPicRepository.updateStatusByIdsIn(notPassIds, VERIFY_NOT_PASS_STATUS);

        if (notPassIds.size()>0){
            //查询未审核通过上传图片的用户
            List<Long> notPassUids = userPicRepository.findUidByIdInAndIsDel(notPassIds, NOT_DEL);
            List<String> toAccounts = getImIdsByUid(notPassUids);
            sendImUserMsg(toAccounts,"Your album contains illegal information and has been removed");

        }
    }

    /**
     * 根据指定UID查询imId
     * @param uids
     * @return
     */
    public List<String> getImIdsByUid(List<Long> uids){
        List<UserAccount> userAccounts = userAccountRepository.findUidAndUsernameByUidAndIsDel(uids,NOT_DEL);
        List<String> toAccounts = userAccounts.stream().map(s->"im_"+s.getUid()+"_"+s.getUsername()).collect(Collectors.toList());
        return toAccounts;
    }


    public boolean isAdmin(Long uid){
        List<Long> roleIds = getUserRoleIdList(uid);
        return  roleIds.contains(adminRoleId);
    }

    public PageView<UserAvatarItemVO> getVerifyAvatarList(String appType, Integer gender, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1,pageSize);
        List<Long> uidList = userAccountRepository.findByAppTypeAndIsDel(appType,NOT_DEL);

        Page<UserBase> userBases = userBaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            predicates.add(criteriaBuilder.and(root.get("uid").in(uidList)));
            //审核中的头像
            predicates.add(criteriaBuilder.equal(root.get("avatarStatus"), VERIFY_PASS_IN));
            predicates.add(criteriaBuilder.equal(root.get("isDel"),0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        },pageable);

        long total = userBases.getTotalElements();
        List<UserAvatarItemVO> itemVOS = userBases.getContent().stream().map(s->new UserAvatarItemVO(s.getUid(),s.getAvatarTemp())).collect(Collectors.toList());
        return new PageView<>(total, itemVOS);
    }

    public PageView<UserAboutItemVO> getVerifyAboutList(Integer page, Integer pageSize, Integer type) {
        Pageable pageable = PageRequest.of(page - 1,pageSize);
        Page<UserExtra> userBases = userExtraRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //审核中的
            if (type == 1) {
                predicates.add(criteriaBuilder.equal(root.get("aboutMeStatus"), VERIFY_PASS_IN));
            }else {
                predicates.add(criteriaBuilder.equal(root.get("aboutMatchStatus"), VERIFY_PASS_IN));
            }
            predicates.add(criteriaBuilder.equal(root.get("isDel"),0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        },pageable);

        long total = userBases.getTotalElements();
        List<UserAboutItemVO> itemVOS = userBases.getContent().stream().map(s-> {
            String t = type == 1?s.getAboutMeTemp():s.getAboutMatchTemp();
            return new UserAboutItemVO(s.getUid(), t);
        }).collect(Collectors.toList());
        return new PageView<>(total, itemVOS);
    }

    public PageView<UserPicItemVO> getVerifyPicList(String appType, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1,pageSize);
        List<Long> uidList = userAccountRepository.findByAppTypeAndIsDel(appType,NOT_DEL);
        Page<UserPic> userBases = userPicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.and(root.get("uid").in(uidList)));
            //审核中的头像
            predicates.add(criteriaBuilder.equal(root.get("verifyStatus"), VERIFY_PASS_IN));
            predicates.add(criteriaBuilder.equal(root.get("isDel"),0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        },pageable);

        long total = userBases.getTotalElements();
        List<UserPicItemVO> itemVOS = userBases.getContent().stream().map(s->new UserPicItemVO(s.getId(),s.getPicUrl())).collect(Collectors.toList());
        return new PageView<>(total, itemVOS);
    }


}
