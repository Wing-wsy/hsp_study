package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.MinIOConfig;
import org.itzixi.MinIOUtils;
import org.itzixi.api.feign.UserInfoMicroServiceFeign;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.exceptions.GraceException;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.vo.UsersVO;
import org.itzixi.pojo.vo.VideoMsgVO;
import org.itzixi.utils.JcodecVideoUtil;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.QrCodeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther
 */
@RestController
@RequestMapping("file")
public class FileController{

//    /**
//     * 基于本地保存实现的方式（看看就好，生产不会保存到本地）
//     * @param file
//     * @param userId
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("uploadFace1")
//    public GraceJSONResult uploadFace1(@RequestParam("file") MultipartFile file,
//                                 String userId,
//                                 HttpServletRequest request) throws Exception {
//
//        // abc.123.456.png
//        String filename = file.getOriginalFilename();   // 获得文件原始名称
//        String suffixName = filename.substring(filename.lastIndexOf("."));  // 从最后一个.开始截取
//        String newFileName = userId + suffixName;   // 文件的新名称
//
//        // 设置文件存储路径，可以存放到任意的指定路径
//        String rootPath = "/Users/wing/architect/github/project/hsp_study/temp" + File.separator; // 上传文件的存放位置
//
//        String filePath = rootPath + File.separator + "face" + File.separator + newFileName;
//        File newFile = new File(filePath);
//        // 判断目标文件所在目录是否存在
//        if (!newFile.getParentFile().exists()) {
//            // 如果目标文件所在目录不存在，则创建父级目录
//            newFile.getParentFile().mkdirs();
//        }
//
//        // 将内存中的数据写入磁盘
//        file.transferTo(newFile);
//
//        return GraceJSONResult.ok();
//    }

    @Resource
    private MinIOConfig minIOConfig;
    @Resource
    private UserInfoMicroServiceFeign userInfoMicroServiceFeign;

