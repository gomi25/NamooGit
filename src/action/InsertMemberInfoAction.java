package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import util.MailSender;

public class InsertMemberInfoAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao mDao = new MemberDao();
		try {
			request.setCharacterEncoding("utf-8");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
			mDao.enterInformation(name, password, email, memberIdx);
			
			String code = "";
			for(int i=1; i<=4; i++)
				code += (int)(Math.random()*10);
			mDao.emailVerification(memberIdx, code);
			
			// TODO HERE : 메일발송API를 사용해서 메일 발송.
			MailSender sender = new MailSender();
			
			String mailContent = "    <div class='emailBackground border'>" +
								"        <table cellpadding='0' cellspacing='0' border='0' style='table-layout:fixed;width:100%;max-width:600px;background-color:#fff;line-height:1.5;font-size:15px;color:#333;margin:0 auto 24px'>" +
								"<tbody>" +
								"  <tr>" +
								"      <td style='padding:0' valign='top'>" +
								"        <table cellpadding='0' cellspacing='0' border='0' style='table-layout:fixed;width:100%;max-width:600px;border-radius:3px;background-color:#fff;line-height:1.5;font-size:15px;color:#333;margin:0 auto'>" +
								"          <tbody>" +
								"            <tr role='email-header' style='height:60px;text-align:left'>" +
							    "              <td role='logo-container' style='padding:24px 30px'>" +
							    "                <img src='https://ci3.googleusercontent.com/meips/ADKq_NaJPA9gQUY8fhrkiLko0yir8bl8qcMiX3r0Ta8Rji_J5M8up9PpugmRV6qUE1gJFf7tOiVynpu8KPwqB7c-ggCIo1fpeyBP4Q=s0-d-e1-ft#https://jandi-box.com/email/payment/logo_jandi.png' height='31' width='115' class='CToWUd' data-bit='iit'>" +
							    "              </td>" +
							    "            </tr>" +
							    "          </tbody>" +
							    "        </table>" +
							    "        <table cellpadding='0' cellspacing='0' border='0' width='100%'>" +
							    "          <tbody><tr>" +
							    "             <td width='5%'></td>" +
							    "                          <td valign='bottom' width='90%'>" +
							    "                              <h2 style='text-align:center;color:#333333;font-weight:700;font-size:24px;line-height:32px;margin:40px 0 0!important'>" +
							    "                           나무를 이용해주셔서 감사합니다!" +
							    "                              </h2>" +
							    "                              <p style='text-align:center;font-size:15px;line-height:22px;margin:12px 0 24px 0!important;padding:0 15px;color:#909090'>" +
							    "                           가입 화면에서 아래 인증번호를 입력해주세요." +
							    "                              </p>" +
							    "                          </td>" +
							    "                          <td width='5%'></td>" +
							    "                      </tr>" +
							    "                  </tbody></table>" +
							    "              </td>" +
							    "          </tr>" +
							    "          <tr>" +
							    "              <td height='0px' width='100%'></td>" +
							    "  </tr><tr>" +
							    "      <td valign='top'>" +
							    "          <table cellpadding='0' cellspacing='0' align='center' border='0' width='100%'>" +
							    "              <tbody>" +
							    "                <tr>" +
							    "                  <td width='5%'></td>" +
							    "                  <td valign='top' width='90%'>" +
							    "                      <table cellpadding='0' cellspacing='0' align='center' border='0' width='163' style='margin-bottom:48px'>" +
							    "                        <tbody>" +
							    "                            <tr>" +
							    "                              <td></td>" +
							    "                              <td width='163px' height='50' style='text-align:center;background-color:#f5f5f5;border-radius:6px'>" +
							    "                                  <a style='padding:0;font-family:Helvetica Neue;font-weight:700;font-size:24px;color:#00c473;letter-spacing:18px;margin-left:18px;white-space:nowrap;word-break:keep-all'>" +
							    "                                    <!-- 인증번호란 -->" +
							                                        code +
							    "                                  </a>" +
							    "                              </td>" +
							    "                              <td></td>" +
							    "                            </tr>" +
							    "                        </tbody>" +
							    "                      </table>" +
							    "                  </td>" +
							    "                  <td width='5%'></td>" +
							    "                </tr>" +
							    "                <tr>" +
							    "                    <td width='5%'></td>" +
							    "                    <td valign='top' width='90%' style='text-align:left;padding:0 12px'>" +
							  	"                        <div style='font-size:15px;line-height:26px;background-color:#fff;margin-top:12px;margin-bottom:0px;color:#505050;font-weight:500;border-top:1px solid #ebebeb;border-bottom:1px solid #ebebeb;padding:36px 12px 40px;text-align:center'>" +
							  	"                          <p style='text-align:center;font-size:15px;line-height:22px;margin:12px 0 20px;padding:0 15px;color:#909090'>" +
							    "                      모바일 앱을 통해 더 쉽고 빠르게 나무를 사용하실 수 있습니다." +
							    "                        </p>" +
							    "                        <a style='text-decoration:none;vertical-align:top' href='https://mandrillapp.com/track/click/30882906/itunes.apple.com?p=eyJzIjoibkZvYjV0eFJqMURlM0tKN0p6MnVhOEUzSnVJIiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2l0dW5lcy5hcHBsZS5jb21cXFwvYXBwXFxcL2phbmRpLXRlYW0tY29sbGFib3JhdGlvbi10b29sXFxcL2lkOTA0ODk1MjA4XCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCIyNTlmZDAwZWNlYjAwZTJmODhlYTFhNzYxMmM5NDdjZDQ5M2M0YzQ3XCJdfSJ9' target='_blank' data-saferedirecturl='https://www.google.com/url?q=https://mandrillapp.com/track/click/30882906/itunes.apple.com?p%3DeyJzIjoibkZvYjV0eFJqMURlM0tKN0p6MnVhOEUzSnVJIiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2l0dW5lcy5hcHBsZS5jb21cXFwvYXBwXFxcL2phbmRpLXRlYW0tY29sbGFib3JhdGlvbi10b29sXFxcL2lkOTA0ODk1MjA4XCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCIyNTlmZDAwZWNlYjAwZTJmODhlYTFhNzYxMmM5NDdjZDQ5M2M0YzQ3XCJdfSJ9&amp;source=gmail&amp;ust=1718236790921000&amp;usg=AOvVaw0zJDZXZAq6-sCibo_ZSEcB'>" +
							    "                                <img style='height:36px;vertical-align:top' height='36' width='122' src='https://ci3.googleusercontent.com/meips/ADKq_NYXV-h7mLfaKPONJQLwkDK2ctYwISOUEU48CJib_6MeIxF-g0KEYodY8TuJ2zB47QctyKZu77Bjt0hMgA5Zyn8jeyT2ZD7Be367mzASEpM4Zp-IBRVPb7LkU6Zk4vYAUsr5f3VQktAaY-lrFG-sPnWT7z0FEjQymaJ4n63feaI=s0-d-e1-ft#https://p4.zdassets.com/hc/theme_assets/599681/200072845/Download_on_the_App_Store_Badge_US-UK_135x40.png' class='CToWUd' data-bit='iit' jslog='32272; 1:WyIjdGhyZWFkLWY6MTgwMDUyOTQ0NDMzMTUyNjU4OSJd; 4:WyIjbXNnLWY6MTgwMDUyOTQ0NDMzMTUyNjU4OSJd'>" +
							    "                              </a>" +
							    "                              <a style='text-decoration:none;vertical-align:top' href='https://mandrillapp.com/track/click/30882906/play.google.com?p=eyJzIjoiN3A0VWYtS2pHUm1vcWd2ald2YUNQNl9Gc1Q4IiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3BsYXkuZ29vZ2xlLmNvbVxcXC9zdG9yZVxcXC9hcHBzXFxcL2RldGFpbHM_aWQ9Y29tLnRvc3NsYWIuamFuZGkuYXBwJmhsPWVuXCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCJhZGEyYzFiZjNlZWYwMDI0NzAzYjJiNTdmMmNmMjc3YTU2MDIyYjU1XCJdfSJ9' target='_blank' data-saferedirecturl='https://www.google.com/url?q=https://mandrillapp.com/track/click/30882906/play.google.com?p%3DeyJzIjoiN3A0VWYtS2pHUm1vcWd2ald2YUNQNl9Gc1Q4IiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3BsYXkuZ29vZ2xlLmNvbVxcXC9zdG9yZVxcXC9hcHBzXFxcL2RldGFpbHM_aWQ9Y29tLnRvc3NsYWIuamFuZGkuYXBwJmhsPWVuXCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCJhZGEyYzFiZjNlZWYwMDI0NzAzYjJiNTdmMmNmMjc3YTU2MDIyYjU1XCJdfSJ9&amp;source=gmail&amp;ust=1718236790921000&amp;usg=AOvVaw1z55GXLpez655jmlxVwYqb'>" +
							    "                                <img style='height:36px;vertical-align:top' height='36' width='' src='https://ci3.googleusercontent.com/meips/ADKq_NZEij5K1X_z0fMdUBgGJkezMDkaFfrjYY9rD1GP4oLEwadghFd3N1RntbQEsZiUT0W-LHPN58NSPqbc8Y6vd-imZpr7u1JKgK7ccWq9lOXCCVGVtFIn-Gq7h-sODw=s0-d-e1-ft#https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png' class='CToWUd' data-bit='iit'>" +
							    "                              </a>" +
							 	"                         </div>" +
							 	"                         <p style='font-size:12px;line-height:18px;color:#959595;padding-left:0px;margin:20px 0 0 0'>" +
							   	"                           나무 사용법이 궁금하시면 <a href='https://mandrillapp.com/track/click/30882906/support.jandi.com?p=eyJzIjoid0cxNXV5Mm9fNmdlWmp1ZVpZNW1rZVFnUm0wIiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3N1cHBvcnQuamFuZGkuY29tXCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCIwMzU5ZDczYzk3N2Y1NjZkYTg2ZmVjMzY1YTYyYmNmOTk1NjM2NmM4XCJdfSJ9' style='color:#00c473;text-decoration:underline' target='_blank' data-saferedirecturl='https://www.google.com/url?q=https://mandrillapp.com/track/click/30882906/support.jandi.com?p%3DeyJzIjoid0cxNXV5Mm9fNmdlWmp1ZVpZNW1rZVFnUm0wIiwidiI6MSwicCI6IntcInVcIjozMDg4MjkwNixcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3N1cHBvcnQuamFuZGkuY29tXCIsXCJpZFwiOlwiOTczZGQ4NDkwZjJlNDk3NjhlMzFiNTE2Yzc5Y2E2MjFcIixcInVybF9pZHNcIjpbXCIwMzU5ZDczYzk3N2Y1NjZkYTg2ZmVjMzY1YTYyYmNmOTk1NjM2NmM4XCJdfSJ9&amp;source=gmail&amp;ust=1718236790921000&amp;usg=AOvVaw17I-Zb_gnu5NT2hW-OuweO'>헬프센터</a>에서 자세한 내용을 확인하실 수 있습니다." +
							    "                       궁금한 점은 언제든지 나무 메뉴의 1:1 문의하기 또는 <a href='mailto:support@tosslab.com' style='color:#00c473;text-decoration:underline' target='_blank'>support@tosslab.com</a>으로 연락 주시기 바랍니다." +
							    "                   </p>" +
							 	"                         <p style='font-size:12px;line-height:24px;color:#959595;padding-left:0px;margin:20px 0'>" +
							    "                        © Toss Lab, Inc. All rights reserved." +
							    "                      </p>" +
							    "                  </td>" +
							    "                  <td width='5%'></td>" +
							    "              </tr>" +
							    "          </tbody></table>" +
							    "      </td>" +
							    "  </tr>" +
							    "</tbody>" +
							  	"</table>" +
							    "</div>";
			sender.sendMail(email, name, "인증 후 바로 NAMOO를 사용하실 수 있습니다.", mailContent);
			request.getRequestDispatcher("Signup3.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
