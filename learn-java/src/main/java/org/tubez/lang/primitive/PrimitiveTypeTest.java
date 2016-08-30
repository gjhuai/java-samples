package org.tubez.lang.primitive;

import org.junit.Test;

public class PrimitiveTypeTest {

	/**
	 *  byte
		Occupies 8 bits or 1 byte, which is:
		-27 to 27-1 or -128 to 127.
		Default value of 0.
		
		short
		Occupies 16 bits or 2 bytes, which is:
		-215 to 215-1 or -32,768 to 32,767
		Default value of 0.
		
		int
		Occupies 32 bits or 4 bytes, which is:
		-231 to 231-1 or -2,147,483,648 to 2,147,483,647.
		Default value of 0.
		
		long
		Occupies 64 bits or 8 bytes, which is:
		-263 to 263-1 or -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807.
		Default value of 0.

	 */
	@Test
	public void test_integers() {
		//integers: byte, short, int, long

		
		byte byteNumber = (byte)8; 	//note default integers are ints, must cast to a byte
		short shortNumber = (short)16; 	//note default integers are ints, must cast to a short
		int intNumber = 32;
		long longNumber = 64L;		//note default integers are ints, L after number indicates long.  
		    
		byte tempByte  =  byteNumber;        
		  //tempByte becomes == 8, byteNumber stays == 8
		  //byte overFlow = 140;
		  //will not compile, byte range is -128 to 127
		
		byte noOverFlow = (byte)140;       
		  //will compile because of cast, but result are unpredicatable, noOverFlow becomes == -116
		
		short tempShort =  (short)(shortNumber * 2);
		  //tempShort becomes == 32, 
		  //all integer computations must be cast to byte or short before assigning to byte or short
		
		int tempInt =  intNumber / 2;        
		  //tempInt becomes == 16, intNumber stays == 32 
		
		tempInt += tempShort;                
		  //temp int becomes 48, this is the same as 
		  //  tempInt = tempInt + tempShort;
		
		long tempLong = longNumber++;        
		  //templong becomes 64, then adds 1 to longNumber 
		  //which becomes 65
		
		tempLong  = ++longNumber;            
		  //adds 1 to longNumber which becomes 66, 
		  //then templong becomes 66
	}
	
	/**
	 * char
		Occupies 16 bits or 2 bytes, stores a - z, A - Z, 0 - 9, as well as +, - , etc.
		Stores 0 to 216-1 or 0 to 65,535.
		Default value '000', which is Unicode null.
		
		Some other Unicode values:
		'u0030' is 0
		'u0039' is 9
		'u0041' is A
		'u005A' is Z
		'u0061' is a
		'u007A' is z
		'u000A' is LF (line feed - will cause compile errors)
		'u000D' is CR (carriage return - will cause compile errors)		
	 */
	@Test
	public void test_char(){
		//char                        

		char  charCharacter = 'A';  //must use single quotes 
		char  uniCharCharacter = '\u0041';   //must use single quotes               
		    
		if (charCharacter == uniCharCharacter) { 
		  System.out.println("'A' = 'u0041'"); 
		}        
		  //prints 'A' == 'u0041'
	}
	
	
	/**
	 * boolean

		true or false
		Default value false.
	 */
	@Test
	public void test_boolean(){
		//boolean

		boolean booleanBoolean = true;       
		boolean tempBoolean  =  booleanBoolean;        
		//tempBoolean becomes == true, booleanBoolean stays == true 
	}

	
	/**
	 * real numbers: float and double

		float
		Occupies 32 bits or 4 bytes, with 6 or 7 significant digits.
		Default value of 0.0
		
		double
		Occupies 64 bits or 8 bytes, with 14 or 15 significant digits.
		Default value of 0.0

	 */
	@Test
	public void test_number(){
		  //real numbers: float and double
		
		float  floatNumber  = 32.32F;	//note default reals are double, must cast to a float
		float  floatNumber2 = (float)32.31;	//note default reals are double, must cast to a float   
		double doubleNumber = 64.64D;	//note default reals are double, don't need to cast to a double
		    
		float  tempFloat  =  floatNumber;	//tempFloat becomes == 32.32, floatNumber stays == 32.32
		
		double tempDouble =  doubleNumber * 2;	//tempDouble becomes == 129.28, doubleNumber stays == 64.64
	}
}
