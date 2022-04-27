package bookApplication;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class bookApplication {

	private JFrame frame;
	private JTextField textBookName;
	private JTextField textEdition;
	private JTextField textPrice;
	private JTable table;
	private JTextField textSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bookApplication window = new bookApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public bookApplication() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String urls = "127.0.0.1";// you can even replace this with localhost
			String username = "root";
			String password = "";
			con = DriverManager.getConnection(
					"jdbc:mysql://" + urls + ":3306/javacrud?useUnicode=yes&characterEncoding=UTF-8", username,
					password);

		} catch (ClassNotFoundException ex) {

		} catch (SQLException ex) {

		}
	}

	void table_load() {
		try {
			pst = con.prepareStatement("SELECT * FROM livres");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 766, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Livres Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(302, 0, 184, 58);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Enregistrement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(38, 61, 344, 258);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Le titre du livre");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 28, 98, 27);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 75, 98, 27);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Prix");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(10, 123, 98, 27);
		panel.add(lblNewLabel_1_2);

		textBookName = new JTextField();
		textBookName.setBounds(136, 33, 178, 20);
		panel.add(textBookName);
		textBookName.setColumns(10);

		textEdition = new JTextField();
		textEdition.setColumns(10);
		textEdition.setBounds(136, 80, 178, 20);
		panel.add(textEdition);

		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(136, 128, 178, 20);
		panel.add(textPrice);

		JButton btnNewButton = new JButton("Sauvegarder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName, edition, price;
				bookName = textBookName.getText();// le nom de la varibale
				edition = textEdition.getText();
				price = textPrice.getText();

				try {
					pst = con.prepareStatement("INSERT INTO livres(nom,edition,prix)VALUES(?,?,?)");
					pst.setString(1, bookName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "Le livre est ajouté");
					table_load();
					textBookName.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBookName.requestFocus();

				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(48, 322, 100, 41);
		frame.getContentPane().add(btnNewButton);

		JButton btnSortir = new JButton("Sortir");
		btnSortir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});
		btnSortir.setBounds(158, 322, 100, 41);
		frame.getContentPane().add(btnSortir);

		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textBookName.setText("");
				textEdition.setText("");
				textPrice.setText("");
				textBookName.requestFocus();

			}
		});
		btnEffacer.setBounds(268, 322, 100, 41);
		frame.getContentPane().add(btnEffacer);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(382, 60, 358, 290);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Recherche", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(38, 374, 336, 65);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_1_1 = new JLabel("Livre ID");
		lblNewLabel_1_1_1.setBounds(10, 21, 55, 17);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1);

		textSearch = new JTextField();
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					String id = textSearch.getText();
					pst = con.prepareStatement("SELECT nom,edition,prix FROM livres WHERE id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {
						String name = rs.getString(1);
						String edi = rs.getString(2);
						String p = rs.getString(3);

						textBookName.setText(name);
						textEdition.setText(edi);
						textPrice.setText(p);
					} else {
						textBookName.setText("");
						textEdition.setText("");
						textPrice.setText("");
					}

				} catch (SQLException ev) {

				}
			}

			private PreparedStatement setString(int i, String id) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		textSearch.setBounds(75, 21, 186, 20);
		textSearch.setColumns(10);
		panel_1.add(textSearch);

		JButton btnEffacer_1 = new JButton("Mise \u00E0 jour");
		btnEffacer_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName, edition, price, searchId;
				bookName = textBookName.getText();// le nom de la varibale
				edition = textEdition.getText();
				price = textPrice.getText();
				searchId = textSearch.getText();

				try {
					pst = con.prepareStatement("UPDATE livres SET nom=?,edition=?,prix=? WHERE id=?");
					pst.setString(1, bookName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, searchId);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "La liste est mise à jour");
					table_load();
					textBookName.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBookName.requestFocus();

				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnEffacer_1.setBounds(457, 374, 100, 41);
		frame.getContentPane().add(btnEffacer_1);

		JButton btnEffacer_2 = new JButton("Supprimer");
		btnEffacer_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String searchId;

				searchId = textSearch.getText();

				try {
					pst = con.prepareStatement("DELETE FROM livres WHERE id=?");

					pst.setString(1, searchId);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "Livre supprimé!");
					table_load();
					textBookName.setText("");
					textEdition.setText("");
					textPrice.setText("");
					textBookName.requestFocus();

				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnEffacer_2.setBounds(585, 374, 100, 41);
		frame.getContentPane().add(btnEffacer_2);
	}
}
