package com.parking.api;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.parking.api.view.MainView;

@SpringBootApplication
public class ParkingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ParkingApplication.class).headless(false)
				.run(args);
		EventQueue.invokeLater(() -> {
			MainView mainView = context.getBean(MainView.class);
			mainView.setVisible(true);
		});
	}

}
