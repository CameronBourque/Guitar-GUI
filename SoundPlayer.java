import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.border.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.applet.*;
/**
 *Retrieves and plays a sound file
 */
public class SoundPlayer
{
	private static AudioClip a;
	/**
	 *Plays a sound file of a specified note
	 *@param note the note to play
	 */
	public static void play(String note)
	{
		try
		{
			a = Applet.newAudioClip(SoundPlayer.class.getResource("sounds/"+note+".wav"));
			a.play();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 *Stops a sound file of a specified note from playing
	 *@param note the note to stop from playing
	 */
	public static void stop(String note)
	{
		try
		{
			a = Applet.newAudioClip(SoundPlayer.class.getResource("sounds/"+note+".wav"));
			a.stop();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}