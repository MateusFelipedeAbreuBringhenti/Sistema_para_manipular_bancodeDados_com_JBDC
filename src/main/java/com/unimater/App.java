package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
import com.unimater.dao.ProductTypeDAO;
import com.unimater.model.ProductType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {
    public static void main( String[] args ){
        try {
            HttpServer servidor = HttpServer.create(
                    new InetSocketAddress(8080),0
            );

            servidor.createContext("/helloWorld",
                    new HelloWorldHandler());

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDb", "root", "root"
            );

            ProductTypeDAO productTypeDAO = new ProductTypeDAO(connection) {
                @Override
                protected void setStatementParameters(PreparedStatement stmt, ProductType entity) throws SQLException {

                }
            };

            productTypeDAO.getAll().forEach(System.out::println);

            servidor.setExecutor(null);
            servidor.start();
            System.out.println("Servidor rodando na porta 8080");
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
