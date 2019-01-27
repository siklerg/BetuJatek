package betuJatek;

import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary {

	private static String[] basic = { "add", "adj", "adó", "adu", "aha", "aki", "akt", "alá", "áld", "alj", "áll", "ama",
			"ami", "apa", "apó", "apu", "ara", "arc", "árt", "áru", "ásó", "bab", "báb", "baj", "báj", "bak", "bal",
			"bál", "bár", "bél", "bér", "bír", "bíz", "bog", "bók", "bón", "bor", "bór", "bot", "bőg", "bök", "bőr",
			"búb", "búg", "búr", "bús", "bűn", "bűz", "cár", "cég", "céh", "cél", "cet", "cím", "dac", "dal", "dán",
			"dél", "dér", "dia", "díj", "dió", "dob", "dóm", "döf", "dög", "dől", "dúc", "dúl", "dug", "dús", "düh",
			"dűl", "edz", "ego", "égő", "ejt", "eke", "elé", "elő", "élő", "elv", "eme", "emu", "eón", "epe", "épp",
			"éra", "érc", "erő", "ért", "érv", "eső", "est", "faj", "fáj", "fák", "fal", "far", "fed", "fej", "fék",
			"fel", "fél", "fém", "fen", "fér", "fia", "fiú", "fog", "fok", "fon", "fos", "föl", "főz", "fúj", "fúl",
			"fúr", "fut", "fül", "fűt", "fűz", "gát", "gaz", "gáz", "gél", "gém", "gén", "gép", "géz", "gin", "gnú",
			"góc", "gól", "gót", "göb", "gőg", "gőz", "hab", "had", "hág", "haj", "háj", "hal", "hál", "hám", "has",
			"hat", "hát", "ház", "heg", "hej", "héj", "hét", "hév", "híd", "híg", "hím", "hír", "hit", "hiú", "hív",
			"hód", "hol", "hon", "hoz", "hőn", "hős", "húg", "huj", "húr", "hús", "húz", "hűl", "hűt", "ide", "idő",
			"iga", "ige", "ima", "íme", "ing", "int", "ion", "író", "irt", "itt", "ivó", "izé", "jaj", "jak", "jár",
			"jég", "jel", "jód", "jog", "jók", "jól", "jón", "jós", "jót", "jön", "juh", "jut", "kád", "kan", "kán",
			"kap", "kar", "kár", "kas", "kéj", "kék", "kel", "kél", "kém", "ken", "kén", "kép", "kér", "kés", "két",
			"kéz", "kik", "kín", "kis", "kit", "kóc", "kód", "kor", "kór", "kos", "köb", "köd", "köp", "kör", "kőr",
			"köt", "köz", "kúp", "kút", "láb", "lak", "lám", "lap", "láp", "lát", "láz", "léc", "lég", "lék", "lel",
			"len", "lép", "les", "lét", "lik", "lóg", "lop", "lök", "lőn", "lúd", "lúg", "luk", "mag", "máj", "mák",
			"mar", "már", "más", "máz", "méd", "meg", "még", "méh", "mén", "mer", "mér", "mez", "méz", "míg", "min",
			"mit", "mód", "mól", "mór", "mos", "nád", "nap", "nem", "net", "nép", "név", "néz", "nos", "nős", "oda",
			"óda", "odú", "oka", "oké", "oki", "old", "olt", "ont", "óra", "orr", "óta", "ott", "óva", "óvó", "ölt",
			"önt", "őrs", "örv", "ősi", "övé", "pác", "pad", "pál", "pap", "pár", "pej", "pék", "pép", "per", "pia",
			"pír", "pók", "pop", "por", "pót", "póz", "pök", "pör", "púp", "rab", "rád", "rag", "rág", "raj", "rak",
			"rák", "rám", "ráz", "reá", "rég", "rém", "rés", "rét", "rév", "réz", "rím", "rom", "rög", "rőt", "rúd",
			"rúg", "rum", "rút", "rüh", "sál", "sáp", "sár", "sas", "sás", "sav", "sáv", "seb", "séf", "sem", "sík",
			"sín", "síp", "sír", "sok", "sók", "som", "sor", "sóz", "sör", "sőt", "súg", "sül", "sün", "süt", "tag",
			"tág", "táj", "tál", "tan", "tán", "táp", "tar", "tár", "tat", "tát", "táv", "tea", "tej", "tél", "tép",
			"tér", "tét", "tik", "tíz", "tok", "tol", "tor", "top", "tót", "tök", "töm", "tör", "tőr", "tré", "tud",
			"túl", "túr", "tus", "tűr", "tűz", "ufó", "ujj", "ura", "úri", "úti", "utó", "üde", "üdv", "ügy", "ülj",
			"ült", "ünő", "üsd", "üst", "üti", "ütő", "űzi", "vad", "vád", "vág", "vaj", "váj", "vak", "vám", "van",
			"var", "vár", "vas", "váz", "véd", "vég", "vej", "vél", "vén", "ver", "vér", "vés", "vet", "vét", "víg",
			"vív", "víz", "von", "zab", "zaj", "záp", "zár", "zen", "zöm", "zug", "zúg", "zúz", "zűr", "lep" };

	private static String[] suffixedWords = { "ága", "ágú", "ára", "árú", "azt", "edd", "égi", "éke", "éle", "éli", "élj",
			"élt", "élű", "éri", "érő", "ess", "éji", "éjt", "éti", "éve", "évi", "evő", "ezt", "fáé", "fán", "fás",
			"fát", "főt", "hőé", "írd", "írj", "írt", "ívű", "íze", "ízt", "ízű", "lóé", "mai", "nőé", "női", "nők",
			"nőn", "nőt", "öld", "öli", "ölj", "ölő", "öné", "övi", "őrá", "őrt", "sós", "sót", "ülő", "űző", };

	private static String[] suffixs = { "hez", "höz", "nak", "nek", "pre", "pro", "tól", "től", "val", "vel", "bel",
			"kül" };

	private static String[] names = { "aba", "ada", "ady", "ali", "áta", "bán", "bea", "bük", "cák", "dég", "dóc",
			"don", "écs", "éda", "ede", "erk", "eta", "ete", "éva", "gál", "göd", "ida", "ila", "iza", "ják", "jen",
			"jób", "kál", "kám", "kup", "lad", "lea", "lél", "leó", "lia", "mád", "mia", "noé", "odó", "ond", "ózd",
			"örs", "pat", "pér", "tab", "tác", "tas", "tás", "tás", "teó", "tés", "ukk", "úny", "usa", "vác", "vál",
			"vát", "vép", "vid", "zia", "zoé"};

	private static String[] shorts = { "ápr", "ari", "ati", "aug", "bel", "biz", "ctg", "dec", "dkg", "doc", "feb",
			"gif", "jan", "jpg", "júl", "jún", "kft", "khz", "lsd", "max", "mHz", "nov", "okt", "ovi", "pdf", "phd",
			"rgb", "sim", "sin", "sos", "stb", "tel", "tnt", "tsa", "tsz", "txt", "vki", "vmi", "zrt" };

	private static ArrayList<String> basicDictionary = new ArrayList<String>(Arrays.asList(basic));

	private static ArrayList<String> suffixedWordsDictionary = new ArrayList<String>(Arrays.asList(suffixedWords));
	private static ArrayList<String> suffixsDictionary = new ArrayList<String>(Arrays.asList(suffixs));
	private static ArrayList<String> namesDictionary = new ArrayList<String>(Arrays.asList(names));
	private static ArrayList<String> shortsDictionary = new ArrayList<String>(Arrays.asList(shorts));

	public Dictionary() {
		super();
	}

	public static ArrayList<String> getBasicDictionary() {
		return basicDictionary;
	}

	public static void setBasicDictionary(ArrayList<String> basicDictionary) {
		Dictionary.basicDictionary = basicDictionary;
	}

	public static ArrayList<String> getSuffixedWordsDictionary() {
		return suffixedWordsDictionary;
	}

	public static void setSuffixedWordsDictionary(ArrayList<String> suffixedWordsDictionary) {
		Dictionary.suffixedWordsDictionary = suffixedWordsDictionary;
	}

	public static ArrayList<String> getSuffixsDictionary() {
		return suffixsDictionary;
	}

	public static void setSuffixsDictionary(ArrayList<String> suffixsDictionary) {
		Dictionary.suffixsDictionary = suffixsDictionary;
	}

	public static ArrayList<String> getNamesDictionary() {
		return namesDictionary;
	}

	public static void setNamesDictionary(ArrayList<String> namesDictionary) {
		Dictionary.namesDictionary = namesDictionary;
	}

	public static ArrayList<String> getShortsDictionary() {
		return shortsDictionary;
	}

	public static void setShortsDictionary(ArrayList<String> shortsDictionary) {
		Dictionary.shortsDictionary = shortsDictionary;
	}

	@Override
	public String toString() {
		return "Dictionary [basicDictionary=" + basicDictionary + "]";
	}

}
