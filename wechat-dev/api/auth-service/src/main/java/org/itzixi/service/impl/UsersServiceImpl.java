package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
//import org.itzixi.api.feign.FileMicroServiceFeign;
import org.itzixi.api.feign.FileMicroServiceFeign;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.Sex;
import org.itzixi.mapper.UsersMapper;
import org.itzixi.pojo.Users;
import org.itzixi.service.UsersService;
import org.itzixi.utils.DesensitizationUtil;
import org.itzixi.utils.LocalDateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 风间影月
 * @since 2024-03-27
 */
@Service
// 不推荐继承 mybatisplus 的 ServiceImpl，因为继承之后就不能继承自定义的类，受限单继承。
//public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users> implements UsersService {
public class UsersServiceImpl extends BaseInfoProperties implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    private static final String USER_FACE1 = "http://127.0.0.1:9000/itzixi/face/1749619640390205441/e9f2be46-56ba-454c-a7ee-3e290bad6a59.jpg";

    @Override
    public Users queryMobileIfExist(String mobile) {
        return usersMapper.selectOne(
                new QueryWrapper<Users>()
                        .eq("mobile", mobile)
        );
    }

    @Transactional
    @Override
    public Users createUsers(String mobile, String nickname) {

        Users user = new Users();

        user.setMobile(mobile);

        String uuid = UUID.randomUUID().toString();
        String uuidStr[] = uuid.split("-");
        String wechatNum = "wx" + uuidStr[0] + uuidStr[1];
        user.setWechatNum(wechatNum);

        String wechatNumUrl = getQrCodeUrl(wechatNum, TEMP_STRING);
        user.setWechatNumImg(wechatNumUrl);

        // 用户138****1234
        // DesensitizationUtil
        if (StringUtils.isBlank(nickname)) {
            user.setNickname("用户" + DesensitizationUtil.commonDisplay(mobile));
        }
        user.setRealName("");

        user.setSex(Sex.secret.type);
        user.setFace(USER_FACE1);
        user.setFriendCircleBg(USER_FACE1);
        user.setEmail("");

        user.setBirthday(LocalDateUtils
                .parseLocalDate("1980-01-01",
                                LocalDateUtils.DATE_PATTERN));

        user.setCountry("中国");
        user.setProvince("");
        user.setCity("");
        user.setDistrict("");

        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        usersMapper.insert(user);

        return user;
    }

    @Resource
    private FileMicroServiceFeign fileMicroServiceFeign;

    private String getQrCodeUrl(String wechatNumber, String userId) {
        try {
            return fileMicroServiceFeign.generatorQrCode(wechatNumber, userId);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            return null;
        }
    }
}
