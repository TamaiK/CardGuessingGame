import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class App {

    private static Random random;
    private static Scanner scanner;

    public enum Suit{
        HRART(1, "ハート"),
        DIAMOND(2, "ダイヤ"),
        SPADE(3, "スペード"),
        CLUB(4, "クローバー");

        public final int Number;
        public final String Name;
        
        private Suit(final int num, final String name){
            Number = num;
            Name = name;
        }

        public static Suit first(){
            return HRART;
        }

        public static Suit last(){
            return CLUB;
        }

        public static Suit getSuitFromNumber(int num){

            for (Suit suit : Suit.values()) {
                
                if(suit.Number == num){

                    return suit;
                }
            }

            return null;
        }
    }

    public enum Rank{
        ACE(1, "A"),
        DEUCE(2, "2"),
        TREY(3, "3"),
        CATER(4, "4"),
        CINQUE(5, "5"),
        SICE(6, "6"),
        SEVEN(7, "7"),
        EIGHT(8, "8"),
        NINE(9, "9"),
        TEN(10, "10"),
        JACK(11, "J"),
        QUEEN(12, "Q"),
        KING(13, "K");

        public final int Number;
        public final String Name;
        
        public static final String RANK_RANGE = "2～10、A、J、Q、K";
        
        private Rank(final int num, final String name){
            Number = num;
            Name = name;
        }

        public static Rank first(){
            return ACE;
        }

        public static Rank last(){
            return KING;
        }

        public static Rank getRankFromNumber(int num){

            for (Rank rank : Rank.values()) {
                
                if(rank.Number == num){

                    return rank;
                }
            }

            return null;
        }

        public static boolean isMatchFromName(String name){

            for (Rank rank : Rank.values()) {
                
                if(rank.Name.equals(name)){

                    return true;
                }
            }

            return false;
        }

        public static Rank getRankFromName(String name){

            for (Rank rank : Rank.values()) {
                
                if(rank.Name.equals(name)){

                    return rank;
                }
            }

            return null;
        }
    }

    private static final String MESSAGE_FOR_BLANK = "";
    private static final String MESSAGE_FOR_PREFACE = "トランプを選んだよ";

    private static final String MESSAGE_FOR_QUESTION_SUIT = "トランプの図柄を当ててね";
    private static final String MESSAGE_FORMAT_FOR_SHOICES_SUIT = "%d:%s";

    private static final String MESSAGE_FOR_REQUIRE_ANSWER = "どれだと思う？：";
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_INPUT_IN_RANGE_NUMBER = "注意：答えは%d～%dの数字で入れてね";

    private static final String MESSAGE_FORMAT_FOR_CORRECT_SUIT = "正解！図柄は%sだよ";
    private static final String MESSAGE_FORMAT_FOR_INCORRECT = "残念！%sじゃないよ";

    private static final String MESSAGE_FOR_QUESTION_RANK = "次は数字を当ててね";
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_INPUT_WITHIN_RANK = "注意：答えは%sのどれかを入れてね";

    private static final String MESSAGE_FORMAT_FOR_CORRECT_CARD = "正解！%sの%sだよ";

    static{
        init();
    }

    public static void main(String[] args) throws Exception {
        
        int correctSuitNumber = createRandomNumber(Suit.first().Number, Suit.last().Number);
        Suit correctSuit = Suit.getSuitFromNumber(correctSuitNumber);

        int correctRankNumber = createRandomNumber(Rank.first().Number, Rank.last().Number);
        Rank correctRank = Rank.getRankFromNumber(correctRankNumber);

        dispPreface();
        guessingSuit(correctSuit);
        guessingRank(correctRank);
        dispCorrectCard(correctSuit,correctRank);
        fin();
    }

    private static void init() {

        random = new Random();
        scanner = new Scanner(System.in);
    }

    private static void fin() {
        scanner.close();
    }

    private static int createRandomNumber(int min, int max) {

        int range = max - min + 1;
        return random.nextInt(range) + min;
    }

    private static void dispPreface() {

        println(MESSAGE_FOR_PREFACE);
    }

    private static void dispCorrectCard(Suit correctSuit, Rank correctRank) {

        println(MESSAGE_FORMAT_FOR_CORRECT_CARD, correctSuit.Name, correctRank.Name);
    }



    private static void guessingSuit(Suit correctSuit) {
        
        dispQuestionSuit();

        while(true){
            Suit answer = getAnswerSuit();

            if(isCorrectSuit(answer, correctSuit)){

                dispCorrectSuit(correctSuit);
                break;
            }

            dispincorrectSuit(answer);
        }
    }

    private static void dispQuestionSuit() {
        
        println(MESSAGE_FOR_QUESTION_SUIT);

        for (Suit suit : Suit.values()) {
            
            println(MESSAGE_FORMAT_FOR_SHOICES_SUIT, suit.Number, suit.Name);
        }

        println(MESSAGE_FOR_BLANK);
    }

    private static Suit getAnswerSuit() {

        int input = 0;

        while(true){

            try{

                dispRequireAnswer();
                
                input = scanner.nextInt();

                if(isInRange(input, Suit.first().Number, Suit.last().Number)){               
                    
                    break;
                }

                dispRequireInputInRangeNumber();
            }
            catch (InputMismatchException e){
                
                dispRequireInputInRangeNumber();
                scanner.next();
            }
        }

        return Suit.getSuitFromNumber(input);
    }

    private static void dispRequireAnswer() {

        print(MESSAGE_FOR_REQUIRE_ANSWER);
    }

    private static void dispRequireInputInRangeNumber() {

        println(MESSAGE_FORMAT_FOR_REQUIRE_INPUT_IN_RANGE_NUMBER, Suit.first().Number, Suit.last().Number);
        println(MESSAGE_FOR_BLANK);
    }

    private static boolean isInRange(int targetNum, int minValue, int maxValue) {

        return targetNum >= minValue && targetNum <= maxValue;
    }

    private static boolean isCorrectSuit(Suit answer, Suit correctSuit) {
        return answer == correctSuit;
    }

    private static void dispCorrectSuit(Suit correctSuit) {

        println(MESSAGE_FORMAT_FOR_CORRECT_SUIT, correctSuit.Name);
    }

    private static void dispincorrectSuit(Suit answer) {

        println(MESSAGE_FORMAT_FOR_INCORRECT, answer.Name);
        println(MESSAGE_FOR_BLANK);
    }



    private static void guessingRank(Rank correctRank) {

        dispQuestionRank();

        while(true){
            Rank answer = getAnswerRank();

            if(isCorrectRank(answer, correctRank)){

                break;
            }

            dispincorrectRank(answer);
        }
    }

    private static void dispQuestionRank() {

        println(MESSAGE_FOR_QUESTION_RANK);
        println(MESSAGE_FOR_BLANK);
    }

    private static Rank getAnswerRank() {
        
        String input = "A";

        while(true){

            dispRequireAnswer();
            
            input = scanner.next();

            if(Rank.isMatchFromName(input)){               
                
                break;
            }

            dispRequireInputWithinRank();
        }

        return Rank.getRankFromName(input);
    }

    private static void dispRequireInputWithinRank() {

        println(MESSAGE_FORMAT_FOR_REQUIRE_INPUT_WITHIN_RANK, Rank.RANK_RANGE);
        println(MESSAGE_FOR_BLANK);
    }

    private static boolean isCorrectRank(Rank answer, Rank correctRank) {
        return answer == correctRank;
    }

    private static void dispincorrectRank(Rank answer) {

        println(MESSAGE_FORMAT_FOR_INCORRECT, answer.Name);
        println(MESSAGE_FOR_BLANK);
    }



    private static void print(String str){
        System.out.print(str);
    }

    private static void println(String str){
        System.out.println(str);
    }

    private static void println(String str, Object... args){
        System.out.println(String.format(str, args));
    }
}
