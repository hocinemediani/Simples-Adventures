package com.projetlong.gh02.inventory.gui;

import com.projetlong.gh02.inventory.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryFrame extends JFrame {

    // Attributs principaux
    private final Inventory inventory;
    private final JTable table;
    private final DefaultTableModel tableModel;

    // Panneau de détails
    private final JLabel lblId = new JLabel();
    private final JLabel lblNom = new JLabel();
    private final JLabel lblRarete = new JLabel();
    private final JLabel lblDegats = new JLabel();
    private final JLabel lblAmmo = new JLabel();
    private final JList<String> listAttachments = new JList<>();
    private final JButton btnReload = new JButton("Recharger"); // Bouton pour recharger les munitions
    private final JButton btnFire = new JButton("Tirer"); // Bouton pour tirer
    private final JButton btnShowInventory = new JButton("Afficher l'inventaire"); // Bouton pour afficher l'inventaire

    public InventoryFrame(Inventory inventory) {
        super("Inventaire des armes");
        this.inventory = inventory;

        // Configuration de base de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Création du tableau
        String[] colonnes = { "ID", "Nom", "Rareté", "Dégâts", "Munitions", "Max Munitions" };
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Sélection d'une ligne → mise à jour du panneau de détails
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int idx = table.getSelectedRow();
                if (idx >= 0) {
                    String id = (String) tableModel.getValueAt(idx, 0);
                    Weapon w = inventory.getItem(id) instanceof Weapon
                                ? (Weapon) inventory.getItem(id)
                                : null;
                    if (w != null) {
                        updateDetails(w);
                    }
                }
            }
        });

        // Chargement des données dans le tableau
        loadTableData();

        // Construction du panneau de détails à droite
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Détails de l'arme"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; detailsPanel.add(new JLabel("ID :"), c);
        c.gridx = 1;              detailsPanel.add(lblId, c);
        c.gridx = 0; c.gridy = 1; detailsPanel.add(new JLabel("Nom :"), c);
        c.gridx = 1;              detailsPanel.add(lblNom, c);
        c.gridx = 0; c.gridy = 2; detailsPanel.add(new JLabel("Rareté :"), c);
        c.gridx = 1;              detailsPanel.add(lblRarete, c);
        c.gridx = 0; c.gridy = 3; detailsPanel.add(new JLabel("Dégâts :"), c);
        c.gridx = 1;              detailsPanel.add(lblDegats, c);
        c.gridx = 0; c.gridy = 4; detailsPanel.add(new JLabel("Munitions :"), c);
        c.gridx = 1;              detailsPanel.add(lblAmmo, c);

        c.gridx = 0; c.gridy = 5; c.gridwidth = 2;
        detailsPanel.add(new JLabel("Pièces jointes :"), c);
        c.gridy = 6; detailsPanel.add(new JScrollPane(listAttachments), c);

        // Boutons d'action
        JPanel buttonPanel = new JPanel();
        btnReload.setEnabled(false);
        btnFire.setEnabled(false);
        buttonPanel.add(btnReload);
        buttonPanel.add(btnFire);
        buttonPanel.add(btnShowInventory);

        // Actions des boutons
        // Action du bouton "Recharger"
        btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = table.getSelectedRow();
                if (idx >= 0) {
                    String id = (String) tableModel.getValueAt(idx, 0);
                    Weapon w = (Weapon) inventory.getItem(id);
                    w.reload();
                    updateDetails(w);
                    tableModel.setValueAt(w.getCurrentAmmo(), idx, 4);
                }
            }
        });
        // Action du bouton "Tirer"
        btnFire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = table.getSelectedRow();
                if (idx >= 0) {
                    String id = (String) tableModel.getValueAt(idx, 0);
                    Weapon w = (Weapon) inventory.getItem(id);
                    if (w.fire()) {
                        updateDetails(w);
                        tableModel.setValueAt(w.getCurrentAmmo(), idx, 4);
                    } else {
                        JOptionPane.showMessageDialog(InventoryFrame.this,
                            "Pas de munitions restantes !",
                            "Erreur",
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        // Action du bouton "Afficher l'inventaire"
        btnShowInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadTableData();
            }
        });

        // Assemblage général
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          new JScrollPane(table),
                                          detailsPanel);
        split.setDividerLocation(450);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(split, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Recharge les données du tableau en vidant le modèle et en le remplissant à nouveau.
     */
    private void reloadTableData() {
        tableModel.setRowCount(0); // Vide le tableau
        loadTableData();           // Recharge les données
    }

    /**
     * Charge les données des armes dans le tableau.
     * Parcourt l'inventaire et ajoute chaque arme au modèle de tableau.
     */
    private void loadTableData() {
        for (Weapon w : inventory.getWeapons()) {
            Object[] ligne = {
                w.getId(),
                w.getName(),
                w.getRarity(),
                w.getDamage(),
                w.getCurrentAmmo(),
                w.getMaxAmmo()
            };
            tableModel.addRow(ligne);
        }
    }

    private void updateDetails(Weapon w) {
        lblId.setText(w.getId());
        lblNom.setText(w.getName());
        lblRarete.setText(w.getRarity().toString());
        lblDegats.setText(String.valueOf(w.getDamage()));
        lblAmmo.setText(w.getCurrentAmmo() + " / " + w.getMaxAmmo());

        DefaultListModel<String> lm = new DefaultListModel<>();
        for (Attachment a : w.getAttachments()) {
            lm.addElement(a.getName() + " : " + a.getDescription());
        }
        listAttachments.setModel(lm);

        btnReload.setEnabled(true);
        btnFire.setEnabled(true);
    }

    // Exemple de lancement
    public static void main(String[] args) {
        // Création d'un inventaire de test
        Inventory inv = new Inventory();
        Weapon w1 = new Weapon("w1", "Fusil d'assaut", Rarity.RARE, 35, 30);
        w1.addAttachment(new Attachment("Silencieux", "Réduit le bruit des tirs"));
        w1.addAttachment(new Attachment("Viseur", "Améliore la précision"));
        Weapon w2 = new Weapon("w2", "Pistolet", Rarity.COMMON, 15, 12);
        inv.addItem(w1);
        inv.addItem(w2);

        SwingUtilities.invokeLater(() -> {
            InventoryFrame frame = new InventoryFrame(inv);
            frame.setVisible(true);
        });
    }
}

