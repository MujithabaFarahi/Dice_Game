/*The below strategy explains on how the computer player re rolls some of it dice and how it decides on
 which ones to re roll. It is assumed that the computer player cannot see the current dice selections of the human player
 but its aware of the current total of the current game for itself and human player. As the computer player knows the
 current total score of itself and the human, it would compare them in each round according to the condition
 If (total Score of Human> total Score of Computer) then it would throw only 5 and 6 for next roll of computer.
 Even if computer total is higher than human total computer dice outcomes won’t be less than 3. */

/*According to the coursework all 5 dice should be rolled using a random strategy, this is unfair for the computer because
the human can select the specific dice and stop the re roll of that specific dice this is not possible to the computer
because it cannot decide which dice/s to stop so a strategy was implemented specifically for the computer*/

/*Advantages
* 1. Compared to the random strategy this strategy is more competitive and interesting for the human.
* 2. Fair play: As the computer player cannot see the current dice selections of the human player,
* the strategy ensures fair play as the computer player is not making decisions based on the human
* player's current dice selection.
* 3. Maximizes the score: The computer player is not only trying to win the game but also trying to maximize
* its score. By re-rolling its dices on higher values, the computer player can potentially get higher scores
* in subsequent rounds.

* Disadvantage
1.  In the first roll the human can select which dices to re roll but the computer cannot select which dices to
*   re roll according to this strategy
2.  Risk of Losing Points: Re-rolling all dices with could lead to the computer player losing points in the
*   current round, as the re-rolled dice may end up with lower values than the previous roll. This could result
*   in the human player gaining an advantage.*/

