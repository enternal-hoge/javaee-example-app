package eternal.hoge.javaee.simple.example.controller;

import java.util.Date;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import eternal.hoge.javaee.simple.example.bean.UserSessionBean;
import eternal.hoge.javaee.simple.example.bean.producer.HttpParam;
import eternal.hoge.javaee.simple.example.dao.NoteDao;
import eternal.hoge.javaee.simple.example.dao.UserDao;
import eternal.hoge.javaee.simple.example.model.ModelFactory;
import eternal.hoge.javaee.simple.example.model.Note;

@Model
public class NoteEditController {

	@Inject
	private NoteDao noteDao;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private UserSessionBean userSession;
	
	@Inject @HttpParam("id")
	private String noteId;
	
	@Inject @HttpParam("add")
	private String addFlag;
	
	private Note note;
	private Boolean adding;
	
	public Note getNote() {
		if(note == null) {
			initNote();
		}
		return note;
	}
	
	@Transactional
	public String save() {
		noteDao.save(getNote());
		return "home";
	}
	
	public Boolean getAdding() {
		return adding;
	}
	
	/**
	 * N.B. this bean has a lot of common code with NoteViewController
	 * for the sake of clarity, we have decided to keep them separated
	 * instead of adding a super class with common code.
	 */
	protected void initNote() {
		if(Boolean.valueOf( addFlag ) ) {
			adding = true;
			note = ModelFactory.note();
			note.setCreationDate( new Date() );
			note.setUser( userDao.findById(userSession.getUserId()) );
			return;
		}
		
		adding = false;
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
