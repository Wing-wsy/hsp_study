package com.powernode.mybatis.pojo;

import java.util.Date;

public class TLoanOrder {
    /**借款订单表主键ID*/
    private Long loanId;

    /**关联客户ID*/
    private String appUserId;

    /**手机号*/
    private String mobile;

    /**机审结果 0拒绝 1通过*/
    private Integer macAuditResult;

    /**订单状态 0待机审 1待初审 2待复审 3终审 4待打款 5打款成功[待还款] 6已拒单[机审] 7已关闭[异常关闭] 8已拒单[人工]*/
    private Integer loanOrderStatus;

    /**还款状态 1已还清 0未还清 2还款中 3未放款*/
    private Integer repayOrderStatus;

    /**应还金额*/
    private Double sureRepayAmounts;

    /**申请日期*/
    private Date requestDate;

    /**关联项目编码*/
    private String itemCode;

    /**产品项目编码*/
    private String productItemCode;

    /**客户姓名*/
    private String appUserName;

    /**客户来源*/
    private String appUserCome;

    /**申请渠道*/
    private String requestChanel;

    /**性别*/
    private Integer sex;

    /**客户等级*/
    private Integer appUserLevel;

    /**接口周期*/
    private Integer loanCycle;

    /**借款金额*/
    private Double loanAmount;

    /**借款类型*/
    private String loanType;

    /**应用包名*/
    private String appPackageName;

    /**应用版本*/
    private String appVersion;

    /**应用名称*/
    private String appName;

    /**是否显示 1是 0否*/
    private Integer isShow;

    /**真实还款金额*/
    private Double realRepayAmounts;

    /**产品名称*/
    private String productName;

    /**服务费*/
    private Double serviceFee;

    /**管理费*/
    private Double manageFee;

    /**逾期罚息*/
    private Double lateFee;

    /**利息*/
    private Double rentFee;

    /**剩余还款天数*/
    private Integer remainingDays;

    /**关联产品ID*/
    private Long productId;

    /**身份证号*/
    private String idCardNo;

    /**交易卡号*/
    private String accountCardno;

    /**分段还款总金额*/
    private Double postponeAmount;

    /**分段还款次数*/
    private Integer postponeTimes;

    /**展期总金额*/
    private Double flatAccountAmount;

    /**展期次数*/
    private Integer flatAccountTimes;

    /**平账状态 2申请中 1平账 0取消*/
    private Integer postponeStatus;

    /**展期状态 2申请中 1展期 0取消*/
    private Integer flatAccountStatus;

    /**分段还款*/
    private Integer postponeDaysCount;

    /**是否新客户 1是 0否 锁定本平台*/
    private Integer isNew;

    /**是否锁定 1是0否*/
    private Integer isLocking;

    /**绑定的在线支付渠道 wyp王杨pay insta instaMoney*/
    private String bindPayChannel;

    /**客户类型 1全新客 2半新客户 3老客 4未检测 锁定全平台*/
    private Integer allplatfromUserType;

    /**机审警告提醒*/
    private String machineAuditWarning;

    /**签名状态 0不需要，1已签名，2未签名*/
    private Integer signatureStatus;

    /**开始审核日期*/
    private Date begeinAuditDate;

    /**放款人ID*/
    private String pushCashUserId;

    /**放款人昵称*/
    private String pushCashUserNick;

    /**放款日期*/
    private Date pushCashDate;

    /**应还款日期*/
    private Date repayDate;

    /**拒绝阶段 0机审拒绝4关闭订单*/
    private String rejectStage;

    /**拒绝原因*/
    private String rejectReason;

    /**销账日期*/
    private Date sellAccountsDate;

    /**初审人ID*/
    private String firstAuditUserId;

    /**初审人昵称*/
    private String firstAuditUserNick;

    /**初审日期*/
    private Date firstAuditDate;

    /**复审人ID*/
    private String seccondAuditUserId;

    /**复审人昵称*/
    private String seccondAuditUserNick;

    /**复审日期*/
    private Date seccondAuditDate;

    /**终审人ID*/
    private String lastOperateUserId;

    /**终审人昵称*/
    private String lastOperateUserNick;

