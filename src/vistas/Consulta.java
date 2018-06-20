/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

/**
 *
 * @author vic_1
 */
import dao.FiltroDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import modelo.Filtro;
public class Consulta extends JFrame {
 public JLabel lblCodigo, lblMarca,lblStock,lblExistencia;
 public JTextField codigo,descripcion,stock;
 public JComboBox marca;
 
 ButtonGroup existencia = new ButtonGroup();
 public JRadioButton no;
 public JRadioButton si;
 public JTable resultados;
 public JPanel table;
 
 public JButton buscar,eliminar,insertar,limpiar,actualizar;
 private static final int ANCHOC = 130,ALTOC = 30;
 DefaultTableModel tm;
 public Consulta(){
     super("Inventario");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLayout(null);
     agregarLabels();
     formulario();
     llenarTabla();
     Container container = getContentPane();
     container.add(lblCodigo);
     container.add(lblMarca);
     container.add(lblStock);
     container.add(lblExistencia);
     container.add(codigo);
     container.add(marca);
     container.add(stock);
     container.add(si);
     container.add(no);
     container.add(buscar);
     container.add(insertar);
     container.add(actualizar);
     container.add(eliminar);
     container.add(limpiar);
     container.add(table);
     setSize(600,600);
     eventos();
     
 }
 public final void agregarLabels(){
     lblCodigo=new JLabel("codigo");
     lblMarca=new JLabel("marca");
     lblStock=new JLabel("stock");
     lblExistencia=new JLabel("existencia");
     lblCodigo.setBounds(10,10,ANCHOC,ALTOC);
     lblMarca.setBounds(10,60,ANCHOC,ALTOC);
     lblStock.setBounds(10,100,ANCHOC,ALTOC);
     lblExistencia.setBounds(10,140,ANCHOC,ALTOC);
 }
 public final void formulario(){
     codigo = new JTextField();
     marca = new JComboBox();
     stock = new JTextField();
     si = new JRadioButton("si",true);
     si = new JRadioButton("no");
     resultados = new JTable();
     buscar =new JButton("Buscar");
     insertar = new JButton("insertar");
     eliminar = new JButton("eliminar");
     actualizar =new JButton("actualizar");
     limpiar = new JButton("limpiar");
     table  = new JPanel();
     marca.addItem("FRAM");
     marca.addItem("WIS");
     marca.addItem("Luber Finer");
     marca.addItem("OSK");
     
     existencia = new ButtonGroup();
     existencia.add(si);
     existencia.add(no);
     
     codigo.setBounds(140,10,ANCHOC,ALTOC);
     marca.setBounds(140,60,ANCHOC,ALTOC);
     stock.setBounds(140,100,ANCHOC,ALTOC);
     si.setBounds(140,140,50,ALTOC);
     no.setBounds(210,140,50,ALTOC);
     
     buscar.setBounds(300,10,ANCHOC,ALTOC);
     insertar.setBounds(10,210,ANCHOC,ALTOC);
     actualizar.setBounds(150,210,ANCHOC,ALTOC);
     eliminar.setBounds(300,210,ANCHOC,ALTOC);
     limpiar.setBounds(450,210,ANCHOC,ALTOC);
     resultados = new JTable();
     table.setBounds(10, 250, 500, 200);
     table.add(new JScrollPane(resultados));
     
 }
 public void llenarTabla(){
     tm = new DefaultTableModel(){
         public Class<?> getColumnClass(int column){
             switch (column){
                 case 0:
                     return String.class;
                 case 1:
                     return String.class;
                 case 2:
                     return String.class;
                 default:
                     return Boolean.class;
             }
         }
     };
     tm.addColumn("Codigo");
     tm.addColumn("Marca");
     tm.addColumn("Stock");
     tm.addColumn("Stock Sucursal");
     
     
     FiltroDao fd = new FiltroDao();
     ArrayList<Filtro> filtros = fd.readAll();
     
     for(Filtro fi : filtros){
         tm.addRow(new Object[]{fi.getCodigo(),fi.getMarca(),fi.getStock(),fi.isExistencia()
             
         });
     }
     resultados.setModel(tm);
 }
 public void eventos(){
     insertar.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         FiltroDao fd = new FiltroDao();
         Filtro f = new Filtro(codigo.getText(),marca.getSelectedItem().toString(),Integer.parseInt(stock.getText()),true);
     if (no.isSelected()){
         f.setExistencia(false);
     }
     if(fd.create(f)){
         JOptionPane.showMessageDialog(null, "Filtro registrado con exito");
         limpiarCampos();
         llenarTabla();
     }else{
         JOptionPane.showMessageDialog(null, "ocurrio un problema");
         
     }
     }        
         });
    actualizar.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         FiltroDao fd = new FiltroDao();
         Filtro f = new Filtro(codigo.getText(),marca.getSelectedItem().toString(),Integer.parseInt(stock.getText()),true);
     if (no.isSelected()){
         f.setExistencia(false);
     }
     if(fd.update(f)){
         JOptionPane.showMessageDialog(null, "Filtro modificado con exito");
         limpiarCampos();
         llenarTabla();
     }else{
         JOptionPane.showMessageDialog(null, "ocurrio un problema");
         
     }
     }        
         });
    eliminar.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         FiltroDao fd = new FiltroDao();
         
     if(fd.delete(codigo.getText())){
         JOptionPane.showMessageDialog(null, "Filtro eliminado con exito");
         limpiarCampos();
         llenarTabla();
     }else{
         JOptionPane.showMessageDialog(null, "ocurrio un problema");
         
     }
     }        
         });
    buscar.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         FiltroDao fd = new FiltroDao();
         Filtro f = new Filtro(codigo.getText(),marca.getSelectedItem().toString(),Integer.parseInt(stock.getText()),true);
     if (f == null){
         JOptionPane.showMessageDialog(null, "Filtro buscado no se ha encontrado");
         
     }
     else{
         codigo.setText(f.getCodigo());
         marca.setSelectedItem(f.getMarca());
         stock.setText(Integer.toString(f.getStock()));
     }
     if(f.isExistencia()){
         si.setSelected(true);
     }else{
         no.setSelected(true);
     }
     }        
         });
    limpiar.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
         limpiarCampos();
     }        
         });
    

    
    
    
 }
 public void limpiarCampos(){
        codigo.setText("");
        marca.setSelectedItem("FRAM");
        stock.setText("");
    }
 public static void main(String[] args) {
      
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new Consulta().setVisible(true);
            }
        });
    }
}
