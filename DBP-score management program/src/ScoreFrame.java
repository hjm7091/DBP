import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
class ScoreFrame extends JFrame implements ActionListener, MouseListener{
	DatabaseManager databaseManager;
	Score score;
	
	JLabel lbName, lbKor, lbEng, lbMath;
	JTextField tfName, tfKor, tfEng, tfMath;
	JButton jbAdd, jbDel, jbChange;
	JTable table;
	Vector<String> col;
	
	ScoreFrame(){
		databaseManager = new DatabaseManager();
		setLayout(null);
		
		add(lbName = new JLabel("�̸�", JLabel.CENTER));
		lbName.setBorder(BorderFactory.createBevelBorder(0));
		lbName.setBounds(10, 10, 120, 50);
		add(tfName = new JTextField());
		tfName.setHorizontalAlignment(JTextField.CENTER);
		tfName.setBounds(140, 10, 120, 50);
		
		add(lbKor = new JLabel("���� ����", JLabel.CENTER));
		lbKor.setBorder(BorderFactory.createBevelBorder(0));
		lbKor.setBounds(10, 70, 120, 50);
		add(tfKor = new JTextField());
		tfKor.setHorizontalAlignment(JTextField.CENTER);
		tfKor.setBounds(140, 70, 120, 50);
		
		add(lbEng = new JLabel("���� ����", JLabel.CENTER));
		lbEng.setBorder(BorderFactory.createBevelBorder(0));
		lbEng.setBounds(10, 130, 120, 50);
		add(tfEng = new JTextField());
		tfEng.setHorizontalAlignment(JTextField.CENTER);
		tfEng.setBounds(140, 130, 120, 50);
		
		add(lbMath = new JLabel("���� ����", JLabel.CENTER));
		lbMath.setBorder(BorderFactory.createBevelBorder(0));
		lbMath.setBounds(10, 190, 120, 50);
		add(tfMath = new JTextField());
		tfMath.setHorizontalAlignment(JTextField.CENTER);
		tfMath.setBounds(140, 190, 120, 50);
		
		add(jbAdd = new JButton("�߰�"));
		jbAdd.setBounds(270, 10, 120, 50);
		jbAdd.addActionListener(this);
		add(jbDel = new JButton("����"));
		jbDel.setBounds(270, 70, 120, 50);
		jbDel.addActionListener(this);
		add(jbChange = new JButton("����"));
		jbChange.setBounds(270, 130, 120, 50);
		jbChange.addActionListener(this);
		
		col = new Vector<String>();
		col.add("���");
		col.add("�̸�");
		col.add("���� ����");
		col.add("���� ����");
		col.add("���� ����");
		col.add("����");
		col.add("���");
		
		DefaultTableModel model = new DefaultTableModel(databaseManager.getScore(), col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		table.addMouseListener(this);
		JScrollPane scroll = new JScrollPane(table);
		jTableSet();
		add(scroll);
		scroll.setBounds(415, 10, 770, 250);
		
		setResizable(false);
		setSize(1200, 300);
		setTitle("���� ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ButtonFlag = e.getActionCommand();
		Score score = new Score();
		
		if(ButtonFlag.equals("�߰�")) {
			try {
				contentSet(score);
				int result = databaseManager.insertScore(score);
				
				if(result == 1) {
					JOptionPane.showMessageDialog(this, "�߰� �Ǿ����ϴ�.");
					jTableRefresh();
					contentClear();
				}else {
					JOptionPane.showMessageDialog(this, "�߰��� �����߽��ϴ�.");
				}
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(this, "�̸� Ȥ�� ������ �Է��ϼ���!");
			}
		}
		else if(ButtonFlag.equals("����")) {
			try {
				contentSet2(score);
				int result = databaseManager.DeleteScore(score);
				
				if(result == 1) {
					JOptionPane.showMessageDialog(this, "���� �Ǿ����ϴ�.");
					jTableRefresh();
					contentClear();
				}else {
					JOptionPane.showMessageDialog(this, "������ �����߽��ϴ�.");
				}
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(this, "���̺� �����ϴ� �̸��� �Է��ϼ���!");
			}
		}
		else if(ButtonFlag.equals("����")) {
			try {
				contentSet(score);
				int result = databaseManager.UpdateScore(score);
				
				if(result == 1) {
					JOptionPane.showMessageDialog(this, "���� �Ǿ����ϴ�.");
					jTableRefresh();
					contentClear();
				}else {
					JOptionPane.showMessageDialog(this, "������ �����߽��ϴ�.");
				}
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(this, "�̸� Ȥ�� ������ �Է��ϼ���!");
			}
		}
	}
	
	public void jTableSet() {
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i=0; i<table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(10);
			table.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
		}
	}
	
	public void contentClear() {
		tfName.setText("");
		tfKor.setText("");
		tfEng.setText("");
		tfMath.setText("");
	}
	
	public void contentSet(Score score) { //����,������ ���� �޼ҵ�
		String name = tfName.getText();
		int kor = Integer.parseInt(tfKor.getText());
		int eng = Integer.parseInt(tfEng.getText());
		int math = Integer.parseInt(tfMath.getText());
		int total = kor + eng + math;
		int average = total / 3;
		
		score.setName(name);
		score.setKor(kor);
		score.setEng(eng);
		score.setMath(math);
		score.setTotal(total);
		score.setAverage(average);
	}
	
	public void contentSet2(Score score) { //������ ���� �޼ҵ�
		String name = tfName.getText();
		score.setName(name);
	}
	
	public void jTableRefresh() {
		DefaultTableModel model = new DefaultTableModel(databaseManager.getScore(), col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		jTableSet();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int rowIndex = table.getSelectedRow();
		tfName.setText(table.getValueAt(rowIndex, 1)+"");
		tfKor.setText(table.getValueAt(rowIndex, 2)+"");
		tfEng.setText(table.getValueAt(rowIndex, 3)+"");
		tfMath.setText(table.getValueAt(rowIndex, 4)+"");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
