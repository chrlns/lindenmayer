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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * A automatically sorted ListModel for JList GUI elements.
 * @author Christian Lins
 */
class SortedListModel implements ListModel {

	protected List<Object> data = new ArrayList<Object>();
	protected Set<ListDataListener> listeners = new HashSet<ListDataListener>();

	public void add(Object obj) {
		this.data.add(obj);
		Collections.sort(data, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
	}

	public void addListDataListener(ListDataListener listener) {
		this.listeners.add(listener);
	}

	public Object getElementAt(int index) {
		return this.data.get(index);
	}

	public void removeListDataListener(ListDataListener listener) {
		this.listeners.remove(listener);
	}

	public int getSize() {
		return this.data.size();
	}
}
