package controller;

import constant.Piece;
import file.IOFile;
import image.NewImage;
import model.Check;
import model.ChessPiece;
import view.CountDown;

import javax.swing.*;
import java.util.Vector;

public class Event implements Piece {
    private int typeClick;
    private Vector<Integer> choose;
    private final JButton top, left, bottom, right;
    private boolean status;
    private final Vector<JButton> buttonH;
    private final Vector<JButton> buttonV;

    public Event() {
        this.typeClick = -1;
        this.status = false;
        this.top = new JButton();
        this.left = new JButton();
        this.bottom = new JButton();
        this.right = new JButton();
        this.buttonH = new Vector<>();
        this.buttonV = new Vector<>();
        this.choose = new Vector<>();

        this.setButton(this.top);
        this.setButton(this.left);
        this.setButton(this.bottom);
        this.setButton(this.right);
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton();
            this.setButton(button);
            this.buttonH.add(button);
        }
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            this.setButton(button);
            this.buttonV.add(button);
        }
        this.setEventListensers();
    }

    private void setChooseEventListeners() {
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        for (ChessPiece _piece_ : pieces) {
            _piece_.addActionListener(e -> {
                CountDown clock_1 = StaticPieces.getClock_1();
                CountDown clock_2 = StaticPieces.getClock_2();
                if (StaticPieces.getChessBoardPanel().getPause() || clock_1.getFullTime() ||
                    clock_2.getFullTime() || StaticPieces.getSetting().getStatus() || StaticPieces.getTurn() == -1)
                    return;
                if (StaticPieces.getTurn() % 2 == 1 - _piece_.getCOLOR()) {
                    for (Integer temp : this.choose) {
                        if (temp == _piece_.getTYPE()) {
                            this.setKillEnemies(_piece_);
                            return;
                        }
                    }
                    return;
                }
                this.status = true;
                this.hideButton();
                this.choose = _piece_.choosePiecePosition(this.buttonH, this.buttonV);
                if (this.choose.isEmpty())
                    this.choose = _piece_.choosePiecePosition(this.top, this.right, this.bottom, this.left);
                if (this.typeClick == _piece_.getTYPE()) {
                    this.hideButton();
                    this.typeClick = -1;
                    this.status = false;
                    this.choose = new Vector<>();
                } else
                    this.typeClick = _piece_.getTYPE();
            });
        }
    }

    private void resetImageChess(Vector<Integer> choose) {
        for (Integer temp : choose) {
            StaticPieces.getPieces().elementAt(temp).setImage();
        }
    }

    private void setMoveTop() {
        this.top.addActionListener(e -> {
            CountDown clock_1 = StaticPieces.getClock_1();
            CountDown clock_2 = StaticPieces.getClock_2();
            Check check = StaticPieces.getCheck();
            Vector<ChessPiece> pieces = StaticPieces.getPieces();
            if (StaticPieces.getSetting().getStatus())
                return;
            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                int cell = temp.getSIZE();
                temp.setLocation(this.top.getX() + Piece.RADIUS - cell / 2, this.top.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                temp.updateLocate("top");
                temp.updateLocate_("tr");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();
                if (this.checkMate(temp))
                    return;
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    clock_1.stop();
                    clock_2.resume();
                    StaticPieces.changeImage("wait", 2);
                } else {
                    clock_2.stop();
                    clock_1.resume();
                    StaticPieces.changeImage("wait", 1);
                }
                StaticPieces.setTurn ((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                if (!temp.checkMate()) {
                    StaticPieces.getSoundEffect().playSoundMove();

                }else {
                    StaticPieces.getSoundEffect().playSoundCheckMate();
                    StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                }
                this.typeClick = -1;
            }
        });
    }

    private void setMoveRight() {
        this.right.addActionListener(e -> {
            CountDown clock_1 = StaticPieces.getClock_1();
            CountDown clock_2 = StaticPieces.getClock_2();
            Check check = StaticPieces.getCheck();
            Vector<ChessPiece> pieces = StaticPieces.getPieces();
            if (StaticPieces.getSetting().getStatus())
                return;
            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                int cell = temp.getSIZE();
                temp.setLocation(this.right.getX() + Piece.RADIUS - cell / 2, this.right.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                temp.updateLocate("right");
                temp.updateLocate_("dr");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();
                if (this.checkMate(temp))
                    return;
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    clock_1.stop();
                    clock_2.resume();
                    StaticPieces.changeImage("wait", 2);
                } else {
                    clock_2.stop();
                    clock_1.resume();
                    StaticPieces.changeImage("wait", 1);
                }
                StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                if (!temp.checkMate())
                    StaticPieces.getSoundEffect().playSoundMove();
                else {
                    StaticPieces.getSoundEffect().playSoundCheckMate();
                    StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                }
                this.typeClick = -1;
            }
        });
    }

    private void setMoveBottom() {
        this.bottom.addActionListener(e -> {
            CountDown clock_1 = StaticPieces.getClock_1();
            CountDown clock_2 = StaticPieces.getClock_2();
            Check check = StaticPieces.getCheck();
            Vector<ChessPiece> pieces = StaticPieces.getPieces();
            if (StaticPieces.getSetting().getStatus())
                return;
            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                int cell = temp.getSIZE();
                temp.setLocation(this.bottom.getX() + Piece.RADIUS - cell / 2, this.bottom.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), pieces.elementAt(this.typeClick).getLocateY(), -1);
                temp.updateLocate("bottom");
                temp.updateLocate_("dl");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();
                if (this.checkMate(temp))
                    return;
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    clock_1.stop();
                    clock_2.resume();
                    StaticPieces.changeImage("wait", 2);
                } else {
                    clock_2.stop();
                    clock_1.resume();
                    StaticPieces.changeImage("wait", 1);
                }
                StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                if (!temp.checkMate())
                    StaticPieces.getSoundEffect().playSoundMove();
                else {
                    StaticPieces.getSoundEffect().playSoundCheckMate();
                    StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                }

                this.typeClick = -1;
            }
        });
    }

    private void setMoveLeft() {
        this.left.addActionListener(e -> {
            CountDown clock_1 = StaticPieces.getClock_1();
            CountDown clock_2 = StaticPieces.getClock_2();
            Check check = StaticPieces.getCheck();
            Vector<ChessPiece> pieces = StaticPieces.getPieces();
            if (StaticPieces.getSetting().getStatus())
                return;
            if (this.status) {
                ChessPiece temp = pieces.elementAt(this.typeClick);
                IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                int cell = temp.getSIZE();
                temp.setLocation(this.left.getX() + Piece.RADIUS - cell / 2, this.left.getY() + Piece.RADIUS - cell / 2);
                this.status = !this.status;
                check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                temp.updateLocate("left");
                temp.updateLocate_("tl");
                check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                this.hideButton();
                if (this.checkMate(temp))
                    return;
                if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                    clock_1.stop();
                    clock_2.resume();
                    StaticPieces.changeImage("wait", 2);
                } else {
                    clock_2.stop();
                    clock_1.resume();
                    StaticPieces.changeImage("wait", 1);
                }
                StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                if (!temp.checkMate())
                    StaticPieces.getSoundEffect().playSoundMove();
                else {
                    StaticPieces.getSoundEffect().playSoundCheckMate();
                    StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                }
                this.typeClick = -1;
            }
        });
    }

    private void setMoveHAnother() {
        for (JButton button : this.buttonH) {
            button.addActionListener(e -> {
                CountDown clock_1 = StaticPieces.getClock_1();
                CountDown clock_2 = StaticPieces.getClock_2();
                Check check = StaticPieces.getCheck();
                Vector<ChessPiece> pieces = StaticPieces.getPieces();
                if (StaticPieces.getSetting().getStatus())
                    return;
                if (this.status) {
                    ChessPiece temp = pieces.elementAt(this.typeClick);
                    IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                    int cell = temp.getSIZE();
                    temp.setLocation(button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                    temp.updateLocate(button);
                    temp.updateLocate(this.buttonH.indexOf(button));
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                    this.hideButton();
                    if (this.checkMate(temp))
                        return;
                    if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                        clock_1.stop();
                        clock_2.resume();
                        StaticPieces.changeImage("wait", 2);
                    } else {
                        clock_2.stop();
                        clock_1.resume();
                        StaticPieces.changeImage("wait", 1);
                    }
                    StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                    if (!temp.checkMate())
                        StaticPieces.getSoundEffect().playSoundMove();
                    else {
                        StaticPieces.getSoundEffect().playSoundCheckMate();
                        StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                    }
                    System.out.println(2 - StaticPieces.getTurn() % 2 + " " + this.typeClick);
                    this.typeClick = -1;
                }
            });
        }
    }

    private void setMoveVAnother() {
        for (JButton button : this.buttonV) {
            button.addActionListener(e -> {
                CountDown clock_1 = StaticPieces.getClock_1();
                CountDown clock_2 = StaticPieces.getClock_2();
                Check check = StaticPieces.getCheck();
                Vector<ChessPiece> pieces = StaticPieces.getPieces();
                if (StaticPieces.getSetting().getStatus())
                    return;
                if (this.status) {
                    ChessPiece temp = pieces.elementAt(this.typeClick);
                    IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), -1, StaticPieces.getTurn());
                    int cell = temp.getSIZE();
                    temp.setLocation(button.getX() + Piece.RADIUS - cell / 2, button.getY() + Piece.RADIUS - cell / 2);
                    this.status = !this.status;
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
                    temp.updateLocate(button);
                    check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
                    this.hideButton();
                    if (this.checkMate(temp))
                        return;
                    if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
                        clock_1.stop();
                        clock_2.resume();
                        StaticPieces.changeImage("wait", 2);
                    } else {
                        clock_2.stop();
                        clock_1.resume();
                        StaticPieces.changeImage("wait", 1);
                    }
                    StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
                    if (!temp.checkMate())
                        StaticPieces.getSoundEffect().playSoundMove();
                    else {
                        StaticPieces.getSoundEffect().playSoundCheckMate();
                        StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
                    }
                    System.out.println(2 - StaticPieces.getTurn() % 2 + " " + this.typeClick);
                    this.typeClick = -1;
                }
            });
        }
    }

    private void setKillEnemies(ChessPiece chessPiece) {
        CountDown clock_1 = StaticPieces.getClock_1();
        CountDown clock_2 = StaticPieces.getClock_2();
        Check check = StaticPieces.getCheck();
        Vector<ChessPiece> pieces = StaticPieces.getPieces();
        ChessPiece temp = pieces.elementAt(this.typeClick);
        IOFile.saveLastMove(this.typeClick, temp.getLocateX(), temp.getLocateY(), chessPiece.getTYPE(), StaticPieces.getTurn());
        System.out.println(temp.getName() + " killed " + chessPiece.getName());
        temp.setLocation((chessPiece.getLocateX() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2, (chessPiece.getLocateY() + 1) * Piece.CELL_SIZE - Piece.SIZE_PIECE / 2);
        this.status = !this.status;
        check.setPiece(temp.getLocateX(), temp.getLocateY(), -1);
        temp.updateLocate(chessPiece);
        check.setPiece(temp.getLocateX(), temp.getLocateY(), this.typeClick);
        this.hideButton();
        chessPiece.setVisible(false);
        if (StaticPieces.getTurn() % 2 == Piece.BLACK) {
            clock_1.stop();
            clock_2.resume();
            StaticPieces.changeImage("wait", 2);
        } else {
            clock_2.stop();
            clock_1.resume();
            StaticPieces.changeImage("wait", 1);
        }
        ChessPiece general_red = pieces.firstElement();
        if (general_red.checkMate()) {
            StaticPieces.setTurn(-1);
            clock_1.stop();
            clock_2.stop();
            StaticPieces.changeImage("", 0);
            JOptionPane.showMessageDialog(null, (chessPiece.getTYPE() == Piece.BLACK) ? "Đen thắng" : "Đỏ thắng");
            return;
        }
        if (chessPiece.getTYPE() == 0 || chessPiece.getTYPE() == 1) {
            StaticPieces.setTurn(-1);
            clock_1.stop();
            clock_2.stop();
            StaticPieces.changeImage("", 0);
            JOptionPane.showMessageDialog(null, (chessPiece.getTYPE() == 0) ? "Đen thắng" : "Đỏ thắng");
            return;
        }
        StaticPieces.setTurn((StaticPieces.getTurn() > 0) ? StaticPieces.getTurn() - 1 : 1);
        if (temp.checkMate()) {
            StaticPieces.getSoundEffect().playSoundCheckMate();
            StaticPieces.changeImage("checkmate", 2 - StaticPieces.getTurn() % 2);
        }
        else
            StaticPieces.getSoundEffect().playSoundMove();
        System.out.println(2 - StaticPieces.getTurn() % 2 + " " + this.typeClick);
        this.typeClick = -1;
    }

    private void setMoveEventListeners() {
        this.setMoveTop();
        this.setMoveRight();
        this.setMoveBottom();
        this.setMoveLeft();
        this.setMoveVAnother();
        this.setMoveHAnother();
    }

    private void setEventListensers() {
        if (StaticPieces.getTurn() == -1)
            return;
        this.setChooseEventListeners();
        this.setMoveEventListeners();
    }

    private void setButton(JButton button) {
        ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/resource/image/R.png");
        imageIcon = new NewImage().resizeImage(imageIcon, Piece.RADIUS * 2, Piece.RADIUS * 2);
        button.setBorderPainted(false);
        button.setIcon(imageIcon);
        button.setContentAreaFilled(false);
        button.setSize(2 * Piece.RADIUS, 2 * Piece.RADIUS);
        button.setVisible(false);
        StaticPieces.getChessBoardPanel().add(button);
    }

    public void hideButton() {
        this.top.setVisible(false);
        this.right.setVisible(false);
        this.bottom.setVisible(false);
        this.left.setVisible(false);
        for (JButton button : this.buttonV)
            button.setVisible(false);
        for (JButton button : this.buttonH)
            button.setVisible(false);
        this.resetImageChess(this.choose);
    }

    private Boolean checkMate(ChessPiece temp) {
        CountDown clock_1 = StaticPieces.getClock_1();
        CountDown clock_2 = StaticPieces.getClock_2();
        ChessPiece general_red = StaticPieces.getPieces().firstElement();
        if (general_red.checkMate()) {
            StaticPieces.setTurn(-1);
            clock_1.stop();
            clock_2.stop();
            JOptionPane.showMessageDialog(null, (temp.getTYPE() % 2 == Piece.BLACK) ? "Đỏ thắng" : "Đen thắng");
            return true;
        }
        return false;
    }
}
