package ui;

import javax.swing.JComponent;

public class Msgs {
	
	/*public static String intro="<html><b>Live Loop</b><br>First connect a midi keyboard to play loops, if you don't have a midi Keyboard select 'PC Keyboard' in the input section. To connect Live Loop to another software like Reason or Ableton, please download MidiYoke. This tool was designed to work with a M-Audio keyboard but can be used for each kind of controller.";
	public static String input="<html><b>Select input</b><br>Choose your midi keyboard or PC Keyboard if you don't have it";
	public static String output="<html><b>Select output</b><br>Select your Sound Card if you want to just play midi sound, or select a MidiYoke port to connect Live Loop to another software.<br>Note : you need to choose this port as input in your software.";
	public static String record="<html><b>Record control</b><br>Enter the control number of the record button or just press it on your midi keyboard";
	public static String clear="<html><b>Clear control</b><br>Enter the control number of the clear button or just press it on your midi keyboard";
	public static String pause="<html><b>Pause control</b><br>Enter the control number of the pause button or just press it on your midi keyboard";
	public static String nbTrack="<html><b>Number of tracks</b><br>You can control severals tracks in your loop. For each track you must set a control (like a slider), this control enables you to select the current track while pressing clear,pause or record, you can also change the volume of each playing track.";
	public static String track="<html><b>Track control</b><br>Enter the control number of the selected track slider or just activate it on your midi keyboard";
	public static String resolution="<html><b>Sequence resolution</b><br>Indicate the number of tick per quarter note. More hight the resolution is, more precise your loop will be rendered. The value should be between 1 and 16";
	public static String seqLen="<html><b>Sequence length</b><br>Indicate the length of the loop in quarter note.";
	public static String tempo="<html><b>Tempo</b><br>Indicate the speed of the loop in quarter note by minute.";
	*/
	/*public static String intro="<form><p><b>Live Loop</b></p>Let's do live looping with a midi keyboard ! <li>First connect your midi keyboard,</li><li>Choose Your keyboard in the list</li><li>Select the controls, you just need to press it on your keyboard</li><li>To connect Live Loop to another software like Reason or Ableton, please download MidiYoke.</li><p>This tool was designed to work with a M-Audio keyboard but can be used for each kind of controller.</p></form>";
	public static String input="<form><p><b>Select input</b></p>Choose your midi keyboard or PC Keyboard if you don't have it</form>";
	public static String output="<form><p><b>Select output</b></p><p>Select your Sound Card if you want to just play midi sound, or select a MidiYoke port to connect Live Loop to another software.</p> Note : you need to choose this port as input in your software.</form>";
	public static String record="<form><p><b>Record control</b></p>Enter the control number of the record button or just press it on your midi keyboard</form>";
	public static String clear="<form><p><b>Clear control</b></p>Enter the control number of the clear button or just press it on your midi keyboard</form>";
	public static String pause="<form><p><b>Pause control</b></p>Enter the control number of the pause button or just press it on your midi keyboard</form>";
	public static String nbTrack="<form><p><b>Number of tracks</b></p>You can control severals tracks in your loop. For each track you must set a control (like a slider), this control enables you to select the current track while pressing clear,pause or record, you can also change the volume of each playing track.</form>";
	public static String track="<form><p><b>Track control</b></p>Enter the control number of the selected track slider or just activate it on your midi keyboard</form>";
	public static String resolution="<form><p><b>Sequence resolution</b></p>Indicate the number of tick per quarter note. More hight the resolution is, more precise your loop will be rendered. The value should be between 1 and 16</form>";
	public static String seqLen="<form><p><b>Sequence length</b></p>Indicate the length of the loop in quarter note.</form>";
	public static String tempo="<form><p><b>Tempo</b></p>Indicate the speed of the loop in quarter note by minute.</form>";
	public static String info="<form><p><b>Thanks for using Live Loop !</b></p>Please visit the website to give comments or suggestions.</form>";
	*/
	
	//manage hyperlink in tooltips http://www.midiox.com/index.htm?http://www.midiox.com/myoke.htm
	public static String intro="<html><p><b>Live Loop</b></p>Let's do live looping with a midi keyboard ! <li><ul>First connect your midi keyboard,</ul><ul>Choose Your keyboard in the list</ul><ul>Select the controls, you just need to press it on your keyboard</ul><ul>To connect Live Loop to another software like Reason or Ableton, please download <b><u>MidiYoke</u></b>.</ul></li><p>This tool was designed to work with a M-Audio keyboard but can be used for each kind of controller.</p></html>";
	public static String input="<html><p><b>Select input</b></p>Choose your midi keyboard or PC Keyboard if you don't have it</html>";
	public static String output="<html><p><b>Select output</b></p><p>Select your Sound Card if you want to just play midi sound, or select a <b><u>MidiYoke</u></b> port to connect Live Loop to another software.</p> Note : you need to choose this port as input in your software.</html>";
	public static String record="<html><p><b>Record control</b></p>Enter the control number of the record button or just press it on your midi keyboard</html>";
	public static String clear="<html><p><b>Clear control</b></p>Enter the control number of the clear button or just press it on your midi keyboard</html>";
	public static String pause="<html><p><b>Pause control</b></p>Enter the control number of the pause button or just press it on your midi keyboard</html>";
	public static String nbTrack="<html><p><b>Number of tracks</b></p>You can control severals tracks in your loop. For each track you must set a control (like a slider), this control enables you to select the current track while pressing clear,pause or record, you can also change the volume of each playing track.</html>";
	public static String track="<html><p><b>Track control</b></p>Enter the control number of the selected track slider or just activate it on your midi keyboard</html>";
	public static String resolution="<html><p><b>Sequence resolution</b></p>Indicate the number of tick per quarter note. More hight the resolution is, more precise your loop will be rendered. The value should be between 1 and 16</html>";
	public static String seqLen="<html><p><b>Sequence length</b></p>Indicate the length of the loop in quarter note.</html>";
	public static String tempo="<html><p><b>Tempo</b></p>Indicate the speed of the loop in quarter note by minute.</html>";
	public static String info="<html><p><b>Thanks for using Live Loop !</b></p>Please visit the website to give comments or suggestions.</html>";

	public static String webSite="http://code.google.com/p/liveloop/";
	
	
	public static String getConfigFile(){
		return System.getenv("USERPROFILE")+"/liveloop.cfg";
	}

	public static void setToolTip(JComponent c,String tool){
		tool=tool.replaceAll("<html>", "<html><body style='width:300px'>");
		tool=tool.replaceAll("</html>", "</body></html>");
		c.setToolTipText(tool);
	}
	

}
