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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.Timer;
import me.lins.lindenmayer.Lindenmayer;
import me.lins.lindenmayer.Status;
import me.lins.lindenmayer.StatusChangeListener;
import me.lins.lindenmayer.i18n.Lang;
import me.lins.lindenmayer.io.Resource;

/**
 * The ToolBar provides useful buttons for controlling the
 * functions of the Lindenmayer program.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
class ToolBar extends JToolBar implements StatusChangeListener {

	private static final ImageIcon IMG_PLAY =
			Resource.getImage("gfx/media-playback-start.png");
	private static final ImageIcon IMG_PAUSE =
			Resource.getImage("gfx/media-playback-pause.png");

	private JButton btnOpen = new JButton();
	private JButton btnSave = new JButton();
	private JButton btnRefresh = new JButton();
	private JButton btnStart = new JButton();
	private JButton btnNext = new JButton();
	private JButton btnPrevious = new JButton();
	private JButton btnZoomIn = new JButton();
	private JButton btnZoomOut = new JButton();
	private Timer timer;

	public ToolBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		// Set icons
		btnOpen.setIcon(Resource.getImage("gfx/document-open.png"));
		btnSave.setIcon(Resource.getImage("gfx/document-save.png"));
		btnRefresh.setIcon(Resource.getImage("gfx/view-refresh.png"));
		btnStart.setIcon(IMG_PLAY);
		btnNext.setIcon(Resource.getImage("gfx/media-seek-forward.png"));
		btnPrevious.setIcon(Resource.getImage("gfx/media-seek-backward.png"));
		btnZoomIn.setIcon(Resource.getImage("gfx/list-add.png"));
		btnZoomOut.setIcon(Resource.getImage("gfx/list-remove.png"));

		// Set tooltips
		btnOpen.setToolTipText(Lang.get(1)); // Open grammar...
		btnSave.setToolTipText(Lang.get(2)); // Save grammar...
		btnRefresh.setToolTipText(Lang.get(8)); // Refresh view
		btnStart.setToolTipText(Lang.get(9));   // Start growing the plant
		btnNext.setToolTipText(Lang.get(10));   // Next recursion step
		btnPrevious.setToolTipText(Lang.get(11)); // Previous recursion step
		btnZoomIn.setToolTipText(Lang.get(12));   // Zoom in
		btnZoomOut.setToolTipText(Lang.get(13));  // Zoom out

		timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				stepForward();
			}
		});

		// Set EventListeners
		btnOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				MainFrame.getInstance().loadGrammar();
			}
		});

		// Set EventListeners
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				MainFrame.getInstance().saveGrammar();
			}
		});

		btnRefresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				Lindenmayer.STATUS.resetTreeStack();
			}
		});

		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (!timer.isRunning()) {
					timer.start();
					btnStart.setIcon(IMG_PAUSE);
				} else {
					timer.stop();
					btnStart.setIcon(IMG_PLAY);
				}
			}
		});

		btnNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				stepForward();
			}
		});

		btnPrevious.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				Lindenmayer.STATUS.lastTree();
			}
		});

		btnZoomIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
//				MainFrame.getInstance().getCanvasPanel().getPlant().zoomIn();
				MainFrame.getInstance().repaint();
			}
		});


		btnZoomOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
		//		MainFrame.getInstance().getCanvasPanel().getPlant().zoomOut();
				MainFrame.getInstance().repaint();
			}
		});

		// Disable Buttons
		setButtonStatus(false);

		add(btnOpen);
		add(btnSave);
		addSeparator();
		add(btnRefresh);
		add(btnStart);
		add(btnPrevious);
		add(btnNext);
		//add(btnZoomIn);
		//add(btnZoomOut);
	}

	public void stopPlay() {
		for (ActionListener al : btnStart.getActionListeners()) {
			al.actionPerformed(new ActionEvent(btnStart, 0, ""));
		}
	}

	/**
	 * Sets the buttons status (enable/disable)
	 * @param status
	 */
	private void setButtonStatus(boolean status) {
		btnSave.setEnabled(status);
		btnRefresh.setEnabled(status);
		btnStart.setEnabled(status);
		btnNext.setEnabled(status);
		btnPrevious.setEnabled(status);
		btnZoomIn.setEnabled(status);
		btnZoomOut.setEnabled(status);
	}

	private void stepForward() {
		Lindenmayer.STATUS.nextTree();
	}

	public void statusChanged(Status status) {
		setButtonStatus(status.getGrammar().isValid());
	}

	public void statusReset(Status status) {
		statusChanged(status);
	}

}
