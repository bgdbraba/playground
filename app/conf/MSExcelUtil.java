package conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.users.enums.Gender;

import org.apache.xmlbeans.impl.common.Levenshtein;

import standalonepoi.JExcelDocument;
import standalonepoi.JExcelDocumentFactory;
import standalonepoi.JExcelSheet;

public class MSExcelUtil {

	public void loadChildren(File file, Long playgroundId) throws FileNotFoundException {
		// attempt to load the document
		JExcelDocument doc = null;
		try {
			doc = JExcelDocumentFactory.create(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc == null) {
			throw new FileNotFoundException();
		} else {
			loadChildrenFromDocument(doc, playgroundId);
		}
	}

	private void loadChildrenFromDocument(JExcelDocument doc, Long playgroundId) {
		// assume the document has only 1 sheet
		JExcelSheet sheet = doc.getJWorksheetAt(0);

		int row = 1;

		while (!sheet.getValueAt(row, 0).isEmpty()) {

			String lastname = sheet.getValueAt(row, 0);
			String firstname = sheet.getValueAt(row, 1);
			char gender = sheet.getValueAt(row, 2).toUpperCase().charAt(0);
			String dateOfBirth = sheet.getValueAt(row, 3);
			String email = "";

			if (sheet.getValueAt(row, 4) != null) {
				email = sheet.getValueAt(row, 4);
			}
			
			String language = sheet.getValueAt(row, 5).toLowerCase();
			
			
			String bebrasId = IdGenerator.generateUnique(firstname, lastname);
			Gender genderStudent = Gender.M;
			
			if (gender == 'F') {
				genderStudent = Gender.F;
			}
			

			// go to next row
			row++;
		}
	}
}