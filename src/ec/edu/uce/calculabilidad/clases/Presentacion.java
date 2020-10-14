package ec.edu.uce.calculabilidad.clases;

import java.applet.AudioClip;
import javax.swing.ImageIcon;
import org.opencv.core.Core;

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
public class Presentacion extends javax.swing.JFrame {

    /**
     * Constructor de la Claes Presentación
     */
    static AudioClip sonido;

    public Presentacion() {
        initComponents();
        setLocationRelativeTo(null);

        setResizable(false); //No permito que cambien de tamo la ventana

        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/Musica/Music.wav"));//me permite enviar un audio a mi splash
        sonido.loop(); 

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        trans = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(332, 325));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Chiller", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FACE X");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(120, 30, 160, 90);

        trans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/uce/calculabilidad/imagenes/black70 copia.png"))); // NOI18N
        getContentPane().add(trans);
        trans.setBounds(0, 0, 330, 300);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/uce/calculabilidad/imagenes/acerc.jpg"))); // NOI18N
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 340, 300);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        for (int segundos = 100; segundos >= 0; segundos--) {
            float a = segundos * 0.01F;
            try {
                //Devuelve una accion cada segundo
                Thread.sleep(100);
                this.setOpacity(a);
            } catch (InterruptedException e) {
            }
        }
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // load native library of opencv
        new Gui().setVisible(true);
        this.setVisible(false);
        //si ya no es visible el splash detengo la música, opcional
        if (!isVisible()) {

            sonido.stop();
        }
        this.dispose();


    }//GEN-LAST:event_formComponentShown
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel trans;
    // End of variables declaration//GEN-END:variables
}
