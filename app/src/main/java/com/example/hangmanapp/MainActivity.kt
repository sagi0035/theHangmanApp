package com.example.hangmanapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    // so this is to keep track of the amount of guesses made by the user
    var numberOfTries = 0;

    // now we will initialise all the Strings

    // the string for the hangman word to guess
    lateinit var wordToGuess: String
    // and the letter or word guessed by the guesser
    lateinit var letterOrWordGuess: String
    // and the string with all the user guesses
    var allTheUserGuessses: String = ""
    // and this is for the blanks of the hangmanword
    lateinit var blanksOfTheHangmanWord: String
    // we will also have a boolean for determining whether or not the game ended with a win
    lateinit var winOrLose: String;


    // now we will set the bases for each of the edit text's

    // first for the inputted word
    lateinit var editTextOne: EditText
    lateinit var editTextTwo: EditText
    // now we will set the buttton
    lateinit var button: Button

    // amd the text view for the blanks
    lateinit var textViewOfTheBlanks: TextView

    // now we set the texts of the guessing process
    lateinit var textViewOfGuesses: TextView
    lateinit var theHangmanPartOne: TextView
    lateinit var theHangmanPartTwo: TextView
    lateinit var theHangmanPartThree: TextView
    lateinit var theHangmanPartFour: TextView
    lateinit var theHangmanPartFive: TextView
    lateinit var theHangmanPartSix: TextView
    lateinit var theHangmanPartSeven: TextView
    lateinit var theHangmanPartEight: TextView


    // now we will se the text view's for when the game has been completed
    // the playagain and win/lose
    lateinit var thePlayAgainButton: Button
    lateinit var theFinishWithWin: TextView
    lateinit var theFinishWithLose: TextView




    // now we will set booleans for which user is playing - the inputter or the guesser (obviously first the inputter plays)
    var playerPlayingInputter = true
    var playerPlayingTheGuesser = false

    // so now we will create an array of all the correct letter guesses
    var arrayOfAllTheCorrectLetters: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // and here we initialise everything!
        editTextOne = findViewById<EditText>(R.id.hangmanText)
        editTextTwo= findViewById<EditText>(R.id.guesser)
        button = findViewById<Button>(R.id.theHangButton)
        thePlayAgainButton = findViewById<Button>(R.id.thePlayAgainButton)
        theFinishWithWin = findViewById<TextView>(R.id.winnerText)
        theFinishWithLose = findViewById<TextView>(R.id.loserText)
        textViewOfGuesses = findViewById<TextView>(R.id.allTheGuesses)
        textViewOfTheBlanks = findViewById<TextView>(R.id.theBlanks)
        theHangmanPartOne = findViewById<TextView>(R.id.textViewOne)
        theHangmanPartTwo = findViewById<TextView>(R.id.textViewTwo)
        theHangmanPartThree = findViewById<TextView>(R.id.textViewThree)
        theHangmanPartFour = findViewById<TextView>(R.id.textViewFour)
        theHangmanPartFive = findViewById<TextView>(R.id.textViewFive)
        theHangmanPartSix = findViewById<TextView>(R.id.textViewSix)
        theHangmanPartSeven = findViewById<TextView>(R.id.textViewSeven)
        theHangmanPartEight = findViewById<TextView>(R.id.textViewEight)


        // so first thing is that we will get the text from the edittext of the hangman text id
        editTextOne.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                // so here we will set the word to guess to that typed in by the user
                wordToGuess = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        // and now this is to rest everything
        thePlayAgainButton.setOnClickListener() {
            // so the entirety of the hangman must now be hiddn again!
            theHangmanPartOne.visibility = View.INVISIBLE
            theHangmanPartTwo.visibility = View.INVISIBLE
            theHangmanPartThree.visibility = View.INVISIBLE
            theHangmanPartFour.visibility = View.INVISIBLE
            theHangmanPartFive.visibility = View.INVISIBLE
            theHangmanPartSix.visibility = View.INVISIBLE
            theHangmanPartSeven.visibility = View.INVISIBLE
            theHangmanPartEight.visibility = View.INVISIBLE
            // now we also will make the list of incorrect guesses invisible
            textViewOfGuesses.visibility = View.INVISIBLE
            // we will also make the blanks invisible
            textViewOfTheBlanks.visibility = View.INVISIBLE

            // now we will make the button visible again
            button.visibility = View.VISIBLE
            editTextOne.visibility = View.VISIBLE
            // also make sure that we clear the text here
            editTextOne.text.clear()
            // and we will also set the string listing all the guesses to once again be empty to start guesses afresh
            allTheUserGuessses = ""
            // and we will make the play again button invisible
            thePlayAgainButton.visibility = View.INVISIBLE
            // and we will assure that both the textview's are also invisible
            theFinishWithLose.visibility = View.INVISIBLE
            theFinishWithWin.visibility = View.INVISIBLE

            // and now the player playing is obviously changed
            playerPlayingInputter = true
            playerPlayingTheGuesser = false

            // we will also re-set the number of tries to 0
            numberOfTries = 0
        }


        button.setOnClickListener() {
            // so if the inputter is playing we will set the basis for the guesser's input and change the player
            if (playerPlayingInputter) {
                Toast.makeText(applicationContext, "Your word is " + wordToGuess + "\nNow please change the user to the guesser!",
                    Toast.LENGTH_LONG
                ).show()
                // and we only allow the layout and game to come when the user actually placed in text with more than 3 letters
                // and that no space was placed in the beginnin
                if (wordToGuess.length > 3 && !wordToGuess[0].isWhitespace()) {
                    createGuesserLayout(wordToGuess)
                    playerPlayingInputter = false
                    playerPlayingTheGuesser = true
                    // now we hide the keyboard
                    hideKeyboard()
                } else {
                    Toast.makeText(applicationContext, "Error\nPlease note that the first letter must not be blank " +
                            "and that the word needs to be more than 3 letters long!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else if (playerPlayingTheGuesser) {
                // so now if the guesser is playing we will check the guesses
                checkTheGuess()
            }

        }

    }

    // this is a function to hide the keyboard
    open fun hideKeyboard(): Unit {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun checkTheGuess() {
        // so firstly we will of course get the guess
        letterOrWordGuess = editTextTwo.text.toString()

        // now first we will check if the user just guessed a letter or a whole word which is required to win
        // if it is a whole word guess it has to perfectly match the hangman word
        // we will also make things easier for the user by assurring that no whitespace is in the beginning
        // and that the length of the guess matches the length of the hangman word
        if (letterOrWordGuess.length > 1 && !letterOrWordGuess[0].isWhitespace() && letterOrWordGuess.length == wordToGuess.length) {
            // so if the words are not equal that means the guess was wrong and we will go about
            // building the hangman and listing the word as an incorrect guess
            if (!letterOrWordGuess.equals(wordToGuess)) {
                // and we will show the incorrect guesses in the edit text with a string
                allTheUserGuessses+=letterOrWordGuess+"\n"
                textViewOfGuesses.setText(allTheUserGuessses)
                numberOfTries+=1
                buildTheHangman()
                hideKeyboard()
            } else if (letterOrWordGuess.equals(wordToGuess)) {
                // so we will set the tring to it being a winner
                winOrLose = "win"
                hideKeyboard()
                // if on the other hand they are equal we will conclude the game for the winner
                concludeGame()
            }
        }
        // now this will be if just a letter is guessed which we make sure is not a whitespace
        else if(letterOrWordGuess.length==1 && !letterOrWordGuess.isNullOrBlank()) {
            if (!wordToGuess.contains(letterOrWordGuess)) {
                // and we will show the incorrect guesses in the edit text with a string
                allTheUserGuessses+=letterOrWordGuess+"\n"
                textViewOfGuesses.setText(allTheUserGuessses)
                numberOfTries+=1
                hideKeyboard()
                // so if the letter guessed is incorrect we build the hangman
                buildTheHangman()
            } else {
                hideKeyboard()
                // so if the guess is correct we will amend the blanks to have the letter instead
                amendTheBlanks()
            }
        }

        // and by the end we will clear the text so user can input something new
        editTextTwo.text.clear()
    }


    fun concludeGame() {
        // so we will remove the edit text and the button
        editTextTwo.visibility = View.INVISIBLE
        button.visibility = View.INVISIBLE
        thePlayAgainButton.visibility = View.VISIBLE
        // so now if the string equals win we had a winner and if lose a loser
        // so which text we put depends on that
        if (winOrLose.equals("win")) {
            theFinishWithWin.visibility = View.VISIBLE
        } else if (winOrLose.equals("lose")) {
            theFinishWithLose.visibility = View.VISIBLE
        }
    }

    fun buildTheHangman() {
        // so depending on the number of incorrect tries we build the hangman
        if (numberOfTries == 1) {
            theHangmanPartOne.visibility = View.VISIBLE
        } else if (numberOfTries == 2) {
            theHangmanPartTwo.visibility = View.VISIBLE
        } else if (numberOfTries == 3) {
            theHangmanPartThree.visibility = View.VISIBLE
        } else if (numberOfTries == 4) {
            theHangmanPartFour.visibility = View.VISIBLE
        } else if (numberOfTries == 5) {
            theHangmanPartFive.visibility = View.VISIBLE
        } else if (numberOfTries == 6) {
            theHangmanPartSix.visibility = View.VISIBLE
        } else if (numberOfTries == 7) {
            theHangmanPartSeven.visibility = View.VISIBLE
        } else if (numberOfTries == 8) {
            theHangmanPartEight.visibility = View.VISIBLE
            // so if it is 8 we used up all our guesses and must conclude with a lose
            winOrLose = "lose"
            concludeGame()
        }
    }


    fun createGuesserLayout(theHangWord:String) {

        // so the first thing we will do is set the first edittext to invisible
        // and the second to visible
        editTextOne.visibility = View.INVISIBLE
        editTextTwo.visibility = View.VISIBLE

        // and we will also set the text view of the guesses
        textViewOfGuesses.visibility = View.VISIBLE
        // and set the text to be as per the string for the play again
        textViewOfGuesses.setText(allTheUserGuessses);



        // this is done through an iteration
        for (i in 0..theHangWord.length-1) {
            // so for each letter (not space) we create a blank
            if (i ==0 && !theHangWord[i].isWhitespace()) {
                // here we initialise because the first letter is definitely not a blank
                blanksOfTheHangmanWord = "__  "
            } else if (!theHangWord[i].isWhitespace()) {
                blanksOfTheHangmanWord+= "__  "
            } else {
                blanksOfTheHangmanWord+= "     "
            }


        }

        // now we will form the textview of the blanks as per how many letters are in word guessed
        textViewOfTheBlanks.setText(blanksOfTheHangmanWord)

        // now in the case were we are replaying the game the text view will be invisible
        // so we will make sure that it is not empty
        if (textViewOfTheBlanks.visibility == View.INVISIBLE) {
            textViewOfTheBlanks.visibility = View.VISIBLE
        }


    }

    fun amendTheBlanks() {
        // so the easiest thing to do in this regard is to just rest the blanks string entirely
        blanksOfTheHangmanWord=""
        // we do this by iterating through the word to guess and atching it with the correct letter
        for (letter in wordToGuess) {
            // now if the letter matches the user's guess we set the letter instead of the blank
            if (letter.toString().equals(letterOrWordGuess)) {
                blanksOfTheHangmanWord+= "$letter  "
                // and we add the letter to the array list
                arrayOfAllTheCorrectLetters.add(letter.toString())
            }
            // now if the array list contains the letter we also do do not place a blank
            else if (arrayOfAllTheCorrectLetters.contains(letter.toString())) {
                blanksOfTheHangmanWord+= "$letter  "
            }
            // once again if there is an empty space we will handle it
            else if (letter.isWhitespace()) {
                blanksOfTheHangmanWord+= "     "
            }
            // and for the words not guessed we will create blanks
            else {
                blanksOfTheHangmanWord+= "__  "
            }
        }

        // and now of course we will set the text as such
        textViewOfTheBlanks.setText(blanksOfTheHangmanWord)

        // and finally we will check if by the end there are any blanks
        // if not the entirety of the word was guessed and we have a winner
        if (!blanksOfTheHangmanWord.contains("__  ")) {
            winOrLose = "win"
            concludeGame()
        }

    }

}