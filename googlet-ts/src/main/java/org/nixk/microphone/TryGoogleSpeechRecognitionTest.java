package main.java.org.nixk.microphone;

import net.sourceforge.javaflacencoder.FLACFileWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description ：
 * Created by  xianguang.skx
 * Since 2020/5/22
 */

public class TryGoogleSpeechRecognitionTest {

    public static void main(String[] args) throws IOException {
        final Microphone mic = new Microphone(FLACFileWriter.FLAC);


        JFrame frame = new JFrame("Jarvis Speech API DEMO");
        frame.setDefaultCloseOperation(3);
        JTextArea response = new JTextArea();
        response.setEditable(false);
        response.setWrapStyleWord(true);
        response.setLineWrap(true);

        final JButton record = new JButton("Record");
        final JButton stop = new JButton("Stop");
        stop.setEnabled(false);

        record.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new Thread(() -> {
                    try {
                        recognize(mic.getTargetDataLine(),mic.getAudioFormat());
                        } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }).start();
                record.setEnabled(false);
                stop.setEnabled(true);
            }
        });
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mic.close();
                 record.setEnabled(true);
                stop.setEnabled(false);
            }
        });
        JLabel infoText = new JLabel(
                "<html><div style=\"text-align: center;\">Just hit record and watch your voice be translated into text.\n<br>Only English is supported by this demo, but the full API supports dozens of languages.<center></html>",

                0);
        frame.getContentPane().add(infoText);
        infoText.setAlignmentX(0.5F);
        JScrollPane scroll = new JScrollPane(response);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), 1));
        frame.getContentPane().add(scroll);
        JPanel recordBar = new JPanel();
        frame.getContentPane().add(recordBar);
        recordBar.setLayout(new BoxLayout(recordBar, 0));
        recordBar.add(record);
        recordBar.add(stop);
        frame.setVisible(true);
        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

    }


    public  static  void recognize(TargetDataLine tl, AudioFormat af) throws LineUnavailableException, InterruptedException, IOException {

        OutputStream out =  new FileOutputStream(new File("/Users/xianguang/temp/record.wav"));
        AudioInputStream ais = new AudioInputStream(tl);
        AudioSystem.write(ais, FLACFileWriter.FLAC, out);

    }



}
