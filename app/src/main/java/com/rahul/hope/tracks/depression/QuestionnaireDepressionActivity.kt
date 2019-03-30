package com.rahul.hope.tracks.depression

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.rahul.hope.R
import com.rahul.hope.fragments.QuestionFragment
import kotlinx.android.synthetic.main.activity_questionnaire_depression.*

class QuestionnaireDepressionActivity : AppCompatActivity() {

    var currentQuestion: Int = 0
    lateinit var scores: ArrayList<Int>
    var questionKey: String = "question"
    lateinit var depressionViewPager: ViewPager

    private var mCurrentUser: FirebaseUser? = null
    private lateinit var mFirebaseDb: FirebaseDatabase

    val questions = arrayOf(
        "Little interest or pleasure in doing things",
        "Feeling down, depressed, or hopeless",
        "Trouble falling or staying asleep, or sleeping too much",
        "Feeling tired or having little energy",
        "Poor appetite or overeating",
        "Feeling bad about yourself—or that you are a failure or have let yourself or your family down",
        "Trouble concentrating on things, such as reading the newspaper or watching television",
        "Moving or speaking so slowly that other people could have noticed? Or the opposite—being " +
                "so fidgety or restless that you have been moving around a lot more than usual",
        "Thoughts that you would be better off dead or of hurting yourself in some way"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rahul.hope.R.layout.activity_questionnaire_depression)
        depressionViewPager = findViewById(R.id.depressionPager)

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        mFirebaseDb = FirebaseDatabase.getInstance()
        scores = ArrayList(questions.size)

        depressionPager.adapter = DepressionPagerAdapter(supportFragmentManager)
        depressionPager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        responseSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedValueText.text = when (progress) {
                    0 -> "Not at all"
                    1 -> "Several Days"
                    2 -> "More than half the days"
                    3 -> "Nearly every day"
                    else -> "Never"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        nextButton.setOnClickListener {
            if (++currentQuestion < questions.size) {
                depressionViewPager.currentItem = currentQuestion
                scores.add(responseSeekBar.progress)
                responseSeekBar.progress = 0
            } else if (currentQuestion == 10) {
                val depressionScoresRef = mFirebaseDb.getReference("users")
                depressionScoresRef.child(mCurrentUser!!.uid).child("depressionScores")
                    .child(System.currentTimeMillis().toString()).setValue(getUserAnxietyScore())
                finish()
            }
            if (depressionViewPager.currentItem + 1 == questions.size) {
                nextButton.text = "Done"
            }
        }
    }

    private fun getUserAnxietyScore(): Int {
        var sum = 0
        scores.forEach {
            sum += it
        }
        return sum
    }

    inner class DepressionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val questionFragment: Fragment = QuestionFragment()
            val bundle = Bundle()
            bundle.putString(questionKey, questions[position])
            questionFragment.arguments = bundle
            return questionFragment
        }

        override fun getCount(): Int {
            return questions.size
        }
    }
}
