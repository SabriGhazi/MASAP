package com.masim.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.masim.core.CooperationStrategy;
import com.masim.emissionAgents.EmissionUnit;
import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.utils.DispersionEngine;
import com.masim.utils.SimulationFramework;

public class EmissionSourcsDataShowDiag extends JDialog {

	public JTable tab = new JTable();
	public DefaultTableModel dfm = new DefaultTableModel();

	public EmissionSourcsDataShowDiag() {
		super();
		JScrollPane jp = new JScrollPane();

		if (SimulationFramework.currentStrategy != CooperationStrategy.NEGOTIATION) {
			dfm.setColumnIdentifiers(new String[] { "ID",
					"Current Emision Rate", "Polutant" });

			tab.setModel(dfm);
			for (SourceConfigInfo iterable_element : SimulationFramework.sourcesInfo
					.values()) {

				dfm.addRow(new String[] { iterable_element.getSourceName(),
						iterable_element.getEmisionRate() + "",
						iterable_element.getP().toString() });
			}
		} else {

			dfm.setColumnIdentifiers(new String[] { "ID",
					"Current Emision Rate", "Polutant", "Rewards",
					"CurrentStrategy", "Penalities" });

			tab.setModel(dfm);
			for (EmissionUnit iterable_element : PrisonnerDelimmaUtl.currentPD.eAgentList
					.values()) {
				dfm.addRow(new String[] {
						iterable_element.getLocalName(),
						SimulationFramework.sourcesInfo.get(
								iterable_element.getLocalName())
								.getEmisionRate()
								+ "",
						SimulationFramework.sourcesInfo
								.get(iterable_element.getLocalName()).getP()
								.toString(), iterable_element.rewards + "",
						iterable_element.lastChoices[0] + "",
						iterable_element.ecoImpact + "",
						iterable_element.getState() + "" });
			}

		}
		jp.setViewportView(tab);
		this.add(jp);
		this.setSize(300, 300);
		this.setVisible(true);
	}
}