package com.example.cw1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity3 : AppCompatActivity() {

    private lateinit var throwButton:Button
    private lateinit var scoreButton:Button

    private lateinit var humanDiceImages: Array<ImageView> //Array to save dice Images
    private lateinit var computerDiceImages: Array<ImageView>

    private lateinit var compScore: TextView
    private lateinit var humanScore: TextView
    private lateinit var winLoseCount: TextView

    private var hDiceValues = mutableListOf<Int>() //list to store the values of dice outcome
    private var cDiceValues = mutableListOf<Int>()

    private var reRollValuesH = mutableListOf<Int>() //list to save the dices that don't wat to reroll
    private var reRollValuesC = mutableListOf<Int>()

    private var pickedBoolean = listOf<Boolean>(true, false)

    private var rollCount = 0

    private var targett = 0

    private var hard: Boolean = true

    private var totalSumOfC = 0
    private var totalSumOfH = 0

    private var sumOfC = 0  //total sum fo player dice outcomes
    private var sumOfH = 0  //total sum fo computer dice outcomes

    var winCount = 0
    var loseCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        throwButton = findViewById(R.id.rollButton)
        scoreButton = findViewById(R.id.scoreButton)
        compScore = findViewById(R.id.compScore)
        humanScore = findViewById(R.id.humanScore)
        winLoseCount = findViewById(R.id.winLoseCount)


        val bundle = intent.extras
        targett = bundle?.getInt("target")!!
        hard = bundle.getBoolean("hardMode")

        humanDiceImages = arrayOf(
            findViewById(R.id.hDice1),
            findViewById(R.id.hDice2),
            findViewById(R.id.hDice3),
            findViewById(R.id.hDice4),
            findViewById(R.id.hDice5)
        )
        //Dice pics stored in array
        computerDiceImages = arrayOf(
            findViewById(R.id.cDice1),
            findViewById(R.id.cDice2),
            findViewById(R.id.cDice3),
            findViewById(R.id.cDice4),
            findViewById(R.id.cDice5)
        )

        scoreButton.setOnClickListener() {

            if (rollCount != 0) {
                if (hard) {
                    computerStratergy()
                }
                else {
                    randomStratergy()
                }

                for (x in hDiceValues) {
                    sumOfH += x
                }
                totalSumOfH += sumOfH
                humanScore.setText("$totalSumOfH")


                for (j in cDiceValues) {
                    sumOfC += j
                }
                totalSumOfC += sumOfC
                compScore.setText("$totalSumOfC")

                winnerSelect(totalSumOfC, totalSumOfH)

                cDiceValues.clear()
                hDiceValues.clear()
                reRollValuesH.clear()
                reRollValuesC.clear()
                rollCount = 0             //clearing all for next set of roll
            }
            else{
                Toast.makeText(applicationContext,"Click Throw button before Score",Toast.LENGTH_SHORT).show()
            }
        }




        throwButton.setOnClickListener { //throw button event
            hDiceValues.clear()
            sumOfC = 0
            sumOfH = 0

            for (i in 0 until 5) {  //diceroll loop for 5 dices
                if (i !in reRollValuesH) {
                    val randomIntH = (1..6).random()        //6 dice faces
                    val drawableResource = when (randomIntH) {
                        1 -> R.drawable.dice_one
                        2 -> R.drawable.dice_two
                        3 -> R.drawable.dice_three
                        4 -> R.drawable.dice_four
                        5 -> R.drawable.dice_five
                        else -> R.drawable.dice_six
                    }
                    humanDiceImages[i].setImageResource(drawableResource)
                    humanDiceImages[i].tag = randomIntH
                    hDiceValues.add(randomIntH)
                } else {
                    hDiceValues.add(Integer.parseInt(humanDiceImages[i].tag.toString()))
                }
            }

            if (rollCount == 0) {
                for (i in 0 until 5) {
                    val randomIntC = (1..6).random()

                    val drawableResource = when (randomIntC) {
                        1 -> R.drawable.dice_one1
                        2 -> R.drawable.dice_two2
                        3 -> R.drawable.dice_three3
                        4 -> R.drawable.dice_four4
                        5 -> R.drawable.dice_five5
                        else -> R.drawable.dice_six6
                    }
                    computerDiceImages[i].setImageResource(drawableResource)
                    cDiceValues.add(randomIntC)
                }
            }
            rollCount++

            if (hard) {
                computerStratergy()
            }
            else {
                randomStratergy()
            }

            if (rollCount == 3) {

                for (i in hDiceValues) {
                    sumOfH += i
                }
                totalSumOfH += sumOfH
                humanScore.text = "$totalSumOfH"
                for (j in cDiceValues) {
                    sumOfC += j
                }
                totalSumOfC += sumOfC
                compScore.text = "$totalSumOfC"

                winnerSelect(totalSumOfC, totalSumOfH)
                rollCount = 0
                reRollValuesH.clear()
                cDiceValues.clear()


            }
        }

        humanDiceImages[0].setOnClickListener() {
            if (rollCount != 0) {
                reRollValuesH.add(0)
            }
        }
        humanDiceImages[1].setOnClickListener() {
            if (rollCount != 0) {
                reRollValuesH.add(1)
            }
        }
        humanDiceImages[2].setOnClickListener() {
            if (rollCount != 0) {
                reRollValuesH.add(2)
            }
        }
        humanDiceImages[3].setOnClickListener() {
            if (rollCount != 0) {
                reRollValuesH.add(3)
            }
        }
        humanDiceImages[4].setOnClickListener() {
            if (rollCount != 0) {
                reRollValuesH.add(4)
            }
        }
    }

    fun winnerSelect(finalSumOfC: Int, finalSumOfH: Int) {
        if (finalSumOfC >= targett || finalSumOfH >= targett) {
            if (finalSumOfC > finalSumOfH) {
                winPopUp(finalSumOfC, finalSumOfH)
            } else if (finalSumOfH > finalSumOfC) {
                winPopUp(finalSumOfC, finalSumOfH)
            } else {
                if (finalSumOfC == finalSumOfH) {
                    winPopUp(sumOfC, sumOfH)
                }
            }
        }
    }

    fun winPopUp(finalSumOfC: Int, finalSumOfH: Int) {

        throwButton.isEnabled = false
        scoreButton.isEnabled = false

        val popUpView = layoutInflater.inflate(R.layout.win_lose_popup, null)
        val popupWindow = PopupWindow(
            popUpView, ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true
        )

        val winOkButton = popUpView.findViewById<Button>(R.id.winOkButton)
        winOkButton.setOnClickListener() {
            popupWindow.dismiss()

            throwButton.isEnabled = true
            scoreButton.isEnabled = true

            totalSumOfC = 0
            totalSumOfH = 0

            humanScore.text = "$totalSumOfH"
            compScore.text = "$totalSumOfC"
        }

        popupWindow.showAtLocation(winOkButton, Gravity.CENTER, 0, 20)

        val textWinLose = popUpView.findViewById<TextView>(R.id.winPopUp)
        if (finalSumOfC < finalSumOfH) {
            val win = "\n\n"+"YOU WIN!"
            textWinLose.text = win
            textWinLose.setTextColor(Color.GREEN)
            winCount++
        } else {
            val lose = "\n\n"+"YOU LOSE!"
            textWinLose.text = lose
            textWinLose.setTextColor(Color.RED)
            loseCount++
        }
        val winLose = "H : $winCount / C : $loseCount"
        winLoseCount.text = winLose
    }

    fun randomStratergy() {
        if (pickedBoolean.random()) {
            reRollValuesC.clear()
            sumOfC = 0
            val reRollDiceCount = Random.nextInt(1, 5)
            for (i in 1..reRollDiceCount) {
                val randomIntC = Random.nextInt(1, 5)
                if (randomIntC !in reRollValuesC) {
                    reRollValuesC.add(randomIntC)
                }
            }
            for (i in 1..5) {
                if (i in reRollValuesC) {
                    val randomIntC = Random.nextInt(1, 6)
                    val drawableResource = when (randomIntC) {
                        1 -> R.drawable.dice_one1
                        2 -> R.drawable.dice_two2
                        3 -> R.drawable.dice_three3
                        4 -> R.drawable.dice_four4
                        5 -> R.drawable.dice_five5
                        else -> R.drawable.dice_six6
                    }
                    cDiceValues[i - 1] = randomIntC
                    computerDiceImages[i - 1].setImageResource(drawableResource)
                }
            }
        }
    }

    fun computerStratergy() {
        if(totalSumOfH>totalSumOfC){
            reRollValuesC.clear()
            sumOfC = 0
            for (i in 1..5) {
                if (i !in reRollValuesC) {
                    reRollValuesC.add(i)
                }
            }
            for (i in 1..5) {
                if (i in reRollValuesC) {
                    val randomIntC = Random.nextInt(5, 7)
                    val drawableResource = when (randomIntC) {
                        5 -> R.drawable.dice_five5
                        else -> R.drawable.dice_six6
                    }
                    cDiceValues[i-1] = randomIntC
                    computerDiceImages[i-1].setImageResource(drawableResource)
                }
            }
        }
        else{
            reRollValuesC.clear()
            sumOfC = 0
            for (i in 1..5) {
                if (i !in reRollValuesC) {
                    reRollValuesC.add(i)
                }
            }
            for (i in 1..5) {
                if (i in reRollValuesC) {
                    val randomIntC = Random.nextInt(3, 7)
                    val drawableResource = when (randomIntC) {
                        3 -> R.drawable.dice_three3
                        4 -> R.drawable.dice_four4
                        5 -> R.drawable.dice_five5
                        else -> R.drawable.dice_six6
                    }
                    cDiceValues[i - 1] = randomIntC
                    computerDiceImages[i - 1].setImageResource(drawableResource)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("key1",rollCount)
        outState.putInt("key2",targett)
        outState.putInt("key3",sumOfC)
        outState.putInt("key4",sumOfH)
        outState.putInt("key5",winCount)
        outState.putInt("key6",loseCount)
        outState.putBoolean("key7",hard)
        outState.putIntegerArrayList("key8", ArrayList(hDiceValues))
        outState.putIntegerArrayList("key9", ArrayList(cDiceValues))
        outState.putIntegerArrayList("key10", ArrayList(reRollValuesC))
        outState.putIntegerArrayList("key11", ArrayList(reRollValuesH))
        outState.putInt("key12",totalSumOfH)
        outState.putInt("key13",totalSumOfC)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rollCount = savedInstanceState.getInt("key1")
        targett = savedInstanceState.getInt("key2")
        sumOfC = savedInstanceState.getInt("key3")
        sumOfH = savedInstanceState.getInt("key4")
        winCount = savedInstanceState.getInt("key5")
        loseCount = savedInstanceState.getInt("key6")
        hard = savedInstanceState.getBoolean("key7")
        hDiceValues = savedInstanceState.getIntegerArrayList("key8")!!
        cDiceValues = savedInstanceState.getIntegerArrayList("key9")!!
        reRollValuesH = savedInstanceState.getIntegerArrayList("key10")!!
        reRollValuesC = savedInstanceState.getIntegerArrayList("key11")!!
        totalSumOfH = savedInstanceState.getInt("key12")
        totalSumOfC = savedInstanceState.getInt("key13")


        humanScore.text = totalSumOfH.toString()
        compScore.text = totalSumOfC.toString()

        val winLoseText =  "H: $winCount / C:$loseCount"
        winLoseCount.text = winLoseText

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }//        https://www.geeksforgeeks.org/how-to-add-and-customize-back-button-of-action-bar-in-android/
}