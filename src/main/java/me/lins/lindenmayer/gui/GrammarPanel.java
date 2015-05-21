/*
 *  Lindenmayer
 *  see AUTHORS for a list of contributors.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.lins.lindenmayer.gui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import me.lins.lindenmayer.Lindenmayer;
import me.lins.lindenmayer.Status;
import me.lins.lindenmayer.StatusChangeListener;
import me.lins.lindenmayer.grammar.Rule;
import me.lins.lindenmayer.grammar.Symbol;
import me.lins.lindenmayer.grammar.SymbolList;
import me.lins.lindenmayer.i18n.Lang;

/**
 * Panel that allows the user to create or edit grammar (elements).
 * @author Christian Lins
 */
@SuppressWarnings("serial")
class GrammarPanel extends JPanel implements StatusChangeListener {

    private javax.swing.JButton btnAddRule;
    private javax.swing.JButton btnDeleteRule;
    private javax.swing.JButton btnEditRule;
    private javax.swing.JComboBox cmbStartSymbol;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAlphabet;
    private javax.swing.JLabel lblRules;
    private javax.swing.JLabel lblStartSymbol;
    private javax.swing.JList listRules;
    private javax.swing.JTextField txtAlphabet;
	private int lastCaretPosition = 0;

    /** Creates new form GrammarPanel */
    public GrammarPanel() {
        initComponents();

		this.lblStartSymbol.setText(Lang.get(26));
		this.lblAlphabet.setText(Lang.get(27));
		this.lblRules.setText(Lang.get(28));
		this.btnAddRule.setText(Lang.get(34));
		this.btnEditRule.setText(Lang.get(35));
		this.btnDeleteRule.setText(Lang.get(36));
    }

    /** 
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {

        lblStartSymbol = new javax.swing.JLabel();
        cmbStartSymbol = new javax.swing.JComboBox();
        lblAlphabet = new javax.swing.JLabel();
        lblRules = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listRules = new javax.swing.JList();
        txtAlphabet = new javax.swing.JTextField();
        btnAddRule = new javax.swing.JButton();
        btnEditRule = new javax.swing.JButton();
        btnDeleteRule = new javax.swing.JButton();

        lblStartSymbol.setText("Start symbol");

        cmbStartSymbol.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        cmbStartSymbol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStartSymbolItemStateChanged(evt);
            }
        });

        lblAlphabet.setText("Alphabet");

        lblRules.setText("Rules");

        listRules.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(listRules);

        txtAlphabet.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtAlphabet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlphabetKeyTyped(evt);
            }
        });

        btnAddRule.setText("New rule");
        btnAddRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRuleActionPerformed(evt);
            }
        });

        btnEditRule.setText("Edit rule");
        btnEditRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRuleActionPerformed(evt);
            }
        });

        btnDeleteRule.setText("Delete rule");
        btnDeleteRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRuleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRules)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAlphabet)
                            .addComponent(lblStartSymbol))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbStartSymbol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAlphabet, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDeleteRule, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(btnEditRule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddRule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartSymbol)
                    .addComponent(cmbStartSymbol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlphabet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlphabet))
                .addGap(18, 18, 18)
                .addComponent(lblRules)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddRule)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditRule)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteRule))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>                        

	private void txtAlphabetKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlphabetKeyTyped
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				textChanged();
			}
		});
	}//GEN-LAST:event_txtAlphabetKeyTyped

	private void btnAddRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRuleActionPerformed
		EditRuleDialog erDlg = new EditRuleDialog();
		erDlg.setCallback(new RuleDialogCallback() {
			public void dialogOk(EditRuleDialog dlg) {
				Rule newRule = new Rule(
						Symbol.create(dlg.getInput()), 
						new SymbolList(dlg.getProduction()));
				Lindenmayer.STATUS.getGrammar().addRuleToRules(newRule);
				Lindenmayer.STATUS.fireStatusChangeEventInFuture();
			}
		});
		erDlg.setModal(true);
		erDlg.setVisible(true);
	}//GEN-LAST:event_btnAddRuleActionPerformed

	private void btnEditRuleActionPerformed(java.awt.event.ActionEvent evt) {
		Object selectedRule = this.listRules.getSelectedValue();
		if(selectedRule != null) {
			final Rule oldRule = (Rule)selectedRule;
			EditRuleDialog erDlg = new EditRuleDialog();
			erDlg.setInput(oldRule.getInput().getText());
			erDlg.setProduction(oldRule.getProduction().getText());
			erDlg.setCallback(new RuleDialogCallback() {
				public void dialogOk(EditRuleDialog dlg) {
					Rule newRule = new Rule(
							Symbol.create(dlg.getInput()),
							new SymbolList(dlg.getProduction()));
					Lindenmayer.STATUS.getGrammar().removeRule(oldRule);
					Lindenmayer.STATUS.getGrammar().addRuleToRules(newRule);
					Lindenmayer.STATUS.fireStatusChangeEventInFuture();
				}
			});

			erDlg.setModal(true);
			erDlg.setVisible(true);
		}
	}

	private void btnDeleteRuleActionPerformed(java.awt.event.ActionEvent evt) {
		Object selObj = listRules.getSelectedValue();
		if(selObj != null && selObj instanceof Rule) {
			Lindenmayer.STATUS.getGrammar().removeRule((Rule)selObj);
		}
	}

	private void cmbStartSymbolItemStateChanged(java.awt.event.ItemEvent evt) {
		Object startSymbol = this.cmbStartSymbol.getSelectedItem();
		if(startSymbol != null && !startSymbol.equals(Lindenmayer.STATUS.getGrammar().getStartSymbol())) {
			Lindenmayer.STATUS.getGrammar().setStartSymbol(
					(Symbol)this.cmbStartSymbol.getSelectedItem());
		}
	}

	private void textChanged() {
		lastCaretPosition = this.txtAlphabet.getCaretPosition();
		String text = this.txtAlphabet.getText();
		Lindenmayer.STATUS.getGrammar().setAlphabet(new SymbolList());
		if(text != null) {
			for(int n = 0; n < text.length(); n++) {
				Symbol symbol = Symbol.create(Character.toString(text.charAt(n)));
				Lindenmayer.STATUS.getGrammar().addElementToAlphabet(symbol);
			}
		}
		Lindenmayer.STATUS.fireStatusChangeEventInFuture();
	}

	public void statusChanged(Status status) {
		SymbolList sset = status.getGrammar().getAlphabet();
		this.cmbStartSymbol.setModel(new DefaultComboBoxModel(sset.getVariables().toArray()));
		this.cmbStartSymbol.setSelectedItem(status.getGrammar().getStartSymbol());
		this.txtAlphabet.setCaretPosition(lastCaretPosition);

		SortedListModel listModel = new SortedListModel();
		for(Rule rule : status.getGrammar().getRules()) {
			listModel.add(rule);
		}
		this.listRules.setModel(listModel);
	}

	public void statusReset(Status status) {
		statusChanged(status);
		SymbolList sset = status.getGrammar().getAlphabet();
		this.txtAlphabet.setText(sset.toString());
	}

}
