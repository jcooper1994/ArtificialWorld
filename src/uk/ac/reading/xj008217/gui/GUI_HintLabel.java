package uk.ac.reading.xj008217.gui;

import javax.swing.JLabel;

/**
 * Extends JLabel to create a label that displays a random hint about the simulation.
 * @author Jon
 *
 */
public class GUI_HintLabel extends JLabel{

	private String[] hints = {
		"Did you know... grass grows faster next to rivers.",
		"Did you know... animals use more energy when they are panicked.",
		"Did you know... sheep will alert the rest of the herd when they see a predator.",
		"Did you know... it is possible for a sheep to run out of energy and die while fleeing.",
		"Did you know... you can click and drag to navigate your world.",
		"Did you know... some grass contains weeds that are poisonous to sheep, the weeds have a distinctive white tint.",
		"Did you know... sheep are social creatures and stay in herds while wolves prefer to travel alone",
		"Did you know... sheep dont pay much attention, they wont notice a wolf until it gets close!",
		};
	
	public GUI_HintLabel(){
		int newHint = (int) Math.floor(Math.random() * hints.length);
		setText(hints[newHint]);
		
	}
}
