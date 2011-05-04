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

package lindenmayer.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.Timer;

import lindenmayer.i18n.Lang;
import lindenmayer.io.Resource;

/**
 * The ToolBar provides useful buttons for controlling the
 * functions of the Lindenmayer program.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class ToolBar extends JToolBar
{

  private JButton btnOpen     = new JButton();
  private JButton btnSave     = new JButton();
  private JButton btnRefresh  = new JButton();
  private JButton btnStart    = new JButton();
  private JButton btnNext     = new JButton();
  private JButton btnPrevious = new JButton();
  private JButton btnZoomIn   = new JButton();
  private JButton btnZoomOut  = new JButton();
  private Timer   timer;
  
  public ToolBar()
  {
    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    
    // Set icons
    btnOpen.setIcon(Resource.getImage("lindenmayer/resource/gfx/document-open.png"));
    btnSave.setIcon(Resource.getImage("lindenmayer/resource/gfx/document-save.png"));
    btnRefresh.setIcon(Resource.getImage("lindenmayer/resource/gfx/view-refresh.png"));
    btnStart.setIcon(Resource.getImage("lindenmayer/resource/gfx/media-playback-start.png"));
    btnNext.setIcon(Resource.getImage("lindenmayer/resource/gfx/media-seek-forward.png"));
    btnPrevious.setIcon(Resource.getImage("lindenmayer/resource/gfx/media-seek-backward.png"));
    btnZoomIn.setIcon(Resource.getImage("lindenmayer/resource/gfx/list-add.png"));
    btnZoomOut.setIcon(Resource.getImage("lindenmayer/resource/gfx/list-remove.png"));
    
    // Set tooltips
    btnOpen.setToolTipText(Lang.get("1")); // Open grammar...
    btnSave.setToolTipText(Lang.get("2")); // Save grammar...
    btnRefresh.setToolTipText(Lang.get("8")); // Refresh view
    btnStart.setToolTipText(Lang.get("9"));   // Start growing the plant
    btnNext.setToolTipText(Lang.get("10"));   // Next recursion step
    btnPrevious.setToolTipText(Lang.get("11")); // Previous recursion step
    btnZoomIn.setToolTipText(Lang.get("12"));   // Zoom in
    btnZoomOut.setToolTipText(Lang.get("13"));  // Zoom out
    
    timer = new Timer(1000, new ActionListener()
    {
      public void actionPerformed(ActionEvent ev)
      {
        stepForward();
      }
    });
    
    // Set EventListeners
    btnOpen.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {        
        MainFrame.getInstance().loadGrammar();
      }
    });
    
    // Set EventListeners
    btnSave.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        MainFrame.getInstance().saveGrammar();
      }
    });
    
    btnRefresh.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        MainFrame.getInstance().getCanvasPanel().createPlant();
        MainFrame.getInstance().repaint();
        MainFrame.getInstance().getLogPanel().reset();
      }
    });
    
    btnStart.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        if(!timer.isRunning())
        {
          timer.start();          
          btnStart.setIcon(Resource.getImage("resource/gfx/media-playback-pause.png"));
        }
        else
        {
          timer.stop();
          btnStart.setIcon(Resource.getImage("resource/gfx/media-playback-start.png"));
        }
      }
    });
    
    btnNext.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        stepForward();
      }
    });
    
    btnPrevious.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        stepBackward();        
      }
    });    
    
    btnZoomIn.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        MainFrame.getInstance().getCanvasPanel().getPlant().zoomIn();
        MainFrame.getInstance().repaint();
      }
    });
    
    
    btnZoomOut.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        MainFrame.getInstance().getCanvasPanel().getPlant().zoomOut();
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
    add(btnZoomIn);
    add(btnZoomOut);
  }
  
  private void stepForward()
  {
    if(MainFrame.getInstance().getCanvasPanel().getPlant() != null)
    {
      MainFrame.getInstance().getLogPanel().reset();
      int mrd = MainFrame.getInstance().getCanvasPanel().getPlant().getRecursionDepth();
      MainFrame.getInstance().getCanvasPanel().getPlant().setRecursionDepth(++mrd);
      MainFrame.getInstance().getCanvasPanel().getPlant().repaintFull();
      MainFrame.getInstance().repaint();
    }
    else
      MainFrame.getInstance().getCanvasPanel().createPlant();
  }
  
  private void stepBackward()
  {
    if(MainFrame.getInstance().getCanvasPanel().getPlant() != null)
    {
      MainFrame.getInstance().getLogPanel().reset();
      int mrd = MainFrame.getInstance().getCanvasPanel().getPlant().getRecursionDepth();
      MainFrame.getInstance().getCanvasPanel().getPlant().setRecursionDepth(--mrd);
      MainFrame.getInstance().getCanvasPanel().getPlant().repaintFull();
      MainFrame.getInstance().repaint();
    }
    else
      MainFrame.getInstance().getCanvasPanel().createPlant();
  }
  
  public void stopPlay()
  {
    for(ActionListener al : btnStart.getActionListeners())
      al.actionPerformed(new ActionEvent(btnStart, 0, ""));
  }
  
  /**
   * Sets the Buttonsstatus (enable/disable)
   * @param status: status of the Buttons
   */
  public void setButtonStatus(boolean status)
  {    
    btnSave.setEnabled(status);
    btnRefresh.setEnabled(status);
    btnStart.setEnabled(status);
    btnNext.setEnabled(status);
    btnPrevious.setEnabled(status);
    btnZoomIn.setEnabled(status);
    btnZoomOut.setEnabled(status);   
  }

}
