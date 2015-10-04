<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>昵称</th>
                  <!--<th>真实姓名</th>
                  <th>性别</th>
                  --><th>邮箱</th>
                  <th>手机</th>
                  <th>注册时间</th>
                  <th>最后登陆时间</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.userlist.size>0">
                <s:iterator value="#request.userlist" var="user">
                 <tr>
                  <td><input type="checkbox" name="adminIds" value='<s:property value="id"/>'/></td>
                  <td><a href='/wfsc/admin/user_detail.Q?id=<s:property value="id"/>' target="_blank"><s:property value="nickName"/></a></td>
                  <td><s:property value="email"/></td>
                  <td><s:property value="telphone"/></td>
                  <td><s:date name="regDate" format="yyyy-MM-dd hh:mm:ss" /></td>
                  <td><s:date name="lastLogin" format="yyyy-MM-dd hh:mm:ss" /></td>
                  <td>
                  	<s:if test="#user.status==1">启用</s:if>
                  	<s:else>禁用</s:else>
                  </td>
                  
                  <td></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="9">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetUserList" divId="userTableId" queryFormId="userQueryForm" page="${page }"></tags:ajaxPagination>
          <!--
          <div class="pagination alternate">
              <ul>
                <li class="disabled"><a href="#">上一页</a></li>
                <li class="active"> <a href="#">1</a> </li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">下一页</a></li>
              </ul>
            </div>
      -->
