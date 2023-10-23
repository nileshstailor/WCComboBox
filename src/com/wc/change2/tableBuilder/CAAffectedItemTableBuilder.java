package com.wc.change2.tableBuilder;

import com.wc.change2.tableViews.CAAffectedItemTableView;
import com.ptc.core.htmlcomp.tableview.ConfigurableTable;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.windchill.enterprise.change2.mvc.builders.tables.AffectedDataTableBuilder;
import com.ptc.mvc.components.OverrideComponentBuilder;

@ComponentBuilder("changeTask.affectedItemsTable")
@OverrideComponentBuilder
public class CAAffectedItemTableBuilder extends AffectedDataTableBuilder {
	@Override
	public ConfigurableTable buildConfigurableTable(String id) {
		return new CAAffectedItemTableView();
	}

}
