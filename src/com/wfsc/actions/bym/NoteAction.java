package com.wfsc.actions.bym;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.constants.LogModule;
import com.wfsc.common.bo.bym.Note;
import com.wfsc.services.bym.service.INoteService;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("NoteAction")
@Scope("prototype")
public class NoteAction extends DispatchPagerAction {

	private static final long serialVersionUID = 6840813999299260353L;
	
	//下载模板
	private static final String MODEL_EXCEL_NAME = "note.xls";

	@Resource(name = "noteService")
	private INoteService noteService;
	
	private Note note;

	public String manager(){
		this.setTopMenu();
		list();
		return "manager";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Note> page = new Page<Note>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		Map<String,Object> paramap = handleRequestParameter();
		page = noteService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = "/wfsc/admin/note_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			note = noteService.getNoteById(Long.valueOf(id));
		}else{
			note = new Note();
		}
		return "input";
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		try {
			noteService.deleteByIds(idList);
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String curAdminName = this.getCurrentAdminUser().getUsername();
		saveSystemLog(LogModule.noteLog, curAdminName+"删除了通告信息");
		return null;
	}
	
	public String getNotes(){
		response.setCharacterEncoding("utf-8");
		List<Note> list = noteService.getNotes();
		StringBuffer sb = new StringBuffer("");
		if(CollectionUtils.isNotEmpty(list)){
			for(Note n : list){
				sb.append("公告:"+n.getVcMemo()+" 。 ");
			}
		}
		try {
			response.getWriter().write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String save(){
		String curAdminName = this.getCurrentAdminUser().getUsername();
		note.setVcUser(curAdminName);
		noteService.saveOrUpdateEntity(note);
		saveSystemLog(LogModule.noteLog, curAdminName+"新增或修改了通告信息");
		return "ok";
	}
	
	/**
	 * 封装request请求参数到map中
	 * @return
	 */
	public Map<String,Object> handleRequestParameter(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		String vcUser = request.getParameter("vcUser");
		String vcName = request.getParameter("vcName");
		if(StringUtils.isNotEmpty(vcUser)){
			paramap.put("vcUser", vcUser);
			request.setAttribute("vcUser", vcUser);
		}
		if(StringUtils.isNotEmpty(vcName)){
			paramap.put("vcName", vcName);
			request.setAttribute("vcName", vcName);
		}
		return paramap;
	}
	
	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	
}
