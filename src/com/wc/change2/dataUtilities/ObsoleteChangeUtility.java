package com.wc.change2.dataUtilities;

import java.util.ArrayList;
import java.util.Map;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.AttributeDataUtilityHelper;
import com.ptc.core.components.rendering.AbstractGuiComponent;
import com.ptc.core.components.rendering.guicomponents.ComboBox;
import com.ptc.core.ui.resources.ComponentMode;
import com.ptc.netmarkets.model.NmOid;
import com.ptc.windchill.enterprise.change2.ChangeLinkAttributeHelper;
import com.ptc.windchill.enterprise.change2.beans.ChangeLinkAttributeBean;
import com.ptc.windchill.enterprise.change2.dataUtilities.ChangeLinkAttributeDataUtility;

import wt.change2.AffectedActivityData;
import wt.fc.BinaryLink;
import wt.fc.WTReference;
import wt.util.WTException;

public class ObsoleteChangeUtility extends ChangeLinkAttributeDataUtility {

	@Override
	protected AbstractGuiComponent createGuiComponent(String variableName, Object lnkObjectHolder, ChangeLinkAttributeBean chgLnkAttBean)
			throws WTException {
		System.out.println("var1=" + variableName);
		System.out.println("var2=" + lnkObjectHolder.getClass().toString());
		System.out.println("chgLnkAttBean=" + chgLnkAttBean.getClass().toString());
		System.out.println("linkBean.getDispositionItems=" + chgLnkAttBean.getDispositionItems());
		WTReference objRef = chgLnkAttBean.getObjectReference(lnkObjectHolder);
		
		//AbstractGuiComponent agc = super.createGuiComponent(variableName, lnkObjectHolder, chgLnkAttBean);
		String compID = super.getGUIComponentId(variableName, objRef);
	    ComboBox cb = new ComboBox();
	    cb.setId(compID);
	    
		ArrayList<String> internalValues = new ArrayList<String>();
		ArrayList<String> displayValues = new ArrayList<String>();
		internalValues.add("Yes");
		displayValues.add("Yes");
		internalValues.add("No");
		displayValues.add("No");
		cb.setValues(displayValues);
		cb.setInternalValues(internalValues);
		//cb.setName(compID);	
		
		if ( chgLnkAttBean.getMode() == ComponentMode.VIEW ) {
			// read value of object and display as text and/or comboxbox that is not selectable
			cb.setEditable(false);

		} else if ( chgLnkAttBean.getMode() == ComponentMode.EDIT) {

		} else if ( chgLnkAttBean.getMode() == ComponentMode.CREATE) {
		
		}

		return cb;		
	  
	}

	@Override
	public Object getDataValue(String component_id, Object datum, ModelContext mc) throws WTException {
//		System.out.println("mc=" + mc);
//		System.out.println("mc.getclass=" + mc.getClass());
//		System.out.println("mc.getdefvalue" + mc.getDefaultValue());
//		Object val = mc.getRawValue();
//		System.out.println("val=" + val);
//		
//		try {
//			AffectedActivityData aad = (AffectedActivityData) var2;
//			System.out.println("aad=" + aad.getValue());
//			System.out.println(("aad.aa=" + aad.getAttributeContainer()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if (val != null && val.getClass() == String.class) {
//			ChangeLinkAttributeBean var5 = ChangeLinkAttributeHelper.getChangeLinkAttributeBean();
//			AbstractGuiComponent var6 = this.createGuiComponent(var1, var2, var5);
//			ComboBox cb = (ComboBox) var6;
//			//cb.setValue((String) val);
//			cb.setSelected((String) val);
//			return cb;
//		} else {
//			return super.getDataValue(var1, var2, mc);
//		}
		ComboBox localComboBox = null;
        localComboBox = new ComboBox(); 
        localComboBox.setId(component_id);
        //localComboBox.setName(component_id);
        localComboBox.setColumnName(AttributeDataUtilityHelper.getColumnName(component_id, datum, mc));
        ArrayList<String> enumerationsInter = new ArrayList<>();
        ArrayList<String> enumerationsDisplay = new ArrayList<>();
        enumerationsInter.add("YES");
        enumerationsInter.add("NO");
        enumerationsDisplay.add("Yes");
        enumerationsDisplay.add("No");
        localComboBox.setInternalValues(enumerationsInter); 
        localComboBox.setValues(enumerationsDisplay);    
        return localComboBox;  
		
	}
	
	@Override
	protected Object retrieveValue(String var1, BinaryLink var2) {
		Object var3 = null;
		if (var2 == null) {
			System.out.println("link was null");
			return var3;
		} else {
			System.out.println("binaryLink=" + var2);
			System.out.println("bl.class=" + var2.getClass());
			System.out.println("var1=" + var1);
			
			if (var1.endsWith("isObsolete") && AffectedActivityData.class.isAssignableFrom(var2.getClass())) {
				var3 = ((AffectedActivityData) var2).getValue();
				System.out.println("var3=" + var3);
			}

			return var3;
		}
	}
	
	@Override
	protected Map<WTReference, BinaryLink> processExistingLinks(NmOid var1, String var2) throws WTException {

			LinkType var3 = LinkType.AFFECTED_OBJECTS;

		return this.processExistingLinks(var1, var2, var3);
	}
	
}
