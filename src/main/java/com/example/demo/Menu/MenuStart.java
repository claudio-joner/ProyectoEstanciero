package com.example.demo.Menu;

import com.example.demo.DemoApplication;
import com.example.demo.Model.Jugador;
import com.example.demo.Model.Partida;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.demo")
@EnableJpaRepositories(basePackages = "com.example.demo.Repositories")

public class MenuStart {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MenuStart.class, args);
        MenuMetodos menuMetodos = context.getBean(MenuMetodos.class);
        Boolean jugarDenuevo = true;

        do {
            Partida partida = context.getBean(Partida.class); // Obtener la instancia administrada por Spring
            menuMetodos.setPartida(partida); // Establecer la partida en MenuMetodos
            menuMetodos.mostrarLogoEstanciero();
            menuMetodos.menuPrincipal();
            Boolean seguirJugando = true;

            do {
                Jugador jugador = menuMetodos.revisarTurno();
                menuMetodos.modoDeTurno(jugador);
                menuMetodos.mostrarTablero();
                if (!(partida.estaTerminada(partida.getJugador()))) {
                    seguirJugando = partida.quiereSeguirJugando();
                }
                menuMetodos.savePartida();
            } while (!(partida.estaTerminada(partida.getJugador())) && seguirJugando);

            menuMetodos.mostrarResultados();
            jugarDenuevo = partida.quiereJugarNuevamente();
        } while (jugarDenuevo);


    }
}
