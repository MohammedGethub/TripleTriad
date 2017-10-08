/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import classes.Card;
import classes.Loader;
import classes.SpriteCard;
import classes.SpriteSet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avata
 */
public class cardListPanel extends javax.swing.JPanel {

    /**
     * Creates new form cardListPanel
     */
    private Card[] cardList;
    private SpriteSet spriteSet;
    private boolean[] collection;
    private List<SpriteCard> cards;
    private List<Integer> selected;

    public cardListPanel() {
        initComponents();
        addMouseListener(new CardSelectMouseAdapter());
        //system
        spriteSet = new SpriteSet();
        cards = new ArrayList();
        collection = Loader.loadCollection(null, null);
        cardList = Loader.loadCards();
        selected = new ArrayList();
        //loads up cards owned by the player either through a random generator or through selection of cards:
        for (int i = 0; i < cardList.length; i++) {
            if (collection[i]) {
                cards.add(new SpriteCard(spriteSet, cardList[i], true));
            }
        }
        int cardSize = 150;
        int gap = 30;
        int width = cardSize * 4 + gap * 5;
        int rows = (cards.size() - 1) / 4 + 1;
        int height = cardSize * rows + gap * (rows + 1);
        setPreferredSize(new Dimension(width, height));
        //
        for (int i = 0; i < cards.size(); i++) {
            int x = gap + (i % 4) * (gap + cardSize);
            int y = gap + (i / 4) * (gap + cardSize);
            cards.get(i).setLocation(x, y);
        }
    }

    public pnlCardSelect getPanel() {
        return (pnlCardSelect) getParent().getParent().getParent();
    }

    public void removeCard() {
        cards.get(selected.get(selected.size() - 1)).setVisible(true);
        selected.remove(selected.size() - 1);
        repaint();
    }

    public List<Integer> getSelected() {
        return selected;
    }
    
    public void reset() {
        selected.clear();
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setVisible(true);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        spriteSet.draw(g2d);
    }
    
    private class CardSelectMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selected.size() > 4) {
                return;
            }
            for (int i = 0; i < cards.size(); i++) {
                SpriteCard card = cards.get(i);
                if (card.getRect().contains(e.getPoint())) {
                    card.setVisible(false);
                    getPanel().addCard(card.getIndex());
                    selected.add(i);
                    if (selected.size() == 5) {
                        getPanel().getAccept().setEnabled(true);
                    }
                    repaint();
                    break;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
