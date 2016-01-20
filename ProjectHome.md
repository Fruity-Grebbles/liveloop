# Live Loop : create your loops in real time #

**LiveLoop** is a live _midi sequencer_ that enables you to record loop while playing piano on a keyboard. You can record, play or delete _midi tracks_ directly with your midi controller. You can connect LiveLoop to other synthesizer software like _Reason_, _Ableton_, ...
# Installation #
  * You need [Midi Yoke](http://www.midiox.com/zip/MidiYokeSetup.msi) to create a midi connection to another software
  * You need [Java](http://www.java.com) to execute this software (may be already installed on your system)

If you have a problem, you can <a href='http://izicontact.appspot.com/contact?id=agppemljb250YWN0cg4LEghNYWlsVXNlchgBDA'><b>Contact the creator</b></a>.

# Screenshot and help #
<table cellpadding='20'>
<tr>
<td valign='top'>
<img src='http://liveloop.googlecode.com/files/screenshot.jpg' width='400' />
</td>
<td valign='top'>
To use it you need :<br>
<ul><li>A midi keyboard (or you can play with your normal keybord using some softwares)<br>
</li><li>A sound software (like Reason), if you don't have you can play with your sound card directly</li></ul>

To start you must select your device (your keyboard) in the input list, you must select your sound card in the output list, or a midiYoke port to use it with your sound software<br>
<br>
Then you need to configure the controls :<br>
<ul><li>record (to start record loops)<br>
</li><li>pause (to stop the record loop)<br>
</li><li>clear (to clear the current track)<br>
To configure a control, just click in the textbox, and then press the control button in your keyboard.</li></ul>

You need to configure also the tracks :<br>
<ul><li>Choose the number of track<br>
</li><li>For each track, you must choose the slider that controls witch is the current track<br>
</li><li>The current track is the one that is recorded or cleared, you can also change the volume of the current track</li></ul>

After all you can configure the loop lenght, you can set :<br>
<ul><li>The resolution (precision of the recording midi events)<br>
</li><li>The global loop length</li></ul>

Configure your sound software : This depends on witch software you use.<br>
To configure your software, you must go in the preferences and select your midi input device. Just select MidiYoke 1.<br>
that's it, now click on <b>Start</b> and enjoy live loop playing.<br>
<br>
</td>
</tr>
</table>

# For programmers only : the Live Loop API #
**LiveLoop** is written in _Java_ with a small _api_ that is sit on top of the javax.midi api. You can easily create your own _midi module_ (a midi filter, a switch, ...) and connect them to each other. Your midi keyboard or controller can now do much more things.

The api provides all these functionalities :

- Device input and output connections

- Midi effects (Filters, Switches, Controllable effects, ...)

- An UDP client/server playing connection to play together through a network

- A live looping sequencer

- a module to play with a standard PC keyboard

- a midi clock synchronization system to synchronize LiveLoop with other softwares

