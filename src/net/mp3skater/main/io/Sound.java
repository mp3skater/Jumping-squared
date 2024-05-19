package net.mp3skater.main.io;

import java.io.ByteArrayInputStream;
import java.net.URL;

import javax.sound.sampled.*;

public class Sound {
        Clip clip;
        URL[] soundURL = new URL[30];
        FloatControl fc;
        public int volumeScale = 3;
        float volume;

        public Sound() {
            soundURL[0] = getClass().getResource("/sound/bgmusic1_leise.wav");
            soundURL[1] = getClass().getResource("/sound/bgmusic2_leise.wav");
            soundURL[2] = getClass().getResource("/sound/bgmusic3_leise.wav");
            soundURL[3] = getClass().getResource("/sound/gameStart.wav");
            soundURL[4] = getClass().getResource("/sound/jump.wav");
            soundURL[5] = getClass().getResource("/sound/lose.wav");
            soundURL[6] = getClass().getResource("/sound/fanfare.wav");
            soundURL[7] = getClass().getResource("/sound/placing.wav");
            soundURL[8] = getClass().getResource("/sound/menuChange.wav");
            soundURL[9] = getClass().getResource("/sound/enter.wav");
        }

        public void setFile(int i) {

            try {
                //Clip clip = AudioSystem.getClip();
                //ByteArrayInputStream audioBytes = new ByteArrayInputStream(SOUNDS.get(file));
                //AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioBytes);
                //clip.open(inputStream);
                //clip.start();

                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                Clip finalClip = clip;
                clip.addLineListener(event -> {
                    if(LineEvent.Type.STOP.equals(event.getType())) {
                        finalClip.close();
                    }
                });
                clip.open(ais);
                fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void play() {
            clip.start();
        }

        public void loop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        public void stop() {
            if(clip != null)
                clip.stop();
        }
        public void checkVolume(){

            switch (volumeScale){
                case 0: volume = -80f; break;
                case 1: volume = -23f; break;
                case 2: volume = -12f; break;
                case 3: volume = -5f; break;
                case 4: volume = 1f; break;
                default: volume = 6f; break;
            }
            fc.setValue(volume);
        }
    }


