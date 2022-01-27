import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.border.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
/**
 *Creates the GUI where a user can use the guitar simulator
 */
public class GuitarFrame extends JFrame implements MouseListener, ActionListener, ChangeListener
{
	private JPanel topPanel;
	private JPanel guitarPanel;
	private JPanel bottomPanel;
	private ArrayList<JButton> topButtons;
	private ArrayList<JButton> bottomButtons;
	private ArrayList<JRadioButton> modeButtons;
	private	ArrayList<JLabel> stringTuningLabels;
	private JComboBox stringTuningChanger;
	private JSlider tempoSlider;
	private JTextField tempoSet;
	private ArrayList<String> series;
	private JLabel tempo;
	private Image guitarIcon;
	private Graphics g;
	private final ButtonGroup selection;
	private JLabel mouseCoords;
	private String selectMode;
	private ArrayList<JLabel> stringSelection;
	private boolean dropTune = false;
	private boolean dTune = false;
	/**
	 *Creates a Frame with a GUI that can be interacted with
	 */
	public GuitarFrame()
	{
		setSize(1500,950);
		setTitle("Guitar Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g = getGraphics();
		
		//TOP OF PANEL
		topPanel = new JPanel();
		GroupLayout top = new GroupLayout(topPanel);
		topPanel.setLayout(top);
		top.setAutoCreateGaps(true);
		top.setAutoCreateContainerGaps(true);
		stringTuningChanger = new JComboBox();
		stringTuningChanger.addItem("E Standard");
		stringTuningChanger.addItem("Drop D");
		stringTuningChanger.addItem("D Standard");
		stringTuningChanger.addActionListener(this);
		topButtons = new ArrayList<JButton>();
		JButton clear = new JButton("Clear");
		JButton strum = new JButton("Strum");
		topButtons.add(clear);
		topButtons.add(strum);
		for(JButton b: topButtons)
			b.addActionListener(this);
		modeButtons = new ArrayList<JRadioButton>();
		selection = new ButtonGroup();
		JRadioButton selectOpenString = new JRadioButton("Select Open String", true);
		JRadioButton select = new JRadioButton("Select", false);
		JRadioButton deselect = new JRadioButton("Deselect", false);
		modeButtons.add(selectOpenString);
		modeButtons.add(select);
		modeButtons.add(deselect);
		selectOpenString.setActionCommand("0");
		select.setActionCommand("1");
		deselect.setActionCommand("2");
		selection.add(selectOpenString);
		selection.add(select);
		selection.add(deselect);
		selectMode = selection.getSelection().getActionCommand();
		
		topPanel.add(stringTuningChanger,BorderLayout.WEST);
		for(JButton b: topButtons)
			topPanel.add(b,BorderLayout.CENTER);
		for(JRadioButton r: modeButtons)
			topPanel.add(r,BorderLayout.EAST);
		topPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.BLACK,Color.LIGHT_GRAY));
		
		top.setHorizontalGroup(
			top.createSequentialGroup()
				.addComponent(stringTuningChanger)
				.addGap(400)
				.addComponent(topButtons.get(0))
				.addComponent(topButtons.get(1))
				.addGap(530)
				.addGroup(top.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(modeButtons.get(0))
					.addComponent(modeButtons.get(1))
					.addComponent(modeButtons.get(2)))
			);
		top.setVerticalGroup(
			top.createSequentialGroup()
				.addComponent(modeButtons.get(0))
				.addGroup(top.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(stringTuningChanger)
					.addComponent(topButtons.get(0))
					.addComponent(topButtons.get(1))
					.addComponent(modeButtons.get(1)))
				.addComponent(modeButtons.get(2))
			);

