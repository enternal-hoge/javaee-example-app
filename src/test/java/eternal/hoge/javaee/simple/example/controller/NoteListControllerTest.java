package eternal.hoge.javaee.simple.example.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import eternal.hoge.javaee.simple.example.bean.UserSessionBean;
import eternal.hoge.javaee.simple.example.dao.NoteDao;
import eternal.hoge.javaee.simple.example.model.ModelFactory;
import eternal.hoge.javaee.simple.example.model.Note;

public class NoteListControllerTest {

	private NoteListController noteListController;
	private NoteDao noteDao;
	private UserSessionBean userSession;
	private Note note;

	@Before
	public void setUp() throws InitializationError {
		noteListController = new NoteListController();
		noteDao = mock(NoteDao.class);
		userSession = new UserSessionBean();
		userSession.setUserId(Long.valueOf(1));
		note = ModelFactory.note();

		try {
			FieldUtils.writeField(note, "id", Long.valueOf(10), true);
			FieldUtils.writeField(noteListController, "noteDao", noteDao, true);
			FieldUtils.writeField(noteListController, "userSession", userSession, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testGetNotes() {
		when(noteDao.findByUserId(Long.valueOf(1))).thenReturn(Collections.singletonList(note));
		
		assertEquals(1, noteListController.getNotes().size());
		assertEquals(note, noteListController.getNotes().get(0));
		
	}

}
