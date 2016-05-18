package gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import utilities.Vector2;

public class HomePage extends Page implements ActionListener{
	
	private BlokusButton buttonOnePLayer;
	
	
	public HomePage() {
		super();
		this.buttonOnePLayer = new BlokusButton("./resources/boutons/oneplayer.png");
		this.buttonOnePLayer.setPosition(new Vector2<Integer>(488, 239));
		this.buttonOnePLayer.addListener(this);
		
	}

	@Override
	public void update(float elapsedTime) {
		this.buttonOnePLayer.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		this.buttonOnePLayer.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("wesh");		
		//Navigation.NavigateTo(Navigation.homePage);
	}

}
