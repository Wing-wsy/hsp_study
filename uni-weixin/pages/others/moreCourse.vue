<!-- 更多风间影月老师的课程 -->
<template>
	<view class="page">
		
		<scroll-view scroll-y="true" style="margin-bottom: 20px;">
			<view style="display: flex;flex-direction: row;justify-content: space-between;padding: 20rpx;">
				<view style="display: flex;flex-direction: column;justify-content: center;">
					<image src="/static/itzixi/lee.jpeg" mode="widthFix" class="fengjianyingyue" style="align-self: center;"></image>
					<!-- <image src="/static/itzixi/123.png" mode="widthFix" class="fengjianyingyue-name" style="align-self: center;"></image> -->
				</view>

				<view style="margin-left: 10px; text-indent: 26px; line-height: 18px;">
					<text class="introduce">	物流科技公司部门经理，近10年开发和技术管理经验。主要从事后端技术和架构领域，有丰富的电商平台与物流平台核心系统的架构设计和开发经验。 拥有丰富的大型项目开发经验，授课风格诙谐幽默，讲解到位细致。</text>
					<text class="introduce">	近20万的学生学习人数，学生入职公司有： 百度云盘，阿里，腾讯云，字节跳动，金山，网易，OPPO，新浪微博，华为，货拉拉，58同城，海信，蘑菇街，金蝶 等。</text>
				</view>
			</view>

			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;">
				<image src="/static/itzixi/javasenior.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/javaarchitect.png" mode="widthFix" class="course" style=""></image>
			</view>

			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;">
				<image src="/static/itzixi/pmp.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/springcloud.png" mode="widthFix" class="course" style=""></image>
			</view>
			
			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;">
				<image src="/static/itzixi/netty.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/zookeeper.png" mode="widthFix" class="course" style=""></image>
			</view>
			
			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;">
				<image src="/static/itzixi/tiktok.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/mpvideo.png" mode="widthFix" class="course" style=""></image>
			</view>
			
			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;">
				<image src="/static/itzixi/springboot2-1.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/springboot2-2.png" mode="widthFix" class="course" style=""></image>
			</view>
			
			<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 20rpx;padding-left: 30rpx;padding-right: 30rpx;margin-bottom: 50rpx;">
				<image src="/static/itzixi/duojicache.png" mode="widthFix" class="course" style=""></image>
				<image src="/static/itzixi/springboot1.jpeg" mode="widthFix" class="course" style=""></image>
			</view>

		</scroll-view>
		
	</view>
</template>

<script>
	var app = getApp();
	export default {
		components: {
		},
		data() {
			return {
				logoutTouched: false
			}
		},
		onLoad() {
		},
		methods: {
			touchstartLogout() {
				this.logoutTouched = true;
			},
			touchendLogout() {
				this.logoutTouched = false;
			},
			logout() {
				var me = this;
				var userId = getApp().getUserInfoSession().id;
				
				var serverUrl = app.globalData.serverUrl;
				uni.request({
					method: "POST",
					url: serverUrl + "/passport/logout?userId=" + userId,
					success(result) {
						var status = result.data.status;
						if (status == 200) {
							getApp().clearUserInfo();
							// 在登录登出后，都需要重新刷新首页list，目的为了刷新关注点赞状态
							// uni.setStorageSync("needRefreshIndexVlogList", "1");
							// uni.switchTab({
							// 	url: "../index/index"
							// })
							uni.reLaunch({
								url: "../index/index"
							})
						} else if (status != 200) {
							uni.showToast({
								title: result.data.msg,
								icon: "none"
							});
						}
						
					}
				});
				
				
			}
		}
	}
</script>

<style>
	@import url("moreCourse.css");
</style>