package ec.edu.uce.calculabilidad.clases;

import javax.swing.ImageIcon;

/**
 * PROYECTO: Face x
 *
 * Clase de tipo Jframe que será la pantalla principal de neustro sistema
 * FECHA:15-02-2018
 *
 *
 * @author: 1312960444 Michael Ponce Cevallos, 1723465066 Jairo Mena 1726581141
 * Marlon Oña
 * @version 1.0.0
 */
public class AcercaDe extends javax.swing.JDialog {

    public AcercaDe(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);// me permite ubciar el jframe en el centro de la pantalla

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(470, 630));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Algerian", 0, 18)); // NOI18N
        jLabel1.setText("Face X");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(200, 20, 90, 24);

        jLabel5.setText("face x is a system that basically allows us");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(60, 360, 200, 20);

        jLabel6.setText("to recognize the face of a person");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(130, 380, 270, 20);

        jLabel3.setText(" which has been previously registered.");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(170, 400, 250, 20);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 3, 13)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Product Version: Face x\nJava version: 1.8.0\nMembers: Michael, Marlon, Jairo.");
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(60, 440, 350, 113);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/uce/calculabilidad/imagenes/acerc.jpg"))); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(60, 50, 360, 300);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 470, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
