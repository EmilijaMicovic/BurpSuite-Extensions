package burpsuite.extension;

import javax.swing.*;
import java.awt.*;

public class ScopeTab extends JPanel {
    private ScopeManager scopeManager;
    private JTextArea textArea;

    public ScopeTab(ScopeManager scopeManager) {
        this.scopeManager = scopeManager;
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setToolTipText("Enter endpoints, one per line");

        JButton loadButton = new JButton("Load Scope");
        loadButton.addActionListener(e -> loadScope());

        JButton showListButton = new JButton("Show Scope List");
        showListButton.addActionListener(e -> showList());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(showListButton);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadScope() {
        String[] lines = textArea.getText().split("\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                scopeManager.addEndpoint(line.trim());
            }
        }
        JOptionPane.showMessageDialog(this, "Scope loaded: " + scopeManager.getScopeList().size() + " endpoints");
    }

    private void showList() {
        String list = String.join("\n", scopeManager.getScopeList());
        JOptionPane.showMessageDialog(this, list.isEmpty() ? "Scope is empty!" : list);
    }

}
