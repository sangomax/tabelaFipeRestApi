package br.com.agdeo.tabelafiperestapi;

import br.com.agdeo.tabelafiperestapi.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipeRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeRestApiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.buscarCarro();

	}
}
