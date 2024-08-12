package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConsumerCaseDao;
import dto.ConsumerCaseDto;

public class ConsumerCaseAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConsumerCaseDao cDao = new ConsumerCaseDao();	
        ArrayList<ConsumerCaseDto> consumerCaseDto = null; 
        int industry = 0;

        if(request.getParameter("industry") == null) {
            try {
				consumerCaseDto = cDao.getListConsumerCaseDto();
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            industry = Integer.parseInt(request.getParameter("industry"));
            try {
				consumerCaseDto = cDao.getListConsumerCaseDtoByIndustry(industry);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

        request.setAttribute("industry", industry);
        request.setAttribute("consumerCaseDto", consumerCaseDto);
        request.getRequestDispatcher("ConsumerCase.jsp").forward(request, response);
    }
}