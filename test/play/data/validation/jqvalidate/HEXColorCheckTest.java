package play.data.validation.jqvalidate;

import static org.junit.Assert.*;

import org.junit.Test;

import play.data.validation.jqvalidate.HEXColorCheck;
import play.test.UnitTest;

public class HEXColorCheckTest extends UnitTest {

	@Test
	public void isSatisfied_CorrectHEXcolor_ReturnTrue(){
		HEXColorCheck colorValidation = new HEXColorCheck();
		String validHEXColor = "#AF664B";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] not correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_HEXColorMissingOneChar_ReturnFalse(){
		HEXColorCheck colorValidation = new HEXColorCheck();
		String validHEXColor = "#AF664";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertFalse("HEXColor ["+validHEXColor+"] must not be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_EmptyHEXColor_ReturnTrue(){
		HEXColorCheck colorValidation = new HEXColorCheck();
		String validHEXColor = "";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] must be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_InvalidHEXColor_ReturnFalse(){
		HEXColorCheck colorValidation = new HEXColorCheck();
		String validHEXColor = "AHJU7J";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertFalse("HEXColor ["+validHEXColor+"] must not be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_ThreeHEXColor_ReturnTrue(){
		HEXColorCheck colorValidation = new HEXColorCheck();
		String validHEXColor = "#AB6";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] not correct",isHEXcolorCorrect);
	}

}
