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
        loadButton.addActionListener(e->loadScope());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(loadButton,BorderLayout.SOUTH);
    }

    private void loadScope(){
        String[] lines = textArea.getText().split("\n");
        for(String line : lines){
            if(!line.trim().isEmpty()){
                scopeManager.addEndpoint(line.trim());
            }
        }
        JOptionPane.showMessageDialog(this,"Scope loaded: " + scopeManager.getScopeList().size() + "endpoints");
    }
}
