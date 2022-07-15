import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class BookShop {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtEdit;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
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
	public BookShop() {
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
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_shop","root","root");
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book"); 
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
		frame.setBounds(100, 100, 913, 504);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbTitle = new JLabel("Book Shop");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setForeground(Color.BLUE);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 34));
		lbTitle.setBounds(10, 10, 879, 61);
		frame.getContentPane().add(lbTitle);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 79, 358, 218);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(24, 34, 95, 44);
		panel.add(lblNewLabel);
		
		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEdition.setBounds(24, 88, 95, 44);
		panel.add(lblEdition);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(24, 142, 95, 44);
		panel.add(lblNewLabel_1_1);
		
		txtName = new JTextField();
		txtName.setBounds(129, 45, 219, 28);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtEdit = new JTextField();
		txtEdit.setColumns(10);
		txtEdit.setBounds(129, 99, 219, 28);
		panel.add(txtEdit);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(129, 153, 219, 28);
		panel.add(txtPrice);
		
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(10, 307, 101, 43);
		frame.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExit.setBounds(135, 307, 101, 43);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
				txtEdit.setText("");
				txtPrice.setText("");
				txtSearch.setText("");
				txtName.requestFocus();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setBounds(267, 307, 101, 43);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(403, 81, 486, 265);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 360, 350, 97);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBokkId = new JLabel("Bokk ID");
		lblBokkId.setBounds(10, 31, 81, 26);
		lblBokkId.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblBokkId);
		
		txtSearch = new JTextField();
		
		txtSearch.setBounds(123, 28, 201, 29);
		txtSearch.setColumns(10);
		panel_1.add(txtSearch);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,edition,price,id;
				
				name = txtName.getText();
				edition = txtEdit.getText();
				price = txtPrice.getText();
				id = txtSearch.getText();
				
				try {
					pst = con.prepareStatement("update book set name=?, edition=?,price=? where id=?");
					pst.setString(1, name);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record" + id +" Updated!!!!!");
					table_load();
					
					txtName.setText("");
					txtEdit.setText("");
					txtPrice.setText("");
					txtSearch.setText("");
					txtName.requestFocus();
					
				} catch (SQLException e4) {
					System.out.println(e4);
				}
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(515, 381, 101, 43);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id;
				
				id = txtSearch.getText();
			
				
				try {
					pst = con.prepareStatement("delete from book where id=?");
					pst.setString(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record" + id +" Delete!!!!!");
					table_load();
					txtName.setText("");
					txtEdit.setText("");
					txtPrice.setText("");
					txtSearch.setText("");
					txtName.requestFocus();
					
				} catch (SQLException ex) {
					System.out.println(ex);
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(665, 381, 101, 43);
		frame.getContentPane().add(btnDelete);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price;
				
				bname = txtName.getText();
				edition = txtEdit.getText();
				price = txtPrice.getText();
				
				try {
					pst = con.prepareStatement("insert into book(name, edition, price) values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!");
					table_load();
					txtName.setText("");
					txtEdit.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
					
				} catch (SQLException ex) {
					System.out.println(ex);
				}
			}
		});
		
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = txtSearch.getText();
					pst = con.prepareStatement("select name,edition,price from book where id=?");
					pst.setString(1, id);
					rs = pst.executeQuery();
					
					if(rs.next()) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtName.setText(name);
						txtEdit.setText(edition);
						txtPrice.setText(price);
						
					}
					else {
						txtName.setText("");
						txtEdit.setText("");
						txtPrice.setText("");
						
					}
					
				} catch (SQLException e3) {
					System.out.println(e3);
				}
			}
		});
		
	}
}
