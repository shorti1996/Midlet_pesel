package App2;

import java.util.*;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MyMidlet2 extends MIDlet implements CommandListener {
	
	private boolean mStarted;
	
	private Display mDisplay;
	private Form mFormMain;
	
	private TextField mImieTF;
	private TextField mPeselTF;
	private TextField mResultTF;
	
	private Command mUrodzinki;

	public MyMidlet2() {
		// TODO Auto-generated constructor stub
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		if (!mStarted) {
            mDisplay = Display.getDisplay(this);
            
            mFormMain = new Form("KARPIE JEDZO GUWNO");
            
            mFormMain.append("Hello");
            mFormMain.append("World");
            
            mImieTF = new TextField("Podaj imie", "", 32, TextField.ANY);
            mFormMain.append(mImieTF);
            
            mPeselTF = new TextField("Podaj pesel", "", 32, TextField.ANY);
            mFormMain.append(mPeselTF); 
            
            mResultTF = new TextField("Wynik", "Wpisz dane i wybierz \"Urodzinki\".", 128, TextField.UNEDITABLE);
            mFormMain.append(mResultTF); 
            
            mDisplay.setCurrent(mFormMain);
            
    		mUrodzinki = new Command("Urodzinki", Command.ITEM, 1);
    		mFormMain.addCommand(mUrodzinki);
    		mFormMain.setCommandListener(this);
            
            mStarted = true;
        }
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == mUrodzinki) {
			urodzinkiMethod();
		}
	}
	
	public void urodzinkiMethod() {		
		String pesel = mPeselTF.getString();
		String name = mImieTF.getString();
		PeselValidator pv = new PeselValidator(pesel);
		boolean peselValid = pv.isValid();
		
		if(pesel.equals("") || name.equals("") || !peselValid) {
			return;
		}
		
		long days = getDaysToBirthday(pesel);
		boolean isWoman = pv.isWoman();
		
		mResultTF.setString(buildResultString(name, days, isWoman));
	}
	
	public String buildResultString(String name, long days, boolean isWoman) {
		String result;
		if(isWoman) {
			result = name + ", bêdziesz mia³a urodziny za " + days + " dni";
		} else {
			result = name + ", bêdziesz mia³ urodziny za " + days + " dni";
		}
		return result;
	}
	
	public long getDaysToBirthday(String pesel) {
		//M //PeselValidator pv = new PeselValidator("96030713339");
		//K //PeselValidator pv = new PeselValidator("74030710501");
		PeselValidator pv = new PeselValidator(pesel);
		int month = pv.getBirthMonth();
		int day = pv.getBirthDay();
			
		Calendar birthDayCalendar = Calendar.getInstance();
		birthDayCalendar.set(Calendar.MONTH, month-1);
		birthDayCalendar.set(Calendar.DAY_OF_MONTH, day);
		
		Calendar nowCalendar = Calendar.getInstance();
		
		//calendar.getTime() returns Date, date.getTime() returns long (milliseconds)
		long diffMillis = birthDayCalendar.getTime().getTime() - nowCalendar.getTime().getTime();
		if(diffMillis < 0) {
			birthDayCalendar.set(Calendar.YEAR, birthDayCalendar.get(Calendar.YEAR) + 1);
			diffMillis = birthDayCalendar.getTime().getTime() - nowCalendar.getTime().getTime();
		}
		
		return (diffMillis / (1000*60*60*24));
	}

}
