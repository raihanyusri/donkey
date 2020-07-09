package donkey;


public class Card {

	private String name;
	
	public Card(String name) {
		this.name = name;
	}
	
	public String getCardName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object object)
	{
	    boolean isEqual = false;

	    if (object != null && object instanceof Card) {
	    	String x = ((Card) object).name;
	        isEqual = (this.name.equals(x));
	    }

	    return isEqual;
	}
	
	@Override
	public int hashCode() {
	    return this.name.charAt(2) * this.name.length() * 7 + 11;
	}

}
