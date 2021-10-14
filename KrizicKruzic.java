package com.marko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class KrizicKruzic {
	
	static ArrayList<Integer> pozicijeIgrac = new ArrayList<Integer>();
	static ArrayList<Integer> pozicijeCPU = new ArrayList<Integer>();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		char[][] igracePolje = {{' ', '|', ' ', '|', ' '},
							    {'-', '+', '-', '+', '-'},
							    {' ', '|', ' ', '|', ' '},
							    {'-', '+', '-', '+', '-'},
							    {' ', '|', ' ', '|', ' '}};
		
		//ispis upute
		printUputstvo();
		
		while(true) {
		
			@SuppressWarnings("resource")
			Scanner unos = new Scanner(System.in);
			int rezultat;
			
			//unos igraceve pozicije
			int pozicijaIgrac;
			boolean zastavica = false;
			while(true) {
				if (!zastavica) {
					System.out.print("Tvoj red! Unesi poziciju (1-9): ");
				} else {
					System.out.print("Pozicija zauzeta! Unesi tocnu poziciju: ");
				}
				pozicijaIgrac = unos.nextInt();
				
				if (pozicijeIgrac.contains(pozicijaIgrac) || pozicijeCPU.contains(pozicijaIgrac)) {
					zastavica = true;
					continue;
				} else {
					break;
				}
			}
			
			//azuriraj igrace polje i listu pozicija igraca sa unosom
			postaviKrizicKruzic(igracePolje, pozicijaIgrac, "igrac");
			
			//ispisi azurirano igrace polje
			printIgracePolje(igracePolje);
			
			rezultat = provjeriPobjednika();
			if (rezultat != 4) {
				if (IgrajPonovno(unos, igracePolje)) {
					continue;
				} else
					unos.close();
					break;
			}
			
			System.out.println();
			//odgodi daljnje izvrsavanje 1 sekundu za bolji efekt
			Thread.sleep(1000);
			
			//generiranje pozicije racunala
			int pozicijaCPU;
			while(true) {
				Random rand = new Random();
				pozicijaCPU = rand.nextInt(9) + 1;
				if(pozicijaCPU == pozicijaIgrac) {
					continue;
				} else if (pozicijeIgrac.contains(pozicijaCPU) || pozicijeCPU.contains(pozicijaCPU)) {
					continue;
				} else {
					break;
				}
			}
				
			//azuriraj igrace polje i listu pozicija racunala
			postaviKrizicKruzic(igracePolje, pozicijaCPU, "cpu");
			
			//ispisi azurirano igrace polje
			printIgracePolje(igracePolje);
			
			rezultat = provjeriPobjednika();
			if (rezultat != 4) {
				if (IgrajPonovno(unos, igracePolje)) {
					continue;
				} else
					unos.close();
					break;
			}
			
		}
		
	}
	
	public static void printUputstvo() {
		System.out.println("KRIZIC KRUZIC\nKAKO IGRATI: unesi broj koji odgovara poziciji polja gdje zelis staviti 'X'");
		System.out.println("1|2|3");
		System.out.println("-+-+-");
		System.out.println("4|5|6");
		System.out.println("-+-+-");
		System.out.println("7|8|9");
		System.out.println();
	}
	
	public static void printIgracePolje(char[][] polje) {
		for(char[] red : polje) {
			for(char c : red) {
				System.out.print(c);
			}
			System.out.println();
		}
	}
	
	public static void postaviKrizicKruzic(char[][] polje, int poz, String red) {
		
		char znak = ' '; 
		
		if (red.equals("igrac")) {
			znak = 'X';
			pozicijeIgrac.add(poz);
		}else {
			znak = 'O';
			pozicijeCPU.add(poz);
		}
			
		switch (poz) {
			case 1:
				polje[0][0] = znak;
				break;
			case 2:
				polje[0][2] = znak;
				break;
			case 3:
				polje[0][4] = znak;
				break;
			case 4:
				polje[2][0] = znak;
				break;
			case 5:
				polje[2][2] = znak;
				break;
			case 6:
				polje[2][4] = znak;
				break;
			case 7:
				polje[4][0] = znak;
				break;
			case 8:
				polje[4][2] = znak;
				break;
			case 9:
				polje[4][4] = znak;
				break;
			default:
				throw new IllegalArgumentException("Pogresan unos: " + poz);
		}
	}
	
	public static int provjeriPobjednika() {
		//svi dobitni uvjeti:
		
		//redci
		List<Integer> gornjiRed = Arrays.asList(1, 2, 3);
		List<Integer> srednjiRed = Arrays.asList(4, 5, 6);
		List<Integer> donjiRed = Arrays.asList(7, 8, 9);
		
		//stupci
		List<Integer> ljeviStup = Arrays.asList(1, 4, 7);
		List<Integer> srednjiStup = Arrays.asList(2, 5, 8);
		List<Integer> desniStup = Arrays.asList(3, 6, 9);
		
		//diagonale
		List<Integer> diagonala1 = Arrays.asList(1, 5, 9);
		List<Integer> diagonala2 = Arrays.asList(7, 5, 3);
		
		List<List<Integer>> uvjetiPobjede = new ArrayList<List<Integer>>();
		uvjetiPobjede.add(gornjiRed);
		uvjetiPobjede.add(srednjiRed);
		uvjetiPobjede.add(donjiRed);
		uvjetiPobjede.add(ljeviStup);
		uvjetiPobjede.add(srednjiStup);
		uvjetiPobjede.add(desniStup);
		uvjetiPobjede.add(diagonala1);
		uvjetiPobjede.add(diagonala2);
		
		//provjeri sadrze li liste igraca i racunala dobitni uvjet
		for(List<Integer> l : uvjetiPobjede) {
			if(pozicijeIgrac.containsAll(l)) {
				System.out.println("Pobjeda: Igrac !");
				return 1;
			} else if(pozicijeCPU.containsAll(l)) {
				System.out.println("Pobjeda: Racunalo !");
				return 2;
			} else if(pozicijeIgrac.size() + pozicijeCPU.size() == 9) {
				System.out.println("Nerijeseno !");
				return 3;
			}
		}
		
		return 4;
	}
	
	public static boolean IgrajPonovno(Scanner unos, char[][] polje) {
		System.out.print("Zelis li igrati ponovno (da/ne): ");
		String odluka = unos.next().toLowerCase();
		if (odluka.compareTo("da") == 0) {
			
			//ciscenje igraceg polja
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if(i % 2 == 0 & j % 2 == 0) {
						polje[i][j] = ' ';
					}
				}
			}
			printIgracePolje(polje);
			
			//ciscenje lista poziicija za igraca i racunalo
			pozicijeIgrac.clear();
			pozicijeCPU.clear();
			
			return true;
		} else
			return false;
	}

}
