/**
 * 推荐用户
 */
function recommend(userId) {
	UserService.recommendUser(userId, function (data) {
		if (data == 1) {
			$("#rec_" + userId).replaceWith("<font color='green'>\u5df2\u63a8\u8350</font>");
		}else if(data == -1){
			cwindow("对不起,已经达到最大推荐数.");
		}else{
			cwindow("该用户不存在.");
		}
	});
}
/**
 * 取消推荐
 */
function cancelRecommend(userId) {
	UserService.cancelRecommendUser(userId, function (data) {
		if (data) {
			$("#rec_" + userId).replaceWith("<font color='gray'>\u5df2\u53d6\u6d88</fon>");
		}
	});
}


/** 发信给会员 */
function sendMsg(userId){
	var msgWindow = art.dialog.open("/love/admin/admin_inputMsg.Q?readerId=" + userId, {
		title:'发消息',
		width: 450,
		height: 300
	});
}

/** 屏蔽用户 */
function maskUser(memberId){
	UserService.maskMember(memberId, function (data) {
		if (data) {
			cwindow("该用户已经被屏蔽.");
			window.location.reload();
		}
	});
}
/** 取消屏蔽用户 */
function unMask(memberId){
	UserService.unMask(memberId, function (data) {
		if (data) {
			cwindow("操作成功!");
			window.location.reload();
		}
	});
}


/** 审核通过 */
function pass(memberId){
	UserService.passMember(memberId, function (data) {
		if (data) {
			cwindow("该用户已经审核通过.");
			window.location.reload();
		}
	});
}


/**
 * 成功故事:通过审核
 */
function passAuditStory(storyId) {
	UserService.passAuditStory(storyId, function (data) {
		if (data) {
			cwindow("该成功故事已经审核通过.");
			window.location.reload();
		}
	});
}

/**
* 删除成功故事
*/
function deleteStory(storyId){
	UserService.deleteStory(storyId, function (data) {
		if (data) {
			cwindow("该故事已经删除.");
			window.location.reload();
		}
	});
}
/**
* 更新成功故事
*/
function updateStory(storyId){
	window.location.href="/love/admin/st_editStory.Q?id="+storyId;
}





function openClevelAuditPage(memberId){
	var url="/love/admin/admin_auditUserCLevel.Q?userId=" + memberId;
	openCenterWindow(url, 500, 300);
}

//用户详情
function viewUserDetail(memberId){
	var url="/love/admin/admin_memberDetail.Q?userId=" + memberId;
	window.open(url);
}


function auditCertify(memberId, isPass, type, auditorId){
	UserService.auditCertify(memberId, isPass, type, auditorId, function (data) {
		if (data) {
			cwindow("该用户已经审核通过.");
			window.location.reload();
		}
	});
}

/** 打开该用户的某个待审核文件 */
function openCertifyItem(userId, fileName){
	var url = "/love/private/UploadImages/U" +userId + "/Certify/" +fileName;
	openCenterWindow(url, 500, 300);
}



/** 新增约会 */
function addDating(){
	var msgWindow = art.dialog.open("/love/admin/admin_addDating.Q", {
		title:'新增约会',
		width: 450,
		height: 400
	});
}

/** 修改约会 */
function modifyDating(datingId){
	var msgWindow = art.dialog.open("/love/admin/admin_modifyDating.Q?op=enter&datingId="+datingId, {
		title:'修改约会信息',
		width: 450,
		height: 300
	});
}


/**
* 删除约会
*/
function deleteDating(datingId){
	showOssConfirmDialog("是否要删除该约会.", function(){
		UserService.deleteDating(datingId, function (data) {
			if (data) {
				cwindow("该约会已经删除.");
				window.location.reload();
			}
		});	
	});
}



/** 新增约会反馈 */
function addFeedBack(datingId){
	var msgWindow = art.dialog.open("/love/admin/admin_addFeedback.Q?datingId=" + datingId, {
		title:'添加反馈',
		width: 450,
		height: 300
	});
}


/**
* 删除角色
*/
function deleteRole(roleId){
	showOssConfirmDialog("是否要删除该角色.", function(){
		UserService.deleteRole(roleId, function (data) {
			if (data) {
				cwindow("该角色已经删除.");
				window.location.reload();
			}
		});	
	});
}



/** 禁用或启用管理员 */
function switchAdmin(adminId, newStatus){
	UserService.switchAdmin(adminId, newStatus, function (data) {
		if (data) {
			if(newStatus == 0){
				cwindow("该用户已经被禁用.");
			}else{
				cwindow("该用户已经启用.");
			}
			window.location.reload();
		}else{
			cwindow("操作失败，请查看控制台日志.");
		}
	});
}


function editAdmin(adminId){
	window.location.href = "/love/admin/sec_editAdmin.Q?adminId=" + adminId;
}


function editRole(roleId){
	window.location.href = "/love/admin/sec_editRole.Q?roleId=" + roleId;
}




function assignUser(userId){
	var assignWindow = art.dialog.open("/love/admin/admin_assignUser.Q?userId=" + userId, {
		title:'资源分配',
		width: 450,
		height: 150,
		closeFn: function(){window.location.reload();}
	});
}


/**
 * 取消微信约见请求
 */
function cancelWxRequest(id){
	if(window.confirm("确定要取消？")){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : '/love/wx/wx_cancelWxRequest.Q?id='+id,
			success : function(data) {
				if(data == "ok"){
					art.dialog("取消成功");
					$("#status"+id).html("<font color=gray>已取消</font>");
				}else{
					art.dialog("操作成功");
				}
			}
		});
	}
}
/**
 * 完成微信约见请求
 */
function finishWxRequest(id){
	if(window.confirm("确定要完成？")){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : '/love/wx/wx_finishWxRequest.Q?id='+id,
			success : function(data) {
				if(data == "ok"){
					art.dialog("操作成功");
					$("#status"+id).html("<font color=gray>已成功</font>");
				}else{
					art.dialog("操作成功");
				}
			}
		});
	}
}
/**
 * 处理中微信约见请求
 */
function processWxRequest(id){
	if(window.confirm("确定要标记为处理中？")){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : '/love/wx/wx_processWxRequest.Q?id='+id,
			success : function(data) {
				if(data == "ok"){
					art.dialog("操作成功");
					$("#status"+id).html("<font color=gray>处理中</font>");
				}else{
					art.dialog("操作成功");
				}
			}
		});
	}
}


function delUser(userId){
		if(window.confirm("确定要删除该用户？")){
			$.ajax({
				dataType : 'text',
				type : 'get',
				url : '/love/admin/admin_deleteUsers.Q?userId='+userId,
				success : function(data) {
					if(data == "ok"){
						window.location.href = "/love/admin/admin_simpleMemberList.Q?mlevel=0&fromUrl=y";
						art.dialog("操作成功");
					}
				}
			});
		}
}

/**
 * 标记用户的status状态
 */
function markUser(userId, status){
	if(window.confirm("确定要改变该用户的状态？")){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : '/love/admin/admin_markUsers.Q?userId='+userId+'&status='+status,
			success : function(data) {
				if(data == "ok"){
					art.dialog("操作成功");
				}
			}
		});
	}
}


/**
 * 生成绑定码发给用户
 */
function genBindCode(userId){
	if(window.confirm("确定要重新生成绑定码吗？")){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : '/love/admin/admin_genBindCode.Q?userId='+userId,
			success : function(data) {
				if(data == "ok"){
					art.dialog("绑定码发送成功，已经发送通知邮件。");
				}
			}
		});
	}
}
