/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a004631408j
 */
public class Control extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        DecimalFormat df = new DecimalFormat("#.##");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String snum1 = request.getParameter("num1").trim();
        String snum2 = request.getParameter("num2").trim();
        //String operador = request.getParameter("operador").trim();
        String operador = "multi";
        Double res = 0.0;
        String exp = "-?[0-9]+([.][0-9]+)?";
        int rand = (int)Math.round(Math.random() * 10 + 1);
        try {
            if (snum1.matches(exp) && snum2.matches(exp)) {
                Double num1 = Double.parseDouble(snum1);
                Double num2 = Double.parseDouble(snum2);
                if (operador.equals("")) {
                    throw new IllegalArgumentException();
                }

                switch (operador) {
                    case "suma":
                        res = num1 + num2;
                        break;
                    case "resta":
                        res = num1 - num2;
                        break;
                    case "multi":
                        res = num1 * num2;
                        break;
                    case "divi":
                        if (num2 == 0) {
                            throw new NumberFormatException();
                        } else {
                            res = num1 / num2;
                            break;
                        }
                    default:
                        throw new IllegalArgumentException();
                }

                String strJson = gson.toJson(df.format(res));
                TimeUnit.SECONDS.sleep(rand);
                out.print(strJson);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {
            response.setStatus(500);
            String error = "El divisor de una division no puede ser 0.";
            String strJson = gson.toJson(error);
            out.print(strJson);
        } catch (IllegalArgumentException iae) {
            response.setStatus(500);
            String error;
            if (snum1.equals("") || snum2.equals("")) {
                error = "No puede dejar campos vacíos.";
            } else {
                if (!(operador.equals("suma") || operador.equals("multi") || operador.equals("divi") || operador.equals("resta"))) {
                    error = "Debe seleccionar una operacion valida.";
                } else {
                    error = "Debe introducir sólo numeros enteros (los decimales se escriben con '.' no con ',').";
                }
            }
            String strJson = gson.toJson(error);
            out.print(strJson);
        } catch (InterruptedException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
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

