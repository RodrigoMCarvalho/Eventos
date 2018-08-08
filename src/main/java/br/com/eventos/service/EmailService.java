package br.com.eventos.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.eventos.models.Evento;

public class EmailService {
	
	public void enviar(String nome, String emailDestinatario, Evento evento) {
		
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("javarodrigo9@gmail.com", "senha"));
			email.setSSLOnConnect(true);
			
			email.setFrom("javarodrigo9@gmail.com");
			email.setSubject("Parabéns, você foi convidado para o Sistema de Eventos Online");
			email.setMsg("Olá " + nome +". Você acaba de ser convidado para o seguinte evento: " + evento + ".");
			email.addTo(emailDestinatario);
			email.send();
			
		} catch (EmailException e) {
			e.printStackTrace();
}
	}

}