    @PostMapping("uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file,
                                       String userId,
                                       HttpServletRequest request) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.FACE.path + File.separator + userId + File.separator + filename;
        MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                                filename,
                                file.getInputStream());

        String faceUrl = minIOConfig.getFileHost()
                + "/"
                + minIOConfig.getBucketName()
                + "/"
                + filename;

        /**
         * 微服务远程调用更新用户头像到数据库 OpenFeign
         * 如果前端没有保存按钮则可以这么做，如果有保存提交按钮，则在前端可以触发
         * 此处则不需要进行微服务调用，让前端触发保存提交到后台进行保存
         */
        GraceJSONResult jsonResult = userInfoMicroServiceFeign.updateFace(userId, faceUrl);
        Object data = jsonResult.getData();

        String json = JsonUtils.objectToJson(data);
        UsersVO usersVO = JsonUtils.jsonToPojo(json, UsersVO.class);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("generatorQrCode")
    public String generatorQrCode(String wechatNumber,
                                  String userId) throws Exception {

        // 构建map对象
        Map<String, String> map = new HashMap<>();
        map.put("wechatNumber", wechatNumber);
        map.put("userId", userId);

        // 把对象转换为json字符串，用于存储到二维码中
        String data = JsonUtils.objectToJson(map);

        // 生成二维码【多线程会存在问题，生产不能这样先保存本地，然后进行上传的方式】
        String qrCodePath = QrCodeUtils.generateQRCode(data);

        // 把二维码上传到minio中
        if (StringUtils.isNotBlank(qrCodePath)) {
            String uuid = UUID.randomUUID().toString();
            String objectName = MinIOUtils.FilePathEnum.WECHAT_NUMBER.path + File.separator + userId + File.separator + uuid + ".png";
//            String objectName = "wechatNumber" + File.separator + userId + File.separator + uuid + ".png";
            String imageQrCodeUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(), objectName, qrCodePath, true);
            return imageQrCodeUrl;
        }

        return null;
    }

    @PostMapping("uploadFriendCircleBg")
    public GraceJSONResult uploadFriendCircleBg(@RequestParam("file") MultipartFile file,
                                      String userId) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.FRIEND_CIRCLE_BG.path
                + File.separator + userId
                + File.separator + dealWithoutFilename(filename);

        String imageUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                filename,
                file.getInputStream(),
                true);

        GraceJSONResult jsonResult = userInfoMicroServiceFeign
                                            .updateFriendCircleBg(userId, imageUrl);
        Object data = jsonResult.getData();

        String json = JsonUtils.objectToJson(data);
        UsersVO usersVO = JsonUtils.jsonToPojo(json, UsersVO.class);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("uploadChatBg")
    public GraceJSONResult uploadChatBg(@RequestParam("file") MultipartFile file,
                                        String userId) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.CHAT_BG.path
                + File.separator + userId
                + File.separator + dealWithoutFilename(filename);

        String imageUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                filename,
                file.getInputStream(),
                true);

        GraceJSONResult jsonResult = userInfoMicroServiceFeign
                    .updateChatBg(userId, imageUrl);
        Object data = jsonResult.getData();

        String json = JsonUtils.objectToJson(data);
        UsersVO usersVO = JsonUtils.jsonToPojo(json, UsersVO.class);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("uploadFriendCircleImage")
    public GraceJSONResult uploadFriendCircleImage(@RequestParam("file") MultipartFile file,
                                        String userId) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.FRIEND_CIRCLE_BG.path
                + File.separator + userId
                + File.separator + dealWithoutFilename(filename);

        String imageUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                filename,
                file.getInputStream(),
                true);

        return GraceJSONResult.ok(imageUrl);
    }

    @PostMapping("uploadChatPhoto")
    public GraceJSONResult uploadChatPhoto(@RequestParam("file") MultipartFile file,
                                           String userId) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.CHAT.path
                + File.separator + userId
                + File.separator + MinIOUtils.FilePathEnum.PHOTO.path
                + File.separator + dealWithoutFilename(filename);

        String imageUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                filename,
                file.getInputStream(),
                true);

        return GraceJSONResult.ok(imageUrl);
    }

    @PostMapping("uploadChatVideo")
    public GraceJSONResult uploadChatVideo(@RequestParam("file") MultipartFile file,
                                           String userId) throws Exception {

        String videoUrl = uploadForChatFiles(file, userId, MinIOUtils.FilePathEnum.VIDEO.path);

        // 帧，封面获取 = 视频截帧 截取第一帧
        String coverName = UUID.randomUUID().toString() + MinIOUtils.FilePathEnum.JPG.path;   // 视频封面的名称
        // 临时保存
        String coverPath = JcodecVideoUtil.videoFramesPath
                + File.separator + "videos"
                + File.separator + coverName;

        File coverFile = new File(coverPath);
        if (!coverFile.getParentFile().exists()) {
            coverFile.getParentFile().mkdirs();
        }

        JcodecVideoUtil.fetchFrame(file, coverFile);

        // 上传封面到minio
        String coverUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                coverName,
                new FileInputStream(coverFile),
                true);

        VideoMsgVO videoMsgVO = new VideoMsgVO();
        videoMsgVO.setVideoPath(videoUrl);
        videoMsgVO.setCover(coverUrl);

        return GraceJSONResult.ok(videoMsgVO);
    }

    @PostMapping("uploadChatVoice")
    public GraceJSONResult uploadChatVoice(@RequestParam("file") MultipartFile file,
                                           String userId) throws Exception {
        String voiceUrl = uploadForChatFiles(file, userId, MinIOUtils.FilePathEnum.VOICE.path);
        return GraceJSONResult.ok(voiceUrl);
    }

    private String uploadForChatFiles(MultipartFile file,
                                      String userId,
                                      String fileType) throws Exception {

        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String filename = file.getOriginalFilename();   // 获得文件原始名称
        if (StringUtils.isBlank(filename)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = MinIOUtils.FilePathEnum.CHAT.path
                + File.separator + userId
                + File.separator + fileType
                + File.separator + dealWithoutFilename(filename);

        String fileUrl = MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                filename,
                file.getInputStream(),
                true);

        return fileUrl;
    }


    private String dealWithFilename(String filename) {
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String fName = filename.substring(0, filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return fName + "-" + uuid + suffixName;
    }

    private String dealWithoutFilename(String filename) {
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return uuid + suffixName;
    }

}
