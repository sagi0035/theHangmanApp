package com.example.hangmanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    // so this is to keep track of the amount of guesses made by the user
    var numberOfTries = 0;

    // now we will initialise all the Strings

    // the string for the hangman word to guess
    lateinit var wordToGuess: String
    // and the letter or word guessed by the guesser
    lateinit var letterOrWordGuess: String
    // and the string with all the user guesses
    lateinit var allTheUserGuessses: String
    // and this is for the blanks of the hangmanword
    lateinit var blanksOfTheHangmanWord: String


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

    // now we will set booleans for which user is playing - the inputter or the guesser (obviously first the inputter plays)
    var playerPlayingInputter = true
    var playerPlayingTheGuesser = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // and here we initialise everything!
        editTextOne = findViewById<EditText>(R.id.hangmanText)
        editTextTwo= findViewById<EditText>(R.id.guesser)
        button = findViewById<Button>(R.id.theHangButton)
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
        }
    }


    fun createGuesserLayout(theHangWord:String) {

        // so the first thing we will do is set the first edittext to invisible
        // and the second to visible
        editTextOne.visibility = View.INVISIBLE
        editTextTwo.visibility = View.VISIBLE

        // and we will also set the textview of the guesses
        textViewOfGuesses.visibility = View.VISIBLE


        // this is done through an iteration
        for (i in 0..theHangWord.length-1) {



            // so for each letter (not space) we create a blank
            if (i ==0 && !theHangWord[i].isWhitespace()) {
                // here we initialise because the first letter is definitely not a blank
                blanksOfTheHangmanWord = "__  "
            } else if (!theHangWord[i].isWhitespace()) {
                blanksOfTheHangmanWord+= "__  "
            } else {
                Toast.makeText(applicationContext,"abcdef",Toast.LENGTH_LONG)
                blanksOfTheHangmanWord+= "     "
            }


        }

        // now we will form the textview of the blanks as per how many letters are in word guessed
        textViewOfTheBlanks.setText(blanksOfTheHangmanWord)


    }
}