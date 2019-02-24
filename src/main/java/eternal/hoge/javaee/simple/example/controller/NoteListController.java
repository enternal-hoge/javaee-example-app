package eternal.hoge.javaee.simple.example.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import eternal.hoge.javaee.simple.example.bean.UserSessionBean;
import eternal.hoge.javaee.simple.example.dao.NoteDao;
import eternal.hoge.javaee.simple.example.model.Note;

@Model
public class NoteListController {

	@Inject
	private UserSessionBean userSession;
	
	@Inject
	private NoteDao noteDao;
	
	private List<Note> notes;
	
	public List<Note> getNotes() {
		if(notes == null) {
			notes = noteDao.findByUserId( userSession.getUserId() );
		}
		
		return notes;
	}
	
}
