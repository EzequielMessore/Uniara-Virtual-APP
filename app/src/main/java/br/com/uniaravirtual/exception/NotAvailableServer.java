package br.com.uniaravirtual.exception;

public class NotAvailableServer extends Exception{

    public NotAvailableServer() {
        super("Servidor não disponível");
    }
}
