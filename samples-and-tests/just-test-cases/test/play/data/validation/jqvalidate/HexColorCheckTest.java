package play.data.validation.jqvalidate;

import static org.junit.Assert.*;

import org.junit.Test;

import play.data.validation.jqvalidate.HexColorCheck;
import play.test.UnitTest;

public class HexColorCheckTest extends UnitTest {

	@Test
	public void isSatisfied_CorrectHEXcolor_ReturnTrue(){
		HexColorCheck colorValidation = new HexColorCheck();
		String validHEXColor = "#AF664B";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] not correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_HEXColorMissingOneChar_ReturnFalse(){
		HexColorCheck colorValidation = new HexColorCheck();
		String validHEXColor = "#AF664";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertFalse("HEXColor ["+validHEXColor+"] must not be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_EmptyHEXColor_ReturnTrue(){
		HexColorCheck colorValidation = new HexColorCheck();
		String validHEXColor = "";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] must be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_InvalidHEXColor_ReturnFalse(){
		HexColorCheck colorValidation = new HexColorCheck();
		String validHEXColor = "AHJU7J";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertFalse("HEXColor ["+validHEXColor+"] must not be defined as correct",isHEXcolorCorrect);
	}
	
	@Test
	public void isSatisfied_ThreeHEXColor_ReturnTrue(){
		HexColorCheck colorValidation = new HexColorCheck();
		String validHEXColor = "#AB6";
		
		boolean isHEXcolorCorrect = colorValidation.isSatisfied(new Object(), validHEXColor,null,null);
		assertTrue("HEXColor ["+validHEXColor+"] not correct",isHEXcolorCorrect);
	}

}
