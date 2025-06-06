package view;

import constant.Piece;
import controller.Constant;
import controller.Notification;
import controller.GameController;
import file.IOFile;
import image.NewImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Home extends JFrame {
    private final JPanel menu;
    private final JButton newGame;
    private final JButton oldGame;
    private final JButton quit;
    private final JButton setting;
    private final JButton volume;
    private final JButton backHome;
    private final JButton time;
    private final JSlider slider_1;
    private final JSlider slider_2;
    private final JLabel label_1;
    private final JLabel label_2;
    private final JButton fifteen;
    private final JButton twelve;
    private final JButton ten;
    private final JButton levalButton;
    private final JButton hardButton;
    private final JButton easyButton;
    private int level;

    public Home() {
        setAlwaysOnTop(false);
        new Thread(() -> {
            GameController.getSoundEffect().playBackgroundMusic();
            GameController.setup();
            GameController.setLevel(IOFile.getLevel());
            GameController.setMinute(IOFile.getTime().firstElement());
            GameController.setSecond(IOFile.getTime().lastElement());
            GameController.setSecond(0);
        }).start();

        ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/resource/image/logo.jpg");
        Image temp = logo.getImage();
        temp = temp.getScaledInstance(210, 280, Image.SCALE_SMOOTH);
        logo = new ImageIcon(temp);

        this.setUndecorated(true);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.fifteen = new JButton();
        this.twelve = new JButton();
        this.ten = new JButton();
        this.slider_1 = new JSlider();
        this.slider_2 = new JSlider();
        this.label_1 = new JLabel();
        this.label_2 = new JLabel();
        this.slider_1.setOpaque(false);
        this.slider_2.setOpaque(false);
        this.slider_1.setFocusable(false);
        this.slider_2.setFocusable(false);
        this.slider_1.setUI(new GameController.CustomSliderUI(this.slider_1));
        this.slider_2.setUI(new GameController.CustomSliderUI(this.slider_2));
        this.label_1.setFont(new Font("Arial", Font.BOLD, 15));
        this.label_2.setFont(new Font("Arial", Font.BOLD, 15));

        this.slider_1.setVisible(false);
        this.label_1.setVisible(false);
        this.slider_2.setVisible(false);
        this.label_2.setVisible(false);

        this.setting = new JButton();
        this.setting.setName("setting");
        this.newGame = new JButton();
        this.newGame.setName("newGame");
        this.oldGame = new JButton();
        this.oldGame.setName("oldGame");
        this.quit = new JButton();
        this.quit.setName("quit");
        this.backHome = new JButton();
        this.volume = new JButton();
        this.time = new JButton();
        this.levalButton = new JButton();
        this.hardButton = new JButton();
        this.easyButton = new JButton();
        this.easyButton.setVisible(false);
        this.hardButton.setVisible(false);
        this.levalButton.setVisible(false);

        this.menu = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/home.png");
                g.drawImage(imageIcon.getImage(), 0, 0, 500, 700, this);
            }
        };

        this.menu.add(this.slider_1);
        this.menu.add(this.slider_2);
        this.menu.add(this.label_1);
        this.menu.add(this.label_2);

        this.menu.setLayout(null);
        this.menu.setSize(500, 700);

        this.setSettingHome();
        this.setButton();
        GameController.getCloseButton().setClose(this.menu);
        GameController.getCloseButton().setHide(this.menu);

        this.menu.add(this.newGame);
        this.menu.add(this.oldGame);
        this.menu.add(this.setting);
        this.menu.add(this.quit);

        JLabel label = new JLabel();
        label.setText("Cờ Tướng");
        label.setFont((new Font(Font.SANS_SERIF, Font.PLAIN, 30)));
        this.add(GameController.getChessBoardPanel());
        GameController.getChessBoardPanel().setVisible(false);
        this.menu.add(label);
        this.add(this.menu);
        this.setVisible(true);
        this.setEvent();
        this.level = 0;
    }

    private void addHoverEffect(JButton button) {
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/" + button.getName() + ".png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/" + button.getName() + ".png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 100, 36);
        hoverIcon = new NewImage().resizeImage(hoverIcon, 100, 36);

        button.setIcon(defaultIcon);
        button.setRolloverIcon(hoverIcon);
    }


    private void setOption(JButton button) {
        this.addHoverEffect(button);
        button.setContentAreaFilled(false);
        if (button.getName().equals("newGame"))
            button.setBounds(200, 350, 100, 36);
        if (!IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/old.txt")) {
            if (button.getName().equals("oldGame")) {
                button.setBounds(200, 400, 100, 36);
                button.setVisible(true);
            }
            if (button.getName().equals("setting"))
                button.setBounds(200, 450, 100, 36);
            if (button.getName().equals("quit"))
                button.setBounds(200, 500, 100, 36);
        } else {
            if (button.getName().equals("oldGame"))
                button.setVisible(false);
            if (button.getName().equals("setting"))
                button.setBounds(200, 400, 100, 36);
            if (button.getName().equals("quit"))
                button.setBounds(200, 450, 100, 36);
        }
        button.setBorderPainted(false);
    }

    private void configButton(JButton button, String name, int x, int y) {
        button.setSize(100, 36);
        button.setName(name);
        button.setLocation(x, y);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setVisible(false);
        this.addHoverEffect(button);
    }


    public void setButton() {
        this.setOption(this.newGame);
        this.setOption(this.oldGame);
        this.setOption(this.setting);
        this.setOption(this.quit);
    }

    private void setNewGame() {
        this.newGame.addActionListener(e -> {
            Notification notification = new Notification();
            notification.Loading("Vui lòng đợi...");
            new Thread(() -> {
                this.setSize(Piece._width_, Piece._height_);
                this.setLocationRelativeTo(null);
                JButton start = new JButton();

                GameController.setNew(start);
                this.menu.setVisible(false);
                GameController.getSetting().setChessBoard();
                GameController.getChessBoardPanel().goHome(this);
                notification.setVisible(false);
            }).start();
        });
    }

    private void setOldGame() {
        this.oldGame.addActionListener(_ -> new Thread(() -> {
            this.menu.setVisible(false);
            this.setSize(Piece._width_, Piece._height_);
            this.setLocationRelativeTo(null);
            IOFile.readGame();
            JButton start = new JButton();

            GameController.setNewSetting();
            GameController.getSetting().setChessBoard();
            GameController.getChessBoardPanel().setButton(start);
            GameController.setEvent();
            GameController.getChessBoardPanel().setVisible(true);
            GameController.getChessBoardPanel().goHome(this);
        }).start());
    }

    private void setQuit() {
        this.quit.addActionListener(e -> GameController.confirmQuit(this));
    }

    private void setClickSetting() {
        this.setting.addActionListener(e -> {
            level++;
            newGame.setVisible(false);
            oldGame.setVisible(false);
            setting.setVisible(false);
            quit.setVisible(false);

            volume.setVisible(true);
            backHome.setVisible(true);
            backHome.setLocation(200, 500);
            time.setVisible(true);
            levalButton.setVisible(true);
        });
    }


    private void setSettingHome() {
        this.level++;
        this.volume.setSize(100, 36);
        this.volume.setName("volume");
        this.volume.setLocation(200, 350);
        this.volume.setContentAreaFilled(false);
        this.volume.setBorderPainted(false);
        this.volume.setVisible(false);

        this.time.setSize(100, 36);
        this.time.setLocation(200, 400);
        this.time.setName("time");
        this.time.setContentAreaFilled(false);
        this.time.setBorderPainted(false);
        this.time.setVisible(false);


        this.backHome.setSize(100, 36);
        this.backHome.setLocation(200, 500);
        this.backHome.setName("back2");
        this.backHome.setContentAreaFilled(false);
        this.backHome.setBorderPainted(false);
        this.backHome.setVisible(false);

        this.fifteen.setSize(100, 36);
        this.fifteen.setLocation(200, 450);
        this.fifteen.setName("15");
        this.fifteen.setContentAreaFilled(false);
        this.fifteen.setBorderPainted(false);
        this.fifteen.setVisible(false);

        this.twelve.setSize(100, 36);
        this.twelve.setLocation(200, 400);
        this.twelve.setName("12");
        this.twelve.setContentAreaFilled(false);
        this.twelve.setBorderPainted(false);
        this.twelve.setVisible(false);

        this.ten.setSize(100, 36);
        this.ten.setLocation(200, 350);
        this.ten.setName("10");
        this.ten.setContentAreaFilled(false);
        this.ten.setBorderPainted(false);
        this.ten.setVisible(false);

        this.hardButton.setSize(100, 36);
        this.hardButton.setName("hardButton");
        this.hardButton.setLocation(200, 350);
        this.hardButton.setContentAreaFilled(false);
        this.hardButton.setBorderPainted(false);
        this.hardButton.setVisible(false);

        this.easyButton.setSize(100, 36);
        this.easyButton.setLocation(200, 400);
        this.easyButton.setName("easyButton");
        this.easyButton.setContentAreaFilled(false);
        this.easyButton.setBorderPainted(false);
        this.easyButton.setVisible(false);


        this.levalButton.setSize(100, 36);
        this.levalButton.setLocation(200, 450);
        this.levalButton.setName("levalButton");
        this.levalButton.setContentAreaFilled(false);
        this.levalButton.setBorderPainted(false);
        this.levalButton.setVisible(false);

        this.addHoverEffect(this.volume);
        this.addHoverEffect(this.time);
        this.addHoverEffect(this.easyButton);
        this.addHoverEffect(this.backHome);
        this.addEffectForButton(this.ten, "1 phút");
        this.addEffectForButton(this.twelve, "2 phút");
        this.addEffectForButton(this.fifteen, "3 phút");
        this.addEffectForButton(this.levalButton, "Độ khó");
        this.addEffectForButton(this.hardButton, "Khó");
        this.addEffectForButton(this.easyButton, "Dễ");
        this.addEffectForButton(this.levalButton, "Độ khó");

        this.menu.add(this.volume);
        this.menu.add(this.time);
        this.menu.add(this.backHome);

        this.menu.add(this.hardButton);
        this.menu.add(this.easyButton);
        this.menu.add(this.levalButton);

        this.menu.add(this.ten);
        this.menu.add(this.twelve);
        this.menu.add(this.fifteen);
    }

    private void clickLevel() {
        this.levalButton.addActionListener(e -> {
            this.level++;
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setLocation(200, 450);
            this.time.setVisible(false);

            this.hardButton.setVisible(true);
            this.easyButton.setVisible(true);
            this.backHome.setVisible(true);
        });
    }

    private void addEffectForButton(JButton button, String title) {
        ImageIcon defaultIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/menu/time_option.png");
        ImageIcon hoverIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/moved/hover.png");
        defaultIcon = new NewImage().resizeImage(defaultIcon, 100, 36);
        hoverIcon = new NewImage().resizeImage(hoverIcon, 100, 36);

        Font font = null;
        try {
            font = Font
                    .createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/resource/file/arialbd.ttf"))
                    .deriveFont(Font.BOLD, 17.5f);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        button.setFont(font);
        button.setText(title);
        button.setIcon(defaultIcon);
        button.setRolloverIcon(hoverIcon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
    }

    private void setClickBack() {
        this.backHome.addActionListener(e -> {
            switch (level) {
                case 1:
                    backHome.setVisible(false);
                    time.setVisible(false);
                    volume.setVisible(false);
                    levalButton.setVisible(false);

                    newGame.setVisible(true);
                    if (!IOFile.isEmpty(System.getProperty("user.dir") + "/resource/file/old.txt"))
                        oldGame.setVisible(true);
                    quit.setVisible(true);
                    setting.setVisible(true);
                    level--;
                    break;
                case 2:
                    slider_1.setVisible(false);
                    label_1.setVisible(false);
                    slider_2.setVisible(false);
                    label_2.setVisible(false);

                    backHome.setLocation(200, 500);
                    time.setVisible(true);
                    volume.setVisible(true);
                    levalButton.setVisible(true);

                    ten.setVisible(false);
                    twelve.setVisible(false);
                    fifteen.setVisible(false);

                    hardButton.setVisible(false);
                    easyButton.setVisible(false);
                    level--;
                    break;
            }
        });
    }



    private void setVolume() {
        this.volume.addActionListener(e -> {
            this.level++;
            Vector<Integer> newVolume = IOFile.getVolume();
            this.slider_1.setMaximum(100);
            this.slider_1.setMinimum(0);
            this.slider_1.setValue(newVolume.elementAt(0));
            this.slider_2.setMaximum(100);
            this.slider_2.setMinimum(0);
            this.slider_2.setValue(newVolume.elementAt(1));
            this.label_1.setText(" Nhạc nền: " + newVolume.elementAt(0));
            this.label_2.setText(" Hiệu ứng: " + newVolume.elementAt(1));
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setLocation(200, 500);
            this.time.setVisible(false);
            this.levalButton.setVisible(false);

            this.slider_1.setBounds(150, 350, 200, 50);
            this.label_1.setBounds(150, 320, 150, 50);
            this.slider_1.setVisible(true);
            this.label_1.setVisible(true);

            this.slider_2.setBounds(150, 440, 200, 50);
            this.label_2.setBounds(150, 410, 150, 50);
            this.slider_2.setVisible(true);
            this.label_2.setVisible(true);
            this.slider_1.addChangeListener(e1 -> {
                this.label_1.setText(" Nhạc nền: " + this.slider_1.getValue());
                GameController.getSoundEffect().setVolumeSoundTrack(this.slider_1.getValue());
            });

            this.slider_2.addChangeListener(e1 -> {
                this.label_2.setText(" Hiệu ứng: " + this.slider_2.getValue());
                GameController.getSoundEffect().setVolumeSoundEffect(this.slider_2.getValue());
            });
        });
    }


    private void setTime() {
        this.time.addActionListener(e -> {
            this.level++;
            this.quit.setVisible(false);
            this.volume.setVisible(false);
            this.backHome.setLocation(200, 500);
            this.time.setVisible(false);
            this.levalButton.setVisible(false);

            this.ten.setVisible(true);
            this.twelve.setVisible(true);
            this.fifteen.setVisible(true);
        });
    }

    private void chooseTime() {
        this.ten.addActionListener(e -> {
            GameController.setMinute(10);
            GameController.setSecond(0);

            IOFile.saveTime(1, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });

        this.twelve.addActionListener(e -> {
            GameController.setMinute(2);
            GameController.setSecond(0);

            IOFile.saveTime(2, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });

        this.fifteen.addActionListener(e -> {
            GameController.setMinute(3);
            GameController.setSecond(0);

            IOFile.saveTime(3, 0);

            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);

            this.ten.setVisible(false);
            this.twelve.setVisible(false);
            this.fifteen.setVisible(false);
            this.level--;
        });
    }

    private void chooseLevel(){
        this.hardButton.addActionListener(e -> {
            GameController.setLevel(Constant.HARD);
            IOFile.saveLevel(Constant.HARD);
            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);
            this.levalButton.setVisible(true);

            this.hardButton.setVisible(false);
            this.easyButton.setVisible(false);
            this.level--;
        });

        this.easyButton.addActionListener(e -> {
            GameController.setLevel(Constant.EASY);
            IOFile.saveLevel(Constant.EASY);
            this.backHome.setLocation(200, 450);
            this.time.setVisible(true);
            this.volume.setVisible(true);
            this.levalButton.setVisible(true);

            this.hardButton.setVisible(false);
            this.easyButton.setVisible(false);
            this.level--;
        });
    }

    private void setEvent() {
        this.setNewGame();
        this.setQuit();
        this.setOldGame();
        this.setClickSetting();
        this.closeFrame();
        this.hideFrame();
        this.setClickBack();
        this.setVolume();
        this.setTime();
        this.chooseTime();
        this.clickLevel();
        this.chooseLevel();
    }

    private void closeFrame() {
        GameController.getCloseButton().getClose().addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }

    private void hideFrame() {
        GameController.getCloseButton().getHide().addActionListener(e -> this.setState(JFrame.ICONIFIED));
    }

    public JPanel getMenu() {
        return this.menu;
    }
}