    /**终结操作日期*/
    private Date lastOperateDate;

    /**还款渠道*/
    private String repayChannel;

    /**拒单人ID*/
    private String rejectUserId;

    /**拒单人昵称*/
    private String rejectUserNick;

    /**拒单日期*/
    private Date rejectDate;

    /**销账人ID*/
    private String sellAccountsUserId;

    /**销账人昵称*/
    private String sellAccountsUserNick;

    /**最终操作日期*/
    private Date endOperateDate;

    /**纬度*/
    private String latitude;

    /**经度*/
    private String longitude;

    /**放款标签 2老客户自动放款 3随机放款 4初审通过放款 5izi达标分放款 6新客通过机审即放款 7异常打款自动放款 8新客开启自动审核放款*/
    private String pushTag;

    /**设备ID*/
    private String deciveId;

    /**设备品牌*/
    private String deciveBrand;

    /**请求网络IP*/
    private String requestNetIp;

    /**逾期天数*/
    private Integer overdueDays;

    /**成功展期天数*/
    private Integer flatAccountDay;

    /**打款账号编码*/
    private String payAccountCode;

    /**到手金额*/
    private Double actualToAccountMoney;

    /**银行名称*/
    private String userBank;

    /**银行编码*/
    private String bankCode;

    /**银行卡片类型*/
    private String bankCardNumberType;

    /**产品应用类型 1贷超内部 2api甲方*/
    private Integer productItemType;

    /**用户风控类型 0未检测 1白名单客户 2投放客户 3自来客 4导流客户*/
    private Integer userRiskType;

    /**总期数*/
    private Integer periods;

    /**已还款期数*/
    private Integer repayPeriods;

    /**商户号*/
    private String merchantCode;

    /**正常应还总金额*/
    private Double normalSureRepayAmount;

    /**来源渠道*/
    private String sourceChannel;

    /**来源渠道详情*/
    private String sourceChannelDetails;

    /**优化师*/
    private String optimizationDivision;

    /**投放方式:自投,代投,代投公司*/
    private String trackChannel;

    /**投放渠道*/
    private String network;

    /**原始的产品ID*/
    private Long originalProductId;

    /**中转账户*/
    private String transferAccount;

    /**是否测试账号标记 1是 0否*/
    private Integer mobileTag;

    /**客户关联银行表主键ID*/
    private Long userBankId;

    /**扩展信息JSON*/
    private String dataJson;

    /**获取借款订单表主键ID*/
    public Long getLoanId() {
        return loanId;
    }

    /**设置借款订单表主键ID*/
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    /**获取关联客户ID*/
    public String getAppUserId() {
        return appUserId;
    }

    /**设置关联客户ID*/
    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    /**获取手机号*/
    public String getMobile() {
        return mobile;
    }

    /**设置手机号*/
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**获取机审结果 0拒绝 1通过*/
    public Integer getMacAuditResult() {
        return macAuditResult;
    }

    /**设置机审结果 0拒绝 1通过*/
    public void setMacAuditResult(Integer macAuditResult) {
        this.macAuditResult = macAuditResult;
    }

    /**获取订单状态 0待机审 1待初审 2待复审 3终审 4待打款 5打款成功[待还款] 6已拒单[机审] 7已关闭[异常关闭] 8已拒单[人工]*/
    public Integer getLoanOrderStatus() {
        return loanOrderStatus;
    }

    /**设置订单状态 0待机审 1待初审 2待复审 3终审 4待打款 5打款成功[待还款] 6已拒单[机审] 7已关闭[异常关闭] 8已拒单[人工]*/
    public void setLoanOrderStatus(Integer loanOrderStatus) {
        this.loanOrderStatus = loanOrderStatus;
    }

    /**获取还款状态 1已还清 0未还清 2还款中 3未放款*/
    public Integer getRepayOrderStatus() {
        return repayOrderStatus;
    }

    /**设置还款状态 1已还清 0未还清 2还款中 3未放款*/
    public void setRepayOrderStatus(Integer repayOrderStatus) {
        this.repayOrderStatus = repayOrderStatus;
    }

    /**获取应还金额*/
    public Double getSureRepayAmounts() {
        return sureRepayAmounts;
    }

