package com.parking.api.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.stereotype.Component;

import com.parking.api.dto.BayResponseDTO;
import com.parking.api.service.BayService;

@Component
public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;

	private final BayService bayService;
	private JPanel cards;
	private CardLayout cardLayout;

	private JPanel gridBaysAvailable;
	private JPanel gridBaysOccupied;

	public MainView(BayService bayService) {
		this.bayService = bayService;

		setTitle("Gerenciador de Vagas");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);

		cards.add(createMainMenu(), "Menu");
		cards.add(checkIn(), "CheckIn");
		cards.add(checkOut(), "CheckOut");

		add(cards);
	}

	private JPanel createMainMenu() {
		JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
		painel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

		JButton btnInsert = new JButton("Insert Bays");
		JButton btnCheckIn = new JButton("Check-in");
		JButton btnCheckOut = new JButton("Check-out");

		btnInsert.addActionListener(e -> {
			String input = JOptionPane.showInputDialog("How many bays you want to create?");
			if (input != null && !input.isEmpty()) {
				try {
					Long quantidade = Long.parseLong(input);
					bayService.createBays(quantidade);
					JOptionPane.showMessageDialog(this, "Successful created!");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Please insert a valid number.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
				}
			}
		});

		btnCheckIn.addActionListener(e -> {
			refreshAvailableGrid();
			cardLayout.show(cards, "CheckIn");
		});

		btnCheckOut.addActionListener(e -> {
			refreshOccupiedGrid();
			cardLayout.show(cards, "CheckOut");
		});

		painel.add(btnInsert);
		painel.add(btnCheckIn);
		painel.add(btnCheckOut);

		return painel;
	}

	private JPanel checkIn() {
		JPanel container = new JPanel(new BorderLayout());

		JButton btnBack = new JButton("<- Back to menu");
		btnBack.addActionListener(e -> cardLayout.show(cards, "Menu"));

		gridBaysAvailable = new JPanel(new GridLayout(0, 5, 10, 10));

		container.add(btnBack, BorderLayout.NORTH);
		container.add(new JScrollPane(gridBaysAvailable), BorderLayout.CENTER);

		return container;
	}

	private JPanel checkOut() {
		JPanel container = new JPanel(new BorderLayout());

		JButton btnBack = new JButton("<- Back to menu");
		btnBack.addActionListener(e -> cardLayout.show(cards, "Menu"));

		gridBaysOccupied = new JPanel(new GridLayout(0, 5, 10, 10));

		container.add(btnBack, BorderLayout.NORTH);
		container.add(new JScrollPane(gridBaysOccupied), BorderLayout.CENTER);

		return container;
	}

	public void refreshAvailableGrid() {
		gridBaysAvailable.removeAll();

		List<BayResponseDTO> baysAvailable = bayService.showAvailableBays();

		for (BayResponseDTO dto : baysAvailable) {
			JButton btnBay = new JButton("Bay: " + dto.getId());
			btnBay.setBackground(Color.GREEN);
			btnBay.addActionListener(e -> {
				try {
					bayService.checkIn(dto.getId());
					JOptionPane.showMessageDialog(this, "Successful Check-in!");
					refreshAvailableGrid();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
				}
			});
			gridBaysAvailable.add(btnBay);
		}

		gridBaysAvailable.revalidate();
		gridBaysAvailable.repaint();
	}

	public void refreshOccupiedGrid() {
		gridBaysOccupied.removeAll();

		List<BayResponseDTO> baysOccupied = bayService.showOccupiedBays();

		for (BayResponseDTO dto : baysOccupied) {
			JButton btnBay = new JButton("Bay: " + dto.getId());
			btnBay.setBackground(Color.RED);
			btnBay.addActionListener(e -> {
				try {
					bayService.checkOut(dto.getId());
					JOptionPane.showMessageDialog(this, "Successful Check-out!");
					refreshOccupiedGrid();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
				}
			});
			gridBaysOccupied.add(btnBay);
		}

		gridBaysOccupied.revalidate();
		gridBaysOccupied.repaint();
	}
}