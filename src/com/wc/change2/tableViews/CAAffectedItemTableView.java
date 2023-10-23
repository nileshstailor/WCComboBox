package com.wc.change2.tableViews;

import java.util.List;
import java.util.Locale;

import com.ptc.core.htmlcomp.createtableview.Attribute.TextAttribute;
import com.ptc.windchill.enterprise.change2.tableViews.ChangeTaskAffectedItemsTableViews;

public class CAAffectedItemTableView extends ChangeTaskAffectedItemsTableViews {

	@Override
	@SuppressWarnings("unchecked")
	public List<?> getSpecialTableColumnsAttrDefinition(Locale var1) {
		List var2 = super.getSpecialTableColumnsAttrDefinition(var1);
		var2.add(new TextAttribute("isObsolete", "Is Obsolete?", var1));
		return var2;
	}
	
	@Override
	protected List<String> getDefaultColumns() {
		List<String> dColumns = super.getDefaultColumns();
		dColumns.add("isObsolete");
		return dColumns;
	}
	
}
