package br.com.biblioteca.db;

public class DbException extends RuntimeException {
    public DbException(String msg) {
        super(msg);
    }
}