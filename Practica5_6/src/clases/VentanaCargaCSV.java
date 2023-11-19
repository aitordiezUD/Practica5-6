package clases;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaCargaCSV extends JFrame{
		
	public static JTextArea textArea;
    private JFileChooser fileChooser;
    private Properties properties;
    private JLabel lblRuta;
    private static JProgressBar pbCarga;
    private String ruta = null;
    private static JButton btnCargar;
    private static JButton btnArchivo;
    private static JButton btnTabla;
	
	public JProgressBar getPbCarga() {
		return pbCarga;
	}

	public VentanaCargaCSV() {
		this.setSize(650,650);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		fileChooser = new JFileChooser();
		// Crear un filtro para archivos CSV
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filter);
        
        properties = new Properties();
        
        
		
		JPanel pnlSuperior = new JPanel();
		pnlSuperior.setPreferredSize(new Dimension(100, 100));
		getContentPane().add(pnlSuperior, BorderLayout.NORTH);
		pnlSuperior.setLayout(new BorderLayout());
		
		JPanel pnlChooser = new JPanel();
		pnlChooser.setPreferredSize(new Dimension(50, 50));
		pnlSuperior.add(pnlChooser, BorderLayout.NORTH);
		pnlChooser.setLayout(new BorderLayout());
		
		lblRuta = new JLabel("No has seleccionado ningun archivo");
		lblRuta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuta.setRequestFocusEnabled(false);
		pnlChooser.add(lblRuta);
		
		loadLastSelectedFile();
		
		JPanel pnlChooserTop = new JPanel();
		pnlChooser.add(pnlChooserTop,BorderLayout.NORTH);
		
		btnArchivo = new JButton("Seleccionar ruta");

		btnArchivo.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
//            	try {
//            		loadLastSelectedFile();
//				} catch (Exception e2) {
//					// TODO: handle exception
//				}
                int result = fileChooser.showOpenDialog(VentanaCargaCSV.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    lblRuta.setText(selectedFile.getAbsolutePath());
                    ruta = selectedFile.getAbsolutePath();
                    saveLastSelectedFile(selectedFile);
                }
            }
        });
		pnlChooserTop.add(btnArchivo);
		
		JLabel lblRutaSel = new JLabel("Ruta seleccionada:");
		lblRutaSel.setPreferredSize(new Dimension(140, 18));
		lblRutaSel.setMaximumSize(new Dimension(110, 20));
		pnlChooserTop.add(lblRutaSel);
		
		JPanel pnlCargar = new JPanel();
		pnlCargar.setPreferredSize(new Dimension(50, 50));
		pnlSuperior.add(pnlCargar, BorderLayout.SOUTH);
		
		btnCargar = new JButton("Cargar CSV");
		btnCargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (ruta != null) {
					HiloProcessCSV h = new HiloProcessCSV(ruta);
					(new Thread(h)).start();
				}else {
					JOptionPane.showMessageDialog(null, "No hay ningún archivo seleccionado");
				}
			}
			
		});
		pnlCargar.add(btnCargar);
		
		pbCarga = new JProgressBar();
		pnlCargar.add(pbCarga);
		
		JPanel pnlInferior = new JPanel();
		pnlInferior.setPreferredSize(new Dimension(50, 50));
		btnTabla = new JButton("Tabla");
		btnTabla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(GestionTwitter.getSetOrdenado());
				VentanaCargaCSV.this.dispose();
				new VentanaTabla();
			}
		});
		pnlInferior.setLayout(new FlowLayout());
		pnlInferior.add(btnTabla);
		
		getContentPane().add(pnlInferior, BorderLayout.SOUTH);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		JPanel pnlMargenIzq = new JPanel();
		pnlMargenIzq.setPreferredSize(new Dimension(50, 50));
		getContentPane().add(pnlMargenIzq, BorderLayout.WEST);
		
		JPanel pnlMargenDer = new JPanel();
		pnlMargenDer.setPreferredSize(new Dimension(50, 50));
		getContentPane().add(pnlMargenDer, BorderLayout.EAST);
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaCargaCSV();
	}
	

    private void loadLastSelectedFile() {
        try {
        	InputStream input = new FileInputStream("lastFile.properties");
            properties.load(input);
            String lastFilePath = properties.getProperty("path");
            fileChooser.setCurrentDirectory(new File(lastFilePath));
            ruta = properties.getProperty("path");
            lblRuta.setText(ruta);
            
        } catch (IOException e) {
            lblRuta.setText("No has seleccionado ningun archivo");
            ruta = null;
        }
    }

    private void saveLastSelectedFile(File file) {
        properties.setProperty("path", file.getAbsolutePath());
        try {
        	OutputStream output = new FileOutputStream("lastFile.properties");
            properties.store(output, "Nueva ruta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void setMaxPB(int value) {
    	pbCarga.setMaximum(value);
    }
    
	public static void incPB() {
		pbCarga.setValue(pbCarga.getValue()+1);
	}
	
	public static void printInTA(String str) {
		textArea.append(str + "\n");
	}
	
	
	private class HiloProcessCSV implements Runnable{
		
		String ruta;
		
		public HiloProcessCSV(String ruta) {
			this.ruta = ruta;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			printInTA("Dentro del hilo");
			
			try {
				btnArchivo.setEnabled(false);
				btnCargar.setEnabled(false);
				btnTabla.setEnabled(false);
				File file = new File(ruta);
				setMaxPB(CSV.countCSVLines(file)*2);
				printInTA("Lineas contadas, empezando a procesar el archivo.");
				CSV.processCSV(file);
				printInTA("Archivo procesado, creando el resto de estructuras de datos");
				GestionTwitter.crearMapaUsuariosUsernameYSetOrdenadoVentana();
				printInTA("Todos las estructuras de datos creadas. Fichero cargado completamente.");
				GestionTwitter.imprimirUsuariosConAmigosEnSistemaVentana();
				GestionTwitter.imprimirSetOrdenadoVentana();
				btnArchivo.setEnabled(true);
				btnCargar.setEnabled(true);
				btnTabla.setEnabled(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
}


