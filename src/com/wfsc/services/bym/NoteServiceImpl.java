package com.wfsc.services.bym;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.bym.Note;
import com.wfsc.daos.bym.NoteDao;
import com.wfsc.services.bym.service.INoteService;

@Service("noteService")
@SuppressWarnings("unchecked")
public class NoteServiceImpl implements INoteService {
	
	@Resource
	private NoteDao noteDao;

	@Override
	public void deleteByIds(List<Long> ids) {
		noteDao.deletAllEntities(ids);
	}

	@Override
	public Page<Note> findForPage(Page<Note> page, Map<String, Object> paramap) {
		return noteDao.findForPage(page, paramap);
	}

	@Override
	public List<Note> getAll() {
		return noteDao.getAllEntities();
	}

	@Override
	public Note getNoteById(Long id) {
		return noteDao.getEntityById(id);
	}

	@Override
	public List<Note> getNotes() {
		return noteDao.getNotes();
	}

	@Override
	public void saveOrUpdateAll(List<Note> list) {
		noteDao.saveOrUpdateAllEntities(list);
	}

	@Override
	public void saveOrUpdateEntity(Note entity) {
		noteDao.saveEntity(entity);
	}

}
