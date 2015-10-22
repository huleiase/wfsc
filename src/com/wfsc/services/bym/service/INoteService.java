package com.wfsc.services.bym.service;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Note;


public interface INoteService {

	public Page<Note> findForPage(Page<Note> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Note entity); 
	
	public Note getNoteById(Long id);
	
	public List<Note> getAll();
	
	public void saveOrUpdateAll(List<Note> list);
	
	public List<Note> getNotes();
	
}
