/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/
package de.willuhn.jameica.hbci.gui.menus;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.internal.action.Print;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.hbci.HBCI;
import de.willuhn.jameica.hbci.gui.action.DBObjectDelete;
import de.willuhn.jameica.hbci.gui.action.EmpfaengerAdd;
import de.willuhn.jameica.hbci.gui.action.UeberweisungExport;
import de.willuhn.jameica.hbci.gui.action.UeberweisungNew;
import de.willuhn.jameica.hbci.io.print.PrintSupportUeberweisungList;
import de.willuhn.jameica.hbci.rmi.Ueberweisung;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Kontext-Menu, welches an Listen mit Ueberweisungen gehangen werden kann.
 * Es ist fix und fertig vorkonfiguriert und mit Elementen gefuellt.
 */
public class UeberweisungList extends ContextMenu
{
	private final static I18N i18n = Application.getPluginLoader().getPlugin(HBCI.class).getResources().getI18N();

	  /**
	 * Erzeugt ein Kontext-Menu fuer eine Liste von Ueberweisungen.
	 */
	public UeberweisungList()
	{
		addItem(new SingleItem(i18n.tr("�ffnen"), new UeberweisungNew(),"document-open.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem(i18n.tr("In Adressbuch �bernehmen"),new EmpfaengerAdd(),"contact-new.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem(i18n.tr("L�schen..."), new DBObjectDelete(),"user-trash-full.png"));
    addItem(ContextMenuItem.SEPARATOR);
    addItem(new CheckedContextMenuItem(i18n.tr("Drucken..."),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        new Print().handleAction(new PrintSupportUeberweisungList(context));
      }
    },"document-print.png"));
    addItem(new CheckedContextMenuItem(i18n.tr("Exportieren..."),new UeberweisungExport(),"document-save.png"));
	}

  /**
   * Ueberschrieben, um zu pruefen, ob ein Array oder ein einzelnes Element markiert ist.
   */
  private class SingleItem extends CheckedContextMenuItem
  {
    /**
     * @param text
     * @param action
     * @param optionale Angabe eines Icons.
     */
    private SingleItem(String text, Action action, String icon)
    {
      super(text,action,icon);
    }
    /**
     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
     */
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Ueberweisung[])
        return false;
      return super.isEnabledFor(o);
    }
  }

}
