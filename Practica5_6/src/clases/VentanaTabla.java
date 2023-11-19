package clases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class VentanaTabla extends JFrame{
	private static final int COL_ID = 0;
	

	private static String filtro = "";
	
	private JPanel pnlArbol;
	private JPanel pnlCentral;
	private JPanel pnlFiltro;
	private JPanel pnlTabla;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JTree arbol;
	private DefaultTreeModel modeloArbol;
	private JTextField tfFiltro;
	private String tag = "";
	
	public VentanaTabla() {
		setSize(1200,650);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel pnlContenido = new JPanel();
		pnlContenido.setLayout(new BorderLayout());
		setContentPane(pnlContenido);
		
		
		pnlArbol = new JPanel();
		pnlArbol.setLayout(new BorderLayout());
		pnlArbol.setPreferredSize(new Dimension(300,650));
		pnlArbol.setBackground(Color.PINK);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Twitter");
		modeloArbol = new DefaultTreeModel(root);
		arbol = new JTree(modeloArbol);
		JScrollPane spArbol = new JScrollPane(arbol);
		pnlArbol.add(spArbol);
		pnlContenido.add(pnlArbol,BorderLayout.WEST);
		
		arbol.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				TreePath path = e.getPath();
				if (path.getPathCount()>1) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					
					anadirAmigosArbol(node);
				}

				
			}
		});
		
		pnlCentral = new JPanel();
		pnlCentral.setLayout(new BorderLayout());
		pnlContenido.add(pnlCentral);
		
		pnlTabla = new JPanel();
		pnlTabla.setBackground(Color.MAGENTA);
		pnlTabla.setLayout(new BorderLayout());
		modeloTabla = new DefaultTableModel((new String[] {"id","screeName","followersCount","friendsCount","lang","lastSeen"}),0); 
		tabla = new JTable(modeloTabla);
		pnlTabla.add(new JScrollPane(tabla));
		initTabla();
		pnlCentral.add(pnlTabla);
		
		pnlFiltro = new JPanel();
		pnlFiltro.setPreferredSize(new Dimension(800,50));
		pnlFiltro.setLayout(new FlowLayout());
		tfFiltro = new JTextField();
		tfFiltro.setPreferredSize(new Dimension(100,25));
		tfFiltro.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTag();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTag();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				updateTag();
			}
		});
		pnlFiltro.add(tfFiltro);
		pnlCentral.add(pnlFiltro,BorderLayout.SOUTH);
		
		
		
		setVisible(true);
	}
	
	public void initTabla() {
		for (UsuarioTwitter usuario : GestionTwitter.getSetOrdenado()) {
			Object[] fila = new Object[] {usuario.getId(),usuario.getScreenName(),usuario.getFollowersCount(),
					usuario.getFriendsCount(),usuario.getLang(),usuario.getLastSeen()};
			modeloTabla.addRow(fila);
		}
		
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ListSelectionModel selectionModel = tabla.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				modelarArbol(GestionTwitter.getMapaUsuariosId().get(tabla.getValueAt(tabla.getSelectedRow(), COL_ID)));
			}
		});
		
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				UsuarioTwitter usuario = GestionTwitter.getMapaUsuariosId().get(tabla.getValueAt(row, COL_ID));
				comp.setBackground(Color.WHITE);

				if (usuario.getTags().contains(tag)) {
					comp.setBackground(Color.GREEN);
				}
				
				
				
				return comp;
			}
			
			
		});
	}
	
	public void updateTag() {
		tag = tfFiltro.getText();
		tabla.repaint();
	}
	
	private void modelarArbol(UsuarioTwitter usuario) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(usuario.getScreenName());
		DefaultTreeModel modelo = new DefaultTreeModel(root);
		int cont = 0;
		for (UsuarioTwitter u : GestionTwitter.getMapaAmigos().get(usuario)) {
			crearNodo(u.getScreenName(), root, cont);
			cont++;
		}
		
		arbol.setModel(modelo);
		arbol.repaint();
	}
	
	
	
	private DefaultMutableTreeNode crearNodo( Object dato, DefaultMutableTreeNode nodoPadre, int posi ) {
		DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode( dato );
		DefaultTreeModel modelo = (DefaultTreeModel) arbol.getModel();
		modelo.insertNodeInto( nodo1, nodoPadre, posi ); 
		return nodo1;
	}
	
	private void anadirAmigosArbol(DefaultMutableTreeNode nodo) {
		String username = nodo.toString();
		UsuarioTwitter usuario = GestionTwitter.getMapaUsuariosUsername().get(username);
		int cont = 0;
		for (UsuarioTwitter u : GestionTwitter.getMapaAmigos().get(usuario)) {
			crearNodo(u.getScreenName(),nodo,cont);
			cont++;
		}
	}
	
}
