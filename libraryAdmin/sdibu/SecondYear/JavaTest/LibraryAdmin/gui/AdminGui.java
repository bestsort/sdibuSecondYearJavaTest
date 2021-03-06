package sdibu.SecondYear.JavaTest.LibraryAdmin.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import sdibu.SecondYear.JavaTest.LibraryAdmin.bean.BooksInformation;
import sdibu.SecondYear.JavaTest.LibraryAdmin.bean.bookHistory;
import sdibu.SecondYear.JavaTest.LibraryAdmin.dao.BooksInformationDaoImpl;
import sdibu.SecondYear.JavaTest.LibraryAdmin.dao.dbBookHistoryFuncion;
import sdibu.SecondYear.JavaTest.LibraryAdmin.Service.FrozenUser;
import sdibu.SecondYear.JavaTest.LibraryAdmin.Service.UnFrozenUser;

public class AdminGui extends JFrame{
	Container con = this.getContentPane();
	JButton logOut = new JButton("注销用户");
	JButton changeBook = new JButton("书籍下架");
	JButton addBook = new JButton("新书上架");
	JButton frozenUser = new JButton("冻结用户");
	JButton unFrozen = new JButton("用户解冻");
	JButton seeLibrary = new JButton("查看馆藏");
	JButton adduser = new JButton("添加用户");
	
	
	
	//JButton deluser = new JButton("删除用户");
	JPanel jsp = new JPanel();
	public void AdminGuiInit() throws Exception { 
		this.setTitle("管理员");
		//this.setLayout(new GridLayout(0,2,30,30));
		this.setLocationRelativeTo(null);
		this.setResizable(false);	//不可拉伸
		
		ImageIcon backGround = new ImageIcon("sdibu//SecondYear/JavaTest/LibraryAdmin/gui/AdminMainUI.jpg");
		JLabel label = new JLabel(null,backGround,JLabel.CENTER);
		con.add(label,BorderLayout.NORTH);
		
		Component box1 = Box.createVerticalStrut(150);
		jsp.add(box1);
		jsp.add(frozenUser);
		jsp.add(unFrozen);
		jsp.add(adduser);
		jsp.add(addBook);
		jsp.add(changeBook);
		jsp.add(seeLibrary);
		jsp.add(logOut);
		//jsp2.add(deluser);
		//jsp.setLayout(new BoxLayout(jsp,BoxLayout.Y_AXIS));
		this.add(jsp);
		
		
		Image icon = Toolkit.getDefaultToolkit().getImage("sdibu//SecondYear/JavaTest/LibraryAdmin/gui/Logo.png");  // 图片的具体位置
		this.setIconImage(icon);   //设置窗口的logo
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {			
			e1.printStackTrace();
		}			//窗口皮肤
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕的长宽
		this.setBounds((width - 650) / 2,(height - 500) / 2, 650, 500);//窗体大小，居中
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public boolean giveBackAction() {
		int m = JOptionPane.showConfirmDialog(null, "确定退出？", "温馨提示", JOptionPane.YES_NO_OPTION);
		return m==0;
	}
	public void Funcion() {
		JButton sure = new JButton("确定");
		JFrame jf = new JFrame();
	/**************查看馆藏**********************/
		seeLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
				    String[]TableName = {"书籍名称","作者","出版日期","类别","书籍状态"}; 
					List<BooksInformation> BookList = new BooksInformationDaoImpl().searchAll();
					String[][] obj = new String[BookList.size()][0];
				   	int cot = 0 ;
				   	for(int i = 0 ; i<BookList.size(); i++) {
				   			/*for(String x:BookList.get(i).BooksInformationToString())
				   				System.out.print(x+" ");
				   			System.out.println("BookList.size is "+BookList.size());*/
				   			obj[cot++] = BookList.get(i).BooksInformationToString();
				   		}
				   	
				   	MyTable jTable = new MyTable(obj,TableName);
				   	//设置点击表头自动实现排序
				    jTable.setAutoCreateRowSorter(true);
				    // 设置表头文字居中显示
				    DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer();
				    renderer.setHorizontalAlignment(renderer.CENTER);
				
				    // 设置表格中的数据居中显示
				    DefaultTableCellRenderer r=new DefaultTableCellRenderer();
				    r.setHorizontalAlignment(JLabel.CENTER);
				    jTable.setDefaultRenderer(Object.class,r);
				
				    jTable.setFocusable(true);
				    jTable.setFont(new Font("新宋体", Font.PLAIN, 18));
				    JScrollPane jsp = new JScrollPane(jTable);
				    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
				    int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取屏幕的长宽
					jf.setBounds((width - 1000) / 2,(height - 1000) / 2, 1000, 1000);//窗体大小，居中
					jf.setVisible(true);
					jTable.setRowHeight(50);
					
					jf.add(jsp,BorderLayout.CENTER);
					jf.add(sure,BorderLayout.AFTER_LAST_LINE);
					
					sure.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							jf.dispose();
						}
					});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(giveBackAction())
					dispose();
			}
		});
/******************增加书籍**********************/
		addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addBookInformationGUI addBook = new addBookInformationGUI();
				
			}
		});
/******************下架书籍**********************/
		changeBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String inputValue = JOptionPane.showInputDialog("请输入要下架的书籍ID:");
				try {
					BooksInformation bookList = new BooksInformationDaoImpl().searchById(inputValue);
					if(inputValue == "") {
					
					}
					else if(bookList.getBookName()==null) {
						JOptionPane.showMessageDialog(null, "查无此书", "警告", JOptionPane.ERROR_MESSAGE);
					}
					else {
												
						dbBookHistoryFuncion dbHistory = new dbBookHistoryFuncion();
						BooksInformationDaoImpl bookInf = new BooksInformationDaoImpl();
						dbHistory.deleteBookHistory(inputValue);
						bookInf.deleteInformation(inputValue);
						JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		/******************冻结账号**********************/
		frozenUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id = JOptionPane.showInputDialog(null,"请输入账号：\n","冻结用户",
						JOptionPane.PLAIN_MESSAGE);
				try {
					FrozenUser frozenUser = new FrozenUser(id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		/******************解冻账号**********************/
		unFrozen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog(null,"请输入账号：\n","用户解冻",
						JOptionPane.PLAIN_MESSAGE);
				try {
					UnFrozenUser unfro = new UnFrozenUser(id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		/******************添加用户*************/
		adduser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addUserGUI add = new addUserGUI();
			}
			
		});
	}
	public static void main(String[] args) throws Exception {
		AdminGui xx = new AdminGui();
		xx.Funcion();
		xx.AdminGuiInit();
		
     }
}
