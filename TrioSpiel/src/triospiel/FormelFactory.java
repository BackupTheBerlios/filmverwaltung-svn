package triospiel;

import java.util.ArrayList;
import java.util.List;

public class FormelFactory {

	public static List<Formel> erstelleFormeln() {
		
		List<Formel> formeln = new ArrayList<Formel>();
		
		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a + b) + c;
			}
			public String showFormel() {
				return "(a + b) + c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " + " + b + ") + " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a + b) - c;
			}
			public String showFormel() {
				return "(a + b) - c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " + " + b + ") - " + c;
			}
		});
		
		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a + b) * c;
			}
			public String showFormel() {
				return "(a + b) * c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " + " + b + ") * " + c;
			}
		});
		
		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a + b) / c;
			}
			public String showFormel() {
				return "(a + b) / c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " + " + b + ") / " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a - b) + c;
			}
			public String showFormel() {
				return "(a - b) + c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " - " + b + ") + " + c;
			}
		});
		
		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a - b) - c;
			}
			public String showFormel() {
				return "(a - b) - c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " - " + b + ") - " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a - b) * c;
			}
			public String showFormel() {
				return "(a - b) * c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " - " + b + ") * " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a - b) / c;
			}
			public String showFormel() {
				return "(a - b) / c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " - " + b + ") / " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a * b) + c;
			}
			public String showFormel() {
				return "(a * b) + c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " * " + b + ") + " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a * b) - c;
			}
			public String showFormel() {
				return "(a * b) - c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " * " + b + ") - " + c;
			}
		});

		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a * b) * c;
			}
			public String showFormel() {
				return "(a * b) * c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " * " + b + ") * " + c;
			}
		});
		
		formeln.add(new Formel() {
			public int berechne(int a, int b, int c) {
				return (a * b) / c;
			}
			public String showFormel() {
				return "(a * b) / c";
			}
			public String showExample(int a, int b, int c) {
				return "(" + a + " * " + b + ") / " + c;
			}
		});
		
		System.out.println("Lade Formeln...");
		for (Formel formel : formeln)
			System.out.println(" -> " + formeln.indexOf(formel) + ". " + formel.showFormel());
		
		return formeln;
	}

	
}
