package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConsumerCaseDao;
import dto.ConsumerCaseDto;

public class ConsumerCaseInfoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConsumerCaseDao cDao = new ConsumerCaseDao();
        ConsumerCaseDto cDto = null;

        try {
            int caseIdx = Integer.parseInt(request.getParameter("case_idx"));
            cDto = cDao.getConsumerCaseDtoByCaseIdx(caseIdx);
            request.setAttribute("cDto", cDto);
            request.setAttribute("case_idx", caseIdx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("ConsumerCaseInfo.jsp").forward(request, response);
    }
}
