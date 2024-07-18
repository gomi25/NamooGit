package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	// convertToBeautifulDateString(String) : 아름다운 문자열 날짜 표현으로 변환해줌.
	// 파라미터 date : (ex) "2024-07-09 10:35:00"
	public static String convertToBeautifulDateString(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		
		Date dateDb = sdf.parse(date);   // String타입 --> Date타입
		Date dateNow = new Date();   // 현재시각을 'Date'객체('dateNow')로 가져옴
		
		long diff = dateNow.getTime() - dateDb.getTime();	//시간의 차이를 계산,
		
		SimpleDateFormat sdfSameDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDateDb = sdfSameDate.format(dateDb);  // 연/월/일
		String strDateNow = sdfSameDate.format(dateNow);
		
		SimpleDateFormat sdfSameYear = new SimpleDateFormat("yyyy");
		String strYearDb = sdfSameYear.format(dateDb);   // 연
		String strYearNow = sdfSameYear.format(dateNow);	
		
		String strDate = null;
		if(diff < 60*1000) {
			strDate = "방금 전";
		} else if(strDateDb.equals(strDateNow)) {
			// date_db로... ---> "오전 10:12"
			sdf = new SimpleDateFormat("a hh:mm");
			strDate = sdf.format(dateDb);    // Date타입 --> String타입
		} else if(strYearDb.equals(strYearNow)) {
			// 7월 8일
			sdf = new SimpleDateFormat("M월 d일");
			strDate = sdf.format(dateDb);
		} else {
			// 2023년 12월 31일
			sdf = new SimpleDateFormat("yyyy년 M월 d일");
			strDate = sdf.format(dateDb);
		}
		return strDate;

	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println(convertToBeautifulDateString("2023/07/08 10:35:00"));
//	}
}
