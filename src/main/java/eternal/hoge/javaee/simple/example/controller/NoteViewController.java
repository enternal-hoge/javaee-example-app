package eternal.hoge.javaee.simple.example.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import eternal.hoge.javaee.simple.example.bean.UserSessionBean;
import eternal.hoge.javaee.simple.example.bean.producer.HttpParam;
import eternal.hoge.javaee.simple.example.dao.NoteDao;
import eternal.hoge.javaee.simple.example.model.Note;

@Model
public class NoteViewController {
	
	@Inject
	private NoteDao noteDao;
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject @HttpParam("id")
	private String noteId;
	
	private Note note;
	
	public Note getNote() {
		if(note == null) {
			initNote();
		}
		return note;
	}
	
	protected void initNote() {
		if(StringUtils.isEmpty(noteId)) {
			throw new IllegalArgumentException("id not found");
		}
		
		try {
			Long id = Long.valueOf(noteId);
			note = noteDao.findById(id);
			
			if(!note.getUser().getId().equals( userSession.getUserId())) {
				throw new IllegalStateException("you are not the owner of the note");
			}
			
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("id not a number");
		}
	}
	
}
