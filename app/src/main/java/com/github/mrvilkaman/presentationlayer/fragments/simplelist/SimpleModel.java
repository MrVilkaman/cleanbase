package com.github.mrvilkaman.presentationlayer.fragments.simplelist;


public class SimpleModel {

	private final int number;
	private String value;

	public SimpleModel(int number, String value) {
		this.number = number;
		this.value = value;
	}

	public int getNumber() {
		return number;
	}

	public String getValue() {
		return value;
	}

	@SuppressWarnings("CloneDoesntCallSuperClone")
	public SimpleModel clone(){
		return new SimpleModel(number,value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SimpleModel))
			return false;

		SimpleModel that = (SimpleModel) o;

		if (number != that.number)
			return false;
		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		int result = number;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	public String getImage() {
		return "http://sodesign.by/wp-content/uploads/2015/12/cat-02.png";
	}

	public void setValue(String value) {
		this.value = value;
	}
}
