<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>===<%=Version.getInstance().getCompany() %>===</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="<%=request.getContextPath()%>/images/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.about{color: #C71585;font-size: 12pt}
		</style>
	</head>
	<body>
		<center>
			<jsp:include page="/WEB-INF/jsps/common/top.jsp" />
			<div class="tj_div_protocol">
			<table width="90%">
				<tbody>
					<tr>
						<td align="left"><h2><center>【相亲很简单】花1分钟看邮件</center></h2></td>
					</tr>
					<tr>
						<td width="98%">
							<hr/>
						</td>
					</tr>
					<tr>
						<td align="left">
							<div style="color: black; font-size: 12pt"> 
								亲，你好！<br/>
								还在为相亲烦恼吗？ 请关注我们的维信订阅号：amylove-cn 结束单身吧 :) <br/>
								如打扰到您请见谅，如果身边有需要的朋友，请将我们分享给他们哦 ：） 送人玫瑰手有余香呢~！
								<br/>
								根据统计会员报名情况及调查以及以往的经验我们了解到：在相亲中—<font color="#A52A2A">更多的男生女生喜欢一对一，有针对性的私密约见，因此我们开始提供实名认证。</font>
								<br/>
								<font color="#191970">凡是通过实名认证的会员可以绑定为微信公众平台的会员，并享受移动微信端的会员资料查询服务。</font>
								<br/>
								实名验证需要提供的证件有：身份证、学历证、户籍证明、生活照。可通过现场、邮件或网络进行验证。
								<br/>
								<font color="red">“看中”后可以委托我们进行后续跟进、代理约见的相关服务，只有双方均认可见面的前提下，我们才会进行约见哦:)</font>
								<br/>
								<font color="green">这种模式摒弃了普通婚介或者传统婚恋网站的乱点鸳鸯谱的不规范现象，也会大大提高各位GGMM的成功概率，主动权都在自己的手中哦~！</font>
								<br/>
								<font color="#CD5C5C">再者就是低成本哦，婚介动不动就是成千上万的收费，婚恋网站就是系统自动匹配也需要收费好几千...在我们的平台上，相亲的成本也大大缩减。鉴于我们做好事的前提下，还要活下去，因此收费。验证费用一次性收取100元，看中后代理约见费用50元/次。</font>
								<br/>
								<font color="#800080">当然还有更重要的，我们的平台是半封闭式的，只有实名制的会员才可以进行绑定查询，所以不用担心自己的信息资料泄露，除了顾问与您联系外，不会有其他人可以打扰到你：）很私密！很安全！</font>
								<br/>
								<b>还等什么呢？赶快进行实名验证吧：）<br/>
								咨询电话：021—68975311、13917981044<br/>
								联 系 人：瞿老师</b><br/>
								-------------------------------------<br/>
								♥ 加入上海地区相亲小分队，幸福就在眼前，请直接回复中文“帮助”或“报名”“相亲”进入相亲报名页面，简单快捷。<br/>
								♥ 给我们提意见或者建议请发送邮件至amylove@amylove.cn与我们联系，我们会尽快的回复您。有你们的支持，我们一定会努力做得更好！2014年我们即将推出平台高级功能和自助交友指令集,并与网站平台对接.我们的微信号是amylove-cn，如果你在上海工作学习或者是上海户籍的白领人士请加入我们，支持我们请把我们推荐给你的朋友。谢谢！<br/>
								工作时间：上午10:00—下午6:00，6:00—9:00或节假日请拨打：13917981044、18018510805 <br/>
								联系人：瞿老师<br/>
								联系电话：021-68975311、021-68975266<br/>
								<img src="image001.jpg" width="750px" height="680px"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="98%">
							<hr/>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" class="ss_bn" onclick="window.close()" value="关闭"/>
						</td>
					</tr>
					
				</tbody>
			</table>
			</div>
			<div class="ge_6px"></div>
			<%@include file="/WEB-INF/jsps/common/foot.jsp"%>
		</center>
	</body>
</html>
