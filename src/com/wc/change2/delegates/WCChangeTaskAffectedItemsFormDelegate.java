package com.wc.change2.delegates;

import java.util.Iterator;

import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.UpdateOperationIdentifier;
import com.ptc.windchill.enterprise.change2.ChangeLinkAttributeHelper;
import com.ptc.windchill.enterprise.change2.ChangeManagementClientHelper;
import com.ptc.windchill.enterprise.change2.beans.ChangeLinkAttributeBean;
import com.ptc.windchill.enterprise.change2.forms.delegates.ChangeTaskAffectedItemsFormDelegate;

import wt.change2.AffectedActivityData;
import wt.change2.ChangeItemIfc;
import wt.change2.ChangeRecord2;
import wt.change2.WTChangeActivity2;
import wt.fc.collections.WTCollection;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

public class WCChangeTaskAffectedItemsFormDelegate extends ChangeTaskAffectedItemsFormDelegate {

	private static final String IS_OBSOLETE = "isObsolete";

	@SuppressWarnings("unchecked")
	@Override
	protected WTCollection processLinkAttributes(ChangeItemIfc item, WTCollection binaryLinks) throws WTException {
		System.out.println("Inside WCChangeTaskAffectedItemsFormDelegate");
		if (item != null) {
			System.out.println("item=" + item);
			System.out.println("item.getClass=" + item.getClass());
		}
		if (binaryLinks != null) {
			System.out.println("binaryLinks" + binaryLinks);
			System.out.println("binaryLinks.size()" + binaryLinks.size());
			try {
				System.out.println("binaryLinks[0]=" + binaryLinks.toArray()[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		WTCollection modifiedLinks = super.processLinkAttributes(item, binaryLinks);
		
		if (modifiedLinks != null) {
			System.out.println("modifiedLinks=" +  modifiedLinks);
			System.out.println("modifiedLinks.size=" + modifiedLinks.size());
		}
		if (binaryLinks != null && !binaryLinks.isEmpty() && item instanceof WTChangeActivity2) {
			// The link bean has knowledge of how of link attributes and supports the JCA interfaces.
			ChangeLinkAttributeBean linkBean = ChangeLinkAttributeHelper.getChangeLinkAttributeBean();
			linkBean.setFormData(getObjectBean());
			for (Iterator<AffectedActivityData> iter = binaryLinks.persistableIterator(AffectedActivityData.class, true); iter.hasNext();) {
				AffectedActivityData record = iter.next();
				try {
					System.out.println("record=" + record);
					System.out.println("ati" + record.getAttributeContainer());
					System.err.println("record.value=" + record.getValue());
					System.err.println("record.roleAObj=" + record.getRoleAObjectRef());
					System.err.println("record.roleBObj=" + record.getRoleBObjectRef());
				} catch (Exception e) {
					e.printStackTrace();
				}
				// The getTableDataValue() will get the attribute from the FORM data supporting
				// any specific change table handlers
				String value = ChangeLinkAttributeHelper.getTableDataValue(IS_OBSOLETE,ChangeManagementClientHelper.getReference(record.getChangeable2()), linkBean);
				
				// The new LWC API for getting and setting attributes.
				PersistableAdapter lwc = new PersistableAdapter(record, null, null, new UpdateOperationIdentifier());
				lwc.load(IS_OBSOLETE);
				Object currentValue = lwc.get(IS_OBSOLETE);
				
				System.out.println("value=" + value);
				System.out.println("currentValue=" + currentValue);
				if (currentValue instanceof String) {
					System.out.println("currentValue=" + ((String) currentValue));
				}
				
				// Only set the attribute if it is different than the current value
				if ((value != null && currentValue != null && !value.equals(currentValue.toString()))
						|| (value == null && currentValue != null)) {
					lwc.set(IS_OBSOLETE, value);
					lwc.apply();
					// calling apply() will require verification, however since this form delegate
					// is setting the changes we can set it as verified.
					try {
						record.getPersistInfo().setVerified(true);
					} catch (WTPropertyVetoException e) {
						e.printStackTrace();
					}
					modifiedLinks.add(record);
				}
			}
		}
		return modifiedLinks;
	}
}
