package upsidextre.comput.xml.sax;


import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.hardware.Gant;

/**

 */
public class SAXHandler extends DefaultHandler {


	private Stack<String> elementStack = new Stack<String>();

	private UpsiDextre hardware;
	
	private Gant gant;
	
	private int x;
	private int y;

	public SAXHandler(UpsiDextre hardware) {
		this.hardware = hardware;
	}

	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException {

		this.elementStack.push(qName);
		
		if (qName.equals("gant")) {
			System.out.println("gants");
			if (attributes.getValue("lat").equals("gauche")) {
				gant = hardware.getMainGauche();
			} else {
				gant = hardware.getMainDroite();
			}
		}
	}

	public void endElement(String uri, String localName,
			String qName) throws SAXException {

		this.elementStack.pop();
		
		if (qName.equals("gant")) {
			gant = null;
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {

		String value = new String(ch, start, length).trim();
		if (value.isEmpty()) return;
		int valueI = Integer.parseInt(value);

		if (value.isEmpty()) return;
		
		switch (currentElement()) {
		case "flexion": 
			switch (xEmeParent(1)) {
			case "index":
				System.out.println("index");
				gant.getIndexe().setFlexion(valueI);
				hardware.feedFinger(valueI);
				break;
			case "majeur":
				System.out.println("majeur");
				gant.getMajeur().setFlexion(valueI);
				break;
			case "pouce":
				System.out.println("pouce");
				gant.getPouce().setFlexion(valueI);
				break;
			}
			break;
		case "opposition":
			System.out.println("opposition");
			gant.getPouce().setOpposition(valueI);
			break;
		case "x":
			System.out.println("x");
			this.x = valueI;
			break;
		case "y":
			System.out.println("y");
			this.y = valueI;
			break;
		case "z":
			System.out.println("z");
			switch (xEmeParent(1)) {
			case "accelerometre":
				System.out.println("accelerometre");
				gant.getPosition().getAccelerometre().setX(x);
				gant.getPosition().getAccelerometre().setY(y);
				gant.getPosition().getAccelerometre().setZ(valueI);
				break;
			case "magnetometre":
				System.out.println("magnetometre");
				gant.getPosition().getMagnetometre().setX(x);
				gant.getPosition().getMagnetometre().setY(y);
				gant.getPosition().getMagnetometre().setZ(valueI);
				break;
			case "gyrometre":
				System.out.println("gyrometre");
				gant.getPosition().getGyroscope().setX(x);
				gant.getPosition().getGyroscope().setY(y);
				gant.getPosition().getGyroscope().setZ(valueI);
				break;
			}
			break;
		}
	}

	private String currentElement() {
		return this.elementStack.peek();
	}

	private String xEmeParent(int deg) {
		if(this.elementStack.size() < deg+1) return null;
		return this.elementStack.get(this.elementStack.size()-(deg+1));
	}

} 