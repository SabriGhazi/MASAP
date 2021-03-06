package com.masim.ui;

import com.masim.core.CooperationStrategy;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

/*
 * ChooseStrategiesDialog.java
 *
 * Created on 17 janv. 2014, 11:23:37
 */

/**
 * Dialog to choose among all possible decision strategies.
 * @author Sabri Ghazi
 */
public class ChooseStrategiesDialog extends javax.swing.JDialog {

	/** Creates new form ChooseStrategiesDialog */
	public ChooseStrategiesDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		strategi_ListBox = new javax.swing.JComboBox();
		cancel_btn = new javax.swing.JButton();
		ok_btn = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setText("Cooperation Strategy");

		strategi_ListBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Greedy", "Naive", "Centralized", "NPD", "Q-learning","Voting" }));

		cancel_btn.setText("Cancel");
		cancel_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancel_btnActionPerformed(evt);
			}
		});

		ok_btn.setText("Ok");
		ok_btn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ok_btnActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel1)
						.addGap(18, 18, 18)
						.addComponent(strategi_ListBox, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(45, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(261, Short.MAX_VALUE)
								.addComponent(ok_btn)
								.addGap(18, 18, 18)
								.addComponent(cancel_btn)
								.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(19, 19, 19)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1)
								.addComponent(strategi_ListBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(cancel_btn)
										.addComponent(ok_btn))
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
		setVisible(false);
	}//GEN-LAST:event_cancel_btnActionPerformed

	private void ok_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_btnActionPerformed
		int i =strategi_ListBox.getSelectedIndex();
		switch (i) {
		case 0:
			SimulationFramework.currentStrategy= CooperationStrategy.GREEDY;
			break;
		case 1:
			SimulationFramework.currentStrategy= CooperationStrategy.NAIVE;
			break;
		case 2:
			SimulationFramework.currentStrategy= CooperationStrategy.CENTRALIZED;
			break;
		case 3:
			SimulationFramework.currentStrategy= CooperationStrategy.NEGOTIATION;
			break;
		case 4:
			SimulationFramework.currentStrategy= CooperationStrategy.Qlearning;
			break;
		case 5:
			SimulationFramework.currentStrategy= CooperationStrategy.Voting;
			break;			
		default:
			break;
		}
		setVisible(false);
}//GEN-LAST:event_ok_btnActionPerformed

/**
 * @param args the command line arguments
 */
public static void main(String args[]) {
	java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
			ChooseStrategiesDialog dialog = new ChooseStrategiesDialog(new javax.swing.JFrame(), true);
			dialog.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		}
	});
}

// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JButton cancel_btn;
private javax.swing.JLabel jLabel1;
private javax.swing.JButton ok_btn;
private javax.swing.JComboBox strategi_ListBox;
// End of variables declaration//GEN-END:variables
}
