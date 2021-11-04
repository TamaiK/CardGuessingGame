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

        public final int number;
        public final String Name;
        
        private Suit(final int num, final String name){
            this.number = num;
            this.Name = name;
        }

        public static Suit first(){
            return HRART;
        }

        public static Suit last(){
            return CLUB;
        }

        public static Suit getSuitFromNumber(int num){

            for (Suit suit : Suit.values()) {
                
                if(suit.number == num){

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

    public enum CheckResult{
        CORRECT,
        GREATER_THAN_CORRECT,
        SMALLER_THAN_CORRECT,
    }

    private static final int LIMIT_CHALLENGE_SUIT = 2;
    private static final int LIMIT_CHALLENGE_RANK = 4;

    private static final String MESSAGE_FOR_BLANK = "";
    private static final String MESSAGE_FOR_CHOCE_CARD = "トランプを選んだよ";

    private static final String MESSAGE_FOR_QUESTION_SUIT = "トランプの図柄を当ててね";
    private static final String MESSAGE_FORMAT_FOR_LIMIT_TIME = "答えられるのは%d回までだよ";
    private static final String MESSAGE_FORMAT_FOR_SHOICES_SUIT = "%d:%s";

    private static final String MESSAGE_FOR_REQUIRE_ANSWER = "どれだと思う？：";
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_INPUT_IN_RANGE_NUMBER = "注意：答えは%d～%dの数字で入れてね";

    private static final String MESSAGE_FORMAT_FOR_CORRECT_SUIT = "正解！図柄は%sだよ";
    private static final String MESSAGE_FORMAT_FOR_INCORRECT = "残念！%sじゃないよ";
    private static final String MESSAGE_FORMAT_FOR_FORCE_CORRECT_SUIT = "正解の図柄は%sでした";

    private static final String MESSAGE_FOR_QUESTION_RANK = "次は数字を当ててね";
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_INPUT_WITHIN_RANK = "注意：答えは%sのどれかを入れてね";

    private static final String MESSAGE_FORMAT_FOR_CORRECT_CARD = "正解！%sの%sだよ";
    private static final String MESSAGE_FORMAT_FOR_FORCE_CORRECT_CARD = "正解は%sの%sでした";

    private static final String MESSAGE_FOR_HINT_SMALLER = "もっと小さい数字だよ";
    private static final String MESSAGE_FOR_HINT_GREATER = "もっと大きい数字だよ";

    static{
        init();
    }

    public static void main(String[] args) {
        
        int correctSuitNumber = createRandomNumber(Suit.first().number, Suit.last().number);
        Suit correctSuit = Suit.getSuitFromNumber(correctSuitNumber);

        int correctRankNumber = createRandomNumber(Rank.first().Number, Rank.last().Number);
        Rank correctRank = Rank.getRankFromNumber(correctRankNumber);

        dispchoseCard();
        guessingSuit(correctSuit);
        
        boolean isCorrect = guessingRank(correctRank);
        
        if(isCorrect){
            dispCorrectCard(correctSuit,correctRank);
        }
        else{
            dispForceCorrectCard(correctSuit,correctRank);
        }

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

    private static void dispchoseCard() {

        println(MESSAGE_FOR_CHOCE_CARD);
    }

    private static void dispCorrectCard(Suit correctSuit, Rank correctRank) {

        println(MESSAGE_FORMAT_FOR_CORRECT_CARD, correctSuit.Name, correctRank.Name);
    }

    private static void dispForceCorrectCard(App.Suit correctSuit, App.Rank correctRank) {

        println(MESSAGE_FORMAT_FOR_FORCE_CORRECT_CARD, correctSuit.Name, correctRank.Name);
    }



    private static void guessingSuit(Suit correctSuit) {
        
        dispQuestionSuit();

        int challengeCount = 0;
        do{
            challengeCount++;

            Suit answer = getAnswerSuit();

            if(isCorrectSuit(answer, correctSuit)){

                dispCorrectSuit(correctSuit);
                break;
            }

            dispincorrectSuit(answer);

            if(isLimitOverSuit(challengeCount)){
    
                dispForceCorrectSuit(correctSuit);
            }

        }while(isNextGuessingSuit(challengeCount));
    }

    private static void dispQuestionSuit() {
        
        println(MESSAGE_FOR_QUESTION_SUIT);
        println(MESSAGE_FORMAT_FOR_LIMIT_TIME, LIMIT_CHALLENGE_SUIT);

        for (Suit suit : Suit.values()) {
            
            println(MESSAGE_FORMAT_FOR_SHOICES_SUIT, suit.number, suit.Name);
        }
        
        println(MESSAGE_FOR_BLANK);
    }

    private static Suit getAnswerSuit() {

        int input = 0;

        do{
            try{
                dispRequireAnswer();
                
                input = scanner.nextInt();

                if(isInRange(input, Suit.first().number, Suit.last().number)){               
                    
                    break;
                }

                dispRequireInputInRangeNumber();
            }
            catch (InputMismatchException e){
                
                dispRequireInputInRangeNumber();
                scanner.next();
            }

        }while(isNeedNextInput());

        return Suit.getSuitFromNumber(input);
    }

    private static void dispRequireAnswer() {

        print(MESSAGE_FOR_REQUIRE_ANSWER);
    }

    private static void dispRequireInputInRangeNumber() {

        println(MESSAGE_FORMAT_FOR_REQUIRE_INPUT_IN_RANGE_NUMBER, Suit.first().number, Suit.last().number);
        println(MESSAGE_FOR_BLANK);
    }

    private static boolean isNeedNextInput() {
        return true;
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

    private static boolean isNextGuessingSuit(int challengeCount) {

        return challengeCount < LIMIT_CHALLENGE_SUIT;
    }

    private static boolean isLimitOverSuit(int challengeCount) {

        return challengeCount >= LIMIT_CHALLENGE_SUIT;
    }

    private static void dispForceCorrectSuit(App.Suit correctSuit) {

        println(MESSAGE_FORMAT_FOR_FORCE_CORRECT_SUIT, correctSuit.Name);
    }



    private static boolean guessingRank(Rank correctRank) {

        dispQuestionRank();

        int challengeCount = 0;
        do{
            challengeCount++;

            Rank answer = getAnswerRank();
            CheckResult result = checkAnswerRank(answer, correctRank);

            if(result == CheckResult.CORRECT){

                return true;
            }

            dispIncorrectRank(answer);
            dispHintCorrectRank(result);
            
        }while(isNextGuessingRank(challengeCount));
        
        return false;
    }

    private static void dispQuestionRank() {

        println(MESSAGE_FOR_QUESTION_RANK);
        println(MESSAGE_FORMAT_FOR_LIMIT_TIME, LIMIT_CHALLENGE_RANK);
        println(MESSAGE_FOR_BLANK);
    }

    private static CheckResult checkAnswerRank(Rank answer, Rank correctRank) {

        if(answer == correctRank){

            return CheckResult.CORRECT;
        }

        if(answer.Number > correctRank.Number){

            return CheckResult.GREATER_THAN_CORRECT;
        }
        
        return CheckResult.SMALLER_THAN_CORRECT;
    }

    private static Rank getAnswerRank() {
        
        String input = "A";

        do{
            dispRequireAnswer();
            
            input = scanner.next();

            if(Rank.isMatchFromName(input)){               
                
                break;
            }

            dispRequireInputWithinRank();
        
        }while(isNeedNextInput());

        return Rank.getRankFromName(input);
    }

    private static void dispRequireInputWithinRank() {

        println(MESSAGE_FORMAT_FOR_REQUIRE_INPUT_WITHIN_RANK, Rank.RANK_RANGE);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispIncorrectRank(Rank answer) {

        println(MESSAGE_FORMAT_FOR_INCORRECT, answer.Name);
    }

    private static boolean isNextGuessingRank() {
        return true;
    }

    private static void dispHintCorrectRank(App.CheckResult result) {

        if(result == CheckResult.GREATER_THAN_CORRECT){

            dispHintCorrectSmaller();
            return;
        }
        
        dispHintCorrectGreater();
    }

    private static void dispHintCorrectSmaller() {

        println(MESSAGE_FOR_HINT_SMALLER);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispHintCorrectGreater() {

        println(MESSAGE_FOR_HINT_GREATER);
        println(MESSAGE_FOR_BLANK);
    }

    private static boolean isNextGuessingRank(int challengeCount) {

        return challengeCount < LIMIT_CHALLENGE_RANK;
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