		//PANEL WITH GUITAR
		guitarPanel = new JPanel();
		stringTuningLabels = new ArrayList<JLabel>();
		JLabel stringLowE = new JLabel("E");
		JLabel stringA = new JLabel("A");
		JLabel stringD = new JLabel("D");
		JLabel stringG = new JLabel("G");
		JLabel stringB = new JLabel("B");
		JLabel stringHighE = new JLabel("E");
		stringTuningLabels.add(stringLowE);
		stringTuningLabels.add(stringA);
		stringTuningLabels.add(stringD);
		stringTuningLabels.add(stringG);
		stringTuningLabels.add(stringB);
		stringTuningLabels.add(stringHighE);
		stringSelection = new ArrayList<JLabel>();
		JLabel lowENote = new JLabel("None");
		JLabel aNote = new JLabel("None");
		JLabel dNote = new JLabel("None");
		JLabel gNote = new JLabel("None");
		JLabel bNote = new JLabel("None");
		JLabel highENote = new JLabel("None");
		stringSelection.add(lowENote);
		stringSelection.add(aNote);
		stringSelection.add(dNote);
		stringSelection.add(gNote);
		stringSelection.add(bNote);
		stringSelection.add(highENote);
		mouseCoords = new JLabel("(0,0)");
		try
		{
			guitarIcon = ImageIO.read(new File("images/guitar1crop.jpg"));
		}
		catch(IOException e)
		{
			System.out.println("Image not found");
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		guitarPanel.add(new JLabel(new ImageIcon(guitarIcon)));
		for(int l=0; l<stringTuningLabels.size(); l++)//JLabel l: stringTuningLabels)
		{
			guitarPanel.add(stringTuningLabels.get(l),BorderLayout.SOUTH);
			guitarPanel.add(stringSelection.get(l),BorderLayout.SOUTH);
		}
//		guitarPanel.add(mouseCoords,BorderLayout.SOUTH);
		guitarPanel.setBackground(Color.WHITE);
		guitarPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.BLACK));
		
		//BOTTOM PANEL
		bottomPanel = new JPanel();
		GroupLayout bottom = new GroupLayout(bottomPanel);
		bottom.setAutoCreateGaps(true);
		bottom.setAutoCreateContainerGaps(true);
		bottomPanel.setLayout(bottom);
		bottomButtons = new ArrayList<JButton>();
		JButton help = new JButton("Help");
		JButton save = new JButton("Save");
		JButton load = new JButton("Load");
		JButton add = new JButton("Add Chord");
		JButton clearSeries = new JButton("Clear Series");
		JButton display = new JButton("Display Chord List");
		JButton play = new JButton("Play Series");
		JButton changeTempo = new JButton("Change Tempo");
		bottomButtons.add(help);
		bottomButtons.add(save);
		bottomButtons.add(load);
		bottomButtons.add(add);
		bottomButtons.add(display);
		bottomButtons.add(clearSeries);
		bottomButtons.add(play);
		bottomButtons.add(changeTempo);
		series = new ArrayList<String>();
		for(JButton b: bottomButtons)
			b.addActionListener(this);
		tempoSlider = new JSlider(1,240,120);
		tempoSlider.addChangeListener(this);
		tempo = new JLabel("120");
		tempoSet = new JTextField(5);
		tempoSet.setVisible(false);
		for(JButton b: bottomButtons)
			bottomPanel.add(b,BorderLayout.WEST);
		bottomPanel.add(tempoSlider,BorderLayout.EAST);
		bottomPanel.add(tempo,BorderLayout.EAST);
		bottomPanel.add(tempoSet,BorderLayout.EAST);
		bottomPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.BLACK,Color.LIGHT_GRAY));
		
		bottom.setHorizontalGroup(
			bottom.createSequentialGroup()
				.addComponent(bottomButtons.get(0))
				.addGap(400)//400
				.addComponent(bottomButtons.get(1))
				.addComponent(bottomButtons.get(2))
				.addGap(100)
				.addGroup(bottom.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(bottomButtons.get(3))
					.addComponent(bottomButtons.get(4))
					.addComponent(bottomButtons.get(5)))
				.addComponent(bottomButtons.get(6))
				.addGap(100)//530
				.addGroup(bottom.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(tempoSlider)
					.addComponent(tempo)
					.addComponent(tempoSet)
					.addComponent(changeTempo))
			);
		bottom.setVerticalGroup(
			bottom.createSequentialGroup()
				.addGroup(bottom.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(bottomButtons.get(3))
					.addComponent(changeTempo))
				.addGroup(bottom.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(bottomButtons.get(0))
					.addComponent(bottomButtons.get(1))
					.addComponent(bottomButtons.get(2))
					.addComponent(bottomButtons.get(4))
					.addComponent(bottomButtons.get(6))
					.addComponent(tempoSet)
					.addComponent(tempo))
				.addComponent(bottomButtons.get(5))
				.addComponent(tempoSlider)
			);
		
		add(topPanel,BorderLayout.NORTH);
		add(guitarPanel,BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);
		
		addMouseListener(this);
		setVisible(true);
	}
	/**
	 *Checks for when the mouse button is released
	 *@param event the event of the button being released
	 */
   	public void mouseReleased(MouseEvent event) {}
   	/**
   	 *Checks for when the mouse button is clicked
   	 *@param event the event of the mouse being clicked
   	 */
   	public void mouseClicked(MouseEvent event) {}
   	/**
   	 *Checks for when the mouse enters a component
   	 *@param event the event of the mouse entering a component
   	 */
   	public void mouseEntered(MouseEvent event) {}
   	/**
   	 *Checks for when the mouse exits a component
   	 *@param event the event of the mouse
   	 */
  	public void mouseExited(MouseEvent event) {}
  	/**
  	 *Checks for when the mouse is pressed and performs an action
  	 *@param event the event of the mouse being pressed
  	 */
  	public void mousePressed(MouseEvent event)
	{
		mouseCoords.setText("(" + (MouseInfo.getPointerInfo().getLocation().getX() - guitarPanel.getLocationOnScreen().getX()) + "," + (MouseInfo.getPointerInfo().getLocation().getY() - guitarPanel.getLocationOnScreen().getY())+")");
		String coords = mouseCoords.getText();
		double x = Double.parseDouble(coords.substring(1,coords.indexOf(',')));
		double y = Double.parseDouble(coords.substring(coords.indexOf(',')+1,coords.length()-1));
		selectMode = selection.getSelection().getActionCommand();
		
		int stringSelected = setString(x,y);
		int fretSelected = setFret(x);
		
		if(selectMode.equals("0"))	//Open
		{
			if(stringSelected>0)
				stringSelection.get(stringSelected-1).setText("Open");
		}
		else if(selectMode.equals("1")) //Fret
		{
			if(stringSelected>0 || fretSelected>0)
				stringSelection.get(stringSelected-1).setText("" + fretSelected);
		}
		else if(selectMode.equals("2")) //Deselect
		{
			if(stringSelected>0)
				stringSelection.get(stringSelected-1).setText("None");
		}
   	}
   	/**
   	 *Checks for when an action is performed on an object and performs an action
   	 *@param e the action being performed
   	 */
   	public void actionPerformed(ActionEvent e)
   	{
   		JButton button=null;
   		JComboBox tuningSelection=null;
   		try
   		{
   			button = (JButton)e.getSource();
   		}
   		catch(ClassCastException ex)
   		{
   		}
   		try
   		{
   			tuningSelection = (JComboBox)e.getSource();
   		}
   		catch(ClassCastException ex)
   		{
   		}
   		if(button!=null)
   		{
   			String buttonName = button.getLabel();
   			if(buttonName.equals("Help"))			//TELL WHAT EVERYTHING DOES
   			{
   				JOptionPane.showMessageDialog(null,"HELP:\n" + 
   					"Use the different selection options to allow you to select an " +
   					"open string, specific note, or deselect a selection from a string.\n" +
   					"You can click on a string on the guitar with the \"Select Open String\" selection to " +
   					"select the open note for that string.\n" +
   					"You can click on a string at a specfied fret with the \"Select\" option " +
   					"to select the note on the specified fret and string.\n" +
   					"To play the current selection, press the \"Strum\" button at the top.\n" +
   					"You can click on a string with the \"Deselect\" option to deselect any note selected " +
   					"on that string and mute it.\n" +
   					"The clear button at the top, allows you to clear all selections made on the guitar.\n" +
   					"You can use the drop down button to change the tuning of the guitar strings.\n" +
   					"At the bottom of the screen, you can save and load selections to and from files.\n" +
   					"You can also add the current selection to a series of chords using the \"Add Chord\" button.\n" +
   					"You can view this series by clicking the \"Display Chord List\" button.\n" +
   					"Use the \"Clear Series\" button to clear out all chords in the current series.\n" +
   					"To play the series, just press the \"Play Series\" button and the series will play at the current tempo.\n" +
   					"To change this tempo, press the \"Change Tempo\" button or use the slider to change the tempo to a desired tempo.\n" +
   					"The current tempo is displayed right above the slider. It can only be a value between 1 and 240.\n");
   			}
   			else if(buttonName.equals("Strum"))
   			{
   				strum();
   			}
   			else if(buttonName.equals("Clear"))
   			{
   				for(int l=0; l<stringSelection.size(); l++)
   					stringSelection.get(l).setText("None");
   			}
   			else if(buttonName.equals("Save"))
   			{
   				boolean done = false;
   				String fileName = "";
   				while(!done && fileName!=null)
   				{
   					fileName = JOptionPane.showInputDialog("What would you like to call the file?");
   					try
   					{
   						PrintWriter out = new PrintWriter(fileName);
   						if(fileName!=null)
   						{
   							for(JLabel l: stringSelection)
   								out.println(l.getText());
   							for(String s: series)
   								out.println(s);
   							out.close();
   							JOptionPane.showMessageDialog(null,"Saved to " + fileName + ".");
   							done = true;
   						}
   					}
   					catch(IOException exc)
   					{
   						JOptionPane.showMessageDialog(null,"Unable to save.");
   						done = true;
   					}
   				}
   			}
   			else if(buttonName.equals("Load"))
   			{
    			JFileChooser chooser = new JFileChooser();
    			chooser.requestFocus();
  			  	File infile = null;
    			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	   	  		{
	   	  			infile = chooser.getSelectedFile();
	   	  	 	}
	  		 	Scanner in = null;
	  		 	try
	  		 	{
	  		 		in = new Scanner(infile);
	  		 	
	  		 		int i=0;
	   				series.clear();
	   				while(in.hasNextLine())
	   				{
	   					String temp = in.nextLine();
	   					if(i<6)
	   					stringSelection.get(i).setText(temp);
	   					else if(i>=6)
	   					{
	   						series.add(temp);
	   					}
	   					i++;
	   				}
	  		 	}
	  		 	catch(FileNotFoundException exc)
	  		 	{
	  		 		exc.printStackTrace();
	  		 	}
			   	in.close();
   			}
   			else if(buttonName.equals("Add Chord"))
   			{
   				String sList = "";
   				for(int i=0; i<6; i++)
   				{
   					sList+=stringTuningLabels.get(i).getText() + " ";
   					sList+=stringSelection.get(i).getText() + " ";
   				}
   				sList.trim();
   				series.add(sList);
   			}
   			else if(buttonName.equals("Clear Series"))
   			{
   				series.clear();
   			}
   			else if(buttonName.equals("Display Chord List"))
   			{
   				String list = "";
   				for(String s: series)
   					list+=s+"\n";
   				if(list.isEmpty())
   					JOptionPane.showMessageDialog(null,"There have been no chords added.\nAdd some chords to the list.");
   				else
	   				JOptionPane.showMessageDialog(null,"Here is the current list of chords\n" + list);
   			}
   			else if(buttonName.equals("Play Series"))		//WHEN SOUNDS WORK, FIX THIS!
   			{
   				if(series.isEmpty())
   					JOptionPane.showMessageDialog(null,"There have been no chords added.\nAdd some chords to the list.");
   				else
   				{
   					playSeries(0);
   				}
   			}
   			else if(buttonName.equals("Change Tempo"))
   			{
   				boolean done = false;
   				String tempoTemp = tempo.getText();
   				while(!done && tempoTemp!=null)
   				{
   					tempoTemp = JOptionPane.showInputDialog("What would you like the tempo to be?",tempo.getText());
   					if(tempoTemp!=null)
   					{
   						Scanner t = new Scanner(tempoTemp);
   						if(t.hasNextInt())
   						{
   							int tInt = t.nextInt();
   							if(tInt>0 && tInt<241)
   							{
   								done = true;
   								tempo.setText(tempoTemp);
   								tempoSlider.setValue(tInt);
   								tempoSet.setText(tempoTemp);
   							}
   							else
   								JOptionPane.showMessageDialog(null,"Please enter a number between 1 and 240.");
   						}
   						else if(tempoTemp!=null)
   							JOptionPane.showMessageDialog(null,"Please enter a number between 1 and 240.");
   						else
   							JOptionPane.showMessageDialog(null,"Tempo selection cancelled.");
   					}
   				}
   			}
   		}
   		else if(tuningSelection!=null)
   		{
   			String tuningState = (String)tuningSelection.getSelectedItem();
   			if(tuningState.equals("E Standard"))
   			{
   				for(int i=0; i<stringTuningLabels.size(); i++)
   				{
   					String tune = "EADGBE";
   					stringTuningLabels.get(i).setText(tune.substring(i,i+1));
   					dropTune=false;
   					dTune=false;
   				}
   			}
   			else if(tuningState.equals("Drop D"))
   			{
   				for(int i=0; i<stringTuningLabels.size(); i++)
   				{
   					String tune = "DADGBE";
   					stringTuningLabels.get(i).setText(tune.substring(i,i+1));
   					dropTune = true;
   				}
   			}
   			else if(tuningState.equals("D Standard"))
   			{
   				for(int i=0; i<stringTuningLabels.size(); i++)
   				{
   					String tune = "DGCFAD";
   					stringTuningLabels.get(i).setText(tune.substring(i,i+1));
   					dTune = true;
   				}
   			}
   		}
   	}
   	/**
   	 *Checks for when a component's state changes and performs an action
   	 *@param e the component who is being changed
   	 */
   	public void stateChanged(ChangeEvent e)
   	{
   		JSlider tempoS = null;
   		try
   		{
   			tempoS = (JSlider)e.getSource();
   		}
   		catch(ClassCastException ex)
   		{
   			System.out.println("Caught");
   		}
   		if(tempoS!=null)
   		{
   			tempo.setText("" + tempoS.getValue());
   		}
   	}
   	/**
   	 *Gets the string number of the guitar based on x and y coordinates
   	 *@param x the x position
   	 *@param y the y position
   	 *@return the string number with 1 being the top string and 6 being the bottom string
   	 */
   	public static int setString(double x, double y)
   	{
   		if((x>=522 && x<1375 && y>=225 && y<250) || (x>=40 && x<522 && y>=225 && y<246)) //LowE
		{
			return 1;
		}
		else if((x>=522 && x<1375 && y>=250 && y<274) || x>=40 && x<522 && y>=246 && y<274) //A
		{
			return 2;
		}
		else if((x>=522 && x<1375 && y>=274 && y<298) || (x>=40 && x<522 && y>=274 && y<301)) //D
		{
			return 3;
		}
		else if((x>=911 && x<1375 && y>=298 && y<324) || (x>=634 && x<911 && y>=298 && y<326) || (x>=522 && x<634 && y>=298 && y<328) || (x>=323 && x<522 && y>=301 && y<330) || (x>=40 && x<323 && y>=301 && y<334)) //G
		{
			return 4;
		}
		else if((x>=911 && x<1375 && y>=324 && y<349) || (x>=762 && x<911 && y>=326 && y<349) || (x>=634 && x<762 && y>=326 && y<352) || (x>=522 && x<634 && y>=328 && y<355) || (x>=323 && x<522 && y>=330 && y<359) || (x>=253 && x<323 && y>=334 && y<359) || (x>=40 && x<253 && y>=334 && y<362)) //B
		{
			return 5;
		}
		else if((x>=762 && x<1375 && y>=349 && y<382) || (x>=634 && x<762 && y>=352 && y<387) || (x>=522 && x<634 && y>=355 && y<394) || (x>=253 && x<522 && y>=359 && y<399) || (x>=40 && x<253 && y>=362 && y<404)) //HighE
		{
			return 6;
		}
		else
		{
			return 0;
		}
   	}
   	/**
   	 *Gets the fret number based on an x position
   	 *@param x the x position
   	 *@return the fret number
   	 */
   	public static int setFret(double x)
   	{
		if(x>=44 && x<75)
		{
			return 23;
		}
		else if(x>=75 && x<105)
		{
			return 22;
		}
		else if(x>=105 && x<136)
		{
			return 21;
		}
		else if(x>=136 && x<170)
		{
			return 20;
		}
		else if(x>=170 && x<204)
		{
			return 19;
		}
		else if(x>=204 && x<241)
		{
			return 18;
		}
		else if(x>=241 && x<281)
		{
			return 17;
		}
		else if(x>=281 && x<323)
		{
			return 16;
		}
		else if(x>=323 && x<368)
		{
			return 15;
		}
		else if(x>=368 && x<414)
		{
			return 14;
		}
		else if(x>=414 && x<463)
		{
			return 13;
		}
		else if(x>=463 && x<517)
		{
			return 12;
		}
		else if(x>=517 && x<573)
		{
			return 11;
		}
		else if(x>=573 && x<632)
		{
			return 10;
		}
		else if(x>=632 && x<695)
		{
			return 9;
		}
		else if(x>=695 && x<761)
		{
			return 8;
		}
		else if(x>=761 && x<833)
		{
			return 7;
		}
		else if(x>=833 && x<908)
		{
			return 6;
		}
		else if(x>=908 && x<987)
		{
			return 5;
		}
		else if(x>=987 && x<1072)
		{
			return 4;
		}
		else if(x>=1072 && x<1162)
		{
			return 3;
		}
		else if(x>=1162 && x<1259)
		{
			return 2;
		}
		else if(x>=1259 && x<1363)
		{
			return 1;
		}
		else
		{
			return 0;
		}
   	}
   	/**
   	 *Plays the guitar and makes sound based on what strings and frets are selected
   	 */
   	public void strum()
   	{   		
   		String[] noteList = {"D3","D#3","E3","F3","F#3","G3","G#3","A3","A#3","B3","C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","C#7","D7","D#7","D#7"};
   		String lowENote = "None";
   		String aNote = "None";
   		String dNote = "None";
   		String gNote = "None";
   		String bNote = "None";
   		String highENote = "None";
   		for(int i=0; i<6; i++)
   		{
   			String temp = stringSelection.get(i).getText();
   			int noteIndex = 0;
   			if(!temp.equals("None"))
   			{
   				switch(i)
   				{
   					case 0://Low E
   						noteIndex=2;
   						if(dTune || dropTune)
   							noteIndex=0;
   						break;
   					case 1://A
   						noteIndex=7;
   						if(dTune)
   							noteIndex=5;
   						break;
   					case 2://D
   						noteIndex=12;
   						if(dTune)
   							noteIndex=10;
   						break;
   					case 3://G
   						noteIndex=17;
   						if(dTune)
   							noteIndex=15;
   						break;
   					case 4://B
   						noteIndex=21;
   						if(dTune)
   							noteIndex=19;
   						break;
   					case 5://High E
   						noteIndex=26;
   						if(dTune)
   							noteIndex=24;
   						break;
   				}
   				if(!temp.equals("Open"))
   				{
   					noteIndex= noteIndex+Integer.parseInt(temp);
   				}
   				switch(i)
   				{
   					case 0://Low E
   						lowENote = noteList[noteIndex];
   						SoundPlayer.play(lowENote);
   						break;
   					case 1://A
   						aNote = noteList[noteIndex];
   						SoundPlayer.play(aNote);
   						break;
   					case 2://D
   						dNote = noteList[noteIndex];
   						SoundPlayer.play(dNote);
   						break;
   					case 3://G
   						gNote = noteList[noteIndex];
   						SoundPlayer.play(gNote);
   						break;
   					case 4://B
   						bNote = noteList[noteIndex];
   						SoundPlayer.play(bNote);
   						break;
   					case 5://High E
   						highENote = noteList[noteIndex];
   						SoundPlayer.play(highENote);
   						break;
   				}
   			}
   		}
   	}
   	
   	/**
   	 *Plays a series of chords or notes that are saved in a list
   	 *@param pass the distance traversed through the list
   	 */
   	public void playSeries(int pass)
   	{
   		String[] noteList = {"D3","D#3","E3","F3","F#3","G3","G#3","A3","A#3","B3","C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","C#7","D7","D#7","D#7"};
   		String sList = series.get(pass);
   		String[] notesToAssign = sList.split(" ");
   		String lowENote = "None";
   		String aNote = "None";
   		String dNote = "None";
   		String gNote = "None";
   		String bNote = "None";
   		String highENote = "None";
   		boolean dropDTune = false;
   		boolean dStanTune = false;
   		if(notesToAssign[0].equals("D"))
   			dropDTune = true;
   		if(notesToAssign[2].equals("G"))
   			dStanTune = true;
   		for(int i=0; i<6; i++)
   		{
   			String temp = notesToAssign[1+(2*i)];
   			int noteIndex = 0;
   			if(!temp.equals("None"))
   			{
   				switch(i)
   				{
   					case 0://Low E
   						noteIndex=2;
   						if(dropDTune || dStanTune)
   							noteIndex=0;
   						break;
   					case 1://A
   						noteIndex=7;
   						if(dStanTune)
   							noteIndex=5;
   						break;
   					case 2://D
   						noteIndex=12;
   						if(dStanTune)
   							noteIndex=10;
   						break;
   					case 3://G
   						noteIndex=17;
   						if(dStanTune)
   							noteIndex=15;
   						break;
   					case 4://B
   						noteIndex=21;
   						if(dStanTune)
   							noteIndex=19;
   						break;
   					case 5://High E
   						noteIndex=26;
   						if(dStanTune)
   							noteIndex=24;
   						break;
   				}
   				if(!temp.equals("Open"))
   				{
   					noteIndex= noteIndex+Integer.parseInt(temp);
   				}
   				switch(i)
   				{
   					case 0://Low E
   						lowENote = noteList[noteIndex];
   						SoundPlayer.play(lowENote);
   						break;
   					case 1://A
   						aNote = noteList[noteIndex];
   						SoundPlayer.play(aNote);
   						break;
   					case 2://D
   						dNote = noteList[noteIndex];
   						SoundPlayer.play(dNote);
   						break;
   					case 3://G
   						gNote = noteList[noteIndex];
   						SoundPlayer.play(gNote);
   						break;
   					case 4://B
   						bNote = noteList[noteIndex];
   						SoundPlayer.play(bNote);
   						break;
   					case 5://High E
   						highENote = noteList[noteIndex];
   						SoundPlayer.play(highENote);
   						break;
   				}
   			}
   		}
   		//60000 = 1 minute
   		int space = (60000/(Integer.parseInt(tempo.getText())));
   		try
		{
			Thread.sleep(space);
			if(!lowENote.equals("None"))
			{
   				SoundPlayer.stop(lowENote);
			}
			else if(!aNote.equals("None"))
			{
   				SoundPlayer.stop(aNote);
			}
			else if(!dNote.equals("None"))
			{
   				SoundPlayer.stop(dNote);
			}
			else if(!gNote.equals("None"))
			{
   				SoundPlayer.stop(gNote);
			}
			else if(!bNote.equals("None"))
			{
   				SoundPlayer.stop(bNote);
			}
			else if(!highENote.equals("None"))
			{
   				SoundPlayer.stop(highENote);
			}
			if(series.size()>pass+1)
				playSeries(pass+1);
		}
		catch(InterruptedException e)
		{
		}
   	}
}