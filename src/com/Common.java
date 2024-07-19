package com;

public class Common {
//파일 외부공유용url생성 - 난수 발생 방법
	public String generateNewUrl() {
		// 97-122
		// 48-57
		
		// -9 ~ +26 : -9~0 = 숫자 | 1~26 => (+96) = 소문자
		String strCode = "";
		for(int i=1; i<=20; i++) {
			int r = (int)(Math.random()*36)-9;   // -9 ~ + 26
			//System.out.print(r + " ");
			if(r<=0) {  // 숫자.
				strCode += (r * -1);
			} else {
				strCode += (char)(r + 96);
			}
		}
		//System.out.println("strCode : " + strCode);
		return strCode;
	} 
	
	/*
	 * // Common클래스. (common 패키지) public static void main(String[] args) { String s
	 * = generateNewUrl(); System.out.println("strCode : " + s);
	 * 
	 * }
	 */
}
