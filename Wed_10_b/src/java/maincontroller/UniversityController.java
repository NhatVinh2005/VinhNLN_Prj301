/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package maincontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UniversityDAO;
import model.UniversityDTO;

/**
 *
 * @author THIS PC
 */
public class UniversityController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String keywords = request.getParameter("keywords");
        String id = request.getParameter("id");
        
        if (keywords == null){
            keywords = "";
        }
        
        if (id == null){
            id = "";
        }
        
        System.out.println(keywords);
        UniversityDAO udao = new UniversityDAO();
        
        if (!id.isEmpty()){
            boolean check = udao.softDelete(id);
            if (check){
                request.setAttribute("mgs", "Deleted!");
            } else {
                request.setAttribute("msg", "Error, can not delete:"+id);
            }
        }
        
        ArrayList<UniversityDTO> list = new ArrayList<>();
        
        if (keywords.trim().length() > 0){
            list = udao.filterByName(keywords);
        }
        
        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);
        
        String url = "search.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
    
    protected void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String keywords = request.getParameter("keywords");
        
        if (keywords == null){
            keywords = "";
        }
        
        UniversityDAO udao = new UniversityDAO();
        ArrayList<UniversityDTO> list = new ArrayList<>();
        
        if (keywords.trim().length() > 0){
            list = udao.filterByName(keywords);
        }
        
        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);
        String url = "search.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
    
    protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String id = request.getParameter("id");
        UniversityDAO udao = new UniversityDAO();
        
        //Search information old from DB
        UniversityDTO u = udao.searchByID(id);
        
        if (u != null){
            request.setAttribute("u", u);
            request.setAttribute("mode", "update");
            request.getRequestDispatcher("university-form.jsp").forward(request, response);
            
        } else {
            request.setAttribute("error", "Cannot serach University with this ID!");
            request.getRequestDispatcher("SearchUniversityController").forward(request, response);
            
        }
    }
    
    private UniversityDTO extracUniversityFromRequest(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String shortName = request.getParameter("shortName");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String region = request.getParameter("region");
        String type = request.getParameter("type");
        
        int foundedYear = parseOrDefault(request.getParameter("foundedYear"), 0);
        int totalStudents = parseOrDefault(request.getParameter("totalStudents"), 0);
        int totalFalcuties = parseOrDefault(request.getParameter("totalFalcuties"), 0);
        boolean isDraft = "on".equals(request.getParameter("isDraft"));
        
        return new UniversityDTO(id, name, shortName, description, foundedYear, 
                address, city, region, type, totalStudents, 
                totalFalcuties, isDraft);
    }
    
    private String validateUniversity(UniversityDTO u, boolean  isUpdate){
        StringBuilder error = new StringBuilder();
        
        if (u.getId() == null || u.getId().trim().isEmpty()){
            error.append("Not imported ID! <br/>");
        }
        
        if (u.getName() == null || u.getName().trim().isEmpty()){
            error.append("Not imported Name! <br/>");
        }
        
        if (u.getFoundedYear() < 0){
            error.append("Founded Year Should >= 0 ! <br/>");
        }
        
        //If new add, need check valid ID (Update should not check because ID is readonly)
        
        if (!isUpdate){
            UniversityDAO udao = new UniversityDAO();
            
            if (udao.searchByID(u.getId()) != null){
                error.append("ID existed, please choose other ID! <br/.");
            }
        }
        return error.toString();
    }
    
    protected void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UniversityDTO u = extracUniversityFromRequest(request);
        String error = validateUniversity(u, false);
        String msg ="";
        
        if (error.isEmpty()){
            UniversityDAO udao = new UniversityDAO();
            if (udao.add(u)){
                msg = "Add University Success!";
            } else {
                error = "Error, cannot add Database!";
            }
        }
        
        request.setAttribute("u", u);
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);
        request.getRequestDispatcher("university-form.jsp").forward(request, response);
    }
    
    protected void doSaveUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UniversityDTO u = extracUniversityFromRequest(request);
        String error = validateUniversity(u, true);
        String msg = "";
        
        if (error.isEmpty()){
            UniversityDAO udao = new UniversityDAO();
            
            if (udao.update(u)){
                msg = "Update Success!";
            } else {
                error = "Error, cannot Update!";
            }
        }
        request.setAttribute("u", u);
        request.setAttribute("mode", "update");
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);
        request.getRequestDispatcher("university-form.jsp").forward(request, response);
    }
    
    private int parseOrDefault(String value, int defaultValue){
        try {
            return Integer.parseInt(value);
            
        } catch (Exception e){
            return defaultValue;
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        //Case action is null (user direct access URL)
        if (action == null || action.isEmpty()){
            doSearch(request, response);
            return;
        }
        
        try {
            switch (action){
                case "searchUniversity":
                    doSearch(request, response);
                    break;
                    
                case "addUniversity":
                    doAdd(request, response);
                    break;
                
                    case "deleteUniversity":
                    doDelete(request, response);
                    break;
                    
                    case "updateUniversity":
                        //When user press button "update" on list 
                    doUpdate(request, response);
                    break;
                    
                    case "saveUpdateUniversity":
                    doSaveUpdate(request, response);
                    break;
                    
                    default:
                        //If action mismatch, return default page search or report error
                        request.setAttribute("error", "Action not valid: " + action);
                        doSearch(request, response);
                        break;
                        
            }
        } catch (Exception e){
            log("Error at UniversityController: "+ e.toString());
            request.setAttribute("error", "System is experiencing problems, please try later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