    /**设置应还金额*/
    public void setSureRepayAmounts(Double sureRepayAmounts) {
        this.sureRepayAmounts = sureRepayAmounts;
    }

    /**获取申请日期*/
    public Date getRequestDate() {
        return requestDate;
    }

    /**设置申请日期*/
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**获取关联项目编码*/
    public String getItemCode() {
        return itemCode;
    }

    /**设置关联项目编码*/
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**获取产品项目编码*/
    public String getProductItemCode() {
        return productItemCode;
    }

    /**设置产品项目编码*/
    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }

    /**获取客户姓名*/
    public String getAppUserName() {
        return appUserName;
    }

    /**设置客户姓名*/
    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    /**获取客户来源*/
    public String getAppUserCome() {
        return appUserCome;
    }

    /**设置客户来源*/
    public void setAppUserCome(String appUserCome) {
        this.appUserCome = appUserCome;
    }

    /**获取申请渠道*/
    public String getRequestChanel() {
        return requestChanel;
    }

    /**设置申请渠道*/
    public void setRequestChanel(String requestChanel) {
        this.requestChanel = requestChanel;
    }

    /**获取性别*/
    public Integer getSex() {
        return sex;
    }

    /**设置性别*/
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**获取客户等级*/
    public Integer getAppUserLevel() {
        return appUserLevel;
    }

    /**设置客户等级*/
    public void setAppUserLevel(Integer appUserLevel) {
        this.appUserLevel = appUserLevel;
    }

    /**获取接口周期*/
    public Integer getLoanCycle() {
        return loanCycle;
    }

    /**设置接口周期*/
    public void setLoanCycle(Integer loanCycle) {
        this.loanCycle = loanCycle;
    }

    /**获取借款金额*/
    public Double getLoanAmount() {
        return loanAmount;
    }

    /**设置借款金额*/
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**获取借款类型*/
    public String getLoanType() {
        return loanType;
    }

    /**设置借款类型*/
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    /**获取应用包名*/
    public String getAppPackageName() {
        return appPackageName;
    }

    /**设置应用包名*/
    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    /**获取应用版本*/
    public String getAppVersion() {
        return appVersion;
    }

    /**设置应用版本*/
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**获取应用名称*/
    public String getAppName() {
        return appName;
    }

    /**设置应用名称*/
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**获取是否显示 1是 0否*/
    public Integer getIsShow() {
        return isShow;
    }

    /**设置是否显示 1是 0否*/
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    /**获取真实还款金额*/
    public Double getRealRepayAmounts() {
        return realRepayAmounts;
    }

    /**设置真实还款金额*/
    public void setRealRepayAmounts(Double realRepayAmounts) {
        this.realRepayAmounts = realRepayAmounts;
    }

    /**获取产品名称*/
    public String getProductName() {
        return productName;
    }

    /**设置产品名称*/
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**获取服务费*/
    public Double getServiceFee() {
        return serviceFee;
    }

    /**设置服务费*/
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**获取管理费*/
    public Double getManageFee() {
        return manageFee;
    }

    /**设置管理费*/
    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }

    /**获取逾期罚息*/
    public Double getLateFee() {
        return lateFee;
    }

    /**设置逾期罚息*/
    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    /**获取利息*/
    public Double getRentFee() {
        return rentFee;
    }

    /**设置利息*/
    public void setRentFee(Double rentFee) {
        this.rentFee = rentFee;
    }

    /**获取剩余还款天数*/
    public Integer getRemainingDays() {
        return remainingDays;
    }

    /**设置剩余还款天数*/
    public void setRemainingDays(Integer remainingDays) {
        this.remainingDays = remainingDays;
    }

    /**获取关联产品ID*/
    public Long getProductId() {
        return productId;
    }

    /**设置关联产品ID*/
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**获取身份证号*/
    public String getIdCardNo() {
        return idCardNo;
    }

    /**设置身份证号*/
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    /**获取交易卡号*/
    public String getAccountCardno() {
        return accountCardno;
    }

    /**设置交易卡号*/
    public void setAccountCardno(String accountCardno) {
        this.accountCardno = accountCardno;
    }

    /**获取分段还款总金额*/
    public Double getPostponeAmount() {
        return postponeAmount;
    }

    /**设置分段还款总金额*/
    public void setPostponeAmount(Double postponeAmount) {
        this.postponeAmount = postponeAmount;
    }

    /**获取分段还款次数*/
    public Integer getPostponeTimes() {
        return postponeTimes;
    }

    /**设置分段还款次数*/
    public void setPostponeTimes(Integer postponeTimes) {
        this.postponeTimes = postponeTimes;
    }

    /**获取展期总金额*/
    public Double getFlatAccountAmount() {
        return flatAccountAmount;
    }

    /**设置展期总金额*/
    public void setFlatAccountAmount(Double flatAccountAmount) {
        this.flatAccountAmount = flatAccountAmount;
    }

    /**获取展期次数*/
    public Integer getFlatAccountTimes() {
        return flatAccountTimes;
    }

    /**设置展期次数*/
    public void setFlatAccountTimes(Integer flatAccountTimes) {
        this.flatAccountTimes = flatAccountTimes;
    }

    /**获取平账状态 2申请中 1平账 0取消*/
    public Integer getPostponeStatus() {
        return postponeStatus;
    }

    /**设置平账状态 2申请中 1平账 0取消*/
    public void setPostponeStatus(Integer postponeStatus) {
        this.postponeStatus = postponeStatus;
    }

    /**获取展期状态 2申请中 1展期 0取消*/
    public Integer getFlatAccountStatus() {
        return flatAccountStatus;
    }

    /**设置展期状态 2申请中 1展期 0取消*/
    public void setFlatAccountStatus(Integer flatAccountStatus) {
        this.flatAccountStatus = flatAccountStatus;
    }

    /**获取分段还款*/
    public Integer getPostponeDaysCount() {
        return postponeDaysCount;
    }

    /**设置分段还款*/
    public void setPostponeDaysCount(Integer postponeDaysCount) {
        this.postponeDaysCount = postponeDaysCount;
    }

    /**获取是否新客户 1是 0否 锁定本平台*/
    public Integer getIsNew() {
        return isNew;
    }

    /**设置是否新客户 1是 0否 锁定本平台*/
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    /**获取是否锁定 1是0否*/
    public Integer getIsLocking() {
        return isLocking;
    }

    /**设置是否锁定 1是0否*/
    public void setIsLocking(Integer isLocking) {
        this.isLocking = isLocking;
    }

    /**获取绑定的在线支付渠道 wyp王杨pay insta instaMoney*/
    public String getBindPayChannel() {
        return bindPayChannel;
    }

    /**设置绑定的在线支付渠道 wyp王杨pay insta instaMoney*/
    public void setBindPayChannel(String bindPayChannel) {
        this.bindPayChannel = bindPayChannel;
    }

    /**获取客户类型 1全新客 2半新客户 3老客 4未检测 锁定全平台*/
    public Integer getAllplatfromUserType() {
        return allplatfromUserType;
    }

    /**设置客户类型 1全新客 2半新客户 3老客 4未检测 锁定全平台*/
    public void setAllplatfromUserType(Integer allplatfromUserType) {
        this.allplatfromUserType = allplatfromUserType;
    }

    /**获取机审警告提醒*/
    public String getMachineAuditWarning() {
        return machineAuditWarning;
    }

    /**设置机审警告提醒*/
    public void setMachineAuditWarning(String machineAuditWarning) {
        this.machineAuditWarning = machineAuditWarning;
    }

    /**获取签名状态 0不需要，1已签名，2未签名*/
    public Integer getSignatureStatus() {
        return signatureStatus;
    }

    /**设置签名状态 0不需要，1已签名，2未签名*/
    public void setSignatureStatus(Integer signatureStatus) {
        this.signatureStatus = signatureStatus;
    }

    /**获取开始审核日期*/
    public Date getBegeinAuditDate() {
        return begeinAuditDate;
    }

    /**设置开始审核日期*/
    public void setBegeinAuditDate(Date begeinAuditDate) {
        this.begeinAuditDate = begeinAuditDate;
    }

    /**获取放款人ID*/
    public String getPushCashUserId() {
        return pushCashUserId;
    }

    /**设置放款人ID*/
    public void setPushCashUserId(String pushCashUserId) {
        this.pushCashUserId = pushCashUserId;
    }

    /**获取放款人昵称*/
    public String getPushCashUserNick() {
        return pushCashUserNick;
    }

    /**设置放款人昵称*/
    public void setPushCashUserNick(String pushCashUserNick) {
        this.pushCashUserNick = pushCashUserNick;
    }

    /**获取放款日期*/
    public Date getPushCashDate() {
        return pushCashDate;
    }

    /**设置放款日期*/
    public void setPushCashDate(Date pushCashDate) {
        this.pushCashDate = pushCashDate;
    }

    /**获取应还款日期*/
    public Date getRepayDate() {
        return repayDate;
    }

    /**设置应还款日期*/
    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    /**获取拒绝阶段 0机审拒绝4关闭订单*/
    public String getRejectStage() {
        return rejectStage;
    }

    /**设置拒绝阶段 0机审拒绝4关闭订单*/
    public void setRejectStage(String rejectStage) {
        this.rejectStage = rejectStage;
    }

    /**获取拒绝原因*/
    public String getRejectReason() {
        return rejectReason;
    }

    /**设置拒绝原因*/
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**获取销账日期*/
    public Date getSellAccountsDate() {
        return sellAccountsDate;
    }

    /**设置销账日期*/
    public void setSellAccountsDate(Date sellAccountsDate) {
        this.sellAccountsDate = sellAccountsDate;
    }

    /**获取初审人ID*/
    public String getFirstAuditUserId() {
        return firstAuditUserId;
    }

    /**设置初审人ID*/
    public void setFirstAuditUserId(String firstAuditUserId) {
        this.firstAuditUserId = firstAuditUserId;
    }

    /**获取初审人昵称*/
    public String getFirstAuditUserNick() {
        return firstAuditUserNick;
    }

    /**设置初审人昵称*/
    public void setFirstAuditUserNick(String firstAuditUserNick) {
        this.firstAuditUserNick = firstAuditUserNick;
    }

    /**获取初审日期*/
    public Date getFirstAuditDate() {
        return firstAuditDate;
    }

    /**设置初审日期*/
    public void setFirstAuditDate(Date firstAuditDate) {
        this.firstAuditDate = firstAuditDate;
    }

    /**获取复审人ID*/
    public String getSeccondAuditUserId() {
        return seccondAuditUserId;
    }

    /**设置复审人ID*/
    public void setSeccondAuditUserId(String seccondAuditUserId) {
        this.seccondAuditUserId = seccondAuditUserId;
    }

    /**获取复审人昵称*/
    public String getSeccondAuditUserNick() {
        return seccondAuditUserNick;
    }

    /**设置复审人昵称*/
    public void setSeccondAuditUserNick(String seccondAuditUserNick) {
        this.seccondAuditUserNick = seccondAuditUserNick;
    }

    /**获取复审日期*/
    public Date getSeccondAuditDate() {
        return seccondAuditDate;
    }

    /**设置复审日期*/
    public void setSeccondAuditDate(Date seccondAuditDate) {
        this.seccondAuditDate = seccondAuditDate;
    }

    /**获取终审人ID*/
    public String getLastOperateUserId() {
        return lastOperateUserId;
    }

    /**设置终审人ID*/
    public void setLastOperateUserId(String lastOperateUserId) {
        this.lastOperateUserId = lastOperateUserId;
    }

    /**获取终审人昵称*/
    public String getLastOperateUserNick() {
        return lastOperateUserNick;
    }

    /**设置终审人昵称*/
    public void setLastOperateUserNick(String lastOperateUserNick) {
        this.lastOperateUserNick = lastOperateUserNick;
    }

    /**获取终结操作日期*/
    public Date getLastOperateDate() {
        return lastOperateDate;
    }

    /**设置终结操作日期*/
    public void setLastOperateDate(Date lastOperateDate) {
        this.lastOperateDate = lastOperateDate;
    }

    /**获取还款渠道*/
    public String getRepayChannel() {
        return repayChannel;
    }

    /**设置还款渠道*/
    public void setRepayChannel(String repayChannel) {
        this.repayChannel = repayChannel;
    }

    /**获取拒单人ID*/
    public String getRejectUserId() {
        return rejectUserId;
    }

    /**设置拒单人ID*/
    public void setRejectUserId(String rejectUserId) {
        this.rejectUserId = rejectUserId;
    }

    /**获取拒单人昵称*/
    public String getRejectUserNick() {
        return rejectUserNick;
    }

    /**设置拒单人昵称*/
    public void setRejectUserNick(String rejectUserNick) {
        this.rejectUserNick = rejectUserNick;
    }

    /**获取拒单日期*/
    public Date getRejectDate() {
        return rejectDate;
    }

    /**设置拒单日期*/
    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    /**获取销账人ID*/
    public String getSellAccountsUserId() {
        return sellAccountsUserId;
    }

    /**设置销账人ID*/
    public void setSellAccountsUserId(String sellAccountsUserId) {
        this.sellAccountsUserId = sellAccountsUserId;
    }

    /**获取销账人昵称*/
    public String getSellAccountsUserNick() {
        return sellAccountsUserNick;
    }

    /**设置销账人昵称*/
    public void setSellAccountsUserNick(String sellAccountsUserNick) {
        this.sellAccountsUserNick = sellAccountsUserNick;
    }

    /**获取最终操作日期*/
    public Date getEndOperateDate() {
        return endOperateDate;
    }

    /**设置最终操作日期*/
    public void setEndOperateDate(Date endOperateDate) {
        this.endOperateDate = endOperateDate;
    }

    /**获取纬度*/
    public String getLatitude() {
        return latitude;
    }

    /**设置纬度*/
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**获取经度*/
    public String getLongitude() {
        return longitude;
    }

    /**设置经度*/
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**获取放款标签 2老客户自动放款 3随机放款 4初审通过放款 5izi达标分放款 6新客通过机审即放款 7异常打款自动放款 8新客开启自动审核放款*/
    public String getPushTag() {
        return pushTag;
    }

    /**设置放款标签 2老客户自动放款 3随机放款 4初审通过放款 5izi达标分放款 6新客通过机审即放款 7异常打款自动放款 8新客开启自动审核放款*/
    public void setPushTag(String pushTag) {
        this.pushTag = pushTag;
    }

    /**获取设备ID*/
    public String getDeciveId() {
        return deciveId;
    }

    /**设置设备ID*/
    public void setDeciveId(String deciveId) {
        this.deciveId = deciveId;
    }

    /**获取设备品牌*/
    public String getDeciveBrand() {
        return deciveBrand;
    }

    /**设置设备品牌*/
    public void setDeciveBrand(String deciveBrand) {
        this.deciveBrand = deciveBrand;
    }

    /**获取请求网络IP*/
    public String getRequestNetIp() {
        return requestNetIp;
    }

    /**设置请求网络IP*/
    public void setRequestNetIp(String requestNetIp) {
        this.requestNetIp = requestNetIp;
    }

    /**获取逾期天数*/
    public Integer getOverdueDays() {
        return overdueDays;
    }

    /**设置逾期天数*/
    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    /**获取成功展期天数*/
    public Integer getFlatAccountDay() {
        return flatAccountDay;
    }

    /**设置成功展期天数*/
    public void setFlatAccountDay(Integer flatAccountDay) {
        this.flatAccountDay = flatAccountDay;
    }

    /**获取打款账号编码*/
    public String getPayAccountCode() {
        return payAccountCode;
    }

    /**设置打款账号编码*/
    public void setPayAccountCode(String payAccountCode) {
        this.payAccountCode = payAccountCode;
    }

    /**获取到手金额*/
    public Double getActualToAccountMoney() {
        return actualToAccountMoney;
    }

    /**设置到手金额*/
    public void setActualToAccountMoney(Double actualToAccountMoney) {
        this.actualToAccountMoney = actualToAccountMoney;
    }

    /**获取银行名称*/
    public String getUserBank() {
        return userBank;
    }

    /**设置银行名称*/
    public void setUserBank(String userBank) {
        this.userBank = userBank;
    }

    /**获取银行编码*/
    public String getBankCode() {
        return bankCode;
    }

    /**设置银行编码*/
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**获取银行卡片类型*/
    public String getBankCardNumberType() {
        return bankCardNumberType;
    }

    /**设置银行卡片类型*/
    public void setBankCardNumberType(String bankCardNumberType) {
        this.bankCardNumberType = bankCardNumberType;
    }

    /**获取产品应用类型 1贷超内部 2api甲方*/
    public Integer getProductItemType() {
        return productItemType;
    }

    /**设置产品应用类型 1贷超内部 2api甲方*/
    public void setProductItemType(Integer productItemType) {
        this.productItemType = productItemType;
    }

    /**获取用户风控类型 0未检测 1白名单客户 2投放客户 3自来客 4导流客户*/
    public Integer getUserRiskType() {
        return userRiskType;
    }

    /**设置用户风控类型 0未检测 1白名单客户 2投放客户 3自来客 4导流客户*/
    public void setUserRiskType(Integer userRiskType) {
        this.userRiskType = userRiskType;
    }

    /**获取总期数*/
    public Integer getPeriods() {
        return periods;
    }

    /**设置总期数*/
    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    /**获取已还款期数*/
    public Integer getRepayPeriods() {
        return repayPeriods;
    }

    /**设置已还款期数*/
    public void setRepayPeriods(Integer repayPeriods) {
        this.repayPeriods = repayPeriods;
    }

    /**获取商户号*/
    public String getMerchantCode() {
        return merchantCode;
    }

    /**设置商户号*/
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**获取正常应还总金额*/
    public Double getNormalSureRepayAmount() {
        return normalSureRepayAmount;
    }

    /**设置正常应还总金额*/
    public void setNormalSureRepayAmount(Double normalSureRepayAmount) {
        this.normalSureRepayAmount = normalSureRepayAmount;
    }

    /**获取来源渠道*/
    public String getSourceChannel() {
        return sourceChannel;
    }

    /**设置来源渠道*/
    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    /**获取来源渠道详情*/
    public String getSourceChannelDetails() {
        return sourceChannelDetails;
    }

    /**设置来源渠道详情*/
    public void setSourceChannelDetails(String sourceChannelDetails) {
        this.sourceChannelDetails = sourceChannelDetails;
    }

    /**获取优化师*/
    public String getOptimizationDivision() {
        return optimizationDivision;
    }

    /**设置优化师*/
    public void setOptimizationDivision(String optimizationDivision) {
        this.optimizationDivision = optimizationDivision;
    }

    /**获取投放方式:自投,代投,代投公司*/
    public String getTrackChannel() {
        return trackChannel;
    }

    /**设置投放方式:自投,代投,代投公司*/
    public void setTrackChannel(String trackChannel) {
        this.trackChannel = trackChannel;
    }

    /**获取投放渠道*/
    public String getNetwork() {
        return network;
    }

    /**设置投放渠道*/
    public void setNetwork(String network) {
        this.network = network;
    }

    /**获取原始的产品ID*/
    public Long getOriginalProductId() {
        return originalProductId;
    }

    /**设置原始的产品ID*/
    public void setOriginalProductId(Long originalProductId) {
        this.originalProductId = originalProductId;
    }

    /**获取中转账户*/
    public String getTransferAccount() {
        return transferAccount;
    }

    /**设置中转账户*/
    public void setTransferAccount(String transferAccount) {
        this.transferAccount = transferAccount;
    }

    /**获取是否测试账号标记 1是 0否*/
    public Integer getMobileTag() {
        return mobileTag;
    }

    /**设置是否测试账号标记 1是 0否*/
    public void setMobileTag(Integer mobileTag) {
        this.mobileTag = mobileTag;
    }

    /**获取客户关联银行表主键ID*/
    public Long getUserBankId() {
        return userBankId;
    }

    /**设置客户关联银行表主键ID*/
    public void setUserBankId(Long userBankId) {
        this.userBankId = userBankId;
    }

    /**获取扩展信息JSON*/
    public String getDataJson() {
        return dataJson;
    }

    /**设置扩展信息JSON*/
    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}