package br.com.uniaravirtual.exception;

/**
 * Created by c1283796 on 11/09/2015.
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException() {
        super("Login Inválido");
    }
}